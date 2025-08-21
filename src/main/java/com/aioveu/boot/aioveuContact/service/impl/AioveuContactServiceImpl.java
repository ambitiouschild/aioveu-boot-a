package com.aioveu.boot.aioveuContact.service.impl;

import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
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

    /**
    * 获取客户联系人分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuContactVO>} 客户联系人分页列表
    */
    @Override
    public IPage<AioveuContactVO> getAioveuContactPage(AioveuContactQuery queryParams) {
        Page<AioveuContactVO> pageVO = this.baseMapper.getAioveuContactPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
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
        AioveuContact entity = this.getById(id);
        return aioveuContactConverter.toForm(entity);
    }
    
    /**
     * 新增客户联系人
     *
     * @param formData 客户联系人表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuContact(AioveuContactForm formData) {
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

}
