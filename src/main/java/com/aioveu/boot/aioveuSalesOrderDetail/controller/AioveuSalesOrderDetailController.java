package com.aioveu.boot.aioveuSalesOrderDetail.controller;

import com.aioveu.boot.aioveuSalesOrderDetail.service.AioveuSalesOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuSalesOrderDetail.model.form.AioveuSalesOrderDetailForm;
import com.aioveu.boot.aioveuSalesOrderDetail.model.query.AioveuSalesOrderDetailQuery;
import com.aioveu.boot.aioveuSalesOrderDetail.model.vo.AioveuSalesOrderDetailVO;
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
 * 订单明细前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:42
 */
@Tag(name = "订单明细接口")
@RestController
@RequestMapping("/api/v1/aioveu-sales-order-detail")
@RequiredArgsConstructor
public class AioveuSalesOrderDetailController  {

    private final AioveuSalesOrderDetailService aioveuSalesOrderDetailService;

    @Operation(summary = "订单明细分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuSalesOrderDetail:aioveu-sales-order-detail:query')")
    public PageResult<AioveuSalesOrderDetailVO> getAioveuSalesOrderDetailPage(AioveuSalesOrderDetailQuery queryParams ) {
        IPage<AioveuSalesOrderDetailVO> result = aioveuSalesOrderDetailService.getAioveuSalesOrderDetailPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增订单明细")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuSalesOrderDetail:aioveu-sales-order-detail:add')")
    public Result<Void> saveAioveuSalesOrderDetail(@RequestBody @Valid AioveuSalesOrderDetailForm formData ) {
        boolean result = aioveuSalesOrderDetailService.saveAioveuSalesOrderDetail(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取订单明细表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuSalesOrderDetail:aioveu-sales-order-detail:edit')")
    public Result<AioveuSalesOrderDetailForm> getAioveuSalesOrderDetailForm(
        @Parameter(description = "订单明细ID") @PathVariable Long id
    ) {
        AioveuSalesOrderDetailForm formData = aioveuSalesOrderDetailService.getAioveuSalesOrderDetailFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改订单明细")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuSalesOrderDetail:aioveu-sales-order-detail:edit')")
    public Result<Void> updateAioveuSalesOrderDetail(
            @Parameter(description = "订单明细ID") @PathVariable Long id,
            @RequestBody @Validated AioveuSalesOrderDetailForm formData
    ) {
        boolean result = aioveuSalesOrderDetailService.updateAioveuSalesOrderDetail(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除订单明细")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuSalesOrderDetail:aioveu-sales-order-detail:delete')")
    public Result<Void> deleteAioveuSalesOrderDetails(
        @Parameter(description = "订单明细ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuSalesOrderDetailService.deleteAioveuSalesOrderDetails(ids);
        return Result.judge(result);
    }
}
