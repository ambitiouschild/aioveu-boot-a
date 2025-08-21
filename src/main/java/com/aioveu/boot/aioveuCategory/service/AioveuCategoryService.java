package com.aioveu.boot.aioveuCategory.service;

import com.aioveu.boot.aioveuCategory.model.entity.AioveuCategory;
import com.aioveu.boot.aioveuCategory.model.form.AioveuCategoryForm;
import com.aioveu.boot.aioveuCategory.model.query.AioveuCategoryQuery;
import com.aioveu.boot.aioveuCategory.model.vo.AioveuCategoryVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 物资分类服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
public interface AioveuCategoryService extends IService<AioveuCategory> {

    /**
     *物资分类分页列表
     *
     * @return {@link IPage<AioveuCategoryVO>} 物资分类分页列表
     */
    IPage<AioveuCategoryVO> getAioveuCategoryPage(AioveuCategoryQuery queryParams);

    /**
     * 获取物资分类表单数据
     *
     * @param id 物资分类ID
     * @return 物资分类表单数据
     */
     AioveuCategoryForm getAioveuCategoryFormData(Long id);

    /**
     * 新增物资分类
     *
     * @param formData 物资分类表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuCategory(AioveuCategoryForm formData);

    /**
     * 修改物资分类
     *
     * @param id   物资分类ID
     * @param formData 物资分类表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuCategory(Long id, AioveuCategoryForm formData);

    /**
     * 删除物资分类
     *
     * @param ids 物资分类ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuCategorys(String ids);

}
