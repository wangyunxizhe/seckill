package com.yuan.service;

import com.yuan.dto.Exposer;
import com.yuan.dto.SeckillExecution;
import com.yuan.entity.Seckill;
import com.yuan.exception.RepeatKillException;
import com.yuan.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by wangy on 2018/9/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckills = seckillService.getSeckillList();
        logger.info("seckills={}", seckills);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
        //Exposer{exposed=true, md5='d2b716a15a33266bf376ac94f3b77ab4', seckillId=1000, now=0, start=0, end=0}
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000;
        long phone = 13561768888L;
        String md5 = "d2b716a15a33266bf376ac94f3b77ab4";
        try {
            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}", execution);
        } catch (RepeatKillException re) {
            logger.error(re.getMessage(), re);
        } catch (SeckillCloseException ce) {
            logger.error(ce.getMessage(), ce);
        }
    }

    //将3，4两个方法结合起来测试
    @Test
    public void testThreeAndFourMethod() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone = 13561768888L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", execution);
            } catch (RepeatKillException re) {
                logger.error(re.getMessage(), re);
            } catch (SeckillCloseException ce) {
                logger.error(ce.getMessage(), ce);
            }
        } else {
            //秒杀未开启
            logger.warn("exposer={}", exposer);
        }
    }

    @Test
    public void executeSeckillByProcedure() {
        long id = 1002;
        long phone = 13561768888L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillByProcedure(id, phone, md5);
            logger.info(execution.getStateInfo());
        }

    }

}