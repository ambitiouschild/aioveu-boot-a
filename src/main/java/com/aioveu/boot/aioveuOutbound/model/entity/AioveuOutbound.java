package com.aioveu.boot.aioveuOutbound.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 出库记录实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:26
 */
@Getter
@Setter
@TableName("aioveu_outbound")
public class AioveuOutbound extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 出库单号
     */
    private String outboundNo;
    /**
     * 物资ID
     */
    private Integer materialId;
    /**
     * 仓库ID
     */
    private Integer warehouseId;
    /**
     * 出库数量
     */
    private BigDecimal quantity;
    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 出库时间
     */
    private LocalDateTime outTime;
    /**
     * 操作员ID
     */
    private Integer operatorId;
    /**
     * 领用人ID
     */
    private Integer recipientId;
    /**
     * 领用部门ID
     */
    private Integer departmentId;
    /**
     * 用途说明
     */
    private String purpose;
    /**
     * 关联项目ID
     */
    private Integer projectId;
    /**
     * 状态
     */
    private Integer status;
}
