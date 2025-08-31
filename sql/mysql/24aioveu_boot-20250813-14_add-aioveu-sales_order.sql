

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 订单管理模块(管理销售订单。) - 订单表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_sales_order`;

CREATE TABLE `aioveu_sales_order` (
                                      `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID（主键）',
                                      `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号（唯一）',
                                      `customer_id` BIGINT UNSIGNED NOT NULL COMMENT '客户ID',
                                      `contact_id` BIGINT UNSIGNED COMMENT '联系人ID',
                                      `order_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
                                      `expected_delivery` DATE COMMENT '预计交货日期',
                                      `actual_delivery` DATE COMMENT '实际交货日期',
                                      `total_amount` DECIMAL(15,2) UNSIGNED NOT NULL COMMENT '订单总金额',
                                      `currency` VARCHAR(3) DEFAULT 'CNY' COMMENT '货币代码',
                                      `discount` DECIMAL(5,2) DEFAULT 0 COMMENT '整体折扣率',
                                      `tax_rate` DECIMAL(5,2) DEFAULT 0 COMMENT '税率',
                                      `tax_amount` DECIMAL(12,2) COMMENT '税额',
                                      `grand_total` DECIMAL(15,2) COMMENT '订单总额（含税）',
                                      `payment_terms` TINYINT UNSIGNED DEFAULT 1 COMMENT '付款条件：1-预付全款，2-货到付款，3-月结30天，4-月结60天，5-其他',
                                      `payment_status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '支付状态：1-未支付，2-部分支付，3-已支付',
                                      `order_status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '订单状态：1-待处理，2-已确认，3-生产中，4-已发货，5-已完成，6-已取消',
                                      `shipping_address` VARCHAR(200) COMMENT '收货地址',
                                      `shipping_method` TINYINT UNSIGNED COMMENT '运输方式：1-快递，2-物流，3-自提，4-其他',
                                      `tracking_no` VARCHAR(50) COMMENT '物流单号',
                                      `sales_rep_id` BIGINT UNSIGNED COMMENT '销售负责人ID',
                                      `operator_id` BIGINT UNSIGNED NOT NULL COMMENT '操作员ID',
                                      `notes` TEXT COMMENT '备注',
                                      `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                      PRIMARY KEY (`id`),

    -- 唯一约束确保订单编号唯一
                                      UNIQUE INDEX `uniq_order_no` (`order_no`),

    -- 外键约束（确保客户存在）
                                      CONSTRAINT `fk_order_customer`
                                          FOREIGN KEY (`customer_id`)
                                              REFERENCES `aioveu_customer`(`id`)
                                              ON DELETE RESTRICT
                                              ON UPDATE CASCADE,

    -- 外键约束（确保联系人存在）
                                      CONSTRAINT `fk_order_contact`
                                          FOREIGN KEY (`contact_id`)
                                              REFERENCES `aioveu_contact`(`id`)
                                              ON DELETE SET NULL
                                              ON UPDATE CASCADE,

    -- 外键约束（确保销售负责人存在）
                                      CONSTRAINT `fk_order_sales_rep`
                                          FOREIGN KEY (`sales_rep_id`)
                                              REFERENCES `aioveu_employee`(`employee_id`)
                                              ON DELETE SET NULL
                                              ON UPDATE CASCADE,

    -- 外键约束（确保操作员存在）
                                      CONSTRAINT `fk_order_operator`
                                          FOREIGN KEY (`operator_id`)
                                              REFERENCES `aioveu_employee`(`employee_id`)
                                              ON DELETE RESTRICT
                                              ON UPDATE CASCADE,

    -- 索引优化
                                      INDEX `idx_customer` (`customer_id`),
                                      INDEX `idx_order_date` (`order_date`),
                                      INDEX `idx_order_status` (`order_status`),
                                      INDEX `idx_payment_status` (`payment_status`),
                                      INDEX `idx_sales_rep` (`sales_rep_id`),
                                      INDEX `idx_tracking_no` (`tracking_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='销售订单表';

-- 插入订单数据
INSERT INTO `aioveu_sales_order` (
    order_no, customer_id, contact_id, order_date,
    expected_delivery, total_amount, discount,
    tax_rate, grand_total, payment_terms,
    payment_status, order_status, shipping_address,
    shipping_method, sales_rep_id, operator_id
) VALUES
      ('SO-202306-001', 1, 1, '2023-06-10 09:30:00',
       '2023-06-20', 50000.00, 0.05,
       0.13, 54825.00, 3,
       2, 2, '深圳市龙岗区坂田华为基地',
       1, 1, 1),

      ('SO-202306-002', 2, 3, '2023-06-11 14:15:00',
       '2023-06-25', 30000.00, 0.00,
       0.13, 33900.00, 1,
       3, 3, '深圳市南山区高新科技园',
       2, 2, 2),

      ('SO-202306-003', 3, 5, '2023-06-12 10:45:00',
       '2023-07-05', 150000.00, 0.10,
       0.13, 154050.00, 4,
       1, 1, '杭州市余杭区文一西路969号',
       3, 3, 3),

      ('SO-202306-004', 4, 5, '2023-06-13 11:20:00',
       '2023-06-30', 75000.00, 0.00,
       0.13, 84750.00, 2,
       1, 2, '北京市海淀区清河中街68号',
       1, 4, 4);