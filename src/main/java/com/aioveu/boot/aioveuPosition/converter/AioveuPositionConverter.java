package com.aioveu.boot.aioveuPosition.converter;

import com.aioveu.boot.aioveuPosition.model.vo.AioveuPositionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuPosition.model.entity.AioveuPosition;
import com.aioveu.boot.aioveuPosition.model.form.AioveuPositionForm;

import java.util.List;


/**
 * 公司岗位信息对象转换器
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
@Mapper(componentModel = "spring")
public interface AioveuPositionConverter{


    /**
     * 实体转VO - 忽略deptName字段，在Service层手动设置
     */
    @Mapping(target = "deptName", ignore = true)
    AioveuPositionVO toVO(AioveuPosition entity);

    /**
     * 实体列表转VO列表 - 忽略deptName字段
     */
    List<AioveuPositionVO> toVO(List<AioveuPosition> entities);

    /**
     * 实体转表单
     *
     * @param entity 实体
     * @return 表单
     */
    @Mapping(target = "deptName", ignore = true)
    AioveuPositionForm toForm(AioveuPosition entity);


    /**
     * 表单转实体
     *
     * @param formData 表单
     * @return 实体
     */
    AioveuPosition toEntity(AioveuPositionForm formData);
}