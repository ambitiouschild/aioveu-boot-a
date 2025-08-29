package com.aioveu.boot.aioveuInventory.model.form;

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
 * 库存信息表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:56
 */
@Getter
@Setter
@Schema(description = "库存信息表单对象")
public class AioveuInventoryForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "仓库ID")
//    @NotNull(message = "仓库ID不能为空")
    private Long warehouseId;

    private String warehouseName;

    @Schema(description = "物资ID")
    private Long materialId;

    private String materialName;

    @Schema(description = "当前库存数量")
    @NotNull(message = "当前库存数量不能为空")
    private BigDecimal quantity;

    @Schema(description = "批次号")
    @Size(max=50, message="批次号长度不能超过50个字符")
    private String batchNumber;

    @Schema(description = "生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期至")
    private LocalDate expiryDate;

    @Schema(description = "最后入库时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastInbound;

    @Schema(description = "最后出库时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastOutbound;


}
