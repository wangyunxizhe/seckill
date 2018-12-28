package com.yuan.exception;

/**
 * Created by wangy on 2018/9/5.
 * 所有秒杀相关业务异常
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
