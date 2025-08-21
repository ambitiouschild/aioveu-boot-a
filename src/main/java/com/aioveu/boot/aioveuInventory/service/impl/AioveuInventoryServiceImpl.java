package com.aioveu.boot.aioveuInventory.service.impl;

import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuInventory.mapper.AioveuInventoryMapper;
import com.aioveu.boot.aioveuInventory.service.AioveuInventoryService;
import com.aioveu.boot.aioveuInventory.model.entity.AioveuInventory;
import com.aioveu.boot.aioveuInventory.model.form.AioveuInventoryForm;
import com.aioveu.boot.aioveuInventory.model.query.AioveuInventoryQuery;
import com.aioveu.boot.aioveuInventory.model.vo.AioveuInventoryVO;
import com.aioveu.boot.aioveuInventory.converter.AioveuInventoryConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 库存信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:56
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuInventoryServiceImpl extends ServiceImpl<AioveuInventoryMapper, AioveuInventory> implements AioveuInventoryService {

    private final AioveuInventoryConverter aioveuInventoryConverter;

    /**
    * 获取库存信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuInventoryVO>} 库存信息分页列表
    */
    @Override
    public IPage<AioveuInventoryVO> getAioveuInventoryPage(AioveuInventoryQuery queryParams) {
        Page<AioveuInventoryVO> pageVO = this.baseMapper.getAioveuInventoryPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取库存信息表单数据
     *
     * @param id 库存信息ID
     * @return 库存信息表单数据
     */
    @Override
    public AioveuInventoryForm getAioveuInventoryFormData(Long id) {
        AioveuInventory entity = this.getById(id);
        return aioveuInventoryConverter.toForm(entity);
    }
    
    /**
     * 新增库存信息
     *
     * @param formData 库存信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuInventory(AioveuInventoryForm formData) {
        AioveuInventory entity = aioveuInventoryConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新库存信息
     *
     * @param id   库存信息ID
     * @param formData 库存信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuInventory(Long id,AioveuInventoryForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuInventory original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 2. 将表单数据转换为实体对象
        AioveuInventory entity = aioveuInventoryConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除库存信息
     *
     * @param ids 库存信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuInventorys(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的库存信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
