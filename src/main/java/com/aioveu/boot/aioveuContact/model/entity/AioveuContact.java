package com.aioveu.boot.aioveuContact.model.entity;

import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

import java.time.LocalDate;

/**
 * 客户联系人实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:59
 */
@Getter
@Setter
@TableName("aioveu_contact")
public class AioveuContact extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 联系人姓名
     */
    private String name;

    /**
     * 客户ID
     */
    private Integer customerId;

    /**
     * 职位
     */
    private String position;
    /**
     * 部门
     */
    private String department;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 办公电话
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 微信号
     */
    private String wechat;
    /**
     * 是否是主要联系人：0-否，1-是
     */
    private Integer isPrimary;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 兴趣爱好
     */
    private String hobbies;
    /**
     * 备注
     */
    private String notes;
}
