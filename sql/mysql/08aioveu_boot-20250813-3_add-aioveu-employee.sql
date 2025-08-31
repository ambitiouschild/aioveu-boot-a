

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
-- 创建规范化的员工表
CREATE TABLE `aioveu_employee` (
                                   `employee_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '员工ID',
                                   `emp_code` VARCHAR(20) NOT NULL COMMENT '员工编号',
                                   `name` VARCHAR(50) NOT NULL COMMENT '姓名',
                                   `gender` TINYINT(1) DEFAULT NULL COMMENT '性别：0-女，1-男', -- 使用 TINYINT(1)明确表示布尔/枚举类型
                                   `birth_date` DATE COMMENT '出生日期',
                                   `id_card` VARCHAR(20) COMMENT '身份证号',
                                   `phone` VARCHAR(20) COMMENT '手机号码',
                                   `email` VARCHAR(50) COMMENT '邮箱',
                                   `dept_id` BIGINT UNSIGNED COMMENT '所属部门',
                                   `position_id` BIGINT UNSIGNED  COMMENT '岗位ID',
                                   `hire_date` DATE NOT NULL COMMENT '入职日期',
                                   `salary` DECIMAL(10,2) COMMENT '基本薪资',
                                   `status` TINYINT(1) DEFAULT 1 COMMENT '状态：0-离职，1-在职,2-休假,3-实习', -- 调整状态值：2-休假，3-实习（更符合实际业务）
                                   `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', --  审计字段添加 自动记录员工信息创建和更新时间
                                   `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', -- 支持员工信息变更追踪

                                   PRIMARY KEY (`employee_id`),
                                   UNIQUE KEY `uk_emp_code` (`emp_code`), -- 确保员工编号唯一
                                   UNIQUE KEY `uk_id_card` (`id_card`),  --  确保身份证号唯一（防止重复登记）

                                    -- 部门外键约束
                                   CONSTRAINT `fk_employee_department`  -- 使用明确的约束名称
                                       FOREIGN KEY (`dept_id`)
                                           REFERENCES `aioveu_department`(`dept_id`)
                                           ON DELETE SET NULL  -- 设置删除保护 (ON DELETE SET NULL) 防止误删部门/岗位导致数据丢失
                                           ON UPDATE CASCADE,  -- 添加级联更新 (ON UPDATE CASCADE) 确保部门/岗位ID变更时自动更新

                                    -- 岗位外键约束
                                   CONSTRAINT `fk_employee_position`  -- 使用明确的约束名称
                                       FOREIGN KEY (`position_id`)
                                           REFERENCES `aioveu_position`(`position_id`)
                                           ON DELETE SET NULL  -- 设置删除保护 (ON DELETE SET NULL) 防止误删部门/岗位导致数据丢失
                                           ON UPDATE CASCADE,  -- 添加级联更新 (ON UPDATE CASCADE) 确保部门/岗位ID变更时自动更新

                                    -- 索引优化
                                   INDEX `idx_name` (`name`),  -- 姓名索引：支持快速按姓名查询
                                   INDEX `idx_dept_position` (`dept_id`, `position_id`),  -- 部门+岗位复合索引：优化组织架构查询
                                   INDEX `idx_hire_date` (`hire_date`)  -- 入职日期索引：加速工龄计算和入职时间分析




)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci
    COMMENT='员工信息表';

-- 插入规范的员工数据
INSERT INTO `aioveu_employee` (
    `emp_code`, `name`, `gender`, `birth_date`, `id_card`,
    `phone`, `email`, `dept_id`, `position_id`, `hire_date`, `salary`, `status`
)
VALUES
    ('EMP1001', '李白', 1, '1990-05-20', '410823199005201234',
     '13061656199', 'libai@aioveu.com', 1, 1, '2020-03-15', 20000.00, 1),

    ('EMP1002', '杜甫', 1, '1988-11-12', '410823198811121235',
     '13061656198', 'dufu@aioveu.com', 1, 2, '2019-07-22', 30000.00, 1),

    ('EMP1003', '李清照', 0, '1995-02-28', '410823199502281236',
     '13061656197', 'liqingzhao@aioveu.com', 2, 5, '2021-01-10', 50000.00, 1),

    ('EMP1004', '白居易', 1, '1992-08-15', '410823199208151237',
     '13061656196', 'baijuyi@aioveu.com', 3, 6, '2022-05-30', 50000.00, 1),

    ('EMP1005', '苏轼', 1, '1993-04-18', '410823199304181238',
     '13061656195', 'sushi@aioveu.com', 4, 7, '2023-02-14', 35000.00, 1);

-- 员工信息视图

# CREATE VIEW employee_detail_view AS
# SELECT
#     e.employee_id,
#     e.emp_code,
#     e.name,
#     CASE e.gender
#         WHEN 0 THEN '女'
#         WHEN 1 THEN '男'
#         ELSE '未知'
#         END AS gender,
#     e.birth_date,
#     TIMESTAMPDIFF(YEAR, e.birth_date, CURDATE()) AS age,
#     e.id_card,
#     e.phone,
#     e.email,
#     d.dept_name,
#     p.position_name,
#     e.hire_date,
#     TIMESTAMPDIFF(YEAR, e.hire_date, CURDATE()) AS seniority,
#     e.salary,
#     CASE e.status
#         WHEN 0 THEN '离职'
#         WHEN 1 THEN '在职'
#         WHEN 2 THEN '休假'
#         WHEN 3 THEN '实习'
#         ELSE '未知'
#         END AS status,
#     e.create_time,
#     e.update_time
# FROM aioveu_employee e
#          LEFT JOIN aioveu_department d ON e.dept_id = d.dept_id
#          LEFT JOIN aioveu_position p ON e.position_id = p.position_id;
#
# -- 使用视图查询
# SELECT * FROM employee_detail_view WHERE dept_name = '技术研发中心';

-- 员工状态管理

-- 员工转正
# UPDATE aioveu_employee
# SET status = 1
# WHERE status = 3
#   AND hire_date <= DATE_SUB(CURDATE(), INTERVAL 3 MONTH);
#
# -- 员工离职处理
# UPDATE aioveu_employee
# SET status = 0,
#     dept_id = NULL,
#     position_id = NULL
# WHERE employee_id = ?;
#
# -- 员工部门调动
# UPDATE aioveu_employee
# SET dept_id = ?,
#     position_id = ?,
#     update_time = CURRENT_TIMESTAMP
# WHERE employee_id = ?;

-- 数据完整性检查
-- 检查无效部门关联
# SELECT e.*
# FROM aioveu_employee e
#          LEFT JOIN aioveu_department d ON e.dept_id = d.dept_id
# WHERE e.dept_id IS NOT NULL
#   AND d.dept_id IS NULL;
#
# -- 检查无效岗位关联
# SELECT e.*
# FROM aioveu_employee e
#          LEFT JOIN aioveu_position p ON e.position_id = p.position_id
# WHERE e.position_id IS NOT NULL
#   AND p.position_id IS NULL;
#
# -- 检查重复身份证
# SELECT id_card, COUNT(*)
# FROM aioveu_employee
# GROUP BY id_card
# HAVING COUNT(*) > 1;

-- 员工薪资历史
#
# CREATE TABLE employee_salary_history (
#                                          history_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
#                                          employee_id INT UNSIGNED NOT NULL,
#                                          old_salary DECIMAL(10,2),
#                                          new_salary DECIMAL(10,2),
#                                          change_date DATE NOT NULL,
#                                          reason VARCHAR(200),
#
#                                          FOREIGN KEY (employee_id)
#                                              REFERENCES aioveu_employee(employee_id)
# );
#
# -- 创建薪资变更触发器
# DELIMITER //
# CREATE TRIGGER salary_change_trigger
#     BEFORE UPDATE ON aioveu_employee
#     FOR EACH ROW
# BEGIN
#     IF OLD.salary <> NEW.salary THEN
#         INSERT INTO employee_salary_history
#         (employee_id, old_salary, new_salary, change_date)
#         VALUES
#             (OLD.employee_id, OLD.salary, NEW.salary, CURDATE());
#     END IF;
# END//
# DELIMITER ;

-- 员工晋升路径

# -- 添加职级字段
# ALTER TABLE aioveu_employee
#     ADD position_level TINYINT UNSIGNED COMMENT '当前职级';
#
# -- 创建晋升记录表
# CREATE TABLE employee_promotion (
#                                     promotion_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
#                                     employee_id INT UNSIGNED NOT NULL,
#                                     old_position_id INT UNSIGNED,
#                                     new_position_id INT UNSIGNED NOT NULL,
#                                     promotion_date DATE NOT NULL,
#                                     salary_before DECIMAL(10,2),
#                                     salary_after DECIMAL(10,2),
#
#                                     FOREIGN KEY (employee_id)
#                                         REFERENCES aioveu_employee(employee_id),
#                                     FOREIGN KEY (old_position_id)
#                                         REFERENCES aioveu_position(position_id),
#                                     FOREIGN KEY (new_position_id)
#                                         REFERENCES aioveu_position(position_id)
# );

-- 员工全生命周期管理

# -- 员工入职流程
# START TRANSACTION;
#
# -- 1. 添加员工记录
# INSERT INTO aioveu_employee (...) VALUES (...);
#
# -- 2. 创建系统账号
# INSERT INTO system_user (employee_id, username, password)
# VALUES (LAST_INSERT_ID(), ?, ?);
#
# -- 3. 分配初始权限
# INSERT INTO user_role (user_id, role_id)
# VALUES (LAST_INSERT_ID(), 3); -- 新员工角色
#
# COMMIT;
#
# -- 员工离职流程
# START TRANSACTION;
#
# -- 1. 标记离职状态
# UPDATE aioveu_employee
# SET status = 0
# WHERE employee_id = ?;
#
# -- 2. 停用系统账号
# UPDATE system_user
# SET is_active = 0
# WHERE employee_id = ?;
#
# -- 3. 记录离职信息
# INSERT INTO employee_offboarding
# (employee_id, offboarding_date, reason)
# VALUES (?, CURDATE(), ?);
#
# COMMIT;