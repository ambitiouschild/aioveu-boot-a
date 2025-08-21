package com.aioveu.boot.aioveuSalary.controller;

import com.aioveu.boot.aioveuSalary.service.AioveuSalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuSalary.model.form.AioveuSalaryForm;
import com.aioveu.boot.aioveuSalary.model.query.AioveuSalaryQuery;
import com.aioveu.boot.aioveuSalary.model.vo.AioveuSalaryVO;
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
 * 员工工资明细前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 17:14
 */
@Tag(name = "员工工资明细接口")
@RestController
@RequestMapping("/api/v1/aioveu-salary")
@RequiredArgsConstructor
public class AioveuSalaryController  {

    private final AioveuSalaryService aioveuSalaryService;

    @Operation(summary = "员工工资明细分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuSalary:aioveu-salary:query')")
    public PageResult<AioveuSalaryVO> getAioveuSalaryPage(AioveuSalaryQuery queryParams ) {
        IPage<AioveuSalaryVO> result = aioveuSalaryService.getAioveuSalaryPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增员工工资明细")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuSalary:aioveu-salary:add')")
    public Result<Void> saveAioveuSalary(@RequestBody @Valid AioveuSalaryForm formData ) {
        boolean result = aioveuSalaryService.saveAioveuSalary(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取员工工资明细表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuSalary:aioveu-salary:edit')")
    public Result<AioveuSalaryForm> getAioveuSalaryForm(
        @Parameter(description = "员工工资明细ID") @PathVariable Long id
    ) {
        AioveuSalaryForm formData = aioveuSalaryService.getAioveuSalaryFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改员工工资明细")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuSalary:aioveu-salary:edit')")
    public Result<Void> updateAioveuSalary(
            @Parameter(description = "员工工资明细ID") @PathVariable Long id,
            @RequestBody @Validated AioveuSalaryForm formData
    ) {
        boolean result = aioveuSalaryService.updateAioveuSalary(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除员工工资明细")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuSalary:aioveu-salary:delete')")
    public Result<Void> deleteAioveuSalarys(
        @Parameter(description = "员工工资明细ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuSalaryService.deleteAioveuSalarys(ids);
        return Result.judge(result);
    }
}
