package com.aioveu.boot.aioveuDepartment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuDepartment.mapper.AioveuDepartmentMapper;
import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.form.AioveuDepartmentForm;
import com.aioveu.boot.aioveuDepartment.model.query.AioveuDepartmentQuery;
import com.aioveu.boot.aioveuDepartment.model.vo.AioveuDepartmentVO;
import com.aioveu.boot.aioveuDepartment.converter.AioveuDepartmentConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 公司部门组织结构服务实现类
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
@Service
@RequiredArgsConstructor
public class AioveuDepartmentServiceImpl extends ServiceImpl<AioveuDepartmentMapper, AioveuDepartment> implements AioveuDepartmentService {

    private final AioveuDepartmentConverter aioveuDepartmentConverter;

    /**
    * 获取公司部门组织结构分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuDepartmentVO>} 公司部门组织结构分页列表
    */
    @Override
    public IPage<AioveuDepartmentVO> getAioveuDepartmentPage(AioveuDepartmentQuery queryParams) {
        Page<AioveuDepartmentVO> pageVO = this.baseMapper.getAioveuDepartmentPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取公司部门组织结构表单数据
     *
     * @param id 公司部门组织结构ID
     * @return 公司部门组织结构表单数据
     */
    @Override
    public AioveuDepartmentForm getAioveuDepartmentFormData(Long id) {
        AioveuDepartment entity = this.getById(id);
        return aioveuDepartmentConverter.toForm(entity);
    }
    
    /**
     * 新增公司部门组织结构
     *
     * @param formData 公司部门组织结构表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuDepartment(AioveuDepartmentForm formData) {
        AioveuDepartment entity = aioveuDepartmentConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新公司部门组织结构
     *
     * @param id   公司部门组织结构ID
     * @param formData 公司部门组织结构表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuDepartment(Long id,AioveuDepartmentForm formData) {
        AioveuDepartment entity = aioveuDepartmentConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除公司部门组织结构
     *
     * @param ids 公司部门组织结构ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuDepartments(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的公司部门组织结构数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
