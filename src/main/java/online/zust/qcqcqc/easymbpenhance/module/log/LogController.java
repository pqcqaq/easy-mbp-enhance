package online.zust.qcqcqc.easymbpenhance.module.log;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import online.zust.qcqcqc.services.entity.dto.PageResult;
import online.zust.qcqcqc.services.entity.dto.Result;
import online.zust.qcqcqc.services.module.log.entity.OperatorLog;
import online.zust.qcqcqc.services.module.log.service.LogService;
import online.zust.qcqcqc.services.utils.DateUtils;
import online.zust.qcqcqc.utils.threads.Tasks;
import online.zust.qcqcqc.utils.utils.BeanConvertUtils;
import online.zust.qcqcqc.easymbpenhance.module.log.vo.OperatorLogVo;
import online.zust.qcqcqc.easymbpenhance.utils.StringUploadUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author qcqcqc
 * Date: 2024/4/10
 * Time: 23:30
 */
@RestController
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    @GetMapping("/logs")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<PageResult<OperatorLogVo>> pageLogs(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime
    ) {
        LambdaQueryWrapper<OperatorLog> operatorLogLambdaQueryWrapper = new LambdaQueryWrapper<>();
        operatorLogLambdaQueryWrapper.like(StringUploadUtils.isParamConcluded(keyword), OperatorLog::getMsg, keyword)
                .or()
                .like(StringUploadUtils.isParamConcluded(keyword), OperatorLog::getCause, keyword)
                .or()
                .like(StringUploadUtils.isParamConcluded(keyword), OperatorLog::getMetadata, keyword);
        operatorLogLambdaQueryWrapper.eq(StringUploadUtils.isParamConcluded(level), OperatorLog::getLevel, level);
        if (StringUploadUtils.isParamConcluded(startTime) && StringUploadUtils.isParamConcluded(endTime)) {
            List<Date> dates = DateUtils.stringToDateList(startTime, endTime);
            operatorLogLambdaQueryWrapper.between(dates.get(0) != null && dates.get(1) != null, OperatorLog::getCreateTime, dates.get(0), dates.get(1));
        }
        operatorLogLambdaQueryWrapper.orderByDesc(OperatorLog::getCreateTime);
        Page<OperatorLog> selected = logService.pageByLambda(page, size, operatorLogLambdaQueryWrapper);
        List<OperatorLogVo> list = Tasks.startWithMultiThreadsSync(selected.getRecords(), operatorLog -> {
            return BeanConvertUtils.objectConvent(operatorLog, OperatorLogVo.class);
        });
        PageResult<OperatorLogVo> objectPageResult = new PageResult<>(selected, list);
        return Result.success(objectPageResult);
    }
}
