

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 物资管理模块(管理企业的物资（如办公用品、生产原料等），包括物资的入库、出库、库存量监控等。) - 采购流程表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_procurement`;

CREATE TABLE `aioveu_procurement` (
                                      `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '采购ID（主键）',
                                      `procurement_no` VARCHAR(50) NOT NULL COMMENT '采购单号（唯一）',
                                      `supplier_id` INT UNSIGNED NOT NULL COMMENT '供应商ID',
                                      `material_id` BIGINT UNSIGNED NOT NULL COMMENT '物资ID',
                                      `quantity` INT UNSIGNED NOT NULL COMMENT '采购数量',
                                      `unit_price` DECIMAL(12,4) UNSIGNED NOT NULL COMMENT '采购单价',
                                      `total_amount` DECIMAL(12,4) UNSIGNED NOT NULL COMMENT '总金额',
                                      `order_date` DATETIME NOT NULL COMMENT '下单时间',
                                      `expected_delivery` DATE COMMENT '预计到货日期',
                                      `actual_delivery` DATE COMMENT '实际到货日期',
                                      `receipt_date` DATETIME COMMENT '签收时间',
                                      `warehouse_id` BIGINT UNSIGNED COMMENT '入库仓库ID',
                                      `inbound_date` DATETIME COMMENT '入库时间',
                                      `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态：1-待审核，2-已下单，3-已发货，4-已收货，5-已入库，6-已取消',
                                      `applicant_id` BIGINT UNSIGNED NOT NULL COMMENT '申请人ID（关联员工表）',
                                      `reviewer_id` BIGINT UNSIGNED COMMENT '审核人ID',
                                      `review_time` DATETIME COMMENT '审核时间',
                                      `remark` VARCHAR(500) COMMENT '备注',
                                      `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                      PRIMARY KEY (`id`),

    -- 唯一约束确保采购单号唯一
                                      UNIQUE INDEX `uniq_procurement_no` (`procurement_no`),

    -- 外键约束（确保物资存在）
                                      CONSTRAINT `fk_procurement_material`
                                          FOREIGN KEY (`material_id`)
                                              REFERENCES `aioveu_material`(`id`)
                                              ON DELETE RESTRICT
                                              ON UPDATE CASCADE,

    -- 外键约束（确保仓库存在）
                                      CONSTRAINT `fk_procurement_warehouse`
                                          FOREIGN KEY (`warehouse_id`)
                                              REFERENCES `aioveu_warehouse`(`id`)
                                              ON DELETE SET NULL
                                              ON UPDATE CASCADE,

    -- 外键约束（确保申请人存在）
                                      CONSTRAINT `fk_procurement_applicant`
                                          FOREIGN KEY (`applicant_id`)
                                              REFERENCES `aioveu_employee`(`employee_id`)
                                              ON DELETE RESTRICT
                                              ON UPDATE CASCADE,

    -- 外键约束（确保审核人存在）
                                      CONSTRAINT `fk_procurement_reviewer`
                                          FOREIGN KEY (`reviewer_id`)
                                              REFERENCES `aioveu_employee`(`employee_id`)
                                              ON DELETE SET NULL
                                              ON UPDATE CASCADE,

    -- 索引优化
                                      INDEX `idx_supplier` (`supplier_id`),
                                      INDEX `idx_material` (`material_id`),
                                      INDEX `idx_status` (`status`),
                                      INDEX `idx_order_date` (`order_date`),
                                      INDEX `idx_expected_delivery` (`expected_delivery`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='采购流程表';

-- 插入采购数据
INSERT INTO `aioveu_procurement` (
    procurement_no, supplier_id, material_id, quantity,
    unit_price, total_amount, order_date,
    expected_delivery, applicant_id, `reviewer_id`,status
) VALUES
      ('PO-202306-001', 2001, 1, 100,
       25.99, 2599.00, '2023-06-01 10:30:00',
       '2023-06-05', 1, 1,2),

      ('PO-202306-002', 2001, 2, 50,
       15.50, 775.00, '2023-06-01 14:15:00',
       '2023-06-06', 1, 1,2),

      ('PO-202306-003', 2003, 3, 2000,
       12.50, 25000.00, '2023-06-02 09:00:00',
       '2023-06-07', 2, 2,2),

      ('PO-202306-004', 2002, 4, 50,
       89.00, 4450.00, '2023-06-03 11:20:00',
       '2023-06-08', 3, 2,2);