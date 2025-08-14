

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 物资管理模块(管理企业的物资（如办公用品、生产原料等），包括物资的入库、出库、库存量监控等。) - 物资表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_material`;

CREATE TABLE `aioveu_material` (
                                   material_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '物资ID',
                                   name VARCHAR(100) NOT NULL COMMENT '物资名称',
                                   category_id INT NOT NULL COMMENT '分类ID',
                                   unit VARCHAR(20) NOT NULL DEFAULT '个' COMMENT '单位',
                                   spec VARCHAR(50) COMMENT '规格型号',
                                   purchase_price DECIMAL(10,2) NOT NULL COMMENT '采购单价',
                                   supplier_id INT COMMENT '供应商ID',
                                   min_stock INT DEFAULT 0 COMMENT '最低库存',
                                   max_stock INT DEFAULT 100 COMMENT '最高库存',
                                   is_active TINYINT(1) DEFAULT 1 COMMENT '启用状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='物资表';

ALTER TABLE aioveu_material ADD COLUMN barcode VARCHAR(20) COMMENT '商品条码';