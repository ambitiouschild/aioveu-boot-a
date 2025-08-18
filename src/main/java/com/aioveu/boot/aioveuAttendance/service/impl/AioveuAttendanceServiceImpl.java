package com.aioveu.boot.aioveuAttendance.service.impl;

import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuAttendance.mapper.AioveuAttendanceMapper;
import com.aioveu.boot.aioveuAttendance.service.AioveuAttendanceService;
import com.aioveu.boot.aioveuAttendance.model.entity.AioveuAttendance;
import com.aioveu.boot.aioveuAttendance.model.form.AioveuAttendanceForm;
import com.aioveu.boot.aioveuAttendance.model.query.AioveuAttendanceQuery;
import com.aioveu.boot.aioveuAttendance.model.vo.AioveuAttendanceVO;
import com.aioveu.boot.aioveuAttendance.converter.AioveuAttendanceConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 考勤信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 21:52
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuAttendanceServiceImpl extends ServiceImpl<AioveuAttendanceMapper, AioveuAttendance> implements AioveuAttendanceService {

    private final AioveuAttendanceConverter aioveuAttendanceConverter;

    /**
    * 获取考勤信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuAttendanceVO>} 考勤信息分页列表
    */
    @Override
    public IPage<AioveuAttendanceVO> getAioveuAttendancePage(AioveuAttendanceQuery queryParams) {
        Page<AioveuAttendanceVO> pageVO = this.baseMapper.getAioveuAttendancePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取考勤信息表单数据
     *
     * @param id 考勤信息ID
     * @return 考勤信息表单数据
     */
    @Override
    public AioveuAttendanceForm getAioveuAttendanceFormData(Long id) {
        AioveuAttendance entity = this.getById(id);
        return aioveuAttendanceConverter.toForm(entity);
    }
    
    /**
     * 新增考勤信息
     *
     * @param formData 考勤信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuAttendance(AioveuAttendanceForm formData) {
        AioveuAttendance entity = aioveuAttendanceConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新考勤信息
     *
     * @param id   考勤信息ID
     * @param formData 考勤信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuAttendance(Long id,AioveuAttendanceForm formData) {
        log.info("开始更新考勤记录: ID={}", id);

        // 1. 获取原始考勤信息
        AioveuAttendance original = getById(id);
        if (original == null) {
            log.error("考勤记录不存在: ID={}", id);
            throw new ServiceException("考勤记录不存在");
        }


        // 3. 将表单数据转换为实体对象
        AioveuAttendance entity = aioveuAttendanceConverter.toEntity(formData);

        // 4. 设置考勤ID
        entity.setAttendanceId(id);

        // 5. 复制未修改的字段
        entity.setCreateTime(original.getCreateTime());

        return this.updateById(entity);
    }
    
    /**
     * 删除考勤信息
     *
     * @param ids 考勤信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuAttendances(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的考勤信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
