package com.aioveu.boot.aioveu.controller;

import com.aioveu.boot.aioveu.service.AioveuProcurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveu.model.form.AioveuProcurementForm;
import com.aioveu.boot.aioveu.model.query.AioveuProcurementQuery;
import com.aioveu.boot.aioveu.model.vo.AioveuProcurementVO;
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
 * 采购流程前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:07
 */
@Tag(name = "采购流程接口")
@RestController
@RequestMapping("/api/v1/aioveu-procurement")
@RequiredArgsConstructor
public class AioveuProcurementController  {

    private final AioveuProcurementService aioveuProcurementService;

    @Operation(summary = "采购流程分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveu:aioveu-procurement:query')")
    public PageResult<AioveuProcurementVO> getAioveuProcurementPage(AioveuProcurementQuery queryParams ) {
        IPage<AioveuProcurementVO> result = aioveuProcurementService.getAioveuProcurementPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增采购流程")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveu:aioveu-procurement:add')")
    public Result<Void> saveAioveuProcurement(@RequestBody @Valid AioveuProcurementForm formData ) {
        boolean result = aioveuProcurementService.saveAioveuProcurement(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取采购流程表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveu:aioveu-procurement:edit')")
    public Result<AioveuProcurementForm> getAioveuProcurementForm(
        @Parameter(description = "采购流程ID") @PathVariable Long id
    ) {
        AioveuProcurementForm formData = aioveuProcurementService.getAioveuProcurementFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改采购流程")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveu:aioveu-procurement:edit')")
    public Result<Void> updateAioveuProcurement(
            @Parameter(description = "采购流程ID") @PathVariable Long id,
            @RequestBody @Validated AioveuProcurementForm formData
    ) {
        boolean result = aioveuProcurementService.updateAioveuProcurement(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除采购流程")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveu:aioveu-procurement:delete')")
    public Result<Void> deleteAioveuProcurements(
        @Parameter(description = "采购流程ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuProcurementService.deleteAioveuProcurements(ids);
        return Result.judge(result);
    }
}
