package com.aioveu.boot.aioveu.service.impl;

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
import com.aioveu.boot.aioveu.mapper.AioveuProcurementMapper;
import com.aioveu.boot.aioveu.service.AioveuProcurementService;
import com.aioveu.boot.aioveu.model.entity.AioveuProcurement;
import com.aioveu.boot.aioveu.model.form.AioveuProcurementForm;
import com.aioveu.boot.aioveu.model.query.AioveuProcurementQuery;
import com.aioveu.boot.aioveu.model.vo.AioveuProcurementVO;
import com.aioveu.boot.aioveu.converter.AioveuProcurementConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 采购流程服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:07
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuProcurementServiceImpl extends ServiceImpl<AioveuProcurementMapper, AioveuProcurement> implements AioveuProcurementService {

    private final AioveuProcurementConverter aioveuProcurementConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    /**
    * 获取采购流程分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuProcurementVO>} 采购流程分页列表
    */
    @Override
    public IPage<AioveuProcurementVO> getAioveuProcurementPage(AioveuProcurementQuery queryParams) {
        Page<AioveuProcurementVO> pageVO = this.baseMapper.getAioveuProcurementPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置申请人
        setApplicantNames(pageVO.getRecords());

        // 设置审核人
        setReviewerNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取采购流程表单数据
     *
     * @param id 采购流程ID
     * @return 采购流程表单数据
     */
    @Override
    public AioveuProcurementForm getAioveuProcurementFormData(Long id) {
        AioveuProcurement entity = this.getById(id);
        return aioveuProcurementConverter.toForm(entity);
    }
    
    /**
     * 新增采购流程
     *
     * @param formData 采购流程表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuProcurement(AioveuProcurementForm formData) {
        AioveuProcurement entity = aioveuProcurementConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新采购流程
     *
     * @param id   采购流程ID
     * @param formData 采购流程表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuProcurement(Long id,AioveuProcurementForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuProcurement original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }
        // 2. 将表单数据转换为实体对象
        AioveuProcurement entity = aioveuProcurementConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除采购流程
     *
     * @param ids 采购流程ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuProcurements(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的采购流程数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }


    /**
     * 批量设置名称到VO对象，将AioveuPerformanceVO绩效表视图对象的员工id,转换为员工姓名
     */
    private void setApplicantNames(List<AioveuProcurementVO> procurementVOS) {
        EmployeeNameSetter.setEmployeeNames(
                procurementVOS,
                AioveuProcurementVO::getApplicantId, // 获取员工ID
                AioveuProcurementVO::setApplicantName, // 设置员工姓名
                aioveuEmployeeService
        );
    }

    /**
     * 批量设置名称到VO对象，将AioveuPerformanceVO绩效表视图对象的员工id,转换为员工姓名
     */
    private void setReviewerNames(List<AioveuProcurementVO> procurementVOS) {
        EmployeeNameSetter.setEmployeeNames(
                procurementVOS,
                AioveuProcurementVO::getReviewerId, // 获取员工ID
                AioveuProcurementVO::setReviewerName, // 设置员工姓名
                aioveuEmployeeService
        );
    }
}
