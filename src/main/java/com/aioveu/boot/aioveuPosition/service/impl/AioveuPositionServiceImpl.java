package com.aioveu.boot.aioveuPosition.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuPosition.mapper.AioveuPositionMapper;
import com.aioveu.boot.aioveuPosition.service.AioveuPositionService;
import com.aioveu.boot.aioveuPosition.model.entity.AioveuPosition;
import com.aioveu.boot.aioveuPosition.model.form.AioveuPositionForm;
import com.aioveu.boot.aioveuPosition.model.query.AioveuPositionQuery;
import com.aioveu.boot.aioveuPosition.model.vo.AioveuPositionVO;
import com.aioveu.boot.aioveuPosition.converter.AioveuPositionConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 公司岗位信息服务实现类
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
@Service
@RequiredArgsConstructor
public class AioveuPositionServiceImpl extends ServiceImpl<AioveuPositionMapper, AioveuPosition> implements AioveuPositionService {

    private final AioveuPositionConverter aioveuPositionConverter;

    /**
    * 获取公司岗位信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuPositionVO>} 公司岗位信息分页列表
    */
    @Override
    public IPage<AioveuPositionVO> getAioveuPositionPage(AioveuPositionQuery queryParams) {
        Page<AioveuPositionVO> pageVO = this.baseMapper.getAioveuPositionPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取公司岗位信息表单数据
     *
     * @param id 公司岗位信息ID
     * @return 公司岗位信息表单数据
     */
    @Override
    public AioveuPositionForm getAioveuPositionFormData(Long id) {
        AioveuPosition entity = this.getById(id);
        return aioveuPositionConverter.toForm(entity);
    }
    
    /**
     * 新增公司岗位信息
     *
     * @param formData 公司岗位信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuPosition(AioveuPositionForm formData) {
        AioveuPosition entity = aioveuPositionConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新公司岗位信息
     *
     * @param id   公司岗位信息ID
     * @param formData 公司岗位信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuPosition(Long id,AioveuPositionForm formData) {
        AioveuPosition entity = aioveuPositionConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除公司岗位信息
     *
     * @param ids 公司岗位信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuPositions(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的公司岗位信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
