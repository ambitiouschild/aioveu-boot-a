package com.aioveu.boot.aioveuInbound.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 入库信息实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:18
 */
@Getter
@Setter
@TableName("aioveu_inbound")
public class AioveuInbound extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 入库单号
     */
    private String inboundNo;
    /**
     * 物资ID
     */
    private Integer materialId;
    /**
     * 仓库ID
     */
    private Integer warehouseId;
    /**
     * 入库数量（支持小数计量）
     */
    private BigDecimal quantity;
    /**
     * 入库单价
     */
    private BigDecimal unitPrice;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
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
     * 供应商ID
     */
    private Integer supplierId;
    /**
     * 入库类型
     */
    private Integer inboundType;
    /**
     * 入库时间
     */
    private LocalDateTime inTime;
    /**
     * 操作员ID
     */
    private Integer operatorId;
    /**
     * 备注
     */
    private String remark;
}
