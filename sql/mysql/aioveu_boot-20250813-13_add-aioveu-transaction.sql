

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 客户管理模块(管理客户信息，包括客户的基本信息、联系人、交易记录等。) - 交易记录表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_ctransaction`;

CREATE TABLE `aioveu_transaction` (
                                      transaction_id INT PRIMARY KEY AUTO_INCREMENT,
                                      customer_id INT NOT NULL COMMENT '客户ID',
                                      amount DECIMAL(12, 2) COMMENT '交易金额',
                                      transaction_date DATETIME COMMENT '交易时间',
                                      payment_method VARCHAR(20) COMMENT '支付方式',
                                      note VARCHAR(200) COMMENT '备注',
                                      FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='交易记录表';