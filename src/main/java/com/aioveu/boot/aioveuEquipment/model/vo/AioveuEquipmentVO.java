package com.aioveu.boot.aioveuEquipment.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 设备管理视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:50
 */
@Getter
@Setter
@Schema( description = "设备管理视图对象")
public class AioveuEquipmentVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "设备ID")
    private Integer id;
    @Schema(description = "资产编号")
    private String assetNo;
    @Schema(description = "设备名称")
    private String name;
    @Schema(description = "设备分类ID")
    private Long categoryId;

    /**
     * 设备分类名称
     */
    private String categoryName;

    @Schema(description = "设备型号")
    private String model;
    @Schema(description = "序列号")
    private String serialNo;
    @Schema(description = "位置ID")
    private Integer locationId;
    @Schema(description = "所属部门ID")
    private Long departmentId;

    private String departmentName;
    @Schema(description = "责任人")
    private Long responsiblePerson;

    /**
     * 员工姓名
     */
    private String responsiblePersonName;

    @Schema(description = "采购日期")
    private LocalDate purchaseDate;
    @Schema(description = "采购价格")
    private BigDecimal purchasePrice;
    @Schema(description = "保修期（月）")
    private Integer warrantyPeriod;
    @Schema(description = "维保周期（月）")
    private Integer maintenanceCycle;
    @Schema(description = "上次维保日期")
    private LocalDate lastMaintenance;
    @Schema(description = "下次维保日期")
    private LocalDate nextMaintenance;
    @Schema(description = "状态")
    private Integer status;
    @Schema(description = "备注")
    private String remark;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
