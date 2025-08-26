package com.aioveu.boot.aioveuContact.model.vo;

/*
 * @Description: TODO 获取所有联系人vo列表（用于下拉选择框）
 * @ClassName:  ContactOptionVO
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/27  0:57
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/27 0:57
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "联系人选项")
public class ContactOptionVO {

    private Long contactId;
    /**
     * 联系人名称
     */
    private String contactName;
    /**
     * 带参数的构造函数
     *
     * @param contactId 联系人ID
     * @param contactName 联系人名称
     */
    public ContactOptionVO(Long contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }
}
