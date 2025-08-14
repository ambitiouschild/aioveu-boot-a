package com.aioveu.boot.system.service;

import com.aioveu.boot.system.model.entity.Member;
import com.aioveu.boot.system.model.form.MemberForm;
import com.aioveu.boot.system.model.query.MemberQuery;
import com.aioveu.boot.system.model.vo.MemberVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 会员信息服务类
 *
 * @author ambitiouschild
 * @since 2025-07-11 15:13
 */
public interface MemberService extends IService<Member> {

    /**
     *会员信息分页列表
     *
     * @return {@link IPage<MemberVO>} 会员信息分页列表
     */
    IPage<MemberVO> getMemberPage(MemberQuery queryParams);

    /**
     * 获取会员信息表单数据
     *
     * @param id 会员信息ID
     * @return 会员信息表单数据
     */
     MemberForm getMemberFormData(Long id);

    /**
     * 新增会员信息
     *
     * @param formData 会员信息表单对象
     * @return 是否新增成功
     */
    boolean saveMember(MemberForm formData);

    /**
     * 修改会员信息
     *
     * @param id   会员信息ID
     * @param formData 会员信息表单对象
     * @return 是否修改成功
     */
    boolean updateMember(Long id, MemberForm formData);

    /**
     * 删除会员信息
     *
     * @param ids 会员信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteMembers(String ids);

}
