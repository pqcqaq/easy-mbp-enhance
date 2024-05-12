package online.zust.qcqcqc.easymbpenhance.module.demo.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.zust.qcqcqc.services.module.tasks.entity.DynamicCronTask;
import online.zust.qcqcqc.services.module.tasks.entity.SpelMetadata;
import online.zust.qcqcqc.services.module.tasks.enums.TaskType;
import online.zust.qcqcqc.services.module.tasks.services.DynamicTaskService;
import org.intellij.lang.annotations.Language;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author qcqcqc
 * Date: 2024/5/12
 * Time: 下午11:21
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TestCronTaskOnBoot implements ApplicationRunner {
    private final DynamicTaskService dynamicTaskService;
    private final ObjectMapper objectMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.warn("这是动态定时任务的使用Demo...............");
        DynamicCronTask dynamicCronTask = new DynamicCronTask();
        dynamicCronTask.setId(1L);
        dynamicCronTask.setTaskName("testTask");
        dynamicCronTask.setCronExpression("0/5 * * * * ?");
        dynamicCronTask.setTaskType(TaskType.SPEL);
        dynamicCronTask.setOnBoot(true);
        dynamicCronTask.setTaskExplain("这是一个测试任务");
        @Language("spel") String spel = "@logServiceImpl.warn('这是一个测试任务')";
        SpelMetadata spelMetadata = new SpelMetadata();
        spelMetadata.setSpel(spel);
        dynamicCronTask.setMetadata(objectMapper.writeValueAsString(spelMetadata));
        dynamicTaskService.saveOrUpdate(dynamicCronTask);
        dynamicTaskService.startTask(1L);
    }
}
