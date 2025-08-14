

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 人员管理模块 (管理员工信息，包括员工的基本信息、部门、岗位、考勤、工资等。) - 考勤表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_attendance`;

CREATE TABLE `aioveu_attendance` (
                                     attendance_id INT PRIMARY KEY AUTO_INCREMENT,
                                     employee_id INT NOT NULL COMMENT '员工ID',
                                     date DATE NOT NULL COMMENT '日期',
                                     checkin_time DATETIME COMMENT '上班打卡时间',
                                     checkout_time DATETIME COMMENT '下班打卡时间',
                                     work_hours FLOAT COMMENT '工作时长(小时)',
                                     status TINYINT COMMENT '考勤状态：0-正常，1-迟到，2-早退，3-缺勤，4-休假',
                                     FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
                                     INDEX idx_date (checkin_time) COMMENT '日期索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='考勤表';