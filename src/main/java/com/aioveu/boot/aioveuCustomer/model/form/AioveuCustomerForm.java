package com.aioveu.boot.aioveuCustomer.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

/**
 * 客户信息表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:42
 */
@Getter
@Setter
@Schema(description = "客户信息表单对象")
public class AioveuCustomerForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "客户编号")
    @NotBlank(message = "客户编号不能为空")
    @Size(max=50, message="客户编号长度不能超过50个字符")
    private String customerNo;

    @Schema(description = "客户名称")
    @NotBlank(message = "客户名称不能为空")
    @Size(max=100, message="客户名称长度不能超过100个字符")
    private String name;

    @Schema(description = "客户类型")
    @NotNull(message = "客户类型不能为空")
    private Integer type;

    @Schema(description = "行业分类ID")
    private Integer industryId;

    @Schema(description = "信用等级")
    @NotNull(message = "信用等级不能为空")
    private Integer creditRating;

    @Schema(description = "客户状态")
    @NotNull(message = "客户状态不能为空")
    private Integer customerStatus;

    @Schema(description = "客户地址")
    @Size(max=200, message="客户地址长度不能超过200个字符")
    private String address;

    @Schema(description = "所在城市")
    @Size(max=50, message="所在城市长度不能超过50个字符")
    private String city;

    @Schema(description = "所在省份")
    @Size(max=50, message="所在省份长度不能超过50个字符")
    private String province;

    @Schema(description = "国家")
    @Size(max=50, message="国家长度不能超过50个字符")
    private String country;

    @Schema(description = "邮政编码")
    @Size(max=20, message="邮政编码长度不能超过20个字符")
    private String postalCode;

    @Schema(description = "联系电话")
    @Size(max=20, message="联系电话长度不能超过20个字符")
    private String phone;

    @Schema(description = "电子邮箱")
    @Size(max=100, message="电子邮箱长度不能超过100个字符")
    private String email;

    @Schema(description = "网址")
    @Size(max=100, message="网址长度不能超过100个字符")
    private String website;

    @Schema(description = "税号")
    @Size(max=50, message="税号长度不能超过50个字符")
    private String taxId;

    @Schema(description = "银行账号")
    @Size(max=50, message="银行账号长度不能超过50个字符")
    private String bankAccount;

    @Schema(description = "开户行")
    @Size(max=100, message="开户行长度不能超过100个字符")
    private String bankName;

    @Schema(description = "年营业额")
    private BigDecimal annualRevenue;

    @Schema(description = "员工人数")
    private Integer employeeCount;

    @Schema(description = "成立日期")
    private LocalDate establishedDate;

    @Schema(description = "客户来源")
    private Integer source;

    @Schema(description = "销售负责人ID")
    private Long salesRepId;

    /**
     * 销售负责人姓名
     */
    private String salesRepName;


}
