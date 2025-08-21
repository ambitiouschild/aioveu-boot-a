package com.aioveu.boot.aioveuEquipment.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 设备管理实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:50
 */
@Getter
@Setter
@TableName("aioveu_equipment")
public class AioveuEquipment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产编号
     */
    private String assetNo;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备分类ID
     */
    private Integer categoryId;
    /**
     * 设备型号
     */
    private String model;
    /**
     * 序列号
     */
    private String serialNo;
    /**
     * 位置ID
     */
    private Integer locationId;
    /**
     * 所属部门ID
     */
    private Integer departmentId;
    /**
     * 责任人
     */
    private Integer responsiblePerson;
    /**
     * 采购日期
     */
    private LocalDate purchaseDate;
    /**
     * 采购价格
     */
    private BigDecimal purchasePrice;
    /**
     * 保修期（月）
     */
    private Integer warrantyPeriod;
    /**
     * 维保周期（月）
     */
    private Integer maintenanceCycle;
    /**
     * 上次维保日期
     */
    private LocalDate lastMaintenance;
    /**
     * 下次维保日期
     */
    private LocalDate nextMaintenance;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
}
