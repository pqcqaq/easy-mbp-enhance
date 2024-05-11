package online.zust.qcqcqc.easymbpenhance.config;

import online.zust.qcqcqc.easymbpenhance.module.demo.entity.CompanyGroup;
import online.zust.qcqcqc.services.module.attachment.entity.Attachment;
import online.zust.qcqcqc.utils.enhance.checker.CheckerConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qcqcqc
 * Date: 2024/4/3
 * Time: 0:27
 */
@Component
public class MyCheckerConfig implements CheckerConfig {
    @Override
    public List<Class<?>> needInversePointCheck() {
        ArrayList<Class<?>> classes = new ArrayList<>();
        // 在这里配置的实体类会在删除时进行反向指针检查，也就是检查有没有任何引用存在
        // 比如这里Attachment在User的头像字段中引用，所以在删除Attachment时会检查有没有任何User引用
        // 注意：反指针检查只对一对一关联进行检查
        classes.add(Attachment.class);
        return classes;
    }

    @Override
    public List<Class<?>> needForwardPointerCheck() {
        // 在这里配置的实体类会在删除时进行前向指针检查，也就是检查有没有任何子项存在
        // 注意：正向指针只对一对多以及多对多进行检查
        ArrayList<Class<?>> classes = new ArrayList<>();
        // 检查的错误信息就是注解@MsgOnCheckError中的信息
        classes.add(CompanyGroup.class);
        return classes;
    }
}
