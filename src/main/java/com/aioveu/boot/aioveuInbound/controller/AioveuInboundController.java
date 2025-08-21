package com.aioveu.boot.aioveuInbound.controller;

import com.aioveu.boot.aioveuInbound.service.AioveuInboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuInbound.model.form.AioveuInboundForm;
import com.aioveu.boot.aioveuInbound.model.query.AioveuInboundQuery;
import com.aioveu.boot.aioveuInbound.model.vo.AioveuInboundVO;
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
 * 入库信息前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:18
 */
@Tag(name = "入库信息接口")
@RestController
@RequestMapping("/api/v1/aioveu-inbound")
@RequiredArgsConstructor
public class AioveuInboundController  {

    private final AioveuInboundService aioveuInboundService;

    @Operation(summary = "入库信息分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuInbound:aioveu-inbound:query')")
    public PageResult<AioveuInboundVO> getAioveuInboundPage(AioveuInboundQuery queryParams ) {
        IPage<AioveuInboundVO> result = aioveuInboundService.getAioveuInboundPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增入库信息")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuInbound:aioveu-inbound:add')")
    public Result<Void> saveAioveuInbound(@RequestBody @Valid AioveuInboundForm formData ) {
        boolean result = aioveuInboundService.saveAioveuInbound(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取入库信息表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuInbound:aioveu-inbound:edit')")
    public Result<AioveuInboundForm> getAioveuInboundForm(
        @Parameter(description = "入库信息ID") @PathVariable Long id
    ) {
        AioveuInboundForm formData = aioveuInboundService.getAioveuInboundFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改入库信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuInbound:aioveu-inbound:edit')")
    public Result<Void> updateAioveuInbound(
            @Parameter(description = "入库信息ID") @PathVariable Long id,
            @RequestBody @Validated AioveuInboundForm formData
    ) {
        boolean result = aioveuInboundService.updateAioveuInbound(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除入库信息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuInbound:aioveu-inbound:delete')")
    public Result<Void> deleteAioveuInbounds(
        @Parameter(description = "入库信息ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuInboundService.deleteAioveuInbounds(ids);
        return Result.judge(result);
    }
}
