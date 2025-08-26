package com.aioveu.boot.aioveuContact.controller;

import com.aioveu.boot.aioveuContact.model.vo.ContactOptionVO;
import com.aioveu.boot.aioveuContact.service.AioveuContactService;
import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuContact.model.form.AioveuContactForm;
import com.aioveu.boot.aioveuContact.model.query.AioveuContactQuery;
import com.aioveu.boot.aioveuContact.model.vo.AioveuContactVO;
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
 * 客户联系人前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:59
 */
@Tag(name = "客户联系人接口")
@RestController
@RequestMapping("/api/v1/aioveu-contact")
@RequiredArgsConstructor
public class AioveuContactController  {

    private final AioveuContactService aioveuContactService;

    @Operation(summary = "客户联系人分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuContact:aioveu-contact:query')")
    public PageResult<AioveuContactVO> getAioveuContactPage(AioveuContactQuery queryParams ) {
        IPage<AioveuContactVO> result = aioveuContactService.getAioveuContactPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增客户联系人")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuContact:aioveu-contact:add')")
    public Result<Void> saveAioveuContact(@RequestBody @Valid AioveuContactForm formData ) {
        boolean result = aioveuContactService.saveAioveuContact(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取客户联系人表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuContact:aioveu-contact:edit')")
    public Result<AioveuContactForm> getAioveuContactForm(
        @Parameter(description = "客户联系人ID") @PathVariable Long id
    ) {
        AioveuContactForm formData = aioveuContactService.getAioveuContactFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改客户联系人")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuContact:aioveu-contact:edit')")
    public Result<Void> updateAioveuContact(
            @Parameter(description = "客户联系人ID") @PathVariable Long id,
            @RequestBody @Validated AioveuContactForm formData
    ) {
        boolean result = aioveuContactService.updateAioveuContact(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除客户联系人")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuContact:aioveu-contact:delete')")
    public Result<Void> deleteAioveuContacts(
        @Parameter(description = "客户联系人ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuContactService.deleteAioveuContacts(ids);
        return Result.judge(result);
    }

    /**
     * 获取所有联系人列表（用于下拉选择框）
     * @return 联系人选项列表
     */
    @Operation(summary = "获取所有联系人列表（用于下拉选择框）")
    @GetMapping("/options")
    public Result<List<ContactOptionVO>> getAllContactOptions() {

        List<ContactOptionVO> contacts  = aioveuContactService.getAllContactOptions();

        return Result.success(contacts);
    }

}
