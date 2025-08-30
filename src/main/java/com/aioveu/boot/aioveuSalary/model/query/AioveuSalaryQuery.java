package com.aioveu.boot.aioveuSalary.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

/**
 * 员工工资明细分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 17:14
 */
@Schema(description ="员工工资明细查询对象")
@Getter
@Setter
public class AioveuSalaryQuery extends BasePageQuery {

    @Schema(description = "工资记录ID")
    private Long id;
    @Schema(description = "员工ID")
    private Long employeeId;

    private String employeeName;

    @Schema(description = "工资周期（月份）")
    private List<String> salaryPeriod;
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
    private List<String> paymentDate;
    @Schema(description = "发放状态")
    private Integer paymentStatus;
    @Schema(description = "创建时间")
    private List<String> createTime;
    @Schema(description = "更新时间")
    private List<String> updateTime;
}
