package com.aioveu.boot.aioveuContact.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuContact.model.vo.ContactOptionVO;
import com.aioveu.boot.aioveuCustomer.mapper.AioveuCustomerMapper;
import com.aioveu.boot.aioveuCustomer.model.entity.AioveuCustomer;
import com.aioveu.boot.aioveuCustomer.service.AioveuCustomerService;
import com.aioveu.boot.aioveuCustomer.service.impl.CustomerNameSetter;
import com.aioveu.boot.aioveuDepartment.mapper.AioveuDepartmentMapper;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
import com.aioveu.boot.aioveuWarehouse.model.form.AioveuWarehouseForm;
import com.aioveu.boot.common.exception.BusinessException;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuContact.mapper.AioveuContactMapper;
import com.aioveu.boot.aioveuContact.service.AioveuContactService;
import com.aioveu.boot.aioveuContact.model.entity.AioveuContact;
import com.aioveu.boot.aioveuContact.model.form.AioveuContactForm;
import com.aioveu.boot.aioveuContact.model.query.AioveuContactQuery;
import com.aioveu.boot.aioveuContact.model.vo.AioveuContactVO;
import com.aioveu.boot.aioveuContact.converter.AioveuContactConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户联系人服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:59
 */



@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuContactServiceImpl extends ServiceImpl<AioveuContactMapper, AioveuContact> implements AioveuContactService {

    private final AioveuContactConverter aioveuContactConverter;

    @Autowired
    private AioveuCustomerService aioveuCustomerService;
    @Autowired
    private  AioveuContactMapper  aioveuContactMapper;


    @Autowired
    private AioveuCustomerMapper aioveuCustomerMapper;

    @Override
    public Long getIdByName(String name) {
        return aioveuContactMapper.findIdByName(name);
    }


    /**
    * 获取客户联系人分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuContactVO>} 客户联系人分页列表
    */
    @Override
    public IPage<AioveuContactVO> getAioveuContactPage(AioveuContactQuery queryParams) {
        // 处理客户名称映射
        if (StringUtils.isNotBlank(queryParams.getCustomerName())) {
            Long customerId = aioveuCustomerService.getIdByName(queryParams.getCustomerName());
            queryParams.setCustomerId(customerId);
        }


        Page<AioveuContactVO> pageVO = this.baseMapper.getAioveuContactPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        setCustomerNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取客户联系人表单数据
     *
     * @param id 客户联系人ID
     * @return 客户联系人表单数据
     */
    @Override
    public AioveuContactForm getAioveuContactFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuContact entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuContactForm form = aioveuContactConverter.toForm(entity);

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

        return form;
    }
    
    /**
     * 新增客户联系人
     *
     * @param formData 客户联系人表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuContact(AioveuContactForm formData) {

//        // 确保客户ID不为空
//        if (formData.getCustomerId() == null) {
//            throw new BusinessException("客户ID不能为空");
//        }

        // 处理主要联系人逻辑
        if (formData.getIsPrimary() == 1) {
            // 检查是否已存在主要联系人
            LambdaQueryWrapper<AioveuContact> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AioveuContact::getCustomerId, formData.getCustomerId())
                    .eq(AioveuContact::getIsPrimary, 1);

            // 检查是否存在其他主要联系人
            Long count = aioveuContactMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException("该客户已存在主要联系人");
            }
        }

        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuContactForm::getName,
                AioveuContact::getName,
                null,
                this,
                "联系人： "
        );

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuContactForm::getCustomerName,
                AioveuCustomer::getName,
                AioveuContactForm::setCustomerId,
                AioveuCustomer::getId,
                aioveuCustomerService,
                "客户： "
        );





        AioveuContact entity = aioveuContactConverter.toEntity(formData);
        return this.save(entity);
    }

    
    /**
     * 更新客户联系人
     *
     * @param id   客户联系人ID
     * @param formData 客户联系人表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuContact(Long id,AioveuContactForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuContact original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }
//        // 确保客户ID不为空
//        if (formData.getCustomerId() == null) {
//            throw new BusinessException("客户ID不能为空");
//        }

        // 处理主要联系人逻辑
        if (formData.getIsPrimary() == 1) {
            // 检查是否已存在主要联系人
            LambdaQueryWrapper<AioveuContact> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AioveuContact::getCustomerId, formData.getCustomerId())
                    .eq(AioveuContact::getIsPrimary, 1);

            // 检查是否存在其他主要联系人
            Long count = aioveuContactMapper.selectCount(queryWrapper);
            if (count > 0 ) {
                throw new BusinessException("该客户已存在主要联系人");
            }
        }else if(formData.getIsPrimary() == 0 ) {

            throw new BusinessException("该客户需要存在一个主要联系人");
        }


        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuContactForm::getCustomerName,
                AioveuCustomer::getName,
                AioveuContactForm::setCustomerId,
                AioveuCustomer::getId,
                aioveuCustomerService,
                "客户： "
        );

        // 2. 将表单数据转换为实体对象
        AioveuContact entity = aioveuContactConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除客户联系人
     *
     * @param ids 客户联系人ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuContacts(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的客户联系人数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量获取联系人信息（新增方法）
     */
    @Override
    public Map<Long, String> getContactMapByIds(List<Long> contactIds) {
        if (contactIds == null || contactIds.isEmpty()) {
            return Map.of();
        }

        // 批量查询联系人信息
        List<AioveuContact> contacts = this.listByIds(contactIds);

        // 转换为Map: key=联系人ID, value=联系人名称
        return contacts.stream()
                .collect(Collectors.toMap(
                        AioveuContact::getId,
                        AioveuContact::getName
                ));
    }

    /**
     * 获取所有联系人列表（用于下拉选择框）
     *
     * @return 联系人选项列表
     */
    @Override
    public List<ContactOptionVO> getAllContactOptions() {
        // 查询所有联系人
        List<AioveuContact> contacts = this.list();

        // 转换为选项对象
        List<ContactOptionVO>  contactOptionVO  = contacts.stream()
                .map(contact -> new ContactOptionVO(contact.getId(), contact.getName()))
                .collect(Collectors.toList());

        return contactOptionVO;
    }

    /**
     * 批量设置名称到VO对象，将视图对象的客户id,转换为客户姓名
     */
    private void setCustomerNames(List<AioveuContactVO> contactVOS) {
        CustomerNameSetter.setCustomerNames(
                contactVOS,
                AioveuContactVO::getCustomerId, // 获取ID
                AioveuContactVO::setCustomerName, // 设置姓名
                aioveuCustomerService
        );
    }

}
