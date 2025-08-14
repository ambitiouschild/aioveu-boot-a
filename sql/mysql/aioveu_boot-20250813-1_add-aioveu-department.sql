

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

-- 以上方案采用模块插件化架构，核心表结构通用性强，通过扩展字段和业务表支持不同行业场景。所有设计遵循数据库三大范式原则，
       -- 同时通过适当冗余（如实时库存表）提升查询性能。建议实施时配合Redis缓存高频数据（如员工基础信息），并使用Elasticsearch实现复杂条件检索。

-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 人员管理模块 (管理员工信息，包括员工的基本信息、部门、岗位、考勤、工资等。) - 部门表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_edepartment`;

CREATE TABLE `aioveu_department` (
                                     dept_id INT PRIMARY KEY AUTO_INCREMENT,
                                     name VARCHAR(50) NOT NULL COMMENT '部门名称',
                                     parent_dept_id INT COMMENT '上级部门ID，用于构建部门树',
                                     manager_id INT COMMENT '部门经理，关联employee表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';