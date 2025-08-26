package com.aioveu.boot.aioveuPosition.controller;

import com.aioveu.boot.aioveuPosition.model.vo.PositionOptionVO;
import com.aioveu.boot.aioveuPosition.service.AioveuPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuPosition.model.form.AioveuPositionForm;
import com.aioveu.boot.aioveuPosition.model.query.AioveuPositionQuery;
import com.aioveu.boot.aioveuPosition.model.vo.AioveuPositionVO;
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
 * 公司岗位信息前端控制层
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
@Tag(name = "公司岗位信息接口")
@RestController
@RequestMapping("/api/v1/aioveu-position")
@RequiredArgsConstructor
public class AioveuPositionController  {

    private final AioveuPositionService aioveuPositionService;

    @Operation(summary = "公司岗位信息分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuPosition:aioveu-position:query')")
    public PageResult<AioveuPositionVO> getAioveuPositionPage(AioveuPositionQuery queryParams ) {
        IPage<AioveuPositionVO> result = aioveuPositionService.getAioveuPositionPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增公司岗位信息")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuPosition:aioveu-position:add')")
    public Result<Void> saveAioveuPosition(@RequestBody @Valid AioveuPositionForm formData ) {
        boolean result = aioveuPositionService.saveAioveuPosition(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取公司岗位信息表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuPosition:aioveu-position:edit')")
    public Result<AioveuPositionForm> getAioveuPositionForm(
        @Parameter(description = "公司岗位信息ID") @PathVariable Long id
    ) {
        AioveuPositionForm formData = aioveuPositionService.getAioveuPositionFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改公司岗位信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuPosition:aioveu-position:edit')")
    public Result<Void> updateAioveuPosition(
            @Parameter(description = "公司岗位信息ID") @PathVariable Long id,
            @RequestBody @Validated AioveuPositionForm formData
    ) {
        boolean result = aioveuPositionService.updateAioveuPosition(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除公司岗位信息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuPosition:aioveu-position:delete')")
    public Result<Void> deleteAioveuPositions(
        @Parameter(description = "公司岗位信息ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuPositionService.deleteAioveuPositions(ids);
        return Result.judge(result);
    }

    /**
     * 根据岗位ID列表获取岗位名称映射
     * @param positionIds 岗位ID列表
     * @return 岗位ID到岗位名称的映射
     */
    @Operation(summary = "根据岗位ID列表获取岗位名称映射")
    @PostMapping("/map")
    public Result<Map<Long, String>> getPositionMapByIds(@RequestBody List<Long> positionIds) {
        Map<Long, String> positionMap = aioveuPositionService.getPositionMapByIds(positionIds);
        return Result.success(positionMap);
    }

    /**
     * 获取所有岗位列表（用于下拉选择框）
     * @return 岗位选项列表
     */
    @Operation(summary = "获取所有岗位列表（用于下拉选择框）")
    @GetMapping("/options")
    public Result<List<PositionOptionVO>> getAllPositionOptions() {

        List<PositionOptionVO> positions  = aioveuPositionService.getAllPositionOptions();

        return Result.success(positions);
    }
}
