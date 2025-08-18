package com.aioveu.boot.aioveuPerformance.controller;

import com.aioveu.boot.aioveuPerformance.service.AioveuPerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuPerformance.model.form.AioveuPerformanceForm;
import com.aioveu.boot.aioveuPerformance.model.query.AioveuPerformanceQuery;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
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
 * 员工绩效考评前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 22:48
 */
@Tag(name = "员工绩效考评接口")
@RestController
@RequestMapping("/api/v1/aioveu-performance")
@RequiredArgsConstructor
public class AioveuPerformanceController  {

    private final AioveuPerformanceService aioveuPerformanceService;

    @Operation(summary = "员工绩效考评分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuPerformance:aioveu-performance:query')")
    public PageResult<AioveuPerformanceVO> getAioveuPerformancePage(AioveuPerformanceQuery queryParams ) {
        IPage<AioveuPerformanceVO> result = aioveuPerformanceService.getAioveuPerformancePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增员工绩效考评")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuPerformance:aioveu-performance:add')")
    public Result<Void> saveAioveuPerformance(@RequestBody @Valid AioveuPerformanceForm formData ) {
        boolean result = aioveuPerformanceService.saveAioveuPerformance(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取员工绩效考评表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuPerformance:aioveu-performance:edit')")
    public Result<AioveuPerformanceForm> getAioveuPerformanceForm(
        @Parameter(description = "员工绩效考评ID") @PathVariable Long id
    ) {
        AioveuPerformanceForm formData = aioveuPerformanceService.getAioveuPerformanceFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改员工绩效考评")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuPerformance:aioveu-performance:edit')")
    public Result<Void> updateAioveuPerformance(
            @Parameter(description = "员工绩效考评ID") @PathVariable Long id,
            @RequestBody @Validated AioveuPerformanceForm formData
    ) {
        boolean result = aioveuPerformanceService.updateAioveuPerformance(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除员工绩效考评")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuPerformance:aioveu-performance:delete')")
    public Result<Void> deleteAioveuPerformances(
        @Parameter(description = "员工绩效考评ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuPerformanceService.deleteAioveuPerformances(ids);
        return Result.judge(result);
    }
}
