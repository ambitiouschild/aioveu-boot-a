package com.aioveu.boot.aioveuInventory.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 库存信息实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:56
 */
@Getter
@Setter
@TableName("aioveu_inventory")
public class AioveuInventory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库ID
     */
    private Integer warehouseId;
    /**
     * 物资ID
     */
    private Integer materialId;
    /**
     * 当前库存数量
     */
    private BigDecimal quantity;
    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 生产日期
     */
    private LocalDate productionDate;
    /**
     * 有效期至
     */
    private LocalDate expiryDate;
    /**
     * 最后入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastInbound;
    /**
     * 最后出库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastOutbound;
}
