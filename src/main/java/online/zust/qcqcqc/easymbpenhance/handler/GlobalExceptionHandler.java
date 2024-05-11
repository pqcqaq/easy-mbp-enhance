package online.zust.qcqcqc.easymbpenhance.handler;

import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import online.zust.qcqcqc.services.entity.dto.Result;
import online.zust.qcqcqc.services.exception.ServiceException;
import online.zust.qcqcqc.services.module.log.utils.SystemLogger;
import online.zust.qcqcqc.utils.exception.ApiCurrentLimitException;
import online.zust.qcqcqc.utils.exception.DependencyCheckException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qcqcqc
 */
@RestControllerAdvice
@Order(1)
public class GlobalExceptionHandler {
    /**
     * 参数校验异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String message = allErrors.stream().map(
                        DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; ")
                );
        return Result.error(403, message);
    }

    /**
     * 参数校验异常
     * BeanPropertyBindingResult
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> handleBindException(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String message = allErrors.stream().map(
                        DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; ")
                );
        return Result.error(403, message);
    }

    /**
     * ApiCurrentLimitException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({ApiCurrentLimitException.class})
    public Result<String> errorApiCurrentLimitExceptionHandler(ApiCurrentLimitException e) {
        SystemLogger.warn("'用户：' + @myLimiterConfig.getUserKey() + ' 请求次数过多，已被限流。'", e.getMessage());
        return Result.error(400, "请求过于频繁,请稍后再试", "请求过于频繁,请稍后再试");
    }

    /**
     * BadCredentialsException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BadCredentialsException.class})
    public Result<String> errorBadCredentialsExceptionHandler(BadCredentialsException e) {
        return Result.error(400, e.getMessage(), "用户名或密码错误");
    }

    /**
     * ServiceException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({ServiceException.class})
    public Result<String> errorServiceExceptionHandler(ServiceException e) {
        return Result.error(e.getCode(), e.getMessage());
    }


    /**
     * MissingServletRequestParameterException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Result<String> errorMissingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return Result.error(400, e.getMessage(), "请求参数不正确");
    }

    /**
     * ServletException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({ServletException.class})
    public Result<String> errorServletExceptionHandler(ServletException e) {
        return Result.error(400, "请求异常，请联系管理员", e.getMessage());
    }

    /**
     * DependencyCheckException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({DependencyCheckException.class})
    public Result<String> errorDependencyCheckExceptionHandler(DependencyCheckException e) {
        return Result.error(400, e.getMessage(), "对象依赖检查失败");
    }

    /**
     * ConstraintViolationException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({ConstraintViolationException.class})
    public Result<String> errorConstraintViolationExceptionHandler(ConstraintViolationException e) {
        return Result.error(400, e.getMessage(), "参数校验失败");
    }

    /**
     * AccessDeniedException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({AccessDeniedException.class})
    public Result<String> errorAccessDeniedExceptionHandler(AccessDeniedException e) {
        return Result.error(403, e.getMessage(), "权限不足");
    }
}
