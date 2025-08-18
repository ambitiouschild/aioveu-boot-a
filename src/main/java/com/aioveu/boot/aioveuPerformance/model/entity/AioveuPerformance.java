package com.aioveu.boot.aioveuPerformance.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 员工绩效考评实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 22:48
 */
@Getter
@Setter
@TableName("aioveu_performance")
public class AioveuPerformance extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    private Integer employeeId;
    /**
     * 考核年份
     */
    private Integer periodYear;
    /**
     * 考核季度(1-4)
     */
    private Integer periodQuarter;
    /**
     * KPI评分(1-100分)
     在表单对象和实体类中都使用BigDecimal
     避免类型转换问题
     */
    private BigDecimal kpiScore;
    /**
     * 生产率百分比(%)
     */
    private BigDecimal productivity;
    /**
     * 主管评语
     */
    private String review;
    /**
     * 绩效等级
     * 在实体类中，将生成列标记为只读，避免MyBatis-Plus在更新时包含该字段
     * insertStrategy = FieldStrategy.NEVER：插入时忽略该字段
     * updateStrategy = FieldStrategy.NEVER：更新时忽略该字段
     */
//    @TableField(value = "performance_grade", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private String performanceGrade; // 普通字段，不再是生成列
}
