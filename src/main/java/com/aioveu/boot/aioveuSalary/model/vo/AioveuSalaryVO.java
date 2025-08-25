package com.aioveu.boot.aioveuSalary.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 员工工资明细视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 17:14
 */
@Getter
@Setter
@Schema( description = "员工工资明细视图对象")
public class AioveuSalaryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "工资记录ID")
    private Long id;
    @Schema(description = "员工ID")
    private Long employeeId;
    /**
     * 员工姓名
     */
    private String employeeName;

    @Schema(description = "工资周期（月份）")
    private LocalDate salaryPeriod;
    @Schema(description = "基本工资")
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
    private BigDecimal netSalary;
    @Schema(description = "实际发放日期")
    private LocalDate paymentDate;
    @Schema(description = "发放状态")
    private Integer paymentStatus;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
