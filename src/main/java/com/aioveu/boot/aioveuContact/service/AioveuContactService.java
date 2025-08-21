package com.aioveu.boot.aioveuContact.service;

import com.aioveu.boot.aioveuContact.model.entity.AioveuContact;
import com.aioveu.boot.aioveuContact.model.form.AioveuContactForm;
import com.aioveu.boot.aioveuContact.model.query.AioveuContactQuery;
import com.aioveu.boot.aioveuContact.model.vo.AioveuContactVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 客户联系人服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:59
 */
public interface AioveuContactService extends IService<AioveuContact> {

    /**
     *客户联系人分页列表
     *
     * @return {@link IPage<AioveuContactVO>} 客户联系人分页列表
     */
    IPage<AioveuContactVO> getAioveuContactPage(AioveuContactQuery queryParams);

    /**
     * 获取客户联系人表单数据
     *
     * @param id 客户联系人ID
     * @return 客户联系人表单数据
     */
     AioveuContactForm getAioveuContactFormData(Long id);

    /**
     * 新增客户联系人
     *
     * @param formData 客户联系人表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuContact(AioveuContactForm formData);

    /**
     * 修改客户联系人
     *
     * @param id   客户联系人ID
     * @param formData 客户联系人表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuContact(Long id, AioveuContactForm formData);

    /**
     * 删除客户联系人
     *
     * @param ids 客户联系人ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuContacts(String ids);

}
