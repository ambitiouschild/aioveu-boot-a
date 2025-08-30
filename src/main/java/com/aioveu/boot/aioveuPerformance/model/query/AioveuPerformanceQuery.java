package com.aioveu.boot.aioveuPerformance.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

/**
 * 员工绩效考评分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 22:48
 */
@Schema(description ="员工绩效考评查询对象")
@Getter
@Setter
public class AioveuPerformanceQuery extends BasePageQuery {

    @Schema(description = "绩效记录ID")
    private Long id;
    @Schema(description = "员工ID")
    private Long employeeId;

    private String employeeName;
    @Schema(description = "考核年份")
    private Integer periodYear;
    @Schema(description = "考核季度(1-4)")
    private Integer periodQuarter;
    @Schema(description = "KPI评分(1-100分)")
    private Integer kpiScore;
    @Schema(description = "生产率百分比(%)")
    private BigDecimal productivity;
    @Schema(description = "创建时间")
    private List<String> createTime;
    @Schema(description = "更新时间")
    private List<String> updateTime;
    @Schema(description = "绩效等级")
    private String performanceGrade;
}
