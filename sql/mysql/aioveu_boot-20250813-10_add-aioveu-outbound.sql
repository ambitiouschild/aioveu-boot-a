

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
                                   outbound_id INT PRIMARY KEY AUTO_INCREMENT,
                                   material_id INT NOT NULL COMMENT '物资ID',
                                   warehouse_id INT NOT NULL COMMENT '仓库ID',
                                   quantity DECIMAL(12, 2) NOT NULL COMMENT '出库数量',
                                   out_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '出库时间',
                                   operator INT COMMENT '操作员，关联员工表(employee)的employee_id',
                                   purpose VARCHAR(200) COMMENT '用途说明',
                                   FOREIGN KEY (material_id) REFERENCES material(material_id),
                                   FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='出库表';