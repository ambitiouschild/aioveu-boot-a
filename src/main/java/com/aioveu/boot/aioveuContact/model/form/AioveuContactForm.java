package com.aioveu.boot.aioveuContact.model.form;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

/**
 * 客户联系人表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:59
 */
@Getter
@Setter
@Schema(description = "客户联系人表单对象")
public class AioveuContactForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "客户ID")
    private Long customerId;

    private String customerName;

    @Schema(description = "联系人姓名")
    @NotBlank(message = "联系人姓名不能为空")
    @Size(max=50, message="联系人姓名长度不能超过50个字符")
    private String name;

    @Schema(description = "职位")
    @Size(max=50, message="职位长度不能超过50个字符")
    private String position;

    @Schema(description = "部门")
    @Size(max=50, message="部门长度不能超过50个字符")
    private String department;

    @Schema(description = "手机号码")
    @Size(max=20, message="手机号码长度不能超过20个字符")
    private String mobile;

    @Schema(description = "办公电话")
    @Size(max=20, message="办公电话长度不能超过20个字符")
    private String phone;

    @Schema(description = "电子邮箱")
    @Size(max=100, message="电子邮箱长度不能超过100个字符")
    private String email;

    @Schema(description = "微信号")
    @Size(max=50, message="微信号长度不能超过50个字符")
    private String wechat;

    @Schema(description = "是否是主要联系人：0-否，1-是")
    @NotNull(message = "是否是主要联系人：0-否，1-是不能为空")
    private Integer isPrimary;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "生日")
    private LocalDate birthday;

    @Schema(description = "兴趣爱好")
    @Size(max=200, message="兴趣爱好长度不能超过200个字符")
    private String hobbies;

    @Schema(description = "备注")
    @Size(max=65535, message="备注长度不能超过65535个字符")
    private String notes;


}
