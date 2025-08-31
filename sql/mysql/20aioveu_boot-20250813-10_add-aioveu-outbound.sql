

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 物资管理模块(管理企业的物资（如办公用品、生产原料等），包括物资的入库、出库、库存量监控等。) - 出库表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_outbound`;

CREATE TABLE `aioveu_outbound` (
                                   `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '出库ID（主键）',
                                   `outbound_no` VARCHAR(50) NOT NULL COMMENT '出库单号（唯一）',
                                   `material_id` BIGINT UNSIGNED NOT NULL COMMENT '物资ID',
                                   `warehouse_id` BIGINT UNSIGNED NOT NULL COMMENT '仓库ID',
                                   `quantity` DECIMAL(12,4) UNSIGNED NOT NULL COMMENT '出库数量',
                                   `batch_number` VARCHAR(50) COMMENT '批次号',
                                   `out_time` DATETIME NOT NULL COMMENT '出库时间',
                                   `operator_id` BIGINT UNSIGNED NOT NULL COMMENT '操作员ID（关联员工表）',
                                   `recipient_id` BIGINT UNSIGNED COMMENT '领用人ID（关联员工表）',
                                   `department_id` BIGINT UNSIGNED COMMENT '领用部门ID',
                                   `purpose` VARCHAR(200) NOT NULL COMMENT '用途说明',
                                   `project_id` INT UNSIGNED COMMENT '关联项目ID',
                                   `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态：1-待出库，2-已出库，3-已取消',
                                   `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                   PRIMARY KEY (`id`),

    -- 唯一约束确保出库单号唯一
                                   UNIQUE INDEX `uniq_outbound_no` (`outbound_no`),

    -- 外键约束（确保物资存在）
                                   CONSTRAINT `fk_outbound_material`
                                       FOREIGN KEY (`material_id`)
                                           REFERENCES `aioveu_material`(`id`)
                                           ON DELETE RESTRICT
                                           ON UPDATE CASCADE,

    -- 外键约束（确保仓库存在）
                                   CONSTRAINT `fk_outbound_warehouse`
                                       FOREIGN KEY (`warehouse_id`)
                                           REFERENCES `aioveu_warehouse`(`id`)
                                           ON DELETE RESTRICT
                                           ON UPDATE CASCADE,

    -- 外键约束（确保操作员存在）
                                   CONSTRAINT `fk_outbound_operator`
                                       FOREIGN KEY (`operator_id`)
                                           REFERENCES `aioveu_employee`(`employee_id`)
                                           ON DELETE RESTRICT
                                           ON UPDATE CASCADE,

    -- 外键约束（确保领用人存在）
                                   CONSTRAINT `fk_outbound_recipient`
                                       FOREIGN KEY (`recipient_id`)
                                           REFERENCES `aioveu_employee`(`employee_id`)
                                           ON DELETE SET NULL
                                           ON UPDATE CASCADE,

    -- 外键约束（确保部门存在）
                                   CONSTRAINT `fk_outbound_department`
                                       FOREIGN KEY (`department_id`)
                                           REFERENCES `aioveu_department`(`dept_id`)
                                           ON DELETE SET NULL
                                           ON UPDATE CASCADE,

    -- 索引优化
                                   INDEX `idx_material` (`material_id`),
                                   INDEX `idx_warehouse` (`warehouse_id`),
                                   INDEX `idx_operator` (`operator_id`),
                                   INDEX `idx_recipient` (`recipient_id`),
                                   INDEX `idx_out_time` (`out_time`),
                                   INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='出库记录表';

-- 插入出库数据
INSERT INTO `aioveu_outbound` (
    outbound_no, material_id, warehouse_id, quantity,
    batch_number, out_time, operator_id, recipient_id,
    department_id, purpose, project_id, status
) VALUES
      ('OUT-202306-001', 1, 1, 50.0000,
       'BATCH-2023-06-001', '2023-06-05 14:30:00', 1, 1,
       1, '部门日常办公用品领用', 3001, 2),

      ('OUT-202306-002', 2, 1, 20.0000,
       'BATCH-2023-06-002', '2023-06-06 10:15:00', 1, 2,
       2, '项目组办公用品领用', 3002, 2),

      ('OUT-202306-003', 3, 2, 500.0000,
       'BATCH-2023-05-001', '2023-06-07 09:45:00', 2, 3,
       3, '生产原料领用', 3003, 2),

      ('OUT-202306-004', 4, 3, 10.0000,
       'BATCH-2023-07-001', '2023-06-08 11:20:00', 3, 4,
       4, '设备维修配件更换', 3004, 2);