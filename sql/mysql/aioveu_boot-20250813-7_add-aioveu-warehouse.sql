

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
                                    warehouse_id INT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(50) NOT NULL COMMENT '仓库名称',
                                    location VARCHAR(100) COMMENT '仓库位置',
                                    manager INT COMMENT '负责人，关联员工表(employee)的employee_id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='仓库表';