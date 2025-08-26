package com.aioveu.boot.aioveuWarehouse.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "仓库选项")
public class WarehouseOptionVO {

    private Long warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 带参数的构造函数
     *
     * @param warehouseId 仓库ID
     * @param warehouseName 仓库名称
     */
    public WarehouseOptionVO(Long warehouseId, String warehouseName) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
    }
}


