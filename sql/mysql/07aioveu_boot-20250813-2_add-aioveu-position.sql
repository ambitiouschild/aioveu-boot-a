

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

-- 创建规范化的岗位表
CREATE TABLE `aioveu_position` (
                                   `position_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
                                   `position_name` VARCHAR(50) NOT NULL COMMENT '岗位名称', -- 使用 position_name替代模糊的 name，明确表示岗位名称
                                   `dept_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '所属部门ID',  -- 添加部门关联字段，明确岗位归属 支持按职级进行薪酬体系管理
                                   `position_level` TINYINT UNSIGNED COMMENT '职级(1-10，数字越大职级越高)', -- 建立标准职级体系（1-10级）
                                   `description` TEXT COMMENT '岗位描述', -- 从 VARCHAR(200) 扩展到 TEXT 类型,支持存储详细的岗位职责和要求,为岗位说明书提供存储空间
                                   `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', -- 审计字段添加 支持岗位变更历史追踪
                                   `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', -- 满足人事管理系统审计需求

                                   PRIMARY KEY (`position_id`),

                                  -- 部门外键约束
                                   CONSTRAINT `fk_position_department`
                                       FOREIGN KEY (`dept_id`)
                                           REFERENCES `aioveu_department`(`dept_id`)
                                           ON DELETE SET NULL  -- 删除保护：删除部门时不解绑岗位（设置为NULL）
                                           ON UPDATE CASCADE,  -- 级联更新：部门ID变更时自动更新岗位记录

                                   -- 唯一索引确保部门内岗位名称唯一
                                   UNIQUE INDEX `idx_dept_position` (`dept_id`, `position_name`)


) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='公司岗位信息表';

-- 插入规范的岗位数据
INSERT INTO `aioveu_position`
(`position_name`, `dept_id`, `position_level`, `description`)
VALUES
    ('董事长', 1, 10, '公司最高决策人，管理总经理'),
    ('总经理', 1, 9, '负责公司日常管理，协调各部门工作'),
    ('法务总监', 3, 8, '负责公司法律事务管理'),
    ('财务总监', 4, 8, '负责公司财务管理工作'),
    ('研发总监', 2, 8, '负责技术研发部门管理工作'),
    ('高级工程师', 2, 6, '负责核心产品研发工作'),
    ('财务专员', 4, 4, '负责日常财务处理工作'),
    ('法务专员', 3, 4, '处理公司合同和合规事务');

# -- 查询各部门的岗位分布
# SELECT
#     d.dept_name AS '部门',
#     p.position_level AS '职级',
#     p.position_name AS '岗位',
#     COUNT(*) AS '人数'
# FROM aioveu_position p
#          JOIN aioveu_employee e ON p.position_id = e.position_id
#          JOIN aioveu_department d ON p.dept_id = d.dept_id
# GROUP BY d.dept_name, p.position_level, p.position_name
# ORDER BY d.dept_name, p.position_level DESC;
#
# -- 职级分布分析
# SELECT
#     CASE
#         WHEN position_level >= 8 THEN '高管层'
#         WHEN position_level BETWEEN 6 AND 7 THEN '中层管理'
#         WHEN position_level BETWEEN 4 AND 5 THEN '资深员工'
#         ELSE '普通员工'
#         END AS '职级分类',
#     COUNT(DISTINCT p.position_id) AS '岗位数量',
#     COUNT(*) AS '员工人数'
# FROM aioveu_position p
#          JOIN aioveu_employee e ON p.position_id = e.position_id
# GROUP BY 职级分类;

