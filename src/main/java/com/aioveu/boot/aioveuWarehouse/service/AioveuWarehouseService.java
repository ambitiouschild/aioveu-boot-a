package com.aioveu.boot.aioveuWarehouse.service;

import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
import com.aioveu.boot.aioveuWarehouse.model.form.AioveuWarehouseForm;
import com.aioveu.boot.aioveuWarehouse.model.query.AioveuWarehouseQuery;
import com.aioveu.boot.aioveuWarehouse.model.vo.AioveuWarehouseVO;
import com.aioveu.boot.aioveuWarehouse.model.vo.WarehouseOptionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 仓库信息服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:42
 */
public interface AioveuWarehouseService extends IService<AioveuWarehouse> {


    public Long getIdByName(String name);
    /**
     *仓库信息分页列表
     *
     * @return {@link IPage<AioveuWarehouseVO>} 仓库信息分页列表
     */
    IPage<AioveuWarehouseVO> getAioveuWarehousePage(AioveuWarehouseQuery queryParams);

    /**
     * 获取仓库信息表单数据
     *
     * @param id 仓库信息ID
     * @return 仓库信息表单数据
     */
     AioveuWarehouseForm getAioveuWarehouseFormData(Long id);

    /**
     * 新增仓库信息
     *
     * @param formData 仓库信息表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuWarehouse(AioveuWarehouseForm formData);

    /**
     * 修改仓库信息
     *
     * @param id   仓库信息ID
     * @param formData 仓库信息表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuWarehouse(Long id, AioveuWarehouseForm formData);

    /**
     * 删除仓库信息
     *
     * @param ids 仓库信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuWarehouses(String ids);

    /**
     * 批量获取仓库信息（新增方法）
     */
    Map<Long, String> getWarehouseMapByIds(List<Long> warehouseIds);



    /**
     * 获取所有仓库列表（用于下拉选择框）
     *
     * @return 仓库选项列表
     */
    List<WarehouseOptionVO> getAllWarehouseOptions();
}
