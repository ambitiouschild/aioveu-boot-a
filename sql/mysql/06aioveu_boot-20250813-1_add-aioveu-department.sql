

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

-- 以上方案采用模块插件化架构，核心表结构通用性强，通过扩展字段和业务表支持不同行业场景。所有设计遵循数据库三大范式原则，
       -- 同时通过适当冗余（如实时库存表）提升查询性能。建议实施时配合Redis缓存高频数据（如员工基础信息），并使用Elasticsearch实现复杂条件检索。

    --  多模块设计，耦合更低，删除更方便，解耦更快，关联更低
-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 人员管理模块 (管理员工信息，包括员工的基本信息、部门、岗位、考勤、工资等。) - 部门表  共6张表
    -- 重新排序自增值需要更新所有后续记录的ID，这会带来巨大的性能开销，尤其是在大表中
    -- 数据库的自增主键（如MySQL中的AUTO_INCREMENT）在设计上是为了保证唯一性，而不是连续性。当您删除记录后，自增计数器不会自动重新排序或填补空缺
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_department`;

CREATE TABLE `aioveu_department` (
                                     `dept_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '部门ID',
                                     `dept_name` VARCHAR(50) NOT NULL COMMENT '部门名称', -- 使用 dept_name替代模糊的 name，明确表示部门名称
                                     `parent_dept_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '上级部门ID，用于构建部门树', -- 顶级部门使用 NULL表示无上级部门 使用 DEFAULT NULL明确表示可选字段
                                     `manager_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '部门经理，关联employee表',
                                     `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',  -- 审计字段添加 自动记录部门创建和更新时间
                                     `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', -- 支持部门变更历史追踪

                                     PRIMARY KEY (`dept_id`),

                                    -- 自引用外键：上级部门约束
                                     CONSTRAINT `fk_department_parent`
                                         FOREIGN KEY (`parent_dept_id`)  -- 部门ID变更时自动更新关联关系
                                             REFERENCES `aioveu_department`(`dept_id`)
                                             ON DELETE SET NULL  -- 除部门时自动解除关联（设为NULL）
                                             ON UPDATE CASCADE,  -- 部门ID变更时自动更新关联关系
                                   -- 部门经理外键约束
#                                      CONSTRAINT `fk_department_manager`  -- 所有约束使用 fk_前缀
#                                          FOREIGN KEY (`manager_id`)
#                                              REFERENCES `aioveu_employee`(`employee_id`)
#                                              ON DELETE SET NULL
#                                              ON UPDATE CASCADE,
-- 数据一致性：如果aioveu_department表中有一条记录的manager_id为1，那么aioveu_employee表中必须存在一个employee_id为1的员工记录。
-- 是的，必须一致！ 这是外键约束的核心要求。
                                    -- 确保部门名称唯一
                                     UNIQUE INDEX `idx_dept_name` (`dept_name`) -- 索引使用 idx_前缀


) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='公司部门组织结构表';

-- 插入规范的部门数据
INSERT INTO `aioveu_department`
(`dept_name`, `parent_dept_id`, `manager_id`)

-- 先创建顶级部门（parent_dept_id = NULL）
-- 再创建子部门
VALUES
    ('技术研发中心', NULL, 1),  -- 顶级部门
    ('产品研发部', 1, 2),
    ('质量保障部', 1, 3),
    ('市场营销中心', NULL, 1),  -- 顶级部门
    ('品牌推广部', 4, 2),
    ('客户关系部', 4, 3),
    ('人力资源中心', NULL, 1),  -- 顶级部门
    ('人才发展部', 7, 2),
    ('薪酬福利部', 7, 3);

-- 部门删除策略
-- 删除部门前转移员工
# UPDATE aioveu_employee
# SET department_id = new_dept_id
# WHERE department_id = old_dept_id;
#
# -- 删除部门
# DELETE FROM aioveu_department WHERE dept_id = old_dept_id;