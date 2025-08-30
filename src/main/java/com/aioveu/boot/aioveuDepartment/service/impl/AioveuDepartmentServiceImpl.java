package com.aioveu.boot.aioveuDepartment.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import com.aioveu.boot.aioveuDepartment.model.vo.ParentDeptOptionVO;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import groovy.lang.Lazy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.ml.neuralnet.twod.util.TopographicErrorHistogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuDepartment.mapper.AioveuDepartmentMapper;
import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.form.AioveuDepartmentForm;
import com.aioveu.boot.aioveuDepartment.model.query.AioveuDepartmentQuery;
import com.aioveu.boot.aioveuDepartment.model.vo.AioveuDepartmentVO;
import com.aioveu.boot.aioveuDepartment.converter.AioveuDepartmentConverter;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * 公司部门组织结构服务实现类
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuDepartmentServiceImpl extends ServiceImpl<AioveuDepartmentMapper, AioveuDepartment> implements AioveuDepartmentService {

    private final AioveuDepartmentConverter aioveuDepartmentConverter;

    //确保 AioveuEmployeeService被正确注入到当前类中（例如通过@Autowired注解），然后通过实例调用 getById方法
    //在控制器层解决：但是这样会破坏分层架构，不推荐
//    private final AioveuEmployeeService aioveuEmployeeService;


    @Autowired
    private AioveuDepartmentMapper aioveuDepartmentMapper;

    @Override
    public Long getIdByName(String name) {
        return aioveuDepartmentMapper.findIdByName(name);
    }

    /**
    * 获取公司部门组织结构分页列表
    * 先进行 tovo,在vo层操作
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuDepartmentVO>} 公司部门组织结构分页列表
    */
    @Override
    public IPage<AioveuDepartmentVO> getAioveuDepartmentPage(AioveuDepartmentQuery queryParams) {

        // 处理上级部门名称映射
        if (StringUtils.isNotBlank(queryParams.getParentDeptName())) {
            Long parentDeptId = this.getIdByName(queryParams.getParentDeptName());
            queryParams.setParentDeptId(parentDeptId);
        }

        Page<AioveuDepartmentVO> pageVO = this.baseMapper.getAioveuDepartmentPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置部门名称
        setParentDeptNames(pageVO.getRecords());

//        // 设置经理姓名
//        setManagerNames(pageVO.getRecords());


        return pageVO;
    }
    
    /**
     * 获取公司部门组织结构表单数据
     *
     * @param id 公司部门组织结构ID
     * @return 公司部门组织结构表单数据
     */
    @Override
    public AioveuDepartmentForm getAioveuDepartmentFormData(Long id) {
        // 1. 根据ID获取部门实体信息
        AioveuDepartment entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("部门不存在");
        }

        // 2. 将实体转换为表单对象
        AioveuDepartmentForm form = aioveuDepartmentConverter.toForm(entity);

        // 3. 处理父部门信息（如果存在）
        if (entity.getParentDeptId() != null) {
            // 使用 MyBatis-Plus 的 LambdaQueryWrapper 查询父部门信息
            // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
            // 泛型 <AioveuDepartment> 指定了查询的实体类型
            LambdaQueryWrapper<AioveuDepartment> parentWrapper = new LambdaQueryWrapper<>();

            // 添加查询条件：部门ID等于指定值
            // AioveuDepartment::getDeptId 是方法引用，表示查询 dept_id 字段
            // entity.getParentDeptId() 是获取要查询的具体部门ID值
            parentWrapper.eq(AioveuDepartment::getDeptId, entity.getParentDeptId())
                    // 指定只选择 dept_name 字段，而不是所有字段
                    // 这是一个性能优化，减少不必要的数据传输
                    .select(AioveuDepartment::getDeptName); // 只选择需要的字段

            AioveuDepartment parentDepartment = this.getOne(parentWrapper);
            //AioveuDepartment department = aioveuDepartmentService.getOne(Wrapper);

            if (parentDepartment != null) {
                form.setParentDeptName(parentDepartment.getDeptName());
            }
        }

        // 4. 处理经理信息（如果存在）
        if (entity.getManagerId() != null) {
            // 在部门服务中不设置经理姓名，而是在展示层（如Controller）通过其他方式获取。
            // 这样部门服务就不需要依赖员工相关的组件。
            //
            // 例如，在Controller中获取部门信息后，再调用员工服务获取经理姓名。
            // 这样部门服务就只负责部门相关的逻辑，不会依赖员工服务
            //
            // 如果需要在此处设置经理姓名，可以取消注释下面的代码，并注入员工服务

//        Employee manager = employeeService.getById(entity.getManagerId());
//        if (manager != null) {
//            form.setManagerName(manager.getName());
//        }

            // 5. 关于使用 MyBatis-Plus 的 LambdaQueryWrapper 的说明：
            // 当前使用 MyBatis-Plus 的 LambdaQueryWrapper 方式，这种方式简洁易懂，但不符合纯 MyBatis 的方式。
            //
            // 如果您希望改为纯 MyBatis 的方式，可以按照以下步骤：
            // 步骤一：在 AioveuDepartmentMapper 中定义一个方法，例如：
            //     AioveuDepartment selectByDeptName(String deptName);
            // 步骤二：在 XML 文件中编写 SQL，或者使用注解方式。
            // 步骤三：在服务层调用这个方法。
            //
            // 示例（纯 MyBatis 方式）：
            /*
            if (entity.getParentDeptId() != null) {
                AioveuDepartment parentDepartment = aioveuDepartmentMapper.selectByDeptId(entity.getParentDeptId());
                if (parentDepartment != null) {
                    form.setParentDeptName(parentDepartment.getDeptName());
                }
            }
            */

        }

        return form;
    }
    
    /**
     * 新增公司部门组织结构
     *
     * @param formData 公司部门组织结构表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuDepartment(AioveuDepartmentForm formData) {


        // 检查是否已存在相同部门名称的记录
        LambdaQueryWrapper<AioveuDepartment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AioveuDepartment::getDeptName, formData.getDeptName());
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new RuntimeException("部门名称已存在");
        }

        // 处理父部门：根据父部门名称查询父部门ID
        if (formData.getParentDeptName() != null && !formData.getParentDeptName().trim().isEmpty()) {
            // 根据父部门名称查询父部门
            // 构建查询条件：根据父部门名称精确匹配查询父部门记录
            LambdaQueryWrapper<AioveuDepartment> parentQueryWrapper = new LambdaQueryWrapper<>();
            parentQueryWrapper.eq(AioveuDepartment::getDeptName, formData.getParentDeptName());

            // 执行查询，获取父部门信息
            AioveuDepartment parentDepartment = this.getOne(parentQueryWrapper);
            if (parentDepartment != null) {
                formData.setParentDeptId(parentDepartment.getDeptId());
            } else {
                // 如果没有找到父部门，可以抛出异常或设置为null
                formData.setParentDeptId(null);
//                 或者抛出异常：throw new RuntimeException("父部门名称不存在");
                throw new RuntimeException("父部门名称不存在");
            }
        }

        //这样，您就不需要调用DepartmentIdFinderWithCompositeKey.setDepartmentIdsByCompositeKeysform方法了。
        //如果您确实需要保留复合键查询的功能，那么请确保复合键的生成和解析方式一致。但是，在保存部门的场景下，您实际上不需
        // 要通过复合键查询部门ID，因为您是要保存新部门，而不是查询已存在的部门。
        //如果可能，考虑修改表单设计，让用户直接选择父部门（通过ID而不是名称）：
        //您就不需要通过复合键来查找父部门ID了。
        //在您的保存部门场景中，由于是处理单个表单的保存操作，使用单个查询是合适的选择。如果您需要处理批量导入或多个部门同时保存，那么使用 Map 进行批量查询会更高效。

        // 调用方法 - 现在可以直接传入单个对象
        AioveuDepartment entity = aioveuDepartmentConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新公司部门组织结构
     *
     * @param id   公司部门组织结构ID
     * @param formData 公司部门组织结构表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuDepartment(Long id,AioveuDepartmentForm formData) {
        log.info("开始更新部门: ID={}", id);

        // 1. 获取原始部门信息
        AioveuDepartment original = getById(id);
        if (original == null) {
            log.error("部门不存在: ID={}", id);
            throw new ServiceException("部门不存在");
        }

        // 2. 将表单数据转换为实体对象  表单里没有id,所以要把id传进来
        /*
        * 是的，表单对象（通常是前端提交的数据）通常不包含ID，因为ID通常通过URL路径参数传递（对于更新操作）。
        * 因此，在服务层中，您需要将传入的ID设置到实体对象中，然后再执行更新
        * 是的，表单对象（通常是前端提交的数据）通常不包含ID，因为ID通常通过URL路径参数传递（对于更新操作）。因此，在服务层中，您需要将传入的ID设置到实体对象中，然后再执行更新。
          在更新操作中，表单数据通常不包含ID（如 deptId），因为ID是通过URL路径参数传递的。
        * 因此，在服务层中，您需要将传入的ID设置到实体对象中，然后再执行更新
        关键步骤
        1.从路径参数获取ID：
            •在控制器中，通过 @PathVariable获取ID
            •将ID传递给服务层
        2.在服务层设置ID：
            •将表单数据转换为实体对象
            •将传入的ID设置到实体对象中
            •然后执行更新

        为什么需要手动设置ID？
        1.安全考虑：
            •不允许前端直接设置ID
            •ID由后端控制
        2.RESTful 设计：
            •资源ID通过URL路径传递
            •请求体只包含需要更新的字段
        3.数据完整性：
            •确保更新的是指定的资源
            •避免ID被意外修改
        * */

        // 2. 处理父部门关系：根据父部门名称查询父部门ID - 使用单个查询
        if (formData.getParentDeptName() != null && !formData.getParentDeptName().trim().isEmpty()) {
            // 构建查询条件：根据父部门名称精确匹配
            LambdaQueryWrapper<AioveuDepartment> parentQueryWrapper = new LambdaQueryWrapper<>();
            parentQueryWrapper.eq(AioveuDepartment::getDeptName, formData.getParentDeptName());

            // 执行单个查询获取父部门信息
            AioveuDepartment parentDepartment = this.getOne(parentQueryWrapper);

            if (parentDepartment != null) {
                // 设置父部门ID到表单数据中
                formData.setParentDeptId(parentDepartment.getDeptId());
                log.info("找到父部门: ID={}, 名称={}", parentDepartment.getDeptId(), formData.getParentDeptName());
            } else {
                // 处理父部门不存在的情况
                formData.setParentDeptId(null);
                log.warn("未找到父部门: 名称={}", formData.getParentDeptName());
                // 根据业务需求，可以选择抛出异常或保留null值
                // throw new ServiceException("父部门名称不存在: " + formData.getParentDeptName());
            }
        } else {
            // 如果父部门名称为空，确保父部门ID也为空
            formData.setParentDeptId(null);
        }


        // 3. 将表单数据转换为实体对象
        // 注意：表单对象通常不包含ID，因此需要手动设置ID,这是与新建的最大区别，保留原始数据的未更新字段
        AioveuDepartment entity = aioveuDepartmentConverter.toEntity(formData);
        entity.setDeptId(id); // 设置部门ID
        log.info("转换后的实体对象: {}", entity);

        // 3. 复制未修改的字段
        entity.setCreateTime(original.getCreateTime());


        return this.updateById(entity);
    }
    
    /**
     * 删除公司部门组织结构
     *
     * @param ids 公司部门组织结构ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuDepartments(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的公司部门组织结构数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }


    /**
     * 批量获取部门信息（新增方法）
     */
    @Override
    public Map<Long, String> getDepartmentMapByIds(List<Long> deptIds) {
        if (deptIds == null || deptIds.isEmpty()) {
            return Map.of();
        }

        // 批量查询部门信息
        List<AioveuDepartment> departments = this.listByIds(deptIds);

        // 转换为Map: key=部门ID, value=部门名称
        return departments.stream()
                .collect(Collectors.toMap(
                        AioveuDepartment::getDeptId,
                        AioveuDepartment::getDeptName
                ));
    }

    /**
     * 根据部门名称列表获取部门ID映射
     */
    @Override
    public Map<String, Long> getDepartmentIdMapByNames(List<String> deptNames) {
        if (deptNames == null || deptNames.isEmpty()) {
            return Map.of();
        }

        // 使用部门名称列表查询部门
        LambdaQueryWrapper<AioveuDepartment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(AioveuDepartment::getDeptName, deptNames);
        List<AioveuDepartment> departments = this.list(queryWrapper);

        // 转换为Map: key=部门名称, value=部门ID
        return departments.stream()
                .collect(Collectors.toMap(
                        AioveuDepartment::getDeptName,
                        AioveuDepartment::getDeptId
                ));
    }

    /**
     * 获取所有部门列表（用于下拉选择框）
     *
     * @return 部门选项列表
     */
    @Override
    public List<DeptOptionVO> getAllDepartmentOptions() {
        // 查询所有部门
        List<AioveuDepartment> departments = this.list();

        // 转换为选项对象
        List<DeptOptionVO>  DeptOptionVO  = departments.stream()
                .map(dept -> new DeptOptionVO(dept.getDeptId(), dept.getDeptName()))
                .collect(Collectors.toList());

        return DeptOptionVO;
    }


    /**
     *  批量设置名称到VO对象，将PositionVO岗位表视图对象的部门id,转换为部门名称，只被分页列表调用
     */
