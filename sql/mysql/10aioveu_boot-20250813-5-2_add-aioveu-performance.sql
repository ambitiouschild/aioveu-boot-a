

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 人员管理模块 (管理员工信息，包括员工的基本信息、部门、岗位、考勤、工资等。) - 员工绩效考评表


-- 修改数据库表结构
# ALTER TABLE aioveu_performance
#     CHANGE COLUMN id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '绩效记录ID';
    -- 在MySQL中，将INT UNSIGNED改为BIGINT UNSIGNED以支持Long类型，并在Java实体类中使用Long类型
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_performance`;

CREATE TABLE `aioveu_performance` (
                                      `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '绩效记录ID',
                                      `employee_id` BIGINT UNSIGNED NOT NULL COMMENT '员工ID',
                                      `period_year` SMALLINT UNSIGNED NOT NULL COMMENT '考核年份', -- 拆分为年份+季度字段，支持灵活查询季度/年度绩效
                                      `period_quarter` TINYINT UNSIGNED COMMENT '考核季度(1-4)',
                                      `kpi_score` TINYINT UNSIGNED COMMENT 'KPI评分(1-100分)', -- 从模糊的1-5分改为更精确的百分制（0-100） TINYINT UNSIGNED 范围0-255完全够用
                                      `productivity` DECIMAL(5,2) UNSIGNED COMMENT '生产率百分比(%)', -- DECIMAL(5,2) 精确存储小数（如98.76%）
                                      `review` TEXT COMMENT '主管评语',
                                      `performance_grade` VARCHAR(10) COMMENT '绩效等级',
                                      `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',  -- 审计字段添加 自动记录数据创建和更新时间
                                      `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', -- 满足数据审计要求 支持绩效历史追踪

                                      PRIMARY KEY (`id`),
                                      INDEX `idx_employee` (`employee_id`),   -- 支持高效查询 按员工查绩效历史
                                      INDEX `idx_period` (`period_year`, `period_quarter`), -- 按时间段查团队绩效

                                      CONSTRAINT `fk_performance_employee`
                                          FOREIGN KEY (`employee_id`)
                                          REFERENCES `aioveu_employee`(`employee_id`)
                                          ON DELETE RESTRICT   -- 员工存在绩效时不能删除  确保员工ID变更时绩效记录保持关联 防止误删有绩效记录的员工
                                          ON UPDATE CASCADE    -- 员工ID变更时同步更新

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='员工绩效考评表';

-- 绩效等级计算列
# ALTER TABLE aioveu_performance
#     ADD performance_grade VARCHAR(10)
#         GENERATED ALWAYS AS (
#             CASE
#                 WHEN kpi_score >= 90 THEN 'A'
#                 WHEN kpi_score >= 80 THEN 'B'
#                 WHEN kpi_score >= 70 THEN 'C'
#                 ELSE 'D'
#                 END
#             ) VIRTUAL COMMENT '绩效等级';


-- 查询2024年Q2所有绩效
-- SELECT * FROM aioveu_performance
-- WHERE period_year = 2024 AND period_quarter = 2;

-- 插入绩效考评数据
INSERT INTO `aioveu_performance` (
    `employee_id`,
    `period_year`,
    `period_quarter`,
    `kpi_score`,
    `productivity`,
    `review`,
    `create_time`,
    `update_time`
) VALUES
      -- 张三 2024Q1绩效
      (1, 2024, 1, 92, 98.50,
       '张同学本季度表现优异，超额完成所有KPI指标。在项目A中发挥关键作用，带领团队提前两周完成交付。',
       '2024-03-31 18:00:00', '2024-04-02 09:15:00'),

      -- 张三 2024Q2绩效
      (1, 2024, 2, 88, 95.20,
       '整体表现良好，但在项目C的需求分析阶段出现延误。建议加强时间管理能力，提高任务分解效率。',
       '2024-06-30 18:00:00', '2024-07-01 10:30:00'),

      -- 李四 2024Q1绩效
      (2, 2024, 1, 75, 87.30,
       '基本达成考核目标，但在团队协作方面有待提高。建议多参与部门技术分享活动，增强沟通能力。',
       '2024-03-31 18:00:00', '2024-04-01 14:20:00'),

      -- 王五 2024Q1绩效
      (3, 2024, 1, 95, 99.00,
       '杰出表现！不仅完成本职工作，还主动协助团队解决关键技术难题。提出的流程优化方案预计可节省10%工时。',
       '2024-03-31 18:00:00', '2024-04-05 11:45:00'),

      -- 李四 2024Q2绩效
      (2, 2024, 2, 82, 90.50,
       '有明显进步，积极参与团队协作并按时完成任务。在客户沟通中展现出良好的专业素养。',
       '2024-06-30 18:00:00', '2024-07-02 16:20:00'),

      -- 王五 2024Q2绩效
      (3, 2024, 2, 97, 99.50,
       '持续卓越表现！主导开发的自动化工具已推广至全部门使用，预计每年可节省人力成本约20万元。',
       '2024-06-30 18:00:00', '2024-07-03 09:30:00');


-- 生成绩效等级（虚拟列自动计算）
-- 这个错误表明您尝试更新数据库中的生成列（generated column）performance_grade，但数据库不允许直接更新生成列的值。
-- 在MySQL中，生成列的值是根据其他列的值自动计算出来的，不能手动更新。
    -- 1. 修改实体类（推荐）
    -- 在实体类中，将生成列标记为只读，避免MyBatis-Plus在更新时包含该字段
-- 2. 修改数据库表结构（可选）

--  修改数据库表结构（可选） 如果不需要生成列，可以将其改为普通列

# ALTER TABLE aioveu_performance
#     MODIFY performance_grade VARCHAR(10) COMMENT '绩效等级';

-- 1.删除生成列performance_grade
# SELECT
#     id,
#     employee_id,
#     period_year,
#     period_quarter,
#     kpi_score,
#     productivity,
#     CASE
#         WHEN kpi_score >= 90 THEN 'A'
#         WHEN kpi_score >= 80 THEN 'B'
#         WHEN kpi_score >= 70 THEN 'C'
#         ELSE 'D'
#         END AS performance_grade
# FROM aioveu_performance;

# ALTER TABLE aioveu_performance
#     DROP COLUMN performance_grade;

-- 2.添加新的普通列performance_grade

# ALTER TABLE aioveu_performance
#     ADD COLUMN performance_grade VARCHAR(10) COMMENT '绩效等级';

# //将SQL查询转换为Java代码的几种方法：
#
# 1.DTO + Mapper注解：最直接的方式，适合简单查询
# 2.实体类计算属性：在Java层计算绩效等级，更灵活  //如果您希望在实体类中直接计算绩效等级，可以添加一个计算属性 //在服务层使用计算属性
# 3.XML映射文件：适合复杂查询，便于维护
# 4.QueryWrapper动态查询：适合需要动态条件的场景
# 5.ResultMap映射：提供更细粒度的控制
# 最佳实践建议：
# •对于简单查询，使用DTO + Mapper注解
# •对于需要动态条件的查询，使用QueryWrapper
# •对于复杂查询，使用XML映射文件
# •如果需要在多个地方使用绩效等级计算，在实体类中添加计算属性
# 您可以根据具体需求选择最适合的方法。如果绩效等级计算逻辑可能变化，推荐在Java层实现（方法2），这样更容易修改和维护。