

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 物资管理模块(管理企业的物资（如办公用品、生产原料等），包括物资的入库、出库、库存量监控等。) - 库存表（记录每个仓库每个物资的库存）
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_inventory`;

CREATE TABLE `aioveu_inventory` (
                                    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '库存ID（主键）',
                                    `warehouse_id` BIGINT UNSIGNED NOT NULL COMMENT '仓库ID',
                                    `material_id` BIGINT UNSIGNED NOT NULL COMMENT '物资ID',
                                    `quantity` DECIMAL(12,4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '当前库存数量（支持小数计量）',
                                    `batch_number` VARCHAR(50) COMMENT '批次号',
                                    `production_date` DATE COMMENT '生产日期',
                                    `expiry_date` DATE COMMENT '有效期至',
                                    `last_inbound` DATETIME COMMENT '最后入库时间',
                                    `last_outbound` DATETIME COMMENT '最后出库时间',
                                    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                    PRIMARY KEY (`id`),

    -- 唯一约束：同一仓库+物资+批次唯一
                                    UNIQUE INDEX `uniq_warehouse_material_batch` (`warehouse_id`, `material_id`, `batch_number`),

    -- 外键约束（确保仓库存在）
                                    CONSTRAINT `fk_inventory_warehouse`
                                        FOREIGN KEY (`warehouse_id`)
                                            REFERENCES `aioveu_warehouse`(`id`)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE,

    -- 外键约束（确保物资存在）
                                    CONSTRAINT `fk_inventory_material`
                                        FOREIGN KEY (`material_id`)
                                            REFERENCES `aioveu_material`(`id`)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE,

    -- 索引优化
                                    INDEX `idx_warehouse` (`warehouse_id`),
                                    INDEX `idx_material` (`material_id`),
                                    INDEX `idx_expiry` (`expiry_date`),
                                    INDEX `idx_batch` (`batch_number`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='库存表（记录每个仓库每个物资的库存）';


-- 插入库存数据
INSERT INTO `aioveu_inventory` (
    warehouse_id, material_id, quantity, batch_number,
    production_date, expiry_date
) VALUES
      -- 仓库1的物资
      (1, 1, 150.0000, 'BATCH-2023-06-001', '2023-06-01', '2024-06-01'),
      (1, 2, 300.0000, 'BATCH-2023-06-002', '2023-06-05', '2025-06-05'),
      (1, 3, 800.0000, 'BATCH-2023-06-003', '2023-06-10', NULL),

      -- 仓库2的物资
      (2, 4, 2000.0000, 'BATCH-2023-05-001', '2023-05-15', '2024-05-15'),
      (2, 5, 15.0000, 'BATCH-2023-07-001', '2023-07-01', '2026-07-01'),

      -- 仓库3的物资
      (3, 6, 50.0000, 'BATCH-2023-04-001', '2023-04-10', '2025-04-10'),
      (3, 7, 100.0000, 'BATCH-2023-07-002', '2023-07-05', NULL);