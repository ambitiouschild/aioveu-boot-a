
-- 在每个SQL脚本开头指定要使用的数据库
use aioveu_boot;
-- 禁用外键检查

SET FOREIGN_KEY_CHECKS = 0;

-- 1. 部门表外键约束
ALTER TABLE `aioveu_department`
#     ADD CONSTRAINT `fk_department_parent`
#         FOREIGN KEY (`parent_dept_id`)
#             REFERENCES `aioveu_department`(`dept_id`)
#             ON DELETE SET NULL
#             ON UPDATE CASCADE,
ADD CONSTRAINT `fk_department_manager`
    FOREIGN KEY (`manager_id`)
    REFERENCES `aioveu_employee`(`employee_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

# -- 2. 员工表外键约束
# ALTER TABLE `aioveu_employee`
#     ADD CONSTRAINT `fk_employee_department`
#         FOREIGN KEY (`dept_id`)
#             REFERENCES `aioveu_department`(`dept_id`)
#             ON DELETE SET NULL
#             ON UPDATE CASCADE,
# ADD CONSTRAINT `fk_employee_position`
#     FOREIGN KEY (`position_id`)
#     REFERENCES `aioveu_position`(`position_id`)
#     ON DELETE SET NULL
#     ON UPDATE CASCADE;
#
# -- 3. 岗位表外键约束
# ALTER TABLE `aioveu_position`
#     ADD CONSTRAINT `fk_position_department`
#         FOREIGN KEY (`dept_id`)
#             REFERENCES `aioveu_department`(`dept_id`)
#             ON DELETE SET NULL
#             ON UPDATE CASCADE;
#
# -- 4. 考勤表外键约束
# ALTER TABLE `aioveu_attendance`
#     ADD CONSTRAINT `fk_attendance_employee`
#         FOREIGN KEY (`employee_id`)
#             REFERENCES `aioveu_employee`(`employee_id`)
#             ON DELETE CASCADE
#             ON UPDATE CASCADE;
#
# -- 5. 绩效表外键约束
# ALTER TABLE `aioveu_performance`
#     ADD CONSTRAINT `fk_performance_employee`
#         FOREIGN KEY (`employee_id`)
#             REFERENCES `aioveu_employee`(`employee_id`)
#             ON DELETE CASCADE
#             ON UPDATE CASCADE;
#
# -- 6. 工资表外键约束
# ALTER TABLE `aioveu_salary`
#     ADD CONSTRAINT `fk_salary_employee`
#         FOREIGN KEY (`employee_id`)
#             REFERENCES `aioveu_employee`(`employee_id`)
#             ON DELETE RESTRICT
#             ON UPDATE CASCADE;

-- 启用外键检查
SET FOREIGN_KEY_CHECKS = 1;