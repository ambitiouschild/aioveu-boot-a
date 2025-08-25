package com.aioveu.boot.aioveuPerformance.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 员工绩效考评视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 22:48
 */
@Getter
@Setter
@Schema( description = "员工绩效考评视图对象")
public class AioveuPerformanceVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "绩效记录ID")
    private Long id;
    @Schema(description = "员工ID")
    private Long employeeId;
    @Schema(description = "考核年份")
    /**
     * 员工姓名
     */
    private String employeeName;

    private Integer periodYear;
    @Schema(description = "考核季度(1-4)")
    private Integer periodQuarter;
    @Schema(description = "KPI评分(1-100分)")
    private Integer kpiScore;
    @Schema(description = "生产率百分比(%)")
    private BigDecimal productivity;
    @Schema(description = "主管评语")
    private String review;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "绩效等级")
    private String performanceGrade;
}
