package com.aioveu.boot.aioveuEmployee.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.aioveu.boot.common.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 员工信息实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 17:29
 */
@Getter
@Setter
@TableName("aioveu_employee")
public class AioveuEmployee {

    private static final long serialVersionUID = 1L;

    /**
     1.getById(id)方法：
     •这里的 id对应实体类中的 employeeId字段
     •MyBatis-Plus 会根据 @TableId注解自动映射
     2.updateById(entity)方法：
     •根据实体类中的 @TableId字段（employeeId）进行更新
     •不需要显式指定主键字段名
     3.employeeId字段：
     •在实体类中定义为 Long类型
     •使用 @TableId注解指定数据库列名
     */
    @TableId(value = "employee_id", type = IdType.AUTO)
    private Long employeeId;
    /**
     * 员工编号
     */
    private String empCode;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 出生日期
     */
    private LocalDate birthDate;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 所属部门
     */
    private Integer deptId;
    /**
     * 岗位ID
     */
    private Integer positionId;
    /**
     * 入职日期
     */
    private LocalDate hireDate;
    /**
     * 基本薪资
     */
    private BigDecimal salary;
    /**
     * 状态：0-离职，1-在职,2-休假,3-实习
     */
    private Integer status;

    /**
     * 创建时间 在实体类中添加双重注解
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //处理 JSON 序列化/反序列化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //处理表单参数绑定 两者使用相同的日期格式
    private LocalDateTime createTime;

    /**
     * 更新时间 在实体类中添加双重注解
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //处理 JSON 序列化/反序列化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //处理表单参数绑定 两者使用相同的日期格式
    private LocalDateTime updateTime;
}
