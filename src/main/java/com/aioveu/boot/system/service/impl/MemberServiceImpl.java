package com.aioveu.boot.system.service.impl;

import com.aioveu.boot.common.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.system.mapper.MemberMapper;
import com.aioveu.boot.system.service.MemberService;
import com.aioveu.boot.system.model.entity.Member;
import com.aioveu.boot.system.model.form.MemberForm;
import com.aioveu.boot.system.model.query.MemberQuery;
import com.aioveu.boot.system.model.vo.MemberVO;
import com.aioveu.boot.system.converter.MemberConverter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 会员信息服务实现类
 *
 * @author ambitiouschild
 * @since 2025-07-11 15:13
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    private final MemberConverter memberConverter;

    /**
    * 获取会员信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<MemberVO>} 会员信息分页列表
    */
    @Override
    public IPage<MemberVO> getMemberPage(MemberQuery queryParams) {
        Page<MemberVO> pageVO = this.baseMapper.getMemberPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取会员信息表单数据
     *
     * @param id 会员信息ID
     * @return 会员信息表单数据
     */
    @Override
    public MemberForm getMemberFormData(Long id) {
        Member entity = this.getById(id);
        return memberConverter.toForm(entity);
    }
    
    /**
     * 新增会员信息
     *
     * @param formData 会员信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveMember(MemberForm formData) {
        Member entity = memberConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新会员信息
     *
     * @param id   会员信息ID
     * @param formData 会员信息表单对象
     * @return 是否修改成功
     * 根据代码，这里使用的是MyBatis-Plus的updateById方法。
     * 解决方案：
     *
     * 确保传入的id被设置到entity对象中。
     * 使用MyBatis-Plus的自动填充功能来更新updateTime字段。
     */
    @Override
    public boolean updateMember(Long id,MemberForm formData) {
        // 1. 验证ID是否存在
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("会员ID不能为空");
        }

        // 2. 使用UpdateWrapper进行部分更新
        UpdateWrapper<Member> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .eq("is_deleted", 0) // 确保未删除
                .set("update_time", LocalDateTime.now());

        // 3. 设置需要更新的字段
        if (formData.getName() != null) {
            updateWrapper.set("name", formData.getName());
        }
        if (formData.getMobile() != null) {
            updateWrapper.set("mobile", formData.getMobile());
        }
        if (formData.getGender() != null) {
            updateWrapper.set("gender", formData.getGender());
        }
        if (formData.getAge() != null) {
            updateWrapper.set("age", formData.getAge());
        }

        // 4. 使用乐观锁（如果配置了）
        Member existingMember = this.getById(id);
        if (existingMember != null && existingMember.getVersion() != null) {
            updateWrapper.eq("version", existingMember.getVersion());
        }

        // 5. 执行更新
        boolean updated = this.update(updateWrapper);

        // 6. 检查更新结果
        if (!updated) {
            // 处理更新失败的情况
            if (this.getById(id) == null) {
                throw new BusinessException("会员不存在，ID: " + id);
            } else if (this.getById(id).getIsDeleted() == 1) {
                throw new BusinessException("会员已被删除，无法更新");
            } else {
                throw new BusinessException("数据已被修改，请刷新后重试");
            }
        }

        return true;

//        Member entity = memberConverter.toEntity(formData);
//        return this.updateById(entity);


    }
    
    /**
     * 删除会员信息
     *
     * @param ids 会员信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteMembers(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的会员信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
