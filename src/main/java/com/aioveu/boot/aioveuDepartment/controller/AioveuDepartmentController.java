package com.aioveu.boot.aioveuDepartment.controller;

import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import lombok.RequiredArgsConstructor;
import org.glassfish.jaxb.core.v2.TODO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuDepartment.model.form.AioveuDepartmentForm;
import com.aioveu.boot.aioveuDepartment.model.query.AioveuDepartmentQuery;
import com.aioveu.boot.aioveuDepartment.model.vo.AioveuDepartmentVO;
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
 * 公司部门组织结构前端控制层
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
@Tag(name = "公司部门组织结构接口")
@RestController
@RequestMapping("/api/v1/aioveu-department")
@RequiredArgsConstructor
public class AioveuDepartmentController  {

    private final AioveuDepartmentService aioveuDepartmentService;

    @Operation(summary = "公司部门组织结构分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuDepartment:aioveu-department:query')")
    public PageResult<AioveuDepartmentVO> getAioveuDepartmentPage(AioveuDepartmentQuery queryParams ) {
        IPage<AioveuDepartmentVO> result = aioveuDepartmentService.getAioveuDepartmentPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增公司部门组织结构")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuDepartment:aioveu-department:add')")
    public Result<Void> saveAioveuDepartment(@RequestBody @Valid AioveuDepartmentForm formData ) {
        boolean result = aioveuDepartmentService.saveAioveuDepartment(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取公司部门组织结构表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuDepartment:aioveu-department:edit')")
    public Result<AioveuDepartmentForm> getAioveuDepartmentForm(
        @Parameter(description = "公司部门组织结构ID") @PathVariable Long id
    ) {
        AioveuDepartmentForm formData = aioveuDepartmentService.getAioveuDepartmentFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改公司部门组织结构")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuDepartment:aioveu-department:edit')")
    public Result<Void> updateAioveuDepartment(
            @Parameter(description = "公司部门组织结构ID") @PathVariable Long id,
            @RequestBody @Validated AioveuDepartmentForm formData
    ) {
        boolean result = aioveuDepartmentService.updateAioveuDepartment(id, formData);
        return Result.judge(result);
    }

    /**
     1.控制器获取ID：
         •使用 @PathVariable获取路径参数
         •将ID传递给服务层
     2.服务层设置ID：
         •将表单数据转换为实体对象
         •手动设置实体对象的ID
         •执行更新操作

     1.表单对象不包含ID：ID通过URL路径传递
     2.服务层设置ID：将路径参数中的ID设置到实体对象中
     3.执行更新：使用 updateById方法
     可以确保更新的是正确的资源，同时保持代码的安全性和可维护性
     **/

    @Operation(summary = "删除公司部门组织结构")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuDepartment:aioveu-department:delete')")
    public Result<Void> deleteAioveuDepartments(
        @Parameter(description = "公司部门组织结构ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuDepartmentService.deleteAioveuDepartments(ids);
        return Result.judge(result);
    }
}
