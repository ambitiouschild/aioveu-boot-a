package com.aioveu.boot.aioveuWarehouse.controller;

import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import com.aioveu.boot.aioveuWarehouse.model.vo.WarehouseOptionVO;
import com.aioveu.boot.aioveuWarehouse.service.AioveuWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuWarehouse.model.form.AioveuWarehouseForm;
import com.aioveu.boot.aioveuWarehouse.model.query.AioveuWarehouseQuery;
import com.aioveu.boot.aioveuWarehouse.model.vo.AioveuWarehouseVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.aioveu.boot.common.result.PageResult;
import com.aioveu.boot.common.result.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 仓库信息前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:42
 */
@Tag(name = "仓库信息接口")
@RestController
@RequestMapping("/api/v1/aioveu-warehouse")
@RequiredArgsConstructor
public class AioveuWarehouseController  {

    private final AioveuWarehouseService aioveuWarehouseService;

    @Operation(summary = "仓库信息分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuWarehouse:aioveu-warehouse:query')")
    public PageResult<AioveuWarehouseVO> getAioveuWarehousePage(AioveuWarehouseQuery queryParams ) {
        IPage<AioveuWarehouseVO> result = aioveuWarehouseService.getAioveuWarehousePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增仓库信息")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuWarehouse:aioveu-warehouse:add')")
    public Result<Void> saveAioveuWarehouse(@RequestBody @Valid AioveuWarehouseForm formData ) {
        boolean result = aioveuWarehouseService.saveAioveuWarehouse(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取仓库信息表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuWarehouse:aioveu-warehouse:edit')")
    public Result<AioveuWarehouseForm> getAioveuWarehouseForm(
        @Parameter(description = "仓库信息ID") @PathVariable Long id
    ) {
        AioveuWarehouseForm formData = aioveuWarehouseService.getAioveuWarehouseFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改仓库信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuWarehouse:aioveu-warehouse:edit')")
    public Result<Void> updateAioveuWarehouse(
            @Parameter(description = "仓库信息ID") @PathVariable Long id,
            @RequestBody @Validated AioveuWarehouseForm formData
    ) {
        boolean result = aioveuWarehouseService.updateAioveuWarehouse(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除仓库信息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuWarehouse:aioveu-warehouse:delete')")
    public Result<Void> deleteAioveuWarehouses(
        @Parameter(description = "仓库信息ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuWarehouseService.deleteAioveuWarehouses(ids);
        return Result.judge(result);
    }

    /**
     * 获取所有仓库列表（用于下拉选择框）
     * @return 仓库选项列表
     */
    @Operation(summary = "获取所有仓库列表（用于下拉选择框）")
    @GetMapping("/options")
    public Result<List<WarehouseOptionVO>> getAllWarehouseOptions() {

        List<WarehouseOptionVO> warehouses  = aioveuWarehouseService.getAllWarehouseOptions();

        return Result.success(warehouses);
    }
}
