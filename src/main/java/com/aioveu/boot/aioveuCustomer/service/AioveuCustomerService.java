package com.aioveu.boot.aioveuCustomer.service;

import com.aioveu.boot.aioveuCustomer.model.entity.AioveuCustomer;
import com.aioveu.boot.aioveuCustomer.model.form.AioveuCustomerForm;
import com.aioveu.boot.aioveuCustomer.model.query.AioveuCustomerQuery;
import com.aioveu.boot.aioveuCustomer.model.vo.AioveuCustomerVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 客户信息服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:42
 */
public interface AioveuCustomerService extends IService<AioveuCustomer> {

    /**
     *客户信息分页列表
     *
     * @return {@link IPage<AioveuCustomerVO>} 客户信息分页列表
     */
    IPage<AioveuCustomerVO> getAioveuCustomerPage(AioveuCustomerQuery queryParams);

    /**
     * 获取客户信息表单数据
     *
     * @param id 客户信息ID
     * @return 客户信息表单数据
     */
     AioveuCustomerForm getAioveuCustomerFormData(Long id);

    /**
     * 新增客户信息
     *
     * @param formData 客户信息表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuCustomer(AioveuCustomerForm formData);

    /**
     * 修改客户信息
     *
     * @param id   客户信息ID
     * @param formData 客户信息表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuCustomer(Long id, AioveuCustomerForm formData);

    /**
     * 删除客户信息
     *
     * @param ids 客户信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuCustomers(String ids);

}
