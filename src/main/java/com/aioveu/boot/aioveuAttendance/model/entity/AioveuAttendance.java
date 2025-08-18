package com.aioveu.boot.aioveuAttendance.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.aioveu.boot.common.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 考勤信息实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 21:52
 */
@Getter
@Setter
@TableName("aioveu_attendance")
public class AioveuAttendance {

    private static final long serialVersionUID = 1L;

    /**
     * 考勤ID
     */
    @TableId(value = "attendance_id", type = IdType.AUTO)
    private Long attendanceId;
    /**
     * 员工ID
     */
    private Integer employeeId;
    /**
     * 日期
     */
    private LocalDate date;
    /**
     * 上班打卡时间
     */
    private LocalDateTime checkinTime;
    /**
     * 下班打卡时间
     */
    private LocalDateTime checkoutTime;
    /**
     * 工作时长(小时)
     */
    private BigDecimal workHours;
    /**
     * 考勤状态：0-正常，1-迟到，2-早退，3-缺勤，4-休假
     */
    private Integer status;

    /**
     * 创建时间 在实体类中添加双重注解
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //处理 JSON 序列化/反序列化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //处理表单参数绑定 两者使用相同的日期格式
    private LocalDateTime createTime;

    /**
     * 更新时间 在实体类中添加双重注解
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //处理 JSON 序列化/反序列化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //处理表单参数绑定 两者使用相同的日期格式
    private LocalDateTime updateTime;
}
