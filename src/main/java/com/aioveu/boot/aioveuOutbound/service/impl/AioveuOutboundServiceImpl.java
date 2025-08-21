package com.aioveu.boot.aioveuOutbound.service.impl;

import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuOutbound.mapper.AioveuOutboundMapper;
import com.aioveu.boot.aioveuOutbound.service.AioveuOutboundService;
import com.aioveu.boot.aioveuOutbound.model.entity.AioveuOutbound;
import com.aioveu.boot.aioveuOutbound.model.form.AioveuOutboundForm;
import com.aioveu.boot.aioveuOutbound.model.query.AioveuOutboundQuery;
import com.aioveu.boot.aioveuOutbound.model.vo.AioveuOutboundVO;
import com.aioveu.boot.aioveuOutbound.converter.AioveuOutboundConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 出库记录服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:26
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuOutboundServiceImpl extends ServiceImpl<AioveuOutboundMapper, AioveuOutbound> implements AioveuOutboundService {

    private final AioveuOutboundConverter aioveuOutboundConverter;

    /**
    * 获取出库记录分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuOutboundVO>} 出库记录分页列表
    */
    @Override
    public IPage<AioveuOutboundVO> getAioveuOutboundPage(AioveuOutboundQuery queryParams) {
        Page<AioveuOutboundVO> pageVO = this.baseMapper.getAioveuOutboundPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取出库记录表单数据
     *
     * @param id 出库记录ID
     * @return 出库记录表单数据
     */
    @Override
    public AioveuOutboundForm getAioveuOutboundFormData(Long id) {
        AioveuOutbound entity = this.getById(id);
        return aioveuOutboundConverter.toForm(entity);
    }
    
    /**
     * 新增出库记录
     *
     * @param formData 出库记录表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuOutbound(AioveuOutboundForm formData) {
        AioveuOutbound entity = aioveuOutboundConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新出库记录
     *
     * @param id   出库记录ID
     * @param formData 出库记录表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuOutbound(Long id,AioveuOutboundForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuOutbound original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 2. 将表单数据转换为实体对象
        AioveuOutbound entity = aioveuOutboundConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除出库记录
     *
     * @param ids 出库记录ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuOutbounds(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的出库记录数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
