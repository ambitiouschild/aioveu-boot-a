package com.aioveu.boot.aioveuEmployee.controller;

import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuEmployee.model.form.AioveuEmployeeForm;
import com.aioveu.boot.aioveuEmployee.model.query.AioveuEmployeeQuery;
import com.aioveu.boot.aioveuEmployee.model.vo.AioveuEmployeeVO;
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
 * 员工信息前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 17:29
 */
@Tag(name = "员工信息接口")
@RestController
@RequestMapping("/api/v1/aioveu-employee")
@RequiredArgsConstructor
public class AioveuEmployeeController  {

    private final AioveuEmployeeService aioveuEmployeeService;

    @Operation(summary = "员工信息分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuEmployee:aioveu-employee:query')")
    public PageResult<AioveuEmployeeVO> getAioveuEmployeePage(AioveuEmployeeQuery queryParams ) {
        IPage<AioveuEmployeeVO> result = aioveuEmployeeService.getAioveuEmployeePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增员工信息")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuEmployee:aioveu-employee:add')")
    public Result<Void> saveAioveuEmployee(@RequestBody @Valid AioveuEmployeeForm formData ) {
        boolean result = aioveuEmployeeService.saveAioveuEmployee(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取员工信息表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuEmployee:aioveu-employee:edit')")
    public Result<AioveuEmployeeForm> getAioveuEmployeeForm(
        @Parameter(description = "员工信息ID") @PathVariable Long id
    ) {
        AioveuEmployeeForm formData = aioveuEmployeeService.getAioveuEmployeeFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改员工信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuEmployee:aioveu-employee:edit')")
    public Result<Void> updateAioveuEmployee(
            @Parameter(description = "员工信息ID") @PathVariable Long id,
            @RequestBody @Validated AioveuEmployeeForm formData
    ) {
        boolean result = aioveuEmployeeService.updateAioveuEmployee(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除员工信息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuEmployee:aioveu-employee:delete')")
    public Result<Void> deleteAioveuEmployees(
        @Parameter(description = "员工信息ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuEmployeeService.deleteAioveuEmployees(ids);
        return Result.judge(result);
    }
}
