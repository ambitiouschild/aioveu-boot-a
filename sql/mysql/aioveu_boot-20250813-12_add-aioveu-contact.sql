

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 客户管理模块(管理客户信息，包括客户的基本信息、联系人、交易记录等。) - 联系人表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_contact`;

CREATE TABLE `aioveu_contact` (
                                  contact_id INT PRIMARY KEY AUTO_INCREMENT,
                                  customer_id INT NOT NULL COMMENT '客户ID',
                                  name VARCHAR(50) COMMENT '联系人姓名',
                                  position VARCHAR(50) COMMENT '职位',
                                  phone VARCHAR(20) COMMENT '电话',
                                  email VARCHAR(50) COMMENT '邮箱',
                                  is_primary TINYINT(1) DEFAULT 0 COMMENT '是否是主要联系人：0-否，1-是',
                                  FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='联系人表';