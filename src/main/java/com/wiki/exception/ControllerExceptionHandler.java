package com.wiki.exception;


import com.wiki.common.R;
import com.wiki.common.WikiConstants;
import com.wiki.common.WikiEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理、数据预处理等
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 校验异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public R validExceptionHandler(BindException e) {
        LOG.warn("参数校验失败：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return R.error().put(WikiConstants.WIKI_SUCCESS,false)
                .put(WikiConstants.WIKI_MESSAGE,e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

    }

    /**
     * 校验异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public R validExceptionHandler(BusinessException e) {
        LOG.warn("业务异常：{}", e.getCode().getDesc());
        return R.error().put(WikiConstants.WIKI_SUCCESS,false)
                .put(WikiConstants.WIKI_MESSAGE,e.getCode().getDesc());
    }

    /**
     * 校验异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R validExceptionHandler(Exception e) {
        return R.error().put(WikiConstants.WIKI_SUCCESS,false)
                .put(WikiConstants.WIKI_MESSAGE, WikiEnum.msg_A001.getName());
    }
}
