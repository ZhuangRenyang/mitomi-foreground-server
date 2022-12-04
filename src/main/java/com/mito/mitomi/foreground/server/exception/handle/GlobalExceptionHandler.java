package com.mito.mitomi.foreground.server.exception.handle;

import com.mito.mitomi.foreground.server.exception.ServiceException;
import com.mito.mitomi.foreground.server.web.JsonResult;
import com.mito.mitomi.foreground.server.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.StringJoiner;

/*
 *统一异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理业务异常
     *
     * @param e 业务异常
     * @return 异常信息
     */
    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e) {
        return JsonResult.fail(e);
    }

    @ExceptionHandler
    public JsonResult handleServiceException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringJoiner joiner = new StringJoiner("; ", "错误信息：", "。");
        for (FieldError fieldError : fieldErrors) {
            joiner.add(fieldError.getDefaultMessage());
        }

        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, joiner.toString());
    }

    @ExceptionHandler
    public JsonResult handleThrowable(Throwable e) {
        log.error("统一处理未明确的异常:{},错误信息:{}", e.getClass().getName(), e.getMessage());
        String message = "服务器繁忙,请稍后重试";
        return JsonResult.fail(ServiceCode.ERR_UNKNOWN, message);
    }
}