//    private void setParentDeptNames(List<AioveuDepartmentVO> departmentVOs) {
//        if (departmentVOs == null || departmentVOs.isEmpty()) {
//            return;
//        }
//
//        // 获取所有上级部门ID（去重）
//        List<Long> parentDeptIds   = departmentVOs.stream()
//                .map(AioveuDepartmentVO::getParentDeptId)
//                .filter(Objects::nonNull)
//                .distinct()
//                .collect(Collectors.toList());
//
//        if (parentDeptIds.isEmpty()) {
//            // 所有部门都没有上级部门，设置默认值
//            return;
//        }
//
//        // 批量查询部门信息
//        Map<Long, String> deptMap = getDepartmentMapByIds(parentDeptIds);
//
//        // 设置部门名称
//        departmentVOs.forEach(vo -> {
//            //遍历列表：使用 forEach方法遍历 employeeVOs中的每个员工对象（vo）。
//            //检查 vo.getDeptId()非空（防止空指针异常）
//            //同时检查 deptMap中包含该岗位ID的键（确保映射中存在对应关系）
//            if (vo.getParentDeptId() != null && deptMap.containsKey(vo.getParentDeptId())) {
//                //通过 deptMap.getOrDefault()方法获取岗位名称：若存在则返回映射值
//                //调用 vo.setDeptName()将名称设置到员工对象中
//                vo.setParentDeptName(deptMap.get(vo.getParentDeptId()));
//            }else if (vo.getParentDeptId() == null) {
//                // 没有上级部门
//                vo.setParentDeptName("");
//            }
//        });
//    }




    /**
     * 批量设置名称到VO对象，将视图对象的部门id,转换为部门姓名
     */
    private void setParentDeptNames(List<AioveuDepartmentVO> departmentVOS) {
        DepartmentNameSetter.setDepartmentNames(
                departmentVOS,
                AioveuDepartmentVO::getParentDeptId, // 获取ID
                AioveuDepartmentVO::setParentDeptName, // 设置姓名
                this
        );
    }

    /**
     *  批量设置名称到VO对象，将AioveuDepartmentVO岗位表视图对象的部门id,转换为经理姓名，只被分页列表调用
     */
