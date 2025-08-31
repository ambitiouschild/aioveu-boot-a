

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 物资管理模块(管理企业的物资（如办公用品、生产原料等），包括物资的入库、出库、库存量监控等。) - 仓库表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_warehouse`;

CREATE TABLE `aioveu_warehouse` (
                                    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '仓库ID（主键）',
                                    `name` VARCHAR(50) NOT NULL COMMENT '仓库名称',
                                    `code` VARCHAR(20) UNIQUE NOT NULL COMMENT '仓库编码（唯一标识）',
                                    `location` VARCHAR(100) NOT NULL COMMENT '仓库位置',
                                    `area` DECIMAL(10,2) UNSIGNED COMMENT '仓库面积（平方米）',
                                    `capacity` DECIMAL(10,2) UNSIGNED COMMENT '存储容量（立方米）',
                                    `manager_id` BIGINT UNSIGNED COMMENT '负责人ID（关联员工表）',
                                    `contact_phone` VARCHAR(20) COMMENT '联系电话',
                                    `description` TEXT COMMENT '仓库描述',
                                    `is_active` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '启用状态：0-停用，1-启用',
                                    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                    PRIMARY KEY (`id`),

    -- 外键约束（确保负责人是有效员工）
                                    CONSTRAINT `fk_warehouse_manager`
                                        FOREIGN KEY (`manager_id`)
                                            REFERENCES `aioveu_employee`(`employee_id`)
                                            ON DELETE SET NULL
                                            ON UPDATE CASCADE,

    -- 唯一索引确保仓库编码唯一
                                    UNIQUE INDEX `uniq_code` (`code`),  -- 唯一编码索引

    -- 索引优化
                                    INDEX `idx_name` (`name`),  -- 名称查询优化
                                    INDEX `idx_location` (`location`),  -- 位置查询优化
                                    INDEX `idx_active` (`is_active`)   -- 状态过滤优化
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='仓库信息表';

INSERT INTO `aioveu_warehouse` (
    name, code, location, area, capacity,
    manager_id, contact_phone, description
) VALUES
      ('总部中心仓', 'WH-001', '北京市朝阳区科技园A栋B1层', 2000.00, 15000.00,
       1, '010-12345678', '公司总部主要仓库，存放办公用品和电子产品'),
      ('南方分仓', 'WH-002', '广州市天河区工业园C区8号', 1500.00, 12000.00,
       2, '020-87654321', '华南地区分仓，主要存放季节性物资'),
      ('生产原料仓', 'WH-003', '上海市浦东新区制造基地', 3000.00, 25000.00,
       3, '021-11223344', '生产原材料专用仓库，恒温恒湿控制');