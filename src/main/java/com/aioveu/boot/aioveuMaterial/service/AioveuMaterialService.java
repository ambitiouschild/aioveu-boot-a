package com.aioveu.boot.aioveuMaterial.service;

import com.aioveu.boot.aioveuMaterial.model.entity.AioveuMaterial;
import com.aioveu.boot.aioveuMaterial.model.form.AioveuMaterialForm;
import com.aioveu.boot.aioveuMaterial.model.query.AioveuMaterialQuery;
import com.aioveu.boot.aioveuMaterial.model.vo.AioveuMaterialVO;
import com.aioveu.boot.aioveuMaterial.model.vo.MaterialOptionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 物资服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:17
 */
public interface AioveuMaterialService extends IService<AioveuMaterial> {



    Long getIdByName(String name);

    /**
     *物资分页列表
     *
     * @return {@link IPage<AioveuMaterialVO>} 物资分页列表
     */
    IPage<AioveuMaterialVO> getAioveuMaterialPage(AioveuMaterialQuery queryParams);

    /**
     * 获取物资表单数据
     *
     * @param id 物资ID
     * @return 物资表单数据
     */
     AioveuMaterialForm getAioveuMaterialFormData(Long id);

    /**
     * 新增物资
     *
     * @param formData 物资表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuMaterial(AioveuMaterialForm formData);

    /**
     * 修改物资
     *
     * @param id   物资ID
     * @param formData 物资表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuMaterial(Long id, AioveuMaterialForm formData);

    /**
     * 删除物资
     *
     * @param ids 物资ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuMaterials(String ids);

    /**
     * 批量获取物资信息（新增方法）
     */
    Map<Long, String> getMaterialMapByIds(List<Long> materialIds);


    /**
     * 获取所有物资列表（用于下拉选择框）
     *
     * @return 物资选项列表
     */
    List<MaterialOptionVO> getAllMaterialOptions();

}
