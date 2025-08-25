package com.aioveu.boot.aioveuCustomer.service.impl;

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
import com.aioveu.boot.aioveuCustomer.mapper.AioveuCustomerMapper;
import com.aioveu.boot.aioveuCustomer.service.AioveuCustomerService;
import com.aioveu.boot.aioveuCustomer.model.entity.AioveuCustomer;
import com.aioveu.boot.aioveuCustomer.model.form.AioveuCustomerForm;
import com.aioveu.boot.aioveuCustomer.model.query.AioveuCustomerQuery;
import com.aioveu.boot.aioveuCustomer.model.vo.AioveuCustomerVO;
import com.aioveu.boot.aioveuCustomer.converter.AioveuCustomerConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 客户信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:42
 */
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuCustomerServiceImpl extends ServiceImpl<AioveuCustomerMapper, AioveuCustomer> implements AioveuCustomerService {

    private final AioveuCustomerConverter aioveuCustomerConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    /**
    * 获取客户信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuCustomerVO>} 客户信息分页列表
    */
    @Override
    public IPage<AioveuCustomerVO> getAioveuCustomerPage(AioveuCustomerQuery queryParams) {
        Page<AioveuCustomerVO> pageVO = this.baseMapper.getAioveuCustomerPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        setSalesRepNames(pageVO.getRecords());


        return pageVO;
    }
    
    /**
     * 获取客户信息表单数据
     *
     * @param id 客户信息ID
     * @return 客户信息表单数据
     */
    @Override
    public AioveuCustomerForm getAioveuCustomerFormData(Long id) {
        AioveuCustomer entity = this.getById(id);
        return aioveuCustomerConverter.toForm(entity);
    }
    
    /**
     * 新增客户信息
     *
     * @param formData 客户信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuCustomer(AioveuCustomerForm formData) {
        AioveuCustomer entity = aioveuCustomerConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新客户信息
     *
     * @param id   客户信息ID
     * @param formData 客户信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuCustomer(Long id,AioveuCustomerForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuCustomer original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 2. 将表单数据转换为实体对象
        AioveuCustomer entity = aioveuCustomerConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除客户信息
     *
     * @param ids 客户信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuCustomers(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的客户信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量设置名称到VO对象，将视图对象的员工id,转换为员工姓名
     */
    private void setSalesRepNames(List<AioveuCustomerVO> customerVOS) {
        EmployeeNameSetter.setEmployeeNames(
                customerVOS,
                AioveuCustomerVO::getSalesRepId, // 获取ID
                AioveuCustomerVO::setSalesRepName, // 设置姓名
                aioveuEmployeeService
        );
    }

}
