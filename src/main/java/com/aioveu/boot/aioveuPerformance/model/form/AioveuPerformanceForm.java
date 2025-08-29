package com.aioveu.boot.aioveuPerformance.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

/**
 * 员工绩效考评表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 22:48
 */
@Getter
@Setter
@Schema(description = "员工绩效考评表单对象")
public class AioveuPerformanceForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "员工ID")
    private Long employeeId;

    /**
     * 员工姓名
     */
    private String employeeName;

    @Schema(description = "考核年份")
    @NotNull(message = "考核年份不能为空")
    private Integer periodYear;

    @Schema(description = "考核季度(1-4)")
    @NotNull(message = "考核季度(1-4)不能为空")
    private Integer periodQuarter;

    @Schema(description = "KPI评分(1-100分)")
    @NotNull(message = "KPI评分(1-100分)不能为空")
    // 将Integer改为BigDecimal
    private BigDecimal kpiScore;

    @Schema(description = "生产率百分比(%)")
    @NotNull(message = "生产率百分比(%)不能为空")
    private BigDecimal productivity;

    @Schema(description = "主管评语")
    @Size(max=65535, message="主管评语长度不能超过65535个字符")
    private String review;

//    @Schema(description = "绩效等级")
//    @Size(max=10, message="绩效等级长度不能超过10个字符")
//    private String performanceGrade;


}
