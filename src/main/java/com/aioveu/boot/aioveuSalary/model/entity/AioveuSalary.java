package com.aioveu.boot.aioveuSalary.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 员工工资明细实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 17:14
 */
@Getter
@Setter
@TableName("aioveu_salary")
public class AioveuSalary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    private Integer employeeId;
    /**
     * 工资周期（月份）
     */
    private LocalDate salaryPeriod;
    /**
     * 基本工资
     */
    private BigDecimal baseSalary;
    /**
     * 绩效奖金
     */
    private BigDecimal performanceBonus;
    /**
     * 加班补贴
     */
    private BigDecimal overtimePay;
    /**
     * 各类津贴
     */
    private BigDecimal allowances;
    /**
     * 社保扣除
     */
    private BigDecimal socialSecurity;
    /**
     * 住房公积金
     */
    private BigDecimal housingFund;
    /**
     * 个人所得税
     */
    private BigDecimal tax;
    /**
     * 其他扣款
     */
    private BigDecimal otherDeductions;
    /**
     * 实发工资
     */
    private BigDecimal netSalary;
    /**
     * 实际发放日期
     */
    private LocalDate paymentDate;
    /**
     * 发放状态
     */
    private Integer paymentStatus;
}
