

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 订单管理模块(管理销售订单。) - 订单明细表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_sales_order_detail`;

CREATE TABLE `aioveu_sales_order_detail` (
                                             detail_id INT PRIMARY KEY AUTO_INCREMENT,
                                             order_id INT NOT NULL COMMENT '订单ID',
                                             material_id INT COMMENT '物资ID，关联material表',
                                             quantity DECIMAL(12, 2) COMMENT '数量',
                                             unit_price DECIMAL(12, 2) COMMENT '单价',
                                             total_price DECIMAL(12, 2) COMMENT '总金额=数量*单价',
                                             FOREIGN KEY (order_id) REFERENCES sales_order(order_id),
                                             FOREIGN KEY (material_id) REFERENCES material(material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单明细表';