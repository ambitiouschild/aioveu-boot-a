

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 人员管理模块 (管理员工信息，包括员工的基本信息、部门、岗位、考勤、工资等。) - 岗位表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_position`;

CREATE TABLE `aioveu_position` (
                                   position_id INT PRIMARY KEY AUTO_INCREMENT,
                                   name VARCHAR(50) NOT NULL COMMENT '岗位名称',
                                   description VARCHAR(200) COMMENT '岗位描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='岗位表';