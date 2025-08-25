package com.aioveu.boot.aioveuInbound.service.impl;

import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuInbound.mapper.AioveuInboundMapper;
import com.aioveu.boot.aioveuInbound.service.AioveuInboundService;
import com.aioveu.boot.aioveuInbound.model.entity.AioveuInbound;
import com.aioveu.boot.aioveuInbound.model.form.AioveuInboundForm;
import com.aioveu.boot.aioveuInbound.model.query.AioveuInboundQuery;
import com.aioveu.boot.aioveuInbound.model.vo.AioveuInboundVO;
import com.aioveu.boot.aioveuInbound.converter.AioveuInboundConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 入库信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:18
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuInboundServiceImpl extends ServiceImpl<AioveuInboundMapper, AioveuInbound> implements AioveuInboundService {

    private final AioveuInboundConverter aioveuInboundConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    /**
    * 获取入库信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuInboundVO>} 入库信息分页列表
    */
    @Override
    public IPage<AioveuInboundVO> getAioveuInboundPage(AioveuInboundQuery queryParams) {
        Page<AioveuInboundVO> pageVO = this.baseMapper.getAioveuInboundPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置员工名称
        setEmployeeNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取入库信息表单数据
     *
     * @param id 入库信息ID
     * @return 入库信息表单数据
     */
    @Override
    public AioveuInboundForm getAioveuInboundFormData(Long id) {
        AioveuInbound entity = this.getById(id);
        return aioveuInboundConverter.toForm(entity);
    }
    
    /**
     * 新增入库信息
     *
     * @param formData 入库信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuInbound(AioveuInboundForm formData) {
        AioveuInbound entity = aioveuInboundConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新入库信息
     *
     * @param id   入库信息ID
     * @param formData 入库信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuInbound(Long id,AioveuInboundForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuInbound original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 2. 将表单数据转换为实体对象
        AioveuInbound entity = aioveuInboundConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除入库信息
     *
     * @param ids 入库信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuInbounds(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的入库信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量设置名称到VO对象，将AioveuPerformanceVO绩效表视图对象的员工id,转换为员工姓名
     */
    private void setEmployeeNames(List<AioveuInboundVO> inboundVOS) {
        EmployeeNameSetter.setEmployeeNames(
                inboundVOS,
                AioveuInboundVO::getOperatorId, // 获取员工ID
                AioveuInboundVO::setOperatorName, // 设置员工姓名
                aioveuEmployeeService
        );
    }
}