//    private void setManagerNames(List<AioveuDepartmentVO> departmentVOs) {
//        if (departmentVOs == null || departmentVOs.isEmpty()) {
//            return;
//        }
//
//        // 获取所有ManagerID（去重）
//        List<Long> ManagerIds  = departmentVOs.stream()
//                .map(AioveuDepartmentVO::getManagerId)
//                .filter(Objects::nonNull)
//                .distinct()
//                .collect(Collectors.toList());
//
//        if (ManagerIds.isEmpty()) {
//            return;
//        }
//
//        // 批量查询部门信息
//        Map<Long, String> managerMap = aioveuEmployeeService.getEmployeeMapByIds(ManagerIds);
//
//        // 设置部门名称
//        departmentVOs.forEach(vo -> {
//            //遍历列表：使用 forEach方法遍历 employeeVOs中的每个员工对象（vo）。
//            //检查 vo.getDeptId()非空（防止空指针异常）
//            //同时检查 deptMap中包含该岗位ID的键（确保映射中存在对应关系）
//            if (vo.getManagerId() != null && managerMap.containsKey(vo.getManagerId())) {
//                //通过 deptMap.getOrDefault()方法获取岗位名称：若存在则返回映射值
//                //调用 vo.setDeptName()将名称设置到员工对象中
//                vo.setManagerName(managerMap.get(vo.getManagerId()));
//            }else if (vo.getManagerId() == null) {
//                // 没有上级部门
//                vo.setManagerName("");
//            }
//        });
//


    // 在服务层直接实现，不需要在 Mapper 中添加方法
    //利用了您已经在使用的 MyBatis-Plus 框架，避免了复杂的 XML 配置和 `@MapKey`注解的问题
    @Override
    public Map<String, Long> getDepartmentIdMapByCompositeKeys(List<String> compositeKeys) {
        // 检查输入参数是否为空或空列表
        if (compositeKeys == null || compositeKeys.isEmpty()) {
            return Collections.emptyMap();
        }

        // 初始化结果Map，用于存储复合键到部门ID的映射
        Map<String, Long> resultMap = new HashMap<>();

        // 遍历所有复合键
        for (String compositeKey : compositeKeys) {
            // 使用竖线分隔符拆分复合键
            String[] parts = compositeKey.split("\\|");

            // 确保复合键至少包含两部分（部门名称和父部门ID）
            if (parts.length >= 2) {
                // 第一部分是部门名称
                String deptName = parts[0];
                // 初始化父部门ID为null
                Integer parentDeptId = null;
                // 如果复合键包含第二部分（父部门ID）
                if (parts.length > 1) {
                    try {
                        // 尝试将第二部分转换为整数作为父部门ID
                        parentDeptId = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        // 如果转换失败，跳过当前复合键的处理
                        continue;
                    }
                }

                // 使用 MyBatis-Plus 的 Lambda 查询
                // 查询条件：部门名称等于指定值，如果父部门ID不为空则添加父部门ID条件
                AioveuDepartment department = aioveuDepartmentMapper.selectOne(
                        new QueryWrapper<AioveuDepartment>()
                                .eq("dept_name", deptName) // 部门名称相等条件
                                .eq(parentDeptId != null, "parent_dept_id", parentDeptId) // 条件性添加父部门ID条件
                );

                // 如果查询到对应的部门记录
                if (department != null) {
                    // 将复合键和部门ID添加到结果Map中
                    resultMap.put(compositeKey, department.getDeptId().longValue());
                }
            }
        }

        // 返回包含所有有效映射的结果Map
        return resultMap;
    }

}
