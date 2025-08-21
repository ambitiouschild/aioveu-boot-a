package com.aioveu.boot.aioveuOutbound.controller;

import com.aioveu.boot.aioveuOutbound.service.AioveuOutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuOutbound.model.form.AioveuOutboundForm;
import com.aioveu.boot.aioveuOutbound.model.query.AioveuOutboundQuery;
import com.aioveu.boot.aioveuOutbound.model.vo.AioveuOutboundVO;
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
 * 出库记录前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:26
 */
@Tag(name = "出库记录接口")
@RestController
@RequestMapping("/api/v1/aioveu-outbound")
@RequiredArgsConstructor
public class AioveuOutboundController  {

    private final AioveuOutboundService aioveuOutboundService;

    @Operation(summary = "出库记录分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuOutbound:aioveu-outbound:query')")
    public PageResult<AioveuOutboundVO> getAioveuOutboundPage(AioveuOutboundQuery queryParams ) {
        IPage<AioveuOutboundVO> result = aioveuOutboundService.getAioveuOutboundPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增出库记录")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuOutbound:aioveu-outbound:add')")
    public Result<Void> saveAioveuOutbound(@RequestBody @Valid AioveuOutboundForm formData ) {
        boolean result = aioveuOutboundService.saveAioveuOutbound(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取出库记录表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuOutbound:aioveu-outbound:edit')")
    public Result<AioveuOutboundForm> getAioveuOutboundForm(
        @Parameter(description = "出库记录ID") @PathVariable Long id
    ) {
        AioveuOutboundForm formData = aioveuOutboundService.getAioveuOutboundFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改出库记录")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuOutbound:aioveu-outbound:edit')")
    public Result<Void> updateAioveuOutbound(
            @Parameter(description = "出库记录ID") @PathVariable Long id,
            @RequestBody @Validated AioveuOutboundForm formData
    ) {
        boolean result = aioveuOutboundService.updateAioveuOutbound(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除出库记录")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuOutbound:aioveu-outbound:delete')")
    public Result<Void> deleteAioveuOutbounds(
        @Parameter(description = "出库记录ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuOutboundService.deleteAioveuOutbounds(ids);
        return Result.judge(result);
    }
}
