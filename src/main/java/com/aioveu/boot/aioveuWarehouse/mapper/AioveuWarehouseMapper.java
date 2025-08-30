package com.aioveu.boot.aioveuWarehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuWarehouse.model.query.AioveuWarehouseQuery;
import com.aioveu.boot.aioveuWarehouse.model.vo.AioveuWarehouseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 仓库信息Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:42
 */
@Mapper
public interface AioveuWarehouseMapper extends BaseMapper<AioveuWarehouse> {

    /**
     * 获取仓库信息分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuWarehouseVO>} 仓库信息分页列表
     */
    Page<AioveuWarehouseVO> getAioveuWarehousePage(Page<AioveuWarehouseVO> page, AioveuWarehouseQuery queryParams);

    @Select("SELECT id FROM aioveu_warehouse WHERE name = #{name}")
    Long findIdByName(@Param("name") String name);

}
