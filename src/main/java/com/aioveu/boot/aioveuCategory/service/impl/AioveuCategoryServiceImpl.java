package com.aioveu.boot.aioveuCategory.service.impl;

import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuCategory.mapper.AioveuCategoryMapper;
import com.aioveu.boot.aioveuCategory.service.AioveuCategoryService;
import com.aioveu.boot.aioveuCategory.model.entity.AioveuCategory;
import com.aioveu.boot.aioveuCategory.model.form.AioveuCategoryForm;
import com.aioveu.boot.aioveuCategory.model.query.AioveuCategoryQuery;
import com.aioveu.boot.aioveuCategory.model.vo.AioveuCategoryVO;
import com.aioveu.boot.aioveuCategory.converter.AioveuCategoryConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 物资分类服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuCategoryServiceImpl extends ServiceImpl<AioveuCategoryMapper, AioveuCategory> implements AioveuCategoryService {

    private final AioveuCategoryConverter aioveuCategoryConverter;

    /**
    * 获取物资分类分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuCategoryVO>} 物资分类分页列表
    */
    @Override
    public IPage<AioveuCategoryVO> getAioveuCategoryPage(AioveuCategoryQuery queryParams) {
        Page<AioveuCategoryVO> pageVO = this.baseMapper.getAioveuCategoryPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取物资分类表单数据
     *
     * @param id 物资分类ID
     * @return 物资分类表单数据
     */
    @Override
    public AioveuCategoryForm getAioveuCategoryFormData(Long id) {
        AioveuCategory entity = this.getById(id);
        return aioveuCategoryConverter.toForm(entity);
    }
    
    /**
     * 新增物资分类
     *
     * @param formData 物资分类表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuCategory(AioveuCategoryForm formData) {
        AioveuCategory entity = aioveuCategoryConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新物资分类
     *
     * @param id   物资分类ID
     * @param formData 物资分类表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuCategory(Long id,AioveuCategoryForm formData) {
        // 1. 验证ID对应的记录是否存在
        AioveuCategory original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }
        // 2. 将表单数据转换为实体对象
        AioveuCategory entity = aioveuCategoryConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);
        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 6. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除物资分类
     *
     * @param ids 物资分类ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuCategorys(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的物资分类数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
