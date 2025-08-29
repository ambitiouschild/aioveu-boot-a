package com.aioveu.boot.aioveuCustomer.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 客户信息视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:42
 */
@Getter
@Setter
@Schema( description = "客户信息视图对象")
public class AioveuCustomerVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "客户ID")
    private Integer id;
    @Schema(description = "客户编号")
    private String customerNo;
    @Schema(description = "客户名称")
    private String name;
    @Schema(description = "客户类型")
    private Integer type;
//    @Schema(description = "行业分类ID")
//    private Integer industryId;
    @Schema(description = "信用等级")
    private Integer creditRating;
    @Schema(description = "客户状态")
    private Integer customerStatus;
    @Schema(description = "客户地址")
    private String address;
    @Schema(description = "所在城市")
    private String city;
    @Schema(description = "所在省份")
    private String province;
    @Schema(description = "国家")
    private String country;
    @Schema(description = "邮政编码")
    private String postalCode;
    @Schema(description = "联系电话")
    private String phone;
    @Schema(description = "电子邮箱")
    private String email;
    @Schema(description = "网址")
    private String website;
    @Schema(description = "税号")
    private String taxId;
    @Schema(description = "银行账号")
    private String bankAccount;
    @Schema(description = "开户行")
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


    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
