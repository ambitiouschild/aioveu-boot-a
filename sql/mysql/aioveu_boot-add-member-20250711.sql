

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 会员信息表
-- ----------------------------
DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
                          `id` int NOT NULL AUTO_INCREMENT COMMENT '会员编号',
                          `name` varchar(50)  DEFAULT NULL COMMENT '会员姓名',
                          `mobile` varchar(20) DEFAULT NULL COMMENT '会员手机号',
                          `gender` tinyint DEFAULT NULL COMMENT '性别',
                          `age` int DEFAULT NULL COMMENT '会员年龄',
                          `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                          `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                          `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除(1:已删除;0:未删除)',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='会员信息表';