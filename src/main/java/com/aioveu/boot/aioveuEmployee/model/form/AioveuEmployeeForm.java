package com.aioveu.boot.aioveuEmployee.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

/**
 * 员工信息表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 17:29
 */
@Getter
@Setter
@Schema(description = "员工信息表单对象")
public class AioveuEmployeeForm implements Serializable {

    //表单对象中不需要包含员工ID，更新操作应该通过路径参数传递ID
    @Serial
    private static final long serialVersionUID = 1L;


    @Schema(description = "员工编号")
    @NotBlank(message = "员工编号不能为空")
    @Size(max=20, message="员工编号长度不能超过20个字符")
    private String empCode;

    @Schema(description = "姓名")
    @NotBlank(message = "姓名不能为空")
    @Size(max=50, message="姓名长度不能超过50个字符")
    private String name;

    @Schema(description = "性别")
    @NotNull(message = "性别不能为空")
    private Integer gender;

    @Schema(description = "出生日期")
    private LocalDate birthDate;

    @Schema(description = "身份证号")
    @Size(max=20, message="身份证号长度不能超过20个字符")
    private String idCard;

    @Schema(description = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    @Size(max=20, message="手机号码长度不能超过20个字符")
    private String phone;

    @Schema(description = "邮箱")
    @Size(max=50, message="邮箱长度不能超过50个字符")
    private String email;

    @Schema(description = "所属部门")

    private Long deptId;

    /**
     * 部门名称
     */
    @NotNull(message = "所属部门不能为空")
    private String deptName;   // 新增：部门名，用于前端表单显示

    @Schema(description = "岗位ID")
    private Long positionId;

    /**
     * 岗位名称
     */
//    @NotNull(message = "岗位不存在")
    private String positionName; // 新增：部门名，用于前端表单显示

    @Schema(description = "入职日期")
    @NotNull(message = "入职日期不能为空")
    private LocalDate hireDate;

    @Schema(description = "基本薪资")
    @NotNull(message = "基本薪资不能为空")
    private BigDecimal salary;

    @Schema(description = "状态：0-离职，1-在职,2-休假,3-实习")
    @NotNull(message = "状态：0-离职，1-在职,2-休假,3-实习不能为空")
    private Integer status;


}
