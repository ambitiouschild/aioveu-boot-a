

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
-- 创建规范化的工资表
CREATE TABLE `aioveu_salary` (
                                 `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '工资记录ID',
                                 `employee_id` BIGINT UNSIGNED NOT NULL COMMENT '员工ID',
                                 `salary_period` DATE NOT NULL COMMENT '工资周期（月份）', -- 使用DATE类型存储工资周期（每月1日） 添加唯一约束确保每月每人只有一条记录
                                 `base_salary` DECIMAL(12, 2) NOT NULL DEFAULT 0.00 COMMENT '基本工资',
                                 `performance_bonus` DECIMAL(12, 2) DEFAULT 0.00 COMMENT '绩效奖金',
                                 `overtime_pay` DECIMAL(12, 2) DEFAULT 0.00 COMMENT '加班补贴',
                                 `allowances` DECIMAL(12, 2) DEFAULT 0.00 COMMENT '各类津贴',
                                 `social_security` DECIMAL(12, 2) DEFAULT 0.00 COMMENT '社保扣除',
                                 `housing_fund` DECIMAL(12, 2) DEFAULT 0.00 COMMENT '住房公积金',
                                 `tax` DECIMAL(12, 2) DEFAULT 0.00 COMMENT '个人所得税',
                                 `other_deductions` DECIMAL(12, 2) DEFAULT 0.00 COMMENT '其他扣款',
                                 `net_salary` DECIMAL(12, 2) NOT NULL COMMENT '实发工资',
                                 `payment_date` DATE COMMENT '实际发放日期',
                                 `payment_status` TINYINT(1) DEFAULT 0 COMMENT '发放状态：0-未发放，1-已发放，2-发放失败', -- 明确工资发放状态,便于财务对账和异常处理
                                 `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', -- 审计字段添加,自动记录工资信息创建和更新时间
                                 `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', -- 支持工资变更历史追踪

                                 PRIMARY KEY (`id`),

                                -- 员工外键约束
                                 CONSTRAINT `fk_salary_employee`
                                     FOREIGN KEY (`employee_id`)
                                         REFERENCES `aioveu_employee`(`employee_id`)
                                         ON DELETE RESTRICT   -- 有工资记录时不能删除员工  ，使用 RESTRICT防止误删有工资记录的员工
                                         ON UPDATE CASCADE,   -- 员工ID变更时同步更新 ，使用 CASCADE确保员工ID变更时工资记录保持关联

                                -- 唯一索引确保每月每人只有一条记录
                                 UNIQUE INDEX `idx_employee_period` (`employee_id`, `salary_period`),

                                -- 索引优化
                                 INDEX `idx_period` (`salary_period`),
                                 INDEX `idx_payment_status` (`payment_status`)




) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='员工工资明细表';

-- 拆分工资组成，支持详细工资条展示
-- 自动计算实发工资：net_salary = base_salary + performance_bonus + overtime_pay + allowances - social_security - housing_fund - tax - other_deductions


-- 插入工资数据（基于员工表）
INSERT INTO `aioveu_salary` (
    `employee_id`,
    `salary_period`,
    `base_salary`,
    `performance_bonus`,
    `overtime_pay`,
    `allowances`,
    `social_security`,
    `housing_fund`,
    `tax`,
    `other_deductions`,
    `net_salary`,
    `payment_date`,
    `payment_status`
)
VALUES
    -- 员工ID=1的工资记录（已发放）
    (
        1,
        '2024-12-01',
        10000.00,
        1500.00,
        300.00,
        800.00,
        850.00,
        600.00,
        280.00,
        0.00,
        -- 计算：10000 + 1500 + 300 + 800 - 850 - 600 - 280 = 10,870
        10870.00,
        '2024-12-05',
        1
    ),
    -- 员工ID=2的工资记录（已发放）
    (
        2,
        '2024-12-01',
        8000.00,
        1200.00,
        150.00,
        500.00,
        700.00,
        500.00,
        210.00,
        50.00,
        -- 计算：8000 + 1200 + 150 + 500 - 700 - 500 - 210 - 50 = 7,390
        7390.00,
        '2024-12-05',
        1
    ),
    -- 员工ID=3的工资记录（未发放）
    (
        3,
        '2025-01-01',
        12000.00,
        2000.00,
        500.00,
        1000.00,
        900.00,
        800.00,
        320.00,
        100.00,
        -- 计算：12000 + 2000 + 500 + 1000 - 900 - 800 - 320 - 100 = 12,380
        12380.00,
        NULL,  -- 未发放时日期为NULL
        0
    ),
    -- 员工ID=1的下个月记录（发放失败）
    (
        1,
        '2025-01-01',
        10000.00,
        1300.00,
        200.00,
        800.00,
        850.00,
        600.00,
        250.00,
        0.00,
        -- 计算：10000 + 1300 + 200 + 800 - 850 - 600 - 250 = 10,600
        10600.00,
        NULL,  -- 发放失败时日期为NULL
        2
    );
# SELECT
#     e.employee_id,
#     '2025-08-01' AS salary_period,  -- 2025年8月工资周期
#     e.salary AS base_salary,
#
#     -- 绩效奖金 = 基本工资 × 绩效系数
#     ROUND(e.salary *
#           CASE
#               WHEN e.name = '李白' THEN 0.15  -- 15%
#               WHEN e.name = '杜甫' THEN 0.20  -- 20%
#               WHEN e.name = '李清照' THEN 0.25 -- 25%
#               WHEN e.name = '白居易' THEN 0.18 -- 18%
#               ELSE 0.10
#               END, 2) AS performance_bonus,
#
#     -- 加班补贴（假设每月固定）
#     800.00 AS overtime_pay,
#
#     -- 各类津贴（岗位津贴+交通补贴）
#     CASE
#         WHEN p.position_level >= 8 THEN 1500.00  -- 高管层
#         WHEN p.position_level BETWEEN 6 AND 7 THEN 1000.00 -- 中层管理
#         ELSE 500.00 -- 普通员工
#         END + 300.00 AS allowances,  -- 交通补贴固定300
#
#     -- 社保扣除（基本工资×12%）
#     ROUND(e.salary * 0.12, 2) AS social_security,
#
#     -- 住房公积金（基本工资×8%）
#     ROUND(e.salary * 0.08, 2) AS housing_fund,
#
#     -- 个人所得税（简化计算）
#     ROUND(GREATEST(e.salary * 0.1 - 210, 0), 2) AS tax,
#
#     -- 其他扣款（假设无）
#     0.00 AS other_deductions,
#
#     -- 实发工资计算
#     ROUND(
#             e.salary +
#             (e.salary *
#              CASE
#                  WHEN e.name = '李白' THEN 0.15
#                  WHEN e.name = '杜甫' THEN 0.20
#                  WHEN e.name = '李清照' THEN 0.25
#                  WHEN e.name = '白居易' THEN 0.18
#                  ELSE 0.10
#                  END) +
#             800.00 +
#             (CASE
#                  WHEN p.position_level >= 8 THEN 1500.00
#                  WHEN p.position_level BETWEEN 6 AND 7 THEN 1000.00
#                  ELSE 500.00
#                  END + 300.00) -
#             (e.salary * 0.12) -
#             (e.salary * 0.08) -
#             ROUND(GREATEST(e.salary * 0.1 - 210, 0), 2) -
#             0.00
#         , 2) AS net_salary,
#
#     -- 发放日期（当月10号）
#     '2025-08-10' AS payment_date,
#
#     -- 发放状态（1-已发放）
#     1 AS payment_status
# FROM aioveu_employee e
#          JOIN aioveu_position p ON e.position_id = p.position_id
# WHERE e.status = 1;  -- 只计算在职员工
