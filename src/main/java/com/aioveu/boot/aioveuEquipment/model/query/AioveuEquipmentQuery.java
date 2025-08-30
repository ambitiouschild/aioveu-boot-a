package com.aioveu.boot.aioveuEquipment.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 设备管理分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:50
 */
@Schema(description ="设备管理查询对象")
@Getter
@Setter
public class AioveuEquipmentQuery extends BasePageQuery {

    @Schema(description = "设备ID")
    private Integer id;
    @Schema(description = "资产编号")
    private String assetNo;
    @Schema(description = "设备名称")
    private String name;
    @Schema(description = "设备分类ID")
    private Long categoryId;

    private String categoryName;

    @Schema(description = "序列号")
    private String serialNo;
    @Schema(description = "责任人")
    private Long responsiblePerson;

    private String responsiblePersonName;
    @Schema(description = "状态")
    private Integer status;
}
