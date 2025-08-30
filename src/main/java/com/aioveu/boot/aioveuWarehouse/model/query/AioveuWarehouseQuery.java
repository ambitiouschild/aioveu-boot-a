package com.aioveu.boot.aioveuWarehouse.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 仓库信息分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:42
 */
@Schema(description ="仓库信息查询对象")
@Getter
@Setter
public class AioveuWarehouseQuery extends BasePageQuery {

    @Schema(description = "仓库ID")
    private Integer id;
    @Schema(description = "仓库名称")
    private String name;
    @Schema(description = "仓库编码")
    private String code;
    @Schema(description = "仓库位置")
    private String location;
    @Schema(description = "负责人ID")
    private Long managerId;

    private String managerName;
    @Schema(description = "联系电话")
    private String contactPhone;
    @Schema(description = "启用状态")
    private Integer isActive;
}
