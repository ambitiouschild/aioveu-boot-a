package com.aioveu.boot.aioveuEquipment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuEquipment.model.entity.AioveuEquipment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuEquipment.model.query.AioveuEquipmentQuery;
import com.aioveu.boot.aioveuEquipment.model.vo.AioveuEquipmentVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备管理Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:50
 */
@Mapper
public interface AioveuEquipmentMapper extends BaseMapper<AioveuEquipment> {

    /**
     * 获取设备管理分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuEquipmentVO>} 设备管理分页列表
     */
    Page<AioveuEquipmentVO> getAioveuEquipmentPage(Page<AioveuEquipmentVO> page, AioveuEquipmentQuery queryParams);

}
