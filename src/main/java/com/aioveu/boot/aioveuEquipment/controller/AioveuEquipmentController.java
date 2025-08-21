package com.aioveu.boot.aioveuEquipment.controller;

import com.aioveu.boot.aioveuEquipment.service.AioveuEquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuEquipment.model.form.AioveuEquipmentForm;
import com.aioveu.boot.aioveuEquipment.model.query.AioveuEquipmentQuery;
import com.aioveu.boot.aioveuEquipment.model.vo.AioveuEquipmentVO;
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
 * 设备管理前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:50
 */
@Tag(name = "设备管理接口")
@RestController
@RequestMapping("/api/v1/aioveu-equipment")
@RequiredArgsConstructor
public class AioveuEquipmentController  {

    private final AioveuEquipmentService aioveuEquipmentService;

    @Operation(summary = "设备管理分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuEquipment:aioveu-equipment:query')")
    public PageResult<AioveuEquipmentVO> getAioveuEquipmentPage(AioveuEquipmentQuery queryParams ) {
        IPage<AioveuEquipmentVO> result = aioveuEquipmentService.getAioveuEquipmentPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增设备管理")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuEquipment:aioveu-equipment:add')")
    public Result<Void> saveAioveuEquipment(@RequestBody @Valid AioveuEquipmentForm formData ) {
        boolean result = aioveuEquipmentService.saveAioveuEquipment(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取设备管理表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuEquipment:aioveu-equipment:edit')")
    public Result<AioveuEquipmentForm> getAioveuEquipmentForm(
        @Parameter(description = "设备管理ID") @PathVariable Long id
    ) {
        AioveuEquipmentForm formData = aioveuEquipmentService.getAioveuEquipmentFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改设备管理")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuEquipment:aioveu-equipment:edit')")
    public Result<Void> updateAioveuEquipment(
            @Parameter(description = "设备管理ID") @PathVariable Long id,
            @RequestBody @Validated AioveuEquipmentForm formData
    ) {
        boolean result = aioveuEquipmentService.updateAioveuEquipment(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除设备管理")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuEquipment:aioveu-equipment:delete')")
    public Result<Void> deleteAioveuEquipments(
        @Parameter(description = "设备管理ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuEquipmentService.deleteAioveuEquipments(ids);
        return Result.judge(result);
    }
}
