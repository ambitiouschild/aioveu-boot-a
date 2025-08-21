package com.aioveu.boot.aioveuInventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuInventory.model.entity.AioveuInventory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuInventory.model.query.AioveuInventoryQuery;
import com.aioveu.boot.aioveuInventory.model.vo.AioveuInventoryVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存信息Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:56
 */
@Mapper
public interface AioveuInventoryMapper extends BaseMapper<AioveuInventory> {

    /**
     * 获取库存信息分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuInventoryVO>} 库存信息分页列表
     */
    Page<AioveuInventoryVO> getAioveuInventoryPage(Page<AioveuInventoryVO> page, AioveuInventoryQuery queryParams);

}
