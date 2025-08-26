package com.aioveu.boot.aioveuCustomer.controller;

import com.aioveu.boot.aioveuCustomer.model.vo.CustomerOptionVO;
import com.aioveu.boot.aioveuCustomer.service.AioveuCustomerService;
import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuCustomer.model.form.AioveuCustomerForm;
import com.aioveu.boot.aioveuCustomer.model.query.AioveuCustomerQuery;
import com.aioveu.boot.aioveuCustomer.model.vo.AioveuCustomerVO;
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
 * 客户信息前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:42
 */
@Tag(name = "客户信息接口")
@RestController
@RequestMapping("/api/v1/aioveu-customer")
@RequiredArgsConstructor
public class AioveuCustomerController  {

    private final AioveuCustomerService aioveuCustomerService;

    @Operation(summary = "客户信息分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuCustomer:aioveu-customer:query')")
    public PageResult<AioveuCustomerVO> getAioveuCustomerPage(AioveuCustomerQuery queryParams ) {
        IPage<AioveuCustomerVO> result = aioveuCustomerService.getAioveuCustomerPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增客户信息")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuCustomer:aioveu-customer:add')")
    public Result<Void> saveAioveuCustomer(@RequestBody @Valid AioveuCustomerForm formData ) {
        boolean result = aioveuCustomerService.saveAioveuCustomer(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取客户信息表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuCustomer:aioveu-customer:edit')")
    public Result<AioveuCustomerForm> getAioveuCustomerForm(
        @Parameter(description = "客户信息ID") @PathVariable Long id
    ) {
        AioveuCustomerForm formData = aioveuCustomerService.getAioveuCustomerFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改客户信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuCustomer:aioveu-customer:edit')")
    public Result<Void> updateAioveuCustomer(
            @Parameter(description = "客户信息ID") @PathVariable Long id,
            @RequestBody @Validated AioveuCustomerForm formData
    ) {
        boolean result = aioveuCustomerService.updateAioveuCustomer(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除客户信息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuCustomer:aioveu-customer:delete')")
    public Result<Void> deleteAioveuCustomers(
        @Parameter(description = "客户信息ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuCustomerService.deleteAioveuCustomers(ids);
        return Result.judge(result);
    }

    /**
     * 获取所有客户列表（用于下拉选择框）
     * @return 客户选项列表
     */
    @Operation(summary = "获取所有客户列表（用于下拉选择框）")
    @GetMapping("/options")
    public Result<List<CustomerOptionVO>> getAllCustomerOptions() {

        List<CustomerOptionVO> customers  = aioveuCustomerService.getAllCustomerOptions();

        return Result.success(customers);
    }

}
