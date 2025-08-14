

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 人员管理模块 (管理员工信息，包括员工的基本信息、部门、岗位、考勤、工资等。) - 员工表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_employee`;

CREATE TABLE `aioveu_employee` (
                                   employee_id INT PRIMARY KEY AUTO_INCREMENT,
                                   emp_code VARCHAR(20) UNIQUE NOT NULL COMMENT '员工编号',
                                   name VARCHAR(50) NOT NULL COMMENT '姓名',
                                   gender TINYINT COMMENT '性别：0-女，1-男',
                                   birth_date DATE COMMENT '出生日期',
                                   id_card VARCHAR(20) COMMENT '身份证号',
                                   phone VARCHAR(20) COMMENT '手机号码',
                                   email VARCHAR(50) COMMENT '邮箱',
                                   dept_id INT COMMENT '所属部门',
                                   position_id INT COMMENT '岗位ID',
                                   hire_date DATE COMMENT '入职日期',
                                   salary DECIMAL(10,2) COMMENT '基本薪资',
                                   status TINYINT DEFAULT 1 COMMENT '状态：0-离职，1-在职,3-休假,4-实习',
                                   FOREIGN KEY (dept_id) REFERENCES department(dept_id),
                                   FOREIGN KEY (position_id) REFERENCES position(position_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='员工表';