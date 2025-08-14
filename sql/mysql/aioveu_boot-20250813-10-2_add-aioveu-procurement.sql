

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
                                   order_id INT PRIMARY KEY AUTO_INCREMENT,
                                   supplier_id INT NOT NULL COMMENT '供应商ID',
                                   material_id INT NOT NULL COMMENT '物资ID',
                                   order_qty INT NOT NULL COMMENT '采购数量',
                                   order_price DECIMAL(10,2) COMMENT '订单单价',
                                   order_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
                                   receipt_date DATETIME COMMENT '到货时间',
                                   status ENUM('待审核','已下单','已收货','已入库') DEFAULT '待审核',
                                   FOREIGN KEY (material_id) REFERENCES materials(material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='采购流程表';