package com.aioveu.boot.aioveuAttendance.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

/**
 * 考勤信息分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 21:52
 */
@Schema(description ="考勤信息查询对象")
@Getter
@Setter
public class AioveuAttendanceQuery extends BasePageQuery {

    @Schema(description = "考勤ID")
    private Integer attendanceId;
    @Schema(description = "员工ID")
    private Long employeeId;

    private String employeeName;

    @Schema(description = "日期")
    private List<String> date;
    @Schema(description = "上班打卡时间")
    private List<String> checkinTime;
    @Schema(description = "下班打卡时间")
    private List<String> checkoutTime;
    @Schema(description = "工作时长(小时)")
    private BigDecimal workHours;
    @Schema(description = "考勤状态：0-正常，1-迟到，2-早退，3-缺勤，4-休假")
    private Integer status;
    @Schema(description = "创建时间")
    private List<String> createTime;
    @Schema(description = "更新时间")
    private List<String> updateTime;
}
