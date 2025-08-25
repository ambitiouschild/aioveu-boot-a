package com.aioveu.boot.aioveuEquipment.service.impl;

import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuEquipment.mapper.AioveuEquipmentMapper;
import com.aioveu.boot.aioveuEquipment.service.AioveuEquipmentService;
import com.aioveu.boot.aioveuEquipment.model.entity.AioveuEquipment;
import com.aioveu.boot.aioveuEquipment.model.form.AioveuEquipmentForm;
import com.aioveu.boot.aioveuEquipment.model.query.AioveuEquipmentQuery;
import com.aioveu.boot.aioveuEquipment.model.vo.AioveuEquipmentVO;
import com.aioveu.boot.aioveuEquipment.converter.AioveuEquipmentConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备管理服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:50
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuEquipmentServiceImpl extends ServiceImpl<AioveuEquipmentMapper, AioveuEquipment> implements AioveuEquipmentService {

    private final AioveuEquipmentConverter aioveuEquipmentConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    /**
    * 获取设备管理分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuEquipmentVO>} 设备管理分页列表
    */
    @Override
    public IPage<AioveuEquipmentVO> getAioveuEquipmentPage(AioveuEquipmentQuery queryParams) {
        Page<AioveuEquipmentVO> pageVO = this.baseMapper.getAioveuEquipmentPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置员工名称
        setEmployeeNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取设备管理表单数据
     *
     * @param id 设备管理ID
     * @return 设备管理表单数据
     */
    @Override
    public AioveuEquipmentForm getAioveuEquipmentFormData(Long id) {
        AioveuEquipment entity = this.getById(id);
        return aioveuEquipmentConverter.toForm(entity);
    }
    
    /**
     * 新增设备管理
     *
     * @param formData 设备管理表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuEquipment(AioveuEquipmentForm formData) {
        AioveuEquipment entity = aioveuEquipmentConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新设备管理
     *
     * @param id   设备管理ID
     * @param formData 设备管理表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuEquipment(Long id,AioveuEquipmentForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuEquipment original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }
        // 2. 将表单数据转换为实体对象
        AioveuEquipment entity = aioveuEquipmentConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除设备管理
     *
     * @param ids 设备管理ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuEquipments(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的设备管理数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量设置名称到VO对象，将AioveuPerformanceVO绩效表视图对象的员工id,转换为员工姓名
     */
    private void setEmployeeNames(List<AioveuEquipmentVO> equipmentVOS) {
        EmployeeNameSetter.setEmployeeNames(
                equipmentVOS,
                AioveuEquipmentVO::getResponsiblePerson, // 获取员工ID
                AioveuEquipmentVO::setResponsiblePersonName, // 设置员工姓名
                aioveuEmployeeService
        );
    }
}
