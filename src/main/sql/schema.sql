-- 数据库初始化脚本

-- 创建秒杀库存表
CREATE TABLE seckill(
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)engine=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';
-- 初始化数据
insert into seckill
(name,number,start_time,end_time)
values
('1000元秒杀iphone6',100,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
('5000元秒杀ipad2',200,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
('3000元秒杀小米4',300,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
('2000元秒杀红米note',400,'2015-11-01 00:00:00','2015-11-02 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证的相关信息
CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标示：-1：无效 0：成功 1：已收款 2：已发货',
  `create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone), /*联合主键*/
  KEY idx_create_time(create_time)
)engine=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
