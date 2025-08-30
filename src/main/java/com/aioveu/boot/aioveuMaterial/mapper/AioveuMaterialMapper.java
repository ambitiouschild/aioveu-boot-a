package com.aioveu.boot.aioveuMaterial.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuMaterial.model.entity.AioveuMaterial;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuMaterial.model.query.AioveuMaterialQuery;
import com.aioveu.boot.aioveuMaterial.model.vo.AioveuMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 物资Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:17
 */
@Mapper
public interface AioveuMaterialMapper extends BaseMapper<AioveuMaterial> {

    /**
     * 获取物资分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuMaterialVO>} 物资分页列表
     */
    Page<AioveuMaterialVO> getAioveuMaterialPage(Page<AioveuMaterialVO> page, AioveuMaterialQuery queryParams);

    @Select("SELECT id FROM aioveu_material WHERE name = #{name}")
    Long findIdByName(@Param("name") String name);



}
