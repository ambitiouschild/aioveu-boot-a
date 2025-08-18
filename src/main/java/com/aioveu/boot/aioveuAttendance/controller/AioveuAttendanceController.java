package com.aioveu.boot.aioveuAttendance.controller;

import com.aioveu.boot.aioveuAttendance.service.AioveuAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuAttendance.model.form.AioveuAttendanceForm;
import com.aioveu.boot.aioveuAttendance.model.query.AioveuAttendanceQuery;
import com.aioveu.boot.aioveuAttendance.model.vo.AioveuAttendanceVO;
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
 * 考勤信息前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 21:52
 */
@Tag(name = "考勤信息接口")
@RestController
@RequestMapping("/api/v1/aioveu-attendance")
@RequiredArgsConstructor
public class AioveuAttendanceController  {

    private final AioveuAttendanceService aioveuAttendanceService;

    @Operation(summary = "考勤信息分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuAttendance:aioveu-attendance:query')")
    public PageResult<AioveuAttendanceVO> getAioveuAttendancePage(AioveuAttendanceQuery queryParams ) {
        IPage<AioveuAttendanceVO> result = aioveuAttendanceService.getAioveuAttendancePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增考勤信息")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuAttendance:aioveu-attendance:add')")
    public Result<Void> saveAioveuAttendance(@RequestBody @Valid AioveuAttendanceForm formData ) {
        boolean result = aioveuAttendanceService.saveAioveuAttendance(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取考勤信息表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuAttendance:aioveu-attendance:edit')")
    public Result<AioveuAttendanceForm> getAioveuAttendanceForm(
        @Parameter(description = "考勤信息ID") @PathVariable Long id
    ) {
        AioveuAttendanceForm formData = aioveuAttendanceService.getAioveuAttendanceFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改考勤信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuAttendance:aioveu-attendance:edit')")
    public Result<Void> updateAioveuAttendance(
            @Parameter(description = "考勤信息ID") @PathVariable Long id,
            @RequestBody @Validated AioveuAttendanceForm formData
    ) {
        boolean result = aioveuAttendanceService.updateAioveuAttendance(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除考勤信息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuAttendance:aioveu-attendance:delete')")
    public Result<Void> deleteAioveuAttendances(
        @Parameter(description = "考勤信息ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuAttendanceService.deleteAioveuAttendances(ids);
        return Result.judge(result);
    }
}
