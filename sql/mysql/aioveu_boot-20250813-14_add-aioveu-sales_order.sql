

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 订单管理模块(管理销售订单。) - 订单表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_sales_order`;

CREATE TABLE `aioveu_sales_order` (
                                      order_id INT PRIMARY KEY AUTO_INCREMENT,
                                      customer_id INT COMMENT '客户ID，关联customer表',
                                      order_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
                                      total_amount DECIMAL(12, 2) COMMENT '订单总金额',
                                      status TINYINT DEFAULT 0 COMMENT '订单状态：0-未支付，1-已支付，2-已发货，3-已完成，4-已取消',
                                      operator_id INT COMMENT '操作员，关联员工表(employee)',
                                      FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
                                      FOREIGN KEY (operator_id) REFERENCES employee(employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';