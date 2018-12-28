package com.yuan.dao;

import com.yuan.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by wangy on 2018/9/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)//配置spring和junit整合
@ContextConfiguration({"classpath:spring/spring-dao.xml"})//告诉junit spring配置文件的位置
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1001L;
        long phone = 13506001122L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount=" + insertCount);
    }

    @Test
    public void queryById() throws Exception {
        long id = 1001L;
        long phone = 13506001122L;
        SuccessKilled successKilled = successKilledDao.queryById(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}