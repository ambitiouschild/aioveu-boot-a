package com.aioveu.boot.aioveuTransaction.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuContact.model.entity.AioveuContact;
import com.aioveu.boot.aioveuContact.service.AioveuContactService;
import com.aioveu.boot.aioveuContact.service.impl.ContactNameSetter;
import com.aioveu.boot.aioveuCustomer.model.entity.AioveuCustomer;
import com.aioveu.boot.aioveuCustomer.service.AioveuCustomerService;
import com.aioveu.boot.aioveuCustomer.service.impl.CustomerNameSetter;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuTransaction.mapper.AioveuTransactionMapper;
import com.aioveu.boot.aioveuTransaction.service.AioveuTransactionService;
import com.aioveu.boot.aioveuTransaction.model.entity.AioveuTransaction;
import com.aioveu.boot.aioveuTransaction.model.form.AioveuTransactionForm;
import com.aioveu.boot.aioveuTransaction.model.query.AioveuTransactionQuery;
import com.aioveu.boot.aioveuTransaction.model.vo.AioveuTransactionVO;
import com.aioveu.boot.aioveuTransaction.converter.AioveuTransactionConverter;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 客户交易记录服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:16
 */



@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuTransactionServiceImpl extends ServiceImpl<AioveuTransactionMapper, AioveuTransaction> implements AioveuTransactionService {

    private final AioveuTransactionConverter aioveuTransactionConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    @Autowired
    private AioveuCustomerService aioveuCustomerService;

    @Autowired
    private AioveuContactService aioveuContactService;

    /**
    * 获取客户交易记录分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuTransactionVO>} 客户交易记录分页列表
    */
    @Override
    public IPage<AioveuTransactionVO> getAioveuTransactionPage(AioveuTransactionQuery queryParams) {

        // 处理客户名称映射
        if (StringUtils.isNotBlank(queryParams.getCustomerName())) {
            Long customerId = aioveuCustomerService.getIdByName(queryParams.getCustomerName());
            queryParams.setCustomerId(customerId);
        }

        // 处理联系人名称映射
        if (StringUtils.isNotBlank(queryParams.getContactName())) {
            Long contactId = aioveuContactService.getIdByName(queryParams.getContactName());
            queryParams.setContactId(contactId);
        }


        Page<AioveuTransactionVO> pageVO = this.baseMapper.getAioveuTransactionPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        setSalesRepNames(pageVO.getRecords());

        setCustomerNames(pageVO.getRecords());

        setContactNames(pageVO.getRecords());


        return pageVO;
    }
    
    /**
     * 获取客户交易记录表单数据
     *
     * @param id 客户交易记录ID
     * @return 客户交易记录表单数据
     */
    @Override
    public AioveuTransactionForm getAioveuTransactionFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuTransaction entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuTransactionForm form = aioveuTransactionConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getCustomerId() != null) {
            LambdaQueryWrapper<AioveuCustomer> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuCustomer::getId, entity.getCustomerId())
                    .select(AioveuCustomer::getName); // 只选择需要的字段

            AioveuCustomer customer = aioveuCustomerService.getOne(Wrapper);

            if (customer != null) {
                form.setCustomerName(customer.getName());
            }
        }

        if (entity.getContactId() != null) {
            LambdaQueryWrapper<AioveuContact> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuContact::getId, entity.getContactId())
                    .select(AioveuContact::getName); // 只选择需要的字段

            AioveuContact contact = aioveuContactService.getOne(Wrapper);

            if (contact != null) {
                form.setContactName(contact.getName());
            }
        }

        if (entity.getSalesRepId() != null) {
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getSalesRepId())
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setSalesRepName(employee.getName());
            }
        }


        return form;
    }
    
    /**
     * 新增客户交易记录
     *
     * @param formData 客户交易记录表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuTransaction(AioveuTransactionForm formData) {

        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuTransactionForm::getTransactionNo,
                AioveuTransaction::getTransactionNo,
                null,
                this,
                "交易编号： "
        );

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuTransactionForm::getCustomerName,
                AioveuCustomer::getName,
                AioveuTransactionForm::setCustomerId,
                AioveuCustomer::getId,
                aioveuCustomerService,
                "客户： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuTransactionForm::getContactName,
                AioveuContact::getName,
                AioveuTransactionForm::setContactId,
                AioveuContact::getId,
                aioveuContactService,
                "联系人： "
        );

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuTransactionForm::getSalesRepName,
                AioveuEmployee::getName,
                AioveuTransactionForm::setSalesRepId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "销售负责人： "
        );

        AioveuTransaction entity = aioveuTransactionConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新客户交易记录
     *
     * @param id   客户交易记录ID
     * @param formData 客户交易记录表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuTransaction(Long id,AioveuTransactionForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuTransaction original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuTransactionForm::getCustomerName,
                AioveuCustomer::getName,
                AioveuTransactionForm::setCustomerId,
                AioveuCustomer::getId,
                aioveuCustomerService,
                "客户： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuTransactionForm::getContactName,
                AioveuContact::getName,
                AioveuTransactionForm::setContactId,
                AioveuContact::getId,
                aioveuContactService,
                "联系人： "
        );

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuTransactionForm::getSalesRepName,
                AioveuEmployee::getName,
                AioveuTransactionForm::setSalesRepId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "销售负责人： "
        );

        // 2. 将表单数据转换为实体对象
        AioveuTransaction entity = aioveuTransactionConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除客户交易记录
     *
     * @param ids 客户交易记录ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuTransactions(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的客户交易记录数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量设置名称到VO对象，将视图对象的员工id,转换为员工姓名
     */
    private void setSalesRepNames(List<AioveuTransactionVO> transactionVOS) {
        EmployeeNameSetter.setEmployeeNames(
                transactionVOS,
                AioveuTransactionVO::getSalesRepId, // 获取ID
                AioveuTransactionVO::setSalesRepName, // 设置姓名
                aioveuEmployeeService
        );
    }

    /**
     * 批量设置名称到VO对象，将视图对象的客户id,转换为客户姓名
     */
    private void setCustomerNames(List<AioveuTransactionVO> transactionVOS) {
        CustomerNameSetter.setCustomerNames(
                transactionVOS,
                AioveuTransactionVO::getCustomerId, // 获取ID
                AioveuTransactionVO::setCustomerName, // 设置姓名
                aioveuCustomerService
        );
    }

    /**
     * 批量设置名称到VO对象，将视图对象的联系人id,转换为联系人姓名
     */
    private void setContactNames(List<AioveuTransactionVO> transactionVOS) {
        ContactNameSetter.setContactNames(
                transactionVOS,
                AioveuTransactionVO::getContactId, // 获取ID
                AioveuTransactionVO::setContactName, // 设置姓名
                aioveuContactService
        );
    }

}
