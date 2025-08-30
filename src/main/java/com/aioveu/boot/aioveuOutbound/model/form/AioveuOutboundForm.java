package com.aioveu.boot.aioveuOutbound.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

/**
 * 出库记录表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:26
 */
@Getter
@Setter
@Schema(description = "出库记录表单对象")
public class AioveuOutboundForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "出库单号")
    @NotBlank(message = "出库单号不能为空")
    @Size(max=50, message="出库单号长度不能超过50个字符")
    private String outboundNo;

    @Schema(description = "物资ID")
    private Long materialId;

    private String materialName;

    @Schema(description = "仓库ID")
    private Long warehouseId;

    private String warehouseName;

    @Schema(description = "出库数量")
    @NotNull(message = "出库数量不能为空")
    private BigDecimal quantity;

    @Schema(description = "批次号")
    @Size(max=50, message="批次号长度不能超过50个字符")
    private String batchNumber;

    @Schema(description = "出库时间")
    @NotNull(message = "出库时间不能为空")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime outTime;

    @Schema(description = "操作员ID")
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

    private Long recipientId;

    /**
     * 领用人姓名
     */
    private String recipientName;


    @Schema(description = "领用部门ID")
    private Long departmentId;

    private String departmentName;


    @Schema(description = "用途说明")
    @Size(max=200, message="用途说明长度不能超过200个字符")
    private String purpose;

    @Schema(description = "关联项目ID")
    private Integer projectId;

    @Schema(description = "状态")
    @NotNull(message = "状态不能为空")
    private Integer status;


}
