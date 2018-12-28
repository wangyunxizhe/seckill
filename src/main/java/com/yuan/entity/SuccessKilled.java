package com.yuan.entity;

import java.util.Date;

/**
 * Created by wangy on 2018/9/5.
 * 秒杀成功明细表
 */
public class SuccessKilled {

    private long seckillId;
    private long useerPhone;
    private short state;
    private Date createTime;
    //多对一引用
    private Seckill seckill;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUseerPhone() {
        return useerPhone;
    }

    public void setUseerPhone(long useerPhone) {
        this.useerPhone = useerPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", useerPhone=" + useerPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
