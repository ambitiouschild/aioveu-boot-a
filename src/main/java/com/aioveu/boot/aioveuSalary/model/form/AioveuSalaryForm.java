package com.aioveu.boot.aioveuSalary.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

/**
 * 员工工资明细表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 17:14
 */
@Getter
@Setter
@Schema(description = "员工工资明细表单对象")
public class AioveuSalaryForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "员工ID")
    private Long employeeId;

    /**
     * 员工姓名
     */
    private String employeeName;

    @Schema(description = "工资周期（月份）")
    @NotNull(message = "工资周期（月份）不能为空")
    private LocalDate salaryPeriod;

    @Schema(description = "基本工资")
    @NotNull(message = "基本工资不能为空")
    private BigDecimal baseSalary;

    @Schema(description = "绩效奖金")
    private BigDecimal performanceBonus;

    @Schema(description = "加班补贴")
    private BigDecimal overtimePay;

    @Schema(description = "各类津贴")
    private BigDecimal allowances;

    @Schema(description = "社保扣除")
    private BigDecimal socialSecurity;

    @Schema(description = "住房公积金")
    private BigDecimal housingFund;

    @Schema(description = "个人所得税")
    private BigDecimal tax;

    @Schema(description = "其他扣款")
    private BigDecimal otherDeductions;

    @Schema(description = "实发工资")
    @NotNull(message = "实发工资不能为空")
    private BigDecimal netSalary;

    @Schema(description = "实际发放日期")
    private LocalDate paymentDate;

    @Schema(description = "发放状态")
    private Integer paymentStatus;


}
