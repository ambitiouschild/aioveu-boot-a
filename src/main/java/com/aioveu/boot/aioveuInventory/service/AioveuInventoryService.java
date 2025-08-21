package com.aioveu.boot.aioveuInventory.service;

import com.aioveu.boot.aioveuInventory.model.entity.AioveuInventory;
import com.aioveu.boot.aioveuInventory.model.form.AioveuInventoryForm;
import com.aioveu.boot.aioveuInventory.model.query.AioveuInventoryQuery;
import com.aioveu.boot.aioveuInventory.model.vo.AioveuInventoryVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 库存信息服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:56
 */
public interface AioveuInventoryService extends IService<AioveuInventory> {

    /**
     *库存信息分页列表
     *
     * @return {@link IPage<AioveuInventoryVO>} 库存信息分页列表
     */
    IPage<AioveuInventoryVO> getAioveuInventoryPage(AioveuInventoryQuery queryParams);

    /**
     * 获取库存信息表单数据
     *
     * @param id 库存信息ID
     * @return 库存信息表单数据
     */
     AioveuInventoryForm getAioveuInventoryFormData(Long id);

    /**
     * 新增库存信息
     *
     * @param formData 库存信息表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuInventory(AioveuInventoryForm formData);

    /**
     * 修改库存信息
     *
     * @param id   库存信息ID
     * @param formData 库存信息表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuInventory(Long id, AioveuInventoryForm formData);

    /**
     * 删除库存信息
     *
     * @param ids 库存信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuInventorys(String ids);

}
