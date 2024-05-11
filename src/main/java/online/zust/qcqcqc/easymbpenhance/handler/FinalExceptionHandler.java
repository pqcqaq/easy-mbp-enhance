package online.zust.qcqcqc.easymbpenhance.handler;

import online.zust.qcqcqc.services.entity.dto.Result;
import online.zust.qcqcqc.services.module.log.utils.SystemLogger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author qcqcqc
 * Date: 2024/4/11
 * Time: 20:24
 * 当出现不可知异常时的兜底处理，避免将服务器原始报错信息返回给前端
 */
@RestControllerAdvice
@Order(999)
public class FinalExceptionHandler {

    /**
     * ExceptionHandler
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> exceptionHandler(Exception e) {
        SystemLogger.error("'服务器异常未被捕获！'", e);
        return Result.error(500, e.getMessage());
    }
}
