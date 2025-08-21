package com.aioveu.boot.aioveuTransaction.controller;

import com.aioveu.boot.aioveuTransaction.service.AioveuTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aioveu.boot.aioveuTransaction.model.form.AioveuTransactionForm;
import com.aioveu.boot.aioveuTransaction.model.query.AioveuTransactionQuery;
import com.aioveu.boot.aioveuTransaction.model.vo.AioveuTransactionVO;
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
 * 客户交易记录前端控制层
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:16
 */
@Tag(name = "客户交易记录接口")
@RestController
@RequestMapping("/api/v1/aioveu-transaction")
@RequiredArgsConstructor
public class AioveuTransactionController  {

    private final AioveuTransactionService aioveuTransactionService;

    @Operation(summary = "客户交易记录分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('aioveuTransaction:aioveu-transaction:query')")
    public PageResult<AioveuTransactionVO> getAioveuTransactionPage(AioveuTransactionQuery queryParams ) {
        IPage<AioveuTransactionVO> result = aioveuTransactionService.getAioveuTransactionPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增客户交易记录")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('aioveuTransaction:aioveu-transaction:add')")
    public Result<Void> saveAioveuTransaction(@RequestBody @Valid AioveuTransactionForm formData ) {
        boolean result = aioveuTransactionService.saveAioveuTransaction(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取客户交易记录表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('aioveuTransaction:aioveu-transaction:edit')")
    public Result<AioveuTransactionForm> getAioveuTransactionForm(
        @Parameter(description = "客户交易记录ID") @PathVariable Long id
    ) {
        AioveuTransactionForm formData = aioveuTransactionService.getAioveuTransactionFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改客户交易记录")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('aioveuTransaction:aioveu-transaction:edit')")
    public Result<Void> updateAioveuTransaction(
            @Parameter(description = "客户交易记录ID") @PathVariable Long id,
            @RequestBody @Validated AioveuTransactionForm formData
    ) {
        boolean result = aioveuTransactionService.updateAioveuTransaction(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除客户交易记录")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('aioveuTransaction:aioveu-transaction:delete')")
    public Result<Void> deleteAioveuTransactions(
        @Parameter(description = "客户交易记录ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = aioveuTransactionService.deleteAioveuTransactions(ids);
        return Result.judge(result);
    }
}
