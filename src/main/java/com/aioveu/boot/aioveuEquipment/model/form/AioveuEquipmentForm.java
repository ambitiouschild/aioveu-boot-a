package com.aioveu.boot.aioveuEquipment.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

/**
 * 设备管理表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:50
 */
@Getter
@Setter
@Schema(description = "设备管理表单对象")
public class AioveuEquipmentForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "资产编号")
    @NotBlank(message = "资产编号不能为空")
    @Size(max=50, message="资产编号长度不能超过50个字符")
    private String assetNo;

    @Schema(description = "设备名称")
    @NotBlank(message = "设备名称不能为空")
    @Size(max=100, message="设备名称长度不能超过100个字符")
    private String name;

    @Schema(description = "设备分类ID")
        private Long categoryId;

    private String categoryName;

    @Schema(description = "设备型号")
    @Size(max=50, message="设备型号长度不能超过50个字符")
    private String model;

    @Schema(description = "序列号")
    @Size(max=50, message="序列号长度不能超过50个字符")
    private String serialNo;

//    @Schema(description = "位置ID")
//    private Integer locationId;

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
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注")
    @Size(max=65535, message="备注长度不能超过65535个字符")
    private String remark;


}
