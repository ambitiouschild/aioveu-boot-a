package com.aioveu.boot.aioveuPosition.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuPosition.model.entity.AioveuPosition;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuPosition.model.query.AioveuPositionQuery;
import com.aioveu.boot.aioveuPosition.model.vo.AioveuPositionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 公司岗位信息Mapper接口
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
@Mapper
public interface AioveuPositionMapper extends BaseMapper<AioveuPosition> {

    /**
     * 获取公司岗位信息分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuPositionVO>} 公司岗位信息分页列表
     */
    Page<AioveuPositionVO> getAioveuPositionPage(Page<AioveuPositionVO> page, AioveuPositionQuery queryParams);


    @Select("SELECT position_id FROM aioveu_position WHERE position_name = #{name}")
    Long findIdByName(@Param("name") String name);

}
