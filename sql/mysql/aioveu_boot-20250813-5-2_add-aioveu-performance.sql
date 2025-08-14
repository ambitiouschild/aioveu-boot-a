

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 人员管理模块 (管理员工信息，包括员工的基本信息、部门、岗位、考勤、工资等。) - 绩效考评表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_performance`;

CREATE TABLE `aioveu_performance` (
                                      record_id INT PRIMARY KEY AUTO_INCREMENT,
                                      employee_id INT NOT NULL COMMENT '员工ID',
                                      period DATE NOT NULL COMMENT '考核周期',
                                      kpi_score TINYINT COMMENT 'KPI评分(1-5)',
                                      productivity DECIMAL(5,2) COMMENT '生产率',
                                      review TEXT COMMENT '主管评语',
                                      FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='绩效考评表';