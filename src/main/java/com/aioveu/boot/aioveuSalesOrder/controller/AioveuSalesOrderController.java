package com.aioveu.boot.aioveuSalesOrder.controller;

import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import com.aioveu.boot.aioveuSalesOrder.model.vo.SalesOrderOptionVO;
import com.aioveu.boot.aioveuSalesOrder.service.AioveuSalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuSalesOrder.model.form.AioveuSalesOrderForm;
import com.aioveu.boot.aioveuSalesOrder.model.query.AioveuSalesOrderQuery;
import com.aioveu.boot.aioveuSalesOrder.model.vo.AioveuSalesOrderVO;
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
 * 销售订单前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:27
 */
@Tag(name = "销售订单接口")
@RestController
@RequestMapping("/api/v1/aioveu-sales-order")
@RequiredArgsConstructor
public class AioveuSalesOrderController  {

    private final AioveuSalesOrderService aioveuSalesOrderService;

    @Operation(summary = "销售订单分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuSalesOrder:aioveu-sales-order:query')")
    public PageResult<AioveuSalesOrderVO> getAioveuSalesOrderPage(AioveuSalesOrderQuery queryParams ) {
        IPage<AioveuSalesOrderVO> result = aioveuSalesOrderService.getAioveuSalesOrderPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增销售订单")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuSalesOrder:aioveu-sales-order:add')")
    public Result<Void> saveAioveuSalesOrder(@RequestBody @Valid AioveuSalesOrderForm formData ) {
        boolean result = aioveuSalesOrderService.saveAioveuSalesOrder(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取销售订单表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuSalesOrder:aioveu-sales-order:edit')")
    public Result<AioveuSalesOrderForm> getAioveuSalesOrderForm(
        @Parameter(description = "销售订单ID") @PathVariable Long id
    ) {
        AioveuSalesOrderForm formData = aioveuSalesOrderService.getAioveuSalesOrderFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改销售订单")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuSalesOrder:aioveu-sales-order:edit')")
    public Result<Void> updateAioveuSalesOrder(
            @Parameter(description = "销售订单ID") @PathVariable Long id,
            @RequestBody @Validated AioveuSalesOrderForm formData
    ) {
        boolean result = aioveuSalesOrderService.updateAioveuSalesOrder(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除销售订单")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuSalesOrder:aioveu-sales-order:delete')")
    public Result<Void> deleteAioveuSalesOrders(
        @Parameter(description = "销售订单ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuSalesOrderService.deleteAioveuSalesOrders(ids);
        return Result.judge(result);
    }

    /**
     * 获取所有订单列表（用于下拉选择框）
     * @return 订单选项列表
     */
    @Operation(summary = "获取所有订单列表（用于下拉选择框）")
    @GetMapping("/options")
    public Result<List<SalesOrderOptionVO>> getAllSalesOrderOptions() {

        List<SalesOrderOptionVO> salesOrders  = aioveuSalesOrderService.getAllSalesOrderOptions();

        return Result.success(salesOrders);
    }
}
