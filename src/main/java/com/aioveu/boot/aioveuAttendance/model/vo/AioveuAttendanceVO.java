package com.aioveu.boot.aioveuAttendance.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 考勤信息视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 21:52
 */
@Getter
@Setter
@Schema( description = "考勤信息视图对象")
public class AioveuAttendanceVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "考勤ID")
    private Integer attendanceId;
    @Schema(description = "员工ID")
    private Long employeeId;
    @Schema(description = "日期")
    private LocalDate date;
    @Schema(description = "上班打卡时间")
    private LocalDateTime checkinTime;
    @Schema(description = "下班打卡时间")
    private LocalDateTime checkoutTime;
    @Schema(description = "工作时长(小时)")
    private BigDecimal workHours;
    @Schema(description = "考勤状态：0-正常，1-迟到，2-早退，3-缺勤，4-休假")
    private Integer status;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;


    /**
     * 员工姓名
     */
    private String employeeName;
}
