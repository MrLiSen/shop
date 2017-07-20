package com.shop.exception;

import com.shop.base.BaseController;
import com.shop.base.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by TW on 2017/7/13.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ParamException.class)
    public ResultInfo handlerParamException(ParamException exception) {
        logger.error("参数异常：{}", exception);
        return failure(exception.getErrorCode(), exception.getMessage());
    }

}
