-- 执行秒杀的存储过程
-- 利用存储过程来优化的特点
-- 减少了事务行级锁持有的时间
DELIMITER $$ -- console 将 ; 号替换成为 $$

-- 开始定义存储过程
CREATE PROCEDURE 'test'.'execute_seckillPro'
  (in v_seckill_id BIGINT,in v_phone BIGINT,in v_kill_time TIMESTAMP,out r_result INT)
  BEGIN
    DECLARE insert_count INT DEFAULT 0; -- 声明变量insert_count记录插入总数
    START TRANSACTION ; -- 开启事务
    INSERT ignore INTO success_killed(seckill_id,user_phone,create_time) VALUES (v_seckill_id,v_phone,v_kill_time);
    SELECT row_count() INTO insert_count;
    -- row_count()函数返回上一条修改类型sql的影响行数：
    -- 返回0表示未修改数据（重复秒杀）；返回 >0 表示修改的行数；返回 <0 表示sql错误/未执行任何修改类型的sql
    IF (insert_count = 0) THEN
      ROLLBACK ;
      SET r_result = -1;
    ELSEIF (insert_count < 0) THEN
      ROLLBACK ;
      SET r_result = -2;
    ELSE -- 成功插入的秒杀成功记录表中，接着进行更新库存的操作
      UPDATE seckill SET number=number-1
      WHERE seckill_id = v_seckill_id AND start_time <= v_kill_time
      AND end_time >= v_kill_time AND number > 0;
      SELECT row_count() INTO insert_count; -- 记录上一条修改类型sql的影响行数，并赋值给变量insert_count
      IF (insert_count = 0) THEN -- 库存表无数据更新，表示秒杀结束
        ROLLBACK ;
        SET r_result = 0;
      ELSEIF (insert_count < 0) THEN
        ROLLBACK ;
        SET r_result = -2;
      ELSE
        COMMIT ;
        SET r_result = 1;
      END IF ;
    END IF ;
  END $$


-- mysql中执行上述存储过程的语法如下：
-- 1：将console换行符还原成为 ;
DELIMITER ;
SET @r_rs = -3; -- 定义变量并赋值
CALL execute_seckillPro(1003,13100880088,now(),@r_rs); -- 调用存储过程，并传递入参和出参
SELECT @r_rs; -- 获取结果
