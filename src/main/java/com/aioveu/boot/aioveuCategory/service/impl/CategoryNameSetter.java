package com.aioveu.boot.aioveuCategory.service.impl;

import com.aioveu.boot.aioveuCategory.service.AioveuCategoryService;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import org.apache.poi.ss.formula.functions.T;
import org.glassfish.jaxb.core.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CategoryNameSetter {

    /**
     * 批量设置库存分类姓名到VO对象
     *
     * @param vos VO对象列表
     * @param idGetter 从VO对象获取库存分类ID的方法
//     * @param nameSetter 设置库存分类姓名到VO对象的方法
     * @param aioveuCategoryService 库存分类服务
     * @param <T> VO对象类型
     */
    public static <T> void setCategoryNames(
            List<T> vos,                //实例化参数
            Function<T, Long> idGetter, //实例化参数
            NameSetter<T> nameSetter,   //实例化参数
            AioveuCategoryService aioveuCategoryService
    ) {
        if (vos == null || vos.isEmpty()) {
            return;
        }

        // 获取所有库存分类ID
        List<Long> categoryIds = vos.stream()
                .map(idGetter)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (categoryIds.isEmpty()) {
            return;
        }

        // 批量查询库存分类信息
        Map<Long, String> categoryMap = aioveuCategoryService.getCategoryMapByIds(categoryIds);

        // 设置库存分类姓名
        vos.forEach(vo -> {
            Long categoryId = idGetter.apply(vo);
            if (categoryId != null && categoryMap.containsKey(categoryId)) {
                nameSetter.set(vo, categoryMap.get(categoryId));
            } else if (categoryId != null) {
                nameSetter.set(vo, "");
            }
        });

        //如果转换为vo.set,则需要改set方法
        //必须要有namesetter，否则每变换一个vos,都需要一个set方法
    }

    /**
     * 设置姓名的函数式接口
     *
     * @param <T> VO对象类型
     */
    @FunctionalInterface
    public interface NameSetter<T> {
        void set(T vo, String name);
    }

    //这个接口是一个函数式接口，只有一个抽象方法set，接受两个参数：T类型的vo和String类型的name。
    //NameSetter是一个函数式接口（通过 @FunctionalInterface注解标记），它允许你使用 Lambda 表达式或方法引用来动态定义如何设置对象的名称字段
    /***函数式接口只定义了一个抽象方法 set(T vo, String name)。
     *  当你传递 EmployeeVO::setCategoryName或 Lambda 表达式 (vo, name) -> vo.setCategoryName(name)时，Java 会自动将其匹配到 NameSetter接口的 set方法。
     *  在 setCategoryNames方法内部，通过 nameSetter.set(vo, categoryName)实际调用的是 EmployeeVO.setCategoryName(String name)。
     *  为什么需要 NameSetter
     *  通用性：该方法不依赖具体 VO 类的类型，任何具有 Long类型 ID 和需要设置名称的类均可复用。
     *  解耦：不需要为每个 VO 类编写重复的设置逻辑，通过传入不同的 NameSetter实现动态行为。
     *
     *  假设我们有一个VO类叫做EmployeeVO，它有一个方法setCategoryName(String name)。
     *  NameSetter<EmployeeVO> nameSetter = (vo, name) -> vo.setCategoryName(name);   // Lambda 实现 NameSetter
     *  或者使用方法引用：
     *  NameSetter<EmployeeVO> nameSetter = EmployeeVO::setCategoryName;  （等价写法）   //方法引用直接指向 setter
     *
     *  这样，当我们在setCategoryNames方法中调用nameSetter.set(vo, name)时，实际上就是调用了vo.setCategoryName(name)。
     *
     *  NameSetter的实现本质是传递行为而非数据，通过 Lambda 或方法引用将设置名称的具体逻辑交给调用者定义，从而保持代码的灵活性和复用性。
     *
     * ***/

}

/**
 * @Description: TODO  通用库存分类姓名设置工具类调用
 * @Author: 雒世松
 * @Date: 2025/8/26 18:53
 * @param null
 * @return:
 *


    @Autowired
    private AioveuCategoryService aioveuCategoryService;

    setCategoryNames(pageVO.getRecords());

    private void setCategoryNames(List<AioveuMaterialVO> materialVOS) {
        CategoryNameSetter.setCategoryNames(
            materialVOS ,
            AioveuMaterialVO::getCategoryId, // 获取库存分类ID
            AioveuMaterialVO::setCategoryName, // 设置库存分类姓名
            aioveuCategoryService
    );
    }

*/

/**
 * 批量设置名称到VO对象，将视图对象的id,转换为姓名
 */




