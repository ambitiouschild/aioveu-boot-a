package com.aioveu.boot.aioveuEmployee.controller;

import com.aioveu.boot.aioveuEmployee.model.vo.EmployeeVO;
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

import java.util.List;
import java.util.Map;

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


    /**
     * 根据员工ID列表获取员工名称映射
     * @param employeeIds 岗位ID列表
     * @return 员工ID到员工名称的映射
     */
    @Operation(summary = "根据员工ID列表获取员工名称映射")
    @PostMapping("/map")
    public Result<Map<Long, String>> getEmployeeMapByIds(@RequestBody List<Long> employeeIds) {
        Map<Long, String> employeeMap = aioveuEmployeeService.getEmployeeMapByIds(employeeIds);
        return Result.success(employeeMap);
    }

    /**
     * 获取所有员工列表（用于下拉选择框）
     * @return 员工选项列表
     */
    @Operation(summary = "获取所有员工列表（用于下拉选择框）")
    @GetMapping("/options")
    public Result<List<EmployeeVO>> getAllEmployeeOptions() {

        List<EmployeeVO> employees  = aioveuEmployeeService.getAllEmployeeOptions();

        return Result.success(employees);
    }

    /**
     * 获取员工总数
     * @return 员工总数
     */
    @Operation(summary = "获取员工总数")
    @GetMapping("/count")
    @PreAuthorize("@ss.hasPerm('aioveuEmployee:aioveu-employee:query')")
    public Result<Long> getEmployeeCount() {
        long count = aioveuEmployeeService.getEmployeeCount();
        return Result.success(count);
    }

}
