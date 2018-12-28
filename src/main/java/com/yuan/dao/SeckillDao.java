package com.yuan.dao;

import com.yuan.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangy on 2018/9/5.
 */
public interface SeckillDao {

    //减库存
    //返回值为更新库存表的行数
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    //根据id查询库存表
    Seckill queryById(long seckillId);

    //根据偏移量查询秒杀商品列表
    //@Param是mybatis提供的注解，跟mapper.xml中使用的占位符绑定，如果形参与占位符名字保证一致也可以不用
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    //使用存储过程执行秒杀
    void killByProcedure(Map<String, Object> param);
}
