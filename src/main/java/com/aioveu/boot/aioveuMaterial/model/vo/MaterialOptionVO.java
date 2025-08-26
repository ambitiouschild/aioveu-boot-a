package com.aioveu.boot.aioveuMaterial.model.vo;

/*
 * @Description: TODO 获取所有vo列表（用于下拉选择框）
 * @ClassName:  MaterialOptionVO
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/26  22:45
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/26 22:45
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "物资选项")
public class MaterialOptionVO {

    private Long materialId;
    /**
     * 物资名称
     */
    private String materialName;
    /**
     * 带参数的构造函数
     *
     * @param materialId 物资ID
     * @param materialName 物资名称
     */
    public MaterialOptionVO(Long materialId, String materialName) {
        this.materialId = materialId;
        this.materialName = materialName;
    }
}
