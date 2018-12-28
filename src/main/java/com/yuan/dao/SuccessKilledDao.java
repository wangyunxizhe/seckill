package com.yuan.dao;

import com.yuan.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by wangy on 2018/9/5.
 */
public interface SuccessKilledDao {

    //插入秒杀成功明细表，因为是联合唯一主键，所以可过滤重复，避免一个号码多次秒杀商品
    //返回值为新增的行数
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    //查询成功表所有属性（包括多对一关系实体）
    SuccessKilled queryById(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
