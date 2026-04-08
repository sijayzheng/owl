package cn.sijay.owl.common.exceptions;


import cn.sijay.owl.common.entity.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * GlobalExceptionHandler
 *
 * @author sijay
 * @since 2026/4/8
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理校验失败的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationException(MethodArgumentNotValidException e) {
        return Result.fail(HttpStatus.BAD_REQUEST.value(), "参数校验失败：" + Objects.requireNonNull(e.getBindingResult().getFieldError())
                                                                                    .getDefaultMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public Result<Void> handleServiceException(ServiceException e) {
        return Result.fail(e.getMessage());
    }


    @ExceptionHandler(BaseException.class)
    public Result<Void> handleBaseException(BaseException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

}
