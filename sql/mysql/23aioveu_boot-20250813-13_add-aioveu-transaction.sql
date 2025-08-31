

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
                                      `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '交易ID（主键）',
                                      `transaction_no` VARCHAR(50) NOT NULL COMMENT '交易编号（唯一）',
                                      `customer_id` BIGINT UNSIGNED NOT NULL COMMENT '客户ID',
                                      `contact_id` BIGINT UNSIGNED COMMENT '联系人ID',
                                      `transaction_date` DATETIME NOT NULL COMMENT '交易日期',
                                      `amount` DECIMAL(15,2) NOT NULL COMMENT '交易金额',
                                      `currency` VARCHAR(3) DEFAULT 'CNY' COMMENT '货币代码',
                                      `payment_method` TINYINT UNSIGNED NOT NULL COMMENT '支付方式：1-现金，2-银行转账，3-信用卡，4-支付宝，5-微信支付，6-其他',
                                      `payment_status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '支付状态：1-待支付，2-部分支付，3-已支付，4-已退款',
                                      `transaction_type` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '交易类型：1-销售，2-服务，3-退款，4-其他',
                                      `invoice_no` VARCHAR(50) COMMENT '发票号码',
                                      `product_id` BIGINT UNSIGNED COMMENT '产品ID',
                                      `quantity` INT UNSIGNED COMMENT '数量',
                                      `unit_price` DECIMAL(12,2) COMMENT '单价',
                                      `discount` DECIMAL(5,2) DEFAULT 0 COMMENT '折扣率',
                                      `tax_rate` DECIMAL(5,2) DEFAULT 0 COMMENT '税率',
                                      `tax_amount` DECIMAL(12,2) COMMENT '税额',
                                      `total_amount` DECIMAL(15,2) COMMENT '总金额',
                                      `sales_rep_id` BIGINT UNSIGNED COMMENT '销售负责人ID',
                                      `transaction_status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '交易状态：1-进行中，2-已完成，3-已取消',
                                      `notes` TEXT COMMENT '备注',
                                      `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                      PRIMARY KEY (`id`),

    -- 唯一约束确保交易编号唯一
                                      UNIQUE INDEX `uniq_transaction_no` (`transaction_no`),

    -- 唯一约束确保发票号码唯一
                                      UNIQUE INDEX `uniq_invoice_no` (`invoice_no`),

    -- 外键约束（确保客户存在）
                                      CONSTRAINT `fk_transaction_customer`
                                          FOREIGN KEY (`customer_id`)
                                              REFERENCES `aioveu_customer`(`id`)
                                              ON DELETE RESTRICT
                                              ON UPDATE CASCADE,

    -- 外键约束（确保联系人存在）
                                      CONSTRAINT `fk_transaction_contact`
                                          FOREIGN KEY (`contact_id`)
                                              REFERENCES `aioveu_contact`(`id`)
                                              ON DELETE SET NULL
                                              ON UPDATE CASCADE,

    -- 外键约束（确保销售负责人存在）
                                      CONSTRAINT `fk_transaction_sales_rep`
                                          FOREIGN KEY (`sales_rep_id`)
                                              REFERENCES `aioveu_employee`(`employee_id`)
                                              ON DELETE SET NULL
                                              ON UPDATE CASCADE,

    -- 索引优化
                                      INDEX `idx_customer` (`customer_id`),
                                      INDEX `idx_transaction_date` (`transaction_date`),
                                      INDEX `idx_payment_status` (`payment_status`),
                                      INDEX `idx_transaction_type` (`transaction_type`),
                                      INDEX `idx_sales_rep` (`sales_rep_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户交易记录表';


-- 插入交易数据
INSERT INTO `aioveu_transaction` (
    transaction_no, customer_id, contact_id, transaction_date,
    amount, payment_method, payment_status, transaction_type,
    invoice_no, product_id, quantity, unit_price, discount,
    tax_rate, tax_amount, total_amount, sales_rep_id, transaction_status
) VALUES
      ('TRX-202306-001', 1, 1, '2023-06-15 10:30:00',
       50000.00, 2, 3, 1,
       'INV-202306-001', 101, 10, 5000.00, 0.00,
       0.13, 6500.00, 56500.00, 1, 2),

      ('TRX-202306-002', 2, 3, '2023-06-16 14:15:00',
       30000.00, 4, 3, 2,
       'INV-202306-002', 102, 1, 30000.00, 0.00,
       0.13, 3900.00, 33900.00, 2, 2),

      ('TRX-202306-003', 3, 5, '2023-06-17 09:45:00',
       150000.00, 2, 3, 1,
       'INV-202306-003', 103, 5, 30000.00, 0.05,
       0.13, 17550.00, 149550.00, 3, 2),

      ('TRX-202306-004', 4, 5, '2023-06-18 11:20:00',
       -5000.00, 2, 4, 3,
       'REF-202306-001', 101, 1, 5000.00, 0.00,
       0.13, 650.00, -5650.00, 4, 2);