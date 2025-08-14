

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
                                    inventory_id INT PRIMARY KEY AUTO_INCREMENT,
                                    warehouse_id INT NOT NULL COMMENT '仓库ID',
                                    material_id INT NOT NULL COMMENT '物资ID',
                                    quantity DECIMAL(12, 2) NOT NULL COMMENT '数量',
                                    stock_qty INT NOT NULL DEFAULT 0 COMMENT '当前库存',
                                    last_inbound DATETIME COMMENT '最后入库时间',
                                    last_outbound DATETIME COMMENT '最后出库时间',
                                    FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id),
                                    FOREIGN KEY (material_id) REFERENCES material(material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='库存表（记录每个仓库每个物资的库存）';