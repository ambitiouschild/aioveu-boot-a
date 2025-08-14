

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 人员管理模块 (管理员工信息，包括员工的基本信息、部门、岗位、考勤、工资等。) - 工资表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_salary`;

CREATE TABLE `aioveu_salary` (
                                 salary_id INT PRIMARY KEY AUTO_INCREMENT,
                                 employee_id INT NOT NULL COMMENT '员工ID',
                                 base_salary DECIMAL(12, 2) COMMENT '基本工资',
                                 bonus DECIMAL(12, 2) COMMENT '奖金',
                                 deduction DECIMAL(12, 2) COMMENT '扣款',
                                 salary_date DATE COMMENT '工资日期（月份）',
                                 payment_date DATE COMMENT '发放日期',
                                 FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='工资表';