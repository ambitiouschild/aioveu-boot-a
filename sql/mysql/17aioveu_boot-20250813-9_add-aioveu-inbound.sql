

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 物资管理模块(管理企业的物资（如办公用品、生产原料等），包括物资的入库、出库、库存量监控等。) - 入库表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_inbound`;

CREATE TABLE `aioveu_inbound` (
                                  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '入库ID（主键）',
                                  `inbound_no` VARCHAR(50) NOT NULL COMMENT '入库单号（唯一）',
                                  `material_id` BIGINT UNSIGNED NOT NULL COMMENT '物资ID',
                                  `warehouse_id` BIGINT UNSIGNED NOT NULL COMMENT '仓库ID',
                                  `quantity` DECIMAL(12,4) UNSIGNED NOT NULL COMMENT '入库数量（支持小数计量）',
                                  `unit_price` DECIMAL(12,4) UNSIGNED NOT NULL COMMENT '入库单价',
                                  `total_amount` DECIMAL(12,4) UNSIGNED NOT NULL COMMENT '总金额',
                                  `batch_number` VARCHAR(50) COMMENT '批次号',
                                  `production_date` DATE COMMENT '生产日期',
                                  `expiry_date` DATE COMMENT '有效期至',
                                  `supplier_id` INT UNSIGNED COMMENT '供应商ID',
                                  `inbound_type` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '入库类型：1-采购入库，2-退货入库，3-调拨入库，4-其他',
                                  `in_time` DATETIME NOT NULL COMMENT '入库时间',
                                  `operator_id` BIGINT UNSIGNED NOT NULL COMMENT '操作员ID（关联员工表）',
                                  `remark` VARCHAR(500) COMMENT '备注',
                                  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                  PRIMARY KEY (`id`),

    -- 唯一约束确保入库单号唯一
                                  UNIQUE INDEX `uniq_inbound_no` (`inbound_no`),

    -- 外键约束（确保物资存在）
                                  CONSTRAINT `fk_inbound_material`
                                      FOREIGN KEY (`material_id`)
                                          REFERENCES `aioveu_material`(`id`)
                                          ON DELETE RESTRICT
                                          ON UPDATE CASCADE,

    -- 外键约束（确保仓库存在）
                                  CONSTRAINT `fk_inbound_warehouse`
                                      FOREIGN KEY (`warehouse_id`)
                                          REFERENCES `aioveu_warehouse`(`id`)
                                          ON DELETE RESTRICT
                                          ON UPDATE CASCADE,

    -- 外键约束（确保操作员存在）
                                  CONSTRAINT `fk_inbound_operator`
                                      FOREIGN KEY (`operator_id`)
                                          REFERENCES `aioveu_employee`(`employee_id`)
                                          ON DELETE RESTRICT
                                          ON UPDATE CASCADE,

    -- 索引优化
                                  INDEX `idx_material` (`material_id`),
                                  INDEX `idx_warehouse` (`warehouse_id`),
                                  INDEX `idx_supplier` (`supplier_id`),
                                  INDEX `idx_inbound_type` (`inbound_type`),
                                  INDEX `idx_in_time` (`in_time`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='入库表';

-- 插入入库数据
INSERT INTO `aioveu_inbound` (
    inbound_no, material_id, warehouse_id, quantity,
    unit_price, total_amount, batch_number,
    production_date, expiry_date, supplier_id,
    inbound_type, in_time, operator_id, remark
) VALUES
      ('IN-202306-001', 1, 1, 100.0000,
       25.99, 2599.0000, 'BATCH-2023-06-001',
       '2023-06-01', '2024-06-01', 2001,
       1, '2023-06-05 10:30:00', 1, '采购入库'),

      ('IN-202306-002', 2, 1, 50.0000,
       15.50, 775.0000, 'BATCH-2023-06-002',
       '2023-06-05', '2025-06-05', 2001,
       1, '2023-06-05 14:15:00', 1, '采购入库'),

      ('IN-202306-003', 3, 2, 2000.0000,
       12.50, 25000.0000, 'BATCH-2023-05-001',
       '2023-05-15', '2024-05-15', 2003,
       1, '2023-06-06 09:00:00', 2, '生产原料采购'),

      ('IN-202306-004', 4, 3, 50.0000,
       89.00, 4450.0000, 'BATCH-2023-07-001',
       '2023-07-01', '2026-07-01', 2002,
       1, '2023-06-07 11:20:00', 3, '电子产品采购');