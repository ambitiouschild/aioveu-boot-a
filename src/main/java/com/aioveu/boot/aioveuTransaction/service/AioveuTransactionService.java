package com.aioveu.boot.aioveuTransaction.service;

import com.aioveu.boot.aioveuTransaction.model.entity.AioveuTransaction;
import com.aioveu.boot.aioveuTransaction.model.form.AioveuTransactionForm;
import com.aioveu.boot.aioveuTransaction.model.query.AioveuTransactionQuery;
import com.aioveu.boot.aioveuTransaction.model.vo.AioveuTransactionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 客户交易记录服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:16
 */
public interface AioveuTransactionService extends IService<AioveuTransaction> {

    /**
     *客户交易记录分页列表
     *
     * @return {@link IPage<AioveuTransactionVO>} 客户交易记录分页列表
     */
    IPage<AioveuTransactionVO> getAioveuTransactionPage(AioveuTransactionQuery queryParams);

    /**
     * 获取客户交易记录表单数据
     *
     * @param id 客户交易记录ID
     * @return 客户交易记录表单数据
     */
     AioveuTransactionForm getAioveuTransactionFormData(Long id);

    /**
     * 新增客户交易记录
     *
     * @param formData 客户交易记录表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuTransaction(AioveuTransactionForm formData);

    /**
     * 修改客户交易记录
     *
     * @param id   客户交易记录ID
     * @param formData 客户交易记录表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuTransaction(Long id, AioveuTransactionForm formData);

    /**
     * 删除客户交易记录
     *
     * @param ids 客户交易记录ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuTransactions(String ids);

}
