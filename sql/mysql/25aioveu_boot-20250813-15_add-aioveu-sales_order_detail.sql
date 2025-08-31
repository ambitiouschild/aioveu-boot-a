

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 订单管理模块(管理销售订单。) - 订单明细表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_sales_order_detail`;

CREATE TABLE `aioveu_sales_order_detail` (
                                             `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '明细ID（主键）',
                                             `order_id` BIGINT UNSIGNED NOT NULL COMMENT '订单ID',
                                             `material_id` BIGINT UNSIGNED NOT NULL COMMENT '物资ID',
                                             `quantity` DECIMAL(12,4) UNSIGNED NOT NULL COMMENT '数量',
                                             `unit_price` DECIMAL(12,4) UNSIGNED NOT NULL COMMENT '单价',
                                             `discount` DECIMAL(5,2) DEFAULT 0 COMMENT '折扣率',
                                             `tax_rate` DECIMAL(5,2) DEFAULT 0 COMMENT '税率',
                                             `subtotal` DECIMAL(15,4)  COMMENT '小计',
                                             `tax_amount` DECIMAL(15,4)  COMMENT '税额',
                                             `total_price` DECIMAL(15,4)  COMMENT '总金额',
                                             `batch_number` VARCHAR(50) COMMENT '批次号',
                                             `delivery_date` DATE COMMENT '要求交货日期',
                                             `warehouse_id` BIGINT UNSIGNED COMMENT '发货仓库ID',
                                             `status` TINYINT UNSIGNED DEFAULT 1 COMMENT '明细状态：1-待处理，2-备货中，3-已发货，4-已收货，5-已取消',
                                             `notes` VARCHAR(200) COMMENT '备注',
                                             `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                             PRIMARY KEY (`id`),

    -- 外键约束（确保订单存在）
                                             CONSTRAINT `fk_detail_order`
                                                 FOREIGN KEY (`order_id`)
                                                     REFERENCES `aioveu_sales_order`(`id`)
                                                     ON DELETE CASCADE
                                                     ON UPDATE CASCADE,

    -- 外键约束（确保物资存在）
                                             CONSTRAINT `fk_detail_material`
                                                 FOREIGN KEY (`material_id`)
                                                     REFERENCES `aioveu_material`(`id`)
                                                     ON DELETE RESTRICT
                                                     ON UPDATE CASCADE,

    -- 外键约束（确保仓库存在）
                                             CONSTRAINT `fk_detail_warehouse`
                                                 FOREIGN KEY (`warehouse_id`)
                                                     REFERENCES `aioveu_warehouse`(`id`)
                                                     ON DELETE SET NULL
                                                     ON UPDATE CASCADE,

    -- 索引优化
                                             INDEX `idx_order` (`order_id`),
                                             INDEX `idx_material` (`material_id`),
                                             INDEX `idx_warehouse` (`warehouse_id`),
                                             INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单明细表';

-- 插入订单明细数据
INSERT INTO `aioveu_sales_order_detail` (
    order_id, material_id, quantity, unit_price,
    discount, tax_rate, batch_number, delivery_date,
    warehouse_id, status
) VALUES
      -- 订单1的明细
      (1, 1, 100.0000, 25.9900, 0.05, 0.13, 'BATCH-2023-06-001', '2023-06-20', 1, 3),
      (1, 2, 50.0000, 15.5000, 0.10, 0.13, 'BATCH-2023-06-002', '2023-06-20', 1, 3),

      -- 订单2的明细
      (2, 3, 2000.0000, 12.5000, 0.00, 0.13, 'BATCH-2023-05-001', '2023-06-25', 2, 2),

      -- 订单3的明细
      (3, 4, 50.0000, 89.0000, 0.15, 0.13, 'BATCH-2023-07-001', '2023-07-05', 3, 1),

      -- 订单4的明细
      (4, 5, 10.0000, 199.0000, 0.00, 0.13, 'BATCH-2023-06-003', '2023-06-30', 1, 1);


# -- 移除生成列
# ALTER TABLE aioveu_sales_order_detail
#     DROP COLUMN subtotal,
#     DROP COLUMN tax_amount,
#     DROP COLUMN total_price;
#
# -- 添加普通列
# ALTER TABLE aioveu_sales_order_detail
#     ADD COLUMN subtotal DECIMAL(15,4) COMMENT '小计（应用层计算）',
#     ADD COLUMN tax_amount DECIMAL(15,4) COMMENT '税额（应用层计算）',
#     ADD COLUMN total_price DECIMAL(15,4) COMMENT '总金额（应用层计算）';