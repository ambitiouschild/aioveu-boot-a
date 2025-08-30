package com.aioveu.boot.aioveuPosition.service.impl;

import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import com.aioveu.boot.aioveuPosition.model.vo.PositionOptionVO;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuPosition.mapper.AioveuPositionMapper;
import com.aioveu.boot.aioveuPosition.service.AioveuPositionService;
import com.aioveu.boot.aioveuPosition.model.entity.AioveuPosition;
import com.aioveu.boot.aioveuPosition.model.form.AioveuPositionForm;
import com.aioveu.boot.aioveuPosition.model.query.AioveuPositionQuery;
import com.aioveu.boot.aioveuPosition.model.vo.AioveuPositionVO;
import com.aioveu.boot.aioveuPosition.converter.AioveuPositionConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 公司岗位信息服务实现类
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuPositionServiceImpl extends ServiceImpl<AioveuPositionMapper, AioveuPosition> implements AioveuPositionService {

    private final AioveuPositionConverter aioveuPositionConverter;
    //添加部门服务依赖,注入 `AioveuDepartmentService`用于查询部门信息
    private final AioveuDepartmentService aioveuDepartmentService;

    @Autowired
    private AioveuPositionMapper positionMapper;

    @Override
    public Long getIdByName(String name) {
        return positionMapper.findIdByName(name);
    }

    /**
    * 获取公司岗位信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuPositionVO>} 公司岗位信息分页列表
    */
    @Override
    public IPage<AioveuPositionVO> getAioveuPositionPage(AioveuPositionQuery queryParams) {
        // 调用自定义Mapper方法进行分页查询
        Page<AioveuPositionVO> pageVO = this.baseMapper.getAioveuPositionPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );


        // 设置部门名称
        setDeptNames(pageVO.getRecords());

        return pageVO;
    }


    /**
     * 获取公司岗位信息表单数据
     *
     * @param id 公司岗位信息ID
     * @return 公司岗位信息表单数据
     */
    @Override
    public AioveuPositionForm getAioveuPositionFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuPosition entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }

        // 2. 将实体转换为表单对象
        AioveuPositionForm form = aioveuPositionConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getDeptId() != null) {
            // 使用 MyBatis-Plus 的 LambdaQueryWrapper 查询信息
            LambdaQueryWrapper<AioveuDepartment> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuDepartment::getDeptId, entity.getDeptId())
                    .select(AioveuDepartment::getDeptName); // 只选择需要的字段

            AioveuDepartment department = aioveuDepartmentService.getOne(Wrapper);

            if (department != null) {
                form.setDeptName(department.getDeptName());
            }
        }


        return form;
    }
    
    /**
     * 新增公司岗位信息
     *
     * @param formData 公司岗位信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuPosition(AioveuPositionForm formData) {
        // 字段1：检查是否已存在相同名称的记录（对于不依赖外键的字段）
        LambdaQueryWrapper<AioveuPosition> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AioveuPosition::getPositionName, formData.getPositionName());
        if (count(queryWrapper) > 0) {
            throw new RuntimeException("名称已存在");
        }

        // 字段2：检查是否存在记录（对于必须依赖外键的字段）
        LambdaQueryWrapper<AioveuDepartment> depWrapper = new LambdaQueryWrapper<>();
        depWrapper.eq(AioveuDepartment::getDeptName, formData.getDeptName());


        AioveuDepartment department = aioveuDepartmentService.getOne(depWrapper);
        if (department != null) {
            formData.setDeptId(department.getDeptId());
        } else {
            throw new RuntimeException("部门名称不存在");
        }


        AioveuPosition entity = aioveuPositionConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新公司岗位信息
     *
     * @param id   公司岗位信息ID
     * @param formData 公司岗位信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuPosition(Long id,AioveuPositionForm formData) {
        log.info("开始更新岗位: ID={}", id);

        // 1. 获取原始岗位信息  确保岗位存在 获取原始数据用于复制未修改字段
        AioveuPosition original = getById(id);
        if (original == null) {
            log.error("岗位不存在: ID={}", id);
            throw new ServiceException("岗位不存在");
        }

        // 2. 检查岗位名称唯一性（如果需要）
        if (!original.getPositionName().equals(formData.getPositionName())) {
            LambdaQueryWrapper<AioveuPosition> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AioveuPosition::getPositionName, formData.getPositionName())
                    .ne(AioveuPosition::getPositionId, id);

            if (count(wrapper) > 0) {
                log.error("岗位名称已存在: {}", formData.getPositionName());
                throw new ServiceException("岗位名称 " + formData.getPositionName() + " 已存在");
            }
        }


        // 字段2：检查是否存在记录（对于必须依赖外键的字段）
        LambdaQueryWrapper<AioveuDepartment> depWrapper = new LambdaQueryWrapper<>();
        depWrapper.eq(AioveuDepartment::getDeptName, formData.getDeptName());


        AioveuDepartment department = aioveuDepartmentService.getOne(depWrapper);
        if (department != null) {
            formData.setDeptId(department.getDeptId());
        } else {
            throw new RuntimeException("部门名称不存在");
        }

        // 字段3：检查是否存在记录（对于必须依赖外键的字段）
//        if (formData.getPositionLevel() >= 10) {
//            throw new RuntimeException("职位级别错误");
//        }else if(formData.getPositionLevel() <= 0){
//            throw new RuntimeException("职位级别错误");
//        }


        // 3. 将表单数据转换为实体对象
        AioveuPosition entity = aioveuPositionConverter.toEntity(formData);

        // 4. 设置岗位ID（关键步骤）
        entity.setPositionId(id);  //手动设置表单数据中没有的ID字段，确保更新正确的岗位

        // 5. 复制未修改的字段（如创建时间等）
        entity.setCreateTime(original.getCreateTime());//保留创建时间和创建人信息，避免覆盖重要字段

        // 6. 设置乐观锁版本号
//        entity.setVersion(original.getVersion());

        // 7. 执行更新
        boolean result = updateById(entity);
        log.info("更新岗位结果: {}", result);
        return result;
    }
    
    /**
     * 删除公司岗位信息
     *
     * @param ids 公司岗位信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuPositions(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的公司岗位信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    //---------------------------------------------------

    /**
     * 批量设置名称到VO对象,，将PositionVO岗位表视图对象的部门id,转换为部门名称
     */
    private void setDeptNames(List<AioveuPositionVO> positionVOs) {
        if (positionVOs == null || positionVOs.isEmpty()) {
            return;
        }

        // 获取所有部门ID
        List<Long> deptIds = positionVOs.stream()
                .map(AioveuPositionVO::getDeptId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (deptIds.isEmpty()) {
            return;
        }

        // 批量查询部门信息
        Map<Long, String> deptMap = aioveuDepartmentService.getDepartmentMapByIds(deptIds);

        // 设置部门名称
        positionVOs.forEach(vo -> {
            if (vo.getDeptId() != null && deptMap.containsKey(vo.getDeptId())) {
                vo.setDeptName(deptMap.getOrDefault(vo.getDeptId(), "未知部门"));
            }
        });
    }

    //---------------------------------------------------

    /**
     * 批量获取岗位信息（新增方法）
     */
    @Override
    public Map<Long, String> getPositionMapByIds(List<Long> positionIds) {
        if (positionIds == null || positionIds.isEmpty()) {
            return Map.of();
        }

        // 批量查询岗位信息
        List<AioveuPosition> positions = this.listByIds(positionIds);

        // 转换为Map: key=岗位ID, value=岗位名称
        return positions.stream()
                .collect(Collectors.toMap(
                        AioveuPosition::getPositionId,
                        AioveuPosition::getPositionName
                ));
    }

    /**
     * 获取所有岗位列表（用于下拉选择框）
     *
     * @return 岗位选项列表
     */
    @Override
    public List<PositionOptionVO> getAllPositionOptions() {
        // 查询所有岗位
        List<AioveuPosition> positions = this.list();

        // 转换为选项对象
        List<PositionOptionVO>  PositionVO  = positions.stream()
                .map(position -> new PositionOptionVO(position.getPositionId(), position.getPositionName()))
                .collect(Collectors.toList());

        return PositionVO;
    }


}
