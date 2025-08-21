package com.aioveu.boot.aioveu.service;

import com.aioveu.boot.aioveu.model.entity.AioveuProcurement;
import com.aioveu.boot.aioveu.model.form.AioveuProcurementForm;
import com.aioveu.boot.aioveu.model.query.AioveuProcurementQuery;
import com.aioveu.boot.aioveu.model.vo.AioveuProcurementVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 采购流程服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:07
 */
public interface AioveuProcurementService extends IService<AioveuProcurement> {

    /**
     *采购流程分页列表
     *
     * @return {@link IPage<AioveuProcurementVO>} 采购流程分页列表
     */
    IPage<AioveuProcurementVO> getAioveuProcurementPage(AioveuProcurementQuery queryParams);

    /**
     * 获取采购流程表单数据
     *
     * @param id 采购流程ID
     * @return 采购流程表单数据
     */
     AioveuProcurementForm getAioveuProcurementFormData(Long id);

    /**
     * 新增采购流程
     *
     * @param formData 采购流程表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuProcurement(AioveuProcurementForm formData);

    /**
     * 修改采购流程
     *
     * @param id   采购流程ID
     * @param formData 采购流程表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuProcurement(Long id, AioveuProcurementForm formData);

    /**
     * 删除采购流程
     *
     * @param ids 采购流程ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuProcurements(String ids);

}
