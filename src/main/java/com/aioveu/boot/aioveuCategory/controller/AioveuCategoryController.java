package com.aioveu.boot.aioveuCategory.controller;

import com.aioveu.boot.aioveuCategory.model.vo.CategoryOptionVO;
import com.aioveu.boot.aioveuCategory.service.AioveuCategoryService;
import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuCategory.model.form.AioveuCategoryForm;
import com.aioveu.boot.aioveuCategory.model.query.AioveuCategoryQuery;
import com.aioveu.boot.aioveuCategory.model.vo.AioveuCategoryVO;
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
 * 物资分类前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
@Tag(name = "物资分类接口")
@RestController
@RequestMapping("/api/v1/aioveu-category")
@RequiredArgsConstructor
public class AioveuCategoryController  {

    private final AioveuCategoryService aioveuCategoryService;

    @Operation(summary = "物资分类分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuCategory:aioveu-category:query')")
    public PageResult<AioveuCategoryVO> getAioveuCategoryPage(AioveuCategoryQuery queryParams ) {
        IPage<AioveuCategoryVO> result = aioveuCategoryService.getAioveuCategoryPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增物资分类")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuCategory:aioveu-category:add')")
    public Result<Void> saveAioveuCategory(@RequestBody @Valid AioveuCategoryForm formData ) {
        boolean result = aioveuCategoryService.saveAioveuCategory(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取物资分类表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuCategory:aioveu-category:edit')")
    public Result<AioveuCategoryForm> getAioveuCategoryForm(
        @Parameter(description = "物资分类ID") @PathVariable Long id
    ) {
        AioveuCategoryForm formData = aioveuCategoryService.getAioveuCategoryFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改物资分类")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuCategory:aioveu-category:edit')")
    public Result<Void> updateAioveuCategory(
            @Parameter(description = "物资分类ID") @PathVariable Long id,
            @RequestBody @Validated AioveuCategoryForm formData
    ) {
        boolean result = aioveuCategoryService.updateAioveuCategory(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除物资分类")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuCategory:aioveu-category:delete')")
    public Result<Void> deleteAioveuCategorys(
        @Parameter(description = "物资分类ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuCategoryService.deleteAioveuCategorys(ids);
        return Result.judge(result);
    }

    /**
     * 获取所有库存分类列表（用于下拉选择框）
     *
     * @return 库存分类选项列表
     */
    @Operation(summary = "获取所有库存分类列表（用于下拉选择框）")
    @GetMapping("/options")
    public Result<List<CategoryOptionVO>> getAllCategoryOptions() {

        List<CategoryOptionVO> categorys  = aioveuCategoryService.getAllCategoryOptions();

        return Result.success(categorys);
    }
}
