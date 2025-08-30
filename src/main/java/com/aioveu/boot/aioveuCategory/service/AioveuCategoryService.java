package com.aioveu.boot.aioveuCategory.service;

import com.aioveu.boot.aioveuCategory.mapper.AioveuCategoryMapper;
import com.aioveu.boot.aioveuCategory.model.entity.AioveuCategory;
import com.aioveu.boot.aioveuCategory.model.form.AioveuCategoryForm;
import com.aioveu.boot.aioveuCategory.model.query.AioveuCategoryQuery;
import com.aioveu.boot.aioveuCategory.model.vo.AioveuCategoryVO;
import com.aioveu.boot.aioveuCategory.model.vo.CategoryOptionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 物资分类服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
public interface AioveuCategoryService extends IService<AioveuCategory> {




    Long getIdByName(String name);
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


    /**
     * 批量获取库存分类信息（新增方法）
     */
    Map<Long, String> getCategoryMapByIds(List<Long> categoryIds);


    /**
     * 获取所有库存分类列表（用于下拉选择框）
     *
     * @return 库存分类选项列表
     */
    List<CategoryOptionVO> getAllCategoryOptions();

}
