package com.aioveu.boot.aioveuContact.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuContact.model.entity.AioveuContact;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuContact.model.query.AioveuContactQuery;
import com.aioveu.boot.aioveuContact.model.vo.AioveuContactVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 客户联系人Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:59
 */
@Mapper
public interface AioveuContactMapper extends BaseMapper<AioveuContact> {

    /**
     * 获取客户联系人分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuContactVO>} 客户联系人分页列表
     */
    Page<AioveuContactVO> getAioveuContactPage(Page<AioveuContactVO> page, AioveuContactQuery queryParams);

    @Select("SELECT id FROM aioveu_contact WHERE name = #{name}")
    Long findIdByName(@Param("name") String name);



}
