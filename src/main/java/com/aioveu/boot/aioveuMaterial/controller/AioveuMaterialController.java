package com.aioveu.boot.aioveuMaterial.controller;

import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import com.aioveu.boot.aioveuMaterial.model.vo.MaterialOptionVO;
import com.aioveu.boot.aioveuMaterial.service.AioveuMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuMaterial.model.form.AioveuMaterialForm;
import com.aioveu.boot.aioveuMaterial.model.query.AioveuMaterialQuery;
import com.aioveu.boot.aioveuMaterial.model.vo.AioveuMaterialVO;
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
 * 物资前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:17
 */
@Tag(name = "物资接口")
@RestController
@RequestMapping("/api/v1/aioveu-material")
@RequiredArgsConstructor
public class AioveuMaterialController  {

    private final AioveuMaterialService aioveuMaterialService;

    @Operation(summary = "物资分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuMaterial:aioveu-material:query')")
    public PageResult<AioveuMaterialVO> getAioveuMaterialPage(AioveuMaterialQuery queryParams ) {
        IPage<AioveuMaterialVO> result = aioveuMaterialService.getAioveuMaterialPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增物资")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuMaterial:aioveu-material:add')")
    public Result<Void> saveAioveuMaterial(@RequestBody @Valid AioveuMaterialForm formData ) {
        boolean result = aioveuMaterialService.saveAioveuMaterial(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取物资表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuMaterial:aioveu-material:edit')")
    public Result<AioveuMaterialForm> getAioveuMaterialForm(
        @Parameter(description = "物资ID") @PathVariable Long id
    ) {
        AioveuMaterialForm formData = aioveuMaterialService.getAioveuMaterialFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改物资")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuMaterial:aioveu-material:edit')")
    public Result<Void> updateAioveuMaterial(
            @Parameter(description = "物资ID") @PathVariable Long id,
            @RequestBody @Validated AioveuMaterialForm formData
    ) {
        boolean result = aioveuMaterialService.updateAioveuMaterial(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除物资")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuMaterial:aioveu-material:delete')")
    public Result<Void> deleteAioveuMaterials(
        @Parameter(description = "物资ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuMaterialService.deleteAioveuMaterials(ids);
        return Result.judge(result);
    }

    /**
     * 获取所有物资列表（用于下拉选择框）
     * @return 物资选项列表
     */
    @Operation(summary = "获取所有物资列表（用于下拉选择框）")
    @GetMapping("/options")
    public Result<List<MaterialOptionVO>> getAllMaterialOptions() {

        List<MaterialOptionVO> materials  = aioveuMaterialService.getAllMaterialOptions();

        return Result.success(materials);
    }
}
