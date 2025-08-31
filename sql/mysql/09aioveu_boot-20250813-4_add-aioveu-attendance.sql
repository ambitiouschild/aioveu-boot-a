

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
                                    attendance_id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, -- 更规范的自增主键 UNSIGNED（非负整数）
                                    employee_id BIGINT UNSIGNED NOT NULL COMMENT '员工ID', -- UNSIGNED（非负整数）
                                    `date` DATE NOT NULL COMMENT '日期',  -- date是保留字，建议用反引号包裹
                                    checkin_time DATETIME COMMENT '上班打卡时间',
                                    checkout_time DATETIME COMMENT '下班打卡时间',
                                    work_hours DECIMAL(4,1) COMMENT '工作时长(小时)', -- 使用DECIMAL更精确 DECIMAL(4,1)精确到0.1小时
                                    status TINYINT COMMENT '考勤状态：0-正常，1-迟到，2-早退，3-缺勤，4-休假',
                                    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', --  审计字段添加 自动记录员工信息创建和更新时间
                                    `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', -- 支持员工信息变更追踪

#                                     PRIMARY KEY (attendance_id),  -- 显式声明主键  -- 唯一主键定义


                                    INDEX idx_date (`date`),  -- 按日期查询的索引 -- 按日期查询(高频操作) -- 移除了COMMENT声明 COMMENT '日期索引'  -- 使用日期字段更合理
                                    INDEX idx_check_time (checkin_time),  -- 如果需要时间查询
                                    INDEX idx_employee (employee_id), -- 按员工查询(可选)
                                    INDEX idx_status (status),       -- 按状态查询(可选)

                                    CONSTRAINT fk_attendance_employee -- 定义了一个外键约束，用于在数据库级别强制保持表之间的引用完整性
                                        -- 命名规范：fk子表名父表名（清晰表明约束关系）
                                    FOREIGN KEY (employee_id)
                                        -- 指定当前表（考勤表）中的 employee_id字段作为外键  该字段将引用另一个表中的主键
                                    REFERENCES aioveu_employee(employee_id)
                                        -- 指定被引用的父表是 aioveu_employee  引用的具体字段是该表的 employee_id（必须是父表的主键或唯一键）
                                    ON DELETE CASCADE  -- 级联删除  --当主表（aioveu_employee）中的某条记录被删除时,自动删除所有引用这条记录的子表（aioveu_attendance）记录
                                        -- 作用：保持数据一致性，避免出现无效的外键（即考勤记录指向一个不存在的员工）
                                    ON UPDATE RESTRICT  -- 限制更新  --当父表（aioveu_employee）中要更新被引用的字段（即 employee_id）时，如果子表（考勤表）中存在引用该 employee_id的记录，那么更新操作会被拒绝（即限制更新）
                                        -- 确保不会破坏外键约束。因为如果允许更新父表的主键，而子表的外键没有跟着更新，就会导致子表记录的外键值在父表中找不到对应的记录。所以RESTRICT选项会阻止这种更新。
-- 该约束确保考勤表(aioveu_attendance)中的每个employee_id值都对应员工表(aioveu_employee)中已存在的employee_id值。
    -- 无法在考勤表中添加不存在的员工ID
    -- 无法删除还有考勤记录的员工
    -- 保持数据关联性（每个考勤记录都对应真实员工）


-- 删除员工时，自动删除其考勤记录（级联删除）
-- 要更新员工的ID时，如果该员工有考勤记录，则不允许更新（限制更新），除非先处理掉子表的记录。
-- 还有其他选项，例如 ON UPDATE CASCADE（级联更新）表示父表的主键更新时，子表的外键跟着更新。但是这里选择了RESTRICT，是为了避免意外更改员工ID导致数据混乱。

    -- MySQL 不允许在索引定义中使用 COMMENT 子句。这是导致语法错误的核心原因。  原索引建立在 checkin_time(打卡时间)上，但查询通常按日期(date)筛选
    -- 将索引改为建立在 date字段（日期字段）上效率更高
    -- 如果需要时间范围查询，可单独为 checkin_time创建索引
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='考勤表';


INSERT INTO `aioveu_attendance` VALUES(1,1,'2025-08-15','2025-08-15 09:00:00','2025-08-15 17:30:00',8,0,'2025-08-15 09:00:00','2025-08-15 09:00:00');
INSERT INTO `aioveu_attendance` VALUES(2,2,'2025-08-15','2025-08-15 09:00:00','2025-08-15 17:30:00',8,0,'2025-08-15 09:00:00','2025-08-15 09:00:00');
INSERT INTO `aioveu_attendance` VALUES(3,3,'2025-08-15','2025-08-15 09:00:00','2025-08-15 17:30:00',8,0,'2025-08-15 09:00:00','2025-08-15 09:00:00');
INSERT INTO `aioveu_attendance` VALUES(4,4,'2025-08-15','2025-08-15 09:00:00','2025-08-15 17:30:00',8,0,'2025-08-15 09:00:00','2025-08-15 09:00:00');
-- 不指定ID，让系统自动生成
INSERT INTO `aioveu_attendance` (employee_id, date, checkin_time, checkout_time, work_hours, status,create_time, update_time)
VALUES(1,'2025-08-15','2025-08-15 09:00:00','2025-08-15 17:30:00',8,0, '2025-08-15 09:00:00','2025-08-15 09:00:00');