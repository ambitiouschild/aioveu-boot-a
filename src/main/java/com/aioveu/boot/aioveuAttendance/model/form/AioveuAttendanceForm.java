package com.aioveu.boot.aioveuAttendance.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

/**
 * 考勤信息表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 21:52
 */
@Getter
@Setter
@Schema(description = "考勤信息表单对象")
public class AioveuAttendanceForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "员工ID")
    private Long employeeId;

    /**
     * 员工姓名
     */
    private String employeeName;

    @Schema(description = "日期")
    @NotNull(message = "日期不能为空")
    private LocalDate date;

    @Schema(description = "上班打卡时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkinTime;

    @Schema(description = "下班打卡时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkoutTime;

    @Schema(description = "工作时长(小时)")
    @NotNull(message = "工作时长(小时)不能为空")
    private BigDecimal workHours;

    @Schema(description = "考勤状态：0-正常，1-迟到，2-早退，3-缺勤，4-休假")
    @NotNull(message = "考勤状态：0-正常，1-迟到，2-早退，3-缺勤，4-休假不能为空")
    private Integer status;




}
