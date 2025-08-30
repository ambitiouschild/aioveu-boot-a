package com.aioveu.boot.aioveuCategory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuCategory.model.entity.AioveuCategory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuCategory.model.query.AioveuCategoryQuery;
import com.aioveu.boot.aioveuCategory.model.vo.AioveuCategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 物资分类Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
@Mapper
public interface AioveuCategoryMapper extends BaseMapper<AioveuCategory> {

    /**
     * 获取物资分类分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuCategoryVO>} 物资分类分页列表
     */
    Page<AioveuCategoryVO> getAioveuCategoryPage(Page<AioveuCategoryVO> page, AioveuCategoryQuery queryParams);

    @Select("SELECT id FROM aioveu_category WHERE name = #{name}")
    Long findIdByName(@Param("name") String name);

}
