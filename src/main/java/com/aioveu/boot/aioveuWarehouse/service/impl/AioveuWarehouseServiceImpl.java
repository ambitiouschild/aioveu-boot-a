package com.aioveu.boot.aioveuWarehouse.service.impl;

import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuWarehouse.mapper.AioveuWarehouseMapper;
import com.aioveu.boot.aioveuWarehouse.service.AioveuWarehouseService;
import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
import com.aioveu.boot.aioveuWarehouse.model.form.AioveuWarehouseForm;
import com.aioveu.boot.aioveuWarehouse.model.query.AioveuWarehouseQuery;
import com.aioveu.boot.aioveuWarehouse.model.vo.AioveuWarehouseVO;
import com.aioveu.boot.aioveuWarehouse.converter.AioveuWarehouseConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 仓库信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:42
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuWarehouseServiceImpl extends ServiceImpl<AioveuWarehouseMapper, AioveuWarehouse> implements AioveuWarehouseService {

    private final AioveuWarehouseConverter aioveuWarehouseConverter;

    /**
    * 获取仓库信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuWarehouseVO>} 仓库信息分页列表
    */
    @Override
    public IPage<AioveuWarehouseVO> getAioveuWarehousePage(AioveuWarehouseQuery queryParams) {
        Page<AioveuWarehouseVO> pageVO = this.baseMapper.getAioveuWarehousePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取仓库信息表单数据
     *
     * @param id 仓库信息ID
     * @return 仓库信息表单数据
     */
    @Override
    public AioveuWarehouseForm getAioveuWarehouseFormData(Long id) {
        AioveuWarehouse entity = this.getById(id);
        return aioveuWarehouseConverter.toForm(entity);
    }
    
    /**
     * 新增仓库信息
     *
     * @param formData 仓库信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuWarehouse(AioveuWarehouseForm formData) {
        AioveuWarehouse entity = aioveuWarehouseConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新仓库信息
     *
     * @param id   仓库信息ID
     * @param formData 仓库信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuWarehouse(Long id,AioveuWarehouseForm formData) {
        // 1. 验证ID对应的记录是否存在
        AioveuWarehouse original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 2. 将表单数据转换为实体对象
        AioveuWarehouse entity = aioveuWarehouseConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除仓库信息
     *
     * @param ids 仓库信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuWarehouses(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的仓库信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
