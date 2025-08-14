package com.aioveu.boot.system.converter;

import com.aioveu.boot.system.model.entity.Dept;
import com.aioveu.boot.system.model.form.DeptForm;
import com.aioveu.boot.system.model.vo.DeptVO;
import org.mapstruct.Mapper;

/**
 * 部门对象转换器
 *
 * @author haoxr
 * @since 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {

    DeptForm toForm(Dept entity);
    
    DeptVO toVo(Dept entity);

    Dept toEntity(DeptForm deptForm);

}