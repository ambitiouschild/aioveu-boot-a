package com.aioveu.boot.aioveuPosition.service;

import com.aioveu.boot.aioveuPosition.model.entity.AioveuPosition;
import com.aioveu.boot.aioveuPosition.model.form.AioveuPositionForm;
import com.aioveu.boot.aioveuPosition.model.query.AioveuPositionQuery;
import com.aioveu.boot.aioveuPosition.model.vo.AioveuPositionVO;
import com.aioveu.boot.aioveuPosition.model.vo.PositionOptionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 公司岗位信息服务类
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
public interface AioveuPositionService extends IService<AioveuPosition> {



    Long getIdByName(String name);
    /**
     * 批量获取岗位信息（新增方法）
     */
    Map<Long, String> getPositionMapByIds(List<Long> positionIds);


    /**
     * 获取所有部门列表（用于下拉选择框）
     *
     * @return 岗位选项列表
     */

    List<PositionOptionVO> getAllPositionOptions();
    /**
     *公司岗位信息分页列表
     *
     * @return {@link IPage<AioveuPositionVO>} 公司岗位信息分页列表
     */
    IPage<AioveuPositionVO> getAioveuPositionPage(AioveuPositionQuery queryParams);


    /**
     * 获取公司岗位信息表单数据
     *
     * @param id 公司岗位信息ID
     * @return 公司岗位信息表单数据
     */
     AioveuPositionForm getAioveuPositionFormData(Long id);

    /**
     * 新增公司岗位信息
     *
     * @param formData 公司岗位信息表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuPosition(AioveuPositionForm formData);

    /**
     * 修改公司岗位信息
     *
     * @param id   公司岗位信息ID
     * @param formData 公司岗位信息表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuPosition(Long id, AioveuPositionForm formData);

    /**
     * 删除公司岗位信息
     *
     * @param ids 公司岗位信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuPositions(String ids);

}
