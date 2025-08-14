package com.aioveu.boot.system.controller;

import com.aioveu.boot.system.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.system.model.form.MemberForm;
import com.aioveu.boot.system.model.query.MemberQuery;
import com.aioveu.boot.system.model.vo.MemberVO;
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
 * 会员信息前端控制层
 *
 * @author ambitiouschild
 * @since 2025-07-11 15:13
 */
@Tag(name = "会员信息接口")
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController  {

    private final MemberService memberService;

    @Operation(summary = "会员信息分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('system:member:query')")
    public PageResult<MemberVO> getMemberPage(MemberQuery queryParams ) {
        IPage<MemberVO> result = memberService.getMemberPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增会员信息")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('system:member:add')")
    public Result<Void> saveMember(@RequestBody @Valid MemberForm formData ) {
        boolean result = memberService.saveMember(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取会员信息表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('system:member:edit')")
    public Result<MemberForm> getMemberForm(
        @Parameter(description = "会员信息ID") @PathVariable Long id
    ) {
        MemberForm formData = memberService.getMemberFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改会员信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('system:member:edit')")
    public Result<Void> updateMember(
            @Parameter(description = "会员信息ID") @PathVariable Long id,
            @RequestBody @Validated MemberForm formData
    ) {
        boolean result = memberService.updateMember(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除会员信息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('system:member:delete')")
    public Result<Void> deleteMembers(
        @Parameter(description = "会员信息ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = memberService.deleteMembers(ids);
        return Result.judge(result);
    }
}
