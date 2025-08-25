package com.aioveu.boot.aioveuWarehouse.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 仓库信息视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:42
 */
@Getter
@Setter
@Schema( description = "仓库信息视图对象")
public class AioveuWarehouseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "仓库ID")
    private Integer id;
    @Schema(description = "仓库名称")
    private String name;
    @Schema(description = "仓库编码")
    private String code;
    @Schema(description = "仓库位置")
    private String location;
    @Schema(description = "仓库面积")
    private BigDecimal area;
    @Schema(description = "存储容量")
    private BigDecimal capacity;
    @Schema(description = "负责人ID")
    private Long managerId;

    /**
     * 员工姓名
     */
    private String managerName;

    @Schema(description = "联系电话")
    private String contactPhone;
    @Schema(description = "仓库描述")
    private String description;
    @Schema(description = "启用状态")
    private Integer isActive;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
