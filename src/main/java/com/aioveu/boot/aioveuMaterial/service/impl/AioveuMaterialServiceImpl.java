package com.aioveu.boot.aioveuMaterial.service.impl;

import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuMaterial.mapper.AioveuMaterialMapper;
import com.aioveu.boot.aioveuMaterial.service.AioveuMaterialService;
import com.aioveu.boot.aioveuMaterial.model.entity.AioveuMaterial;
import com.aioveu.boot.aioveuMaterial.model.form.AioveuMaterialForm;
import com.aioveu.boot.aioveuMaterial.model.query.AioveuMaterialQuery;
import com.aioveu.boot.aioveuMaterial.model.vo.AioveuMaterialVO;
import com.aioveu.boot.aioveuMaterial.converter.AioveuMaterialConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 物资服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:17
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuMaterialServiceImpl extends ServiceImpl<AioveuMaterialMapper, AioveuMaterial> implements AioveuMaterialService {

    private final AioveuMaterialConverter aioveuMaterialConverter;

    /**
    * 获取物资分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuMaterialVO>} 物资分页列表
    */
    @Override
    public IPage<AioveuMaterialVO> getAioveuMaterialPage(AioveuMaterialQuery queryParams) {
        Page<AioveuMaterialVO> pageVO = this.baseMapper.getAioveuMaterialPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取物资表单数据
     *
     * @param id 物资ID
     * @return 物资表单数据
     */
    @Override
    public AioveuMaterialForm getAioveuMaterialFormData(Long id) {
        AioveuMaterial entity = this.getById(id);
        return aioveuMaterialConverter.toForm(entity);
    }
    
    /**
     * 新增物资
     *
     * @param formData 物资表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuMaterial(AioveuMaterialForm formData) {
        AioveuMaterial entity = aioveuMaterialConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新物资
     *
     * @param id   物资ID
     * @param formData 物资表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuMaterial(Long id,AioveuMaterialForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuMaterial original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }
        // 2. 将表单数据转换为实体对象
        AioveuMaterial entity = aioveuMaterialConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 6. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除物资
     *
     * @param ids 物资ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuMaterials(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的物资数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
