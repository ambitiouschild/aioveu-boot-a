package com.aioveu.boot.aioveuCustomer.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 客户信息实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:42
 */
@Getter
@Setter
@TableName("aioveu_customer")
public class AioveuCustomer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    private String customerNo;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 客户类型
     */
    private Integer type;
    /**
     * 行业分类ID
     */
    private Integer industryId;
    /**
     * 信用等级
     */
    private Integer creditRating;
    /**
     * 客户状态
     */
    private Integer customerStatus;
    /**
     * 客户地址
     */
    private String address;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 所在省份
     */
    private String province;
    /**
     * 国家
     */
    private String country;
    /**
     * 邮政编码
     */
    private String postalCode;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 网址
     */
    private String website;
    /**
     * 税号
     */
    private String taxId;
    /**
     * 银行账号
     */
    private String bankAccount;
    /**
     * 开户行
     */
    private String bankName;
    /**
     * 年营业额
     */
    private BigDecimal annualRevenue;
    /**
     * 员工人数
     */
    private Integer employeeCount;
    /**
     * 成立日期
     */
    private LocalDate establishedDate;
    /**
     * 客户来源
     */
    private Integer source;
    /**
     * 销售负责人ID
     */
    private Integer salesRepId;
}
