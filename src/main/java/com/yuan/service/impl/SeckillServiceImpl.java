package com.yuan.service.impl;

import com.yuan.dao.SeckillDao;
import com.yuan.dao.SuccessKilledDao;
import com.yuan.dto.Exposer;
import com.yuan.dto.SeckillExecution;
import com.yuan.entity.Seckill;
import com.yuan.entity.SuccessKilled;
import com.yuan.enums.SeckillStatEnum;
import com.yuan.exception.RepeatKillException;
import com.yuan.exception.SeckillCloseException;
import com.yuan.exception.SeckillException;
import com.yuan.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangy on 2018/9/5.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    //md5盐值字符串，用于混淆MD5，随便打，越复杂越好
    private final String slat = "gsgfdghsdh413!*()&@#&!*&";

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        //第一种情况：根据id查不到秒杀商品
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();
        //第二种情况：秒杀时间不在规定时间内
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Override
    @Transactional
    /**
     * 使用注解控制事务的优点：
     * 1：开发团队达成一致约定，明确了哪个是事务方法
     * 2：保证事务方法的执行时间尽可能短，不要穿插其他网络操作，如HTTP/RPC请求
     * 3：不是所有方法都需要事务
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, SeckillCloseException, RepeatKillException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("秒杀的数据被重写了");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        Date killTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(seckillId, killTime);
            if (updateCount <= 0) {
                //库存表没有更新任何记录
                throw new SeckillCloseException("秒杀已经结束了");
            } else {
                //库存成功减少，记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("重复秒杀");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryById(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译期异常 转化位运行期异常
            throw new SeckillException("秒杀内部发生异常：" + e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            return new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);
        }
        Date killTime = new Date();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("seckillId", seckillId);
        param.put("phone", userPhone);
        param.put("killTime", killTime);
        param.put("rs", null);
        try {
            //存储过程执行完后，rs将被赋值
            seckillDao.killByProcedure(param);
            //获取rs
            int result = MapUtils.getInteger(param, "rs", -2);
            if (result == 1) {
                SuccessKilled sk = successKilledDao.queryById(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, sk);
            } else {
                return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
        }
    }
}
