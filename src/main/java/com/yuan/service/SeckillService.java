package com.yuan.service;

import com.yuan.dto.Exposer;
import com.yuan.dto.SeckillExecution;
import com.yuan.entity.Seckill;
import com.yuan.exception.RepeatKillException;
import com.yuan.exception.SeckillCloseException;
import com.yuan.exception.SeckillException;

import java.util.List;

/**
 * Created by wangy on 2018/9/5.
 */
public interface SeckillService {

    //查询所有的秒杀商品数据
    List<Seckill> getSeckillList();

    //查询单个秒杀记录
    Seckill getById(long seckillId);

    //秒杀开启时输出秒杀接口的url地址
    //秒杀未开启时输出系统时间和秒杀时间
    Exposer exportSeckillUrl(long seckillId);

    //执行秒杀操作
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, SeckillCloseException, RepeatKillException;

    //使用存储过程优化之后的执行秒杀操作
    SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5);

}
