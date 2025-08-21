package com.aioveu.boot.aioveuInventory.controller;

import com.aioveu.boot.aioveuInventory.service.AioveuInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuInventory.model.form.AioveuInventoryForm;
import com.aioveu.boot.aioveuInventory.model.query.AioveuInventoryQuery;
import com.aioveu.boot.aioveuInventory.model.vo.AioveuInventoryVO;
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

/**
 * 库存信息前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:56
 */
@Tag(name = "库存信息接口")
@RestController
@RequestMapping("/api/v1/aioveu-inventory")
@RequiredArgsConstructor
public class AioveuInventoryController  {

    private final AioveuInventoryService aioveuInventoryService;

    @Operation(summary = "库存信息分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuInventory:aioveu-inventory:query')")
    public PageResult<AioveuInventoryVO> getAioveuInventoryPage(AioveuInventoryQuery queryParams ) {
        IPage<AioveuInventoryVO> result = aioveuInventoryService.getAioveuInventoryPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增库存信息")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuInventory:aioveu-inventory:add')")
    public Result<Void> saveAioveuInventory(@RequestBody @Valid AioveuInventoryForm formData ) {
        boolean result = aioveuInventoryService.saveAioveuInventory(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取库存信息表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuInventory:aioveu-inventory:edit')")
    public Result<AioveuInventoryForm> getAioveuInventoryForm(
        @Parameter(description = "库存信息ID") @PathVariable Long id
    ) {
        AioveuInventoryForm formData = aioveuInventoryService.getAioveuInventoryFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改库存信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuInventory:aioveu-inventory:edit')")
    public Result<Void> updateAioveuInventory(
            @Parameter(description = "库存信息ID") @PathVariable Long id,
            @RequestBody @Validated AioveuInventoryForm formData
    ) {
        boolean result = aioveuInventoryService.updateAioveuInventory(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除库存信息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuInventory:aioveu-inventory:delete')")
    public Result<Void> deleteAioveuInventorys(
        @Parameter(description = "库存信息ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuInventoryService.deleteAioveuInventorys(ids);
        return Result.judge(result);
    }
}
