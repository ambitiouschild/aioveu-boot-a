

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
                                    device_id INT PRIMARY KEY,
                                    name VARCHAR(50) NOT NULL,
                                    maintenance_cycle TINYINT COMMENT '维保周期(月)',
                                    last_maintenance DATE COMMENT '上次维保日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='设备管理表';