

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 物资管理模块(管理企业的物资（如办公用品、生产原料等），包括物资的入库、出库、库存量监控等。) - 设备管理表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_equipment`;

CREATE TABLE `aioveu_equipment` (
                                    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '设备ID（主键）',
                                    `asset_no` VARCHAR(50) NOT NULL COMMENT '资产编号（唯一）',
                                    `name` VARCHAR(100) NOT NULL COMMENT '设备名称',
                                    `category_id` BIGINT UNSIGNED NOT NULL COMMENT '设备分类ID',
                                    `model` VARCHAR(50) COMMENT '设备型号',
                                    `serial_no` VARCHAR(50) UNIQUE COMMENT '序列号（唯一）',
                                    `location_id` INT UNSIGNED COMMENT '位置ID（关联位置表）',
                                    `department_id` BIGINT UNSIGNED COMMENT '所属部门ID',
                                    `responsible_person` BIGINT UNSIGNED COMMENT '责任人（关联员工表）',
                                    `purchase_date` DATE COMMENT '采购日期',
                                    `purchase_price` DECIMAL(12,2) UNSIGNED COMMENT '采购价格',
                                    `warranty_period` TINYINT UNSIGNED COMMENT '保修期（月）',
                                    `maintenance_cycle` TINYINT UNSIGNED COMMENT '维保周期（月）',
                                    `last_maintenance` DATE COMMENT '上次维保日期',
                                    `next_maintenance` DATE COMMENT '下次维保日期',
                                    `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态：1-在用，2-闲置，3-维修中，4-报废',
                                    `remark` TEXT COMMENT '备注',
                                    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                    PRIMARY KEY (`id`),

    -- 唯一约束确保资产编号唯一
                                    UNIQUE INDEX `uniq_asset_no` (`asset_no`),

    -- 外键约束（确保分类存在）
                                    CONSTRAINT `fk_equipment_category`
                                        FOREIGN KEY (`category_id`)
                                            REFERENCES `aioveu_category`(`id`)
                                            ON DELETE RESTRICT
                                            ON UPDATE CASCADE,

    -- 外键约束（确保部门存在）
                                    CONSTRAINT `fk_equipment_department`
                                        FOREIGN KEY (`department_id`)
                                            REFERENCES `aioveu_department`(`dept_id`)
                                            ON DELETE SET NULL
                                            ON UPDATE CASCADE,

    -- 外键约束（确保责任人存在）
                                    CONSTRAINT `fk_equipment_responsible`
                                        FOREIGN KEY (`responsible_person`)
                                            REFERENCES `aioveu_employee`(`employee_id`)
                                            ON DELETE SET NULL
                                            ON UPDATE CASCADE,

    -- 索引优化
                                    INDEX `idx_name` (`name`),
                                    INDEX `idx_category` (`category_id`),
                                    INDEX `idx_status` (`status`),
                                    INDEX `idx_next_maintenance` (`next_maintenance`)


) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='设备管理表';

-- 插入设备数据
INSERT INTO `aioveu_equipment` (
    asset_no, name, category_id, model, serial_no,
    location_id, department_id, responsible_person,
    purchase_date, purchase_price, warranty_period,
    maintenance_cycle, last_maintenance, status
) VALUES
      ('ASSET-001', '激光打印机', 3, 'HP LaserJet Pro M404dn', 'SN-PRT-001',
       1, 1, 1,
       '2022-01-15', 1200.00, 24,
       6, '2023-05-10', 1),

      ('ASSET-002', '投影仪', 9, 'Epson EB-U05', 'SN-PROJ-001',
       2, 2, 2,
       '2022-03-20', 3500.00, 36,
       12, '2023-04-15', 1),

      ('ASSET-003', '服务器', 10, 'Dell PowerEdge R740', 'SN-SVR-001',
       3, 3, 3,
       '2021-11-10', 25000.00, 48,
       6, '2023-06-01', 1),

      ('ASSET-004', '空调', 5, '格力 KFR-72LW', 'SN-AC-001',
       4, 4, 4,
       '2020-07-05', 6500.00, 60,
       3, '2023-05-20', 1);