

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 客户管理模块(管理客户信息，包括客户的基本信息、联系人、交易记录等。) - 客户表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_customer`;

CREATE TABLE `aioveu_customer` (
                                   customer_id INT PRIMARY KEY AUTO_INCREMENT,
                                   name VARCHAR(100) NOT NULL COMMENT '客户名称',
                                   address VARCHAR(200) COMMENT '客户地址',
                                   phone VARCHAR(20) COMMENT '客户电话',
                                   credit_rating TINYINT COMMENT '信用等级'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户表';