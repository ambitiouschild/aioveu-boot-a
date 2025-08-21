package com.aioveu.boot.aioveuEquipment.service;

import com.aioveu.boot.aioveuEquipment.model.entity.AioveuEquipment;
import com.aioveu.boot.aioveuEquipment.model.form.AioveuEquipmentForm;
import com.aioveu.boot.aioveuEquipment.model.query.AioveuEquipmentQuery;
import com.aioveu.boot.aioveuEquipment.model.vo.AioveuEquipmentVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 设备管理服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:50
 */
public interface AioveuEquipmentService extends IService<AioveuEquipment> {

    /**
     *设备管理分页列表
     *
     * @return {@link IPage<AioveuEquipmentVO>} 设备管理分页列表
     */
    IPage<AioveuEquipmentVO> getAioveuEquipmentPage(AioveuEquipmentQuery queryParams);

    /**
     * 获取设备管理表单数据
     *
     * @param id 设备管理ID
     * @return 设备管理表单数据
     */
     AioveuEquipmentForm getAioveuEquipmentFormData(Long id);

    /**
     * 新增设备管理
     *
     * @param formData 设备管理表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuEquipment(AioveuEquipmentForm formData);

    /**
     * 修改设备管理
     *
     * @param id   设备管理ID
     * @param formData 设备管理表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuEquipment(Long id, AioveuEquipmentForm formData);

    /**
     * 删除设备管理
     *
     * @param ids 设备管理ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuEquipments(String ids);

}
