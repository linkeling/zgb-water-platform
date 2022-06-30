package com.zgb.water.common.handler;

import com.zgb.water.common.base.ResponseCode;
import com.zgb.water.common.base.ResponseDataModel;
import com.zgb.water.common.exception.BusinessException;
import com.zgb.water.common.exception.RpcException;
import com.zgb.water.common.exception.SystemException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

/**
 * 功能：Controller统一控制器
 *
 * @author wsx
 * @version 2022-06-30
 */
@SuppressWarnings("checked")
@ControllerAdvice
public class ControllerHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 全局异常处理
     * @param e 异常类
     * @return 相应数据封装类
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseDataModel exceptionHandler(Exception e) {
        ResponseDataModel model;
        if (e instanceof BusinessException) {
            model = new ResponseDataModel(((BusinessException)e).getErrorCode(), e.getMessage());
        } else if (e instanceof ValidationException || e instanceof HttpRequestMethodNotSupportedException || e instanceof HttpMessageConversionException || e instanceof BeansException
            || e instanceof ServletRequestBindingException || e instanceof MethodArgumentNotValidException || e instanceof IllegalArgumentException|| e instanceof IllegalStateException) {
            model = new ResponseDataModel(ResponseCode.PARAMETER_EXCEPTION, e.getMessage());
            logger.error(e.getMessage(), e);
        } else if (e instanceof DataAccessException) {
            model = new ResponseDataModel(ResponseCode.DB_EXCEPTION, e.getMessage());
            logger.error(e.getMessage(), e);
        } else if (e instanceof RpcException) {
            model = new ResponseDataModel(ResponseCode.RPC_EXCEPTION, e.getMessage());
            logger.error(e.getMessage(), e);
        } else if (e instanceof SystemException || e instanceof NullPointerException) {
            model = new ResponseDataModel(ResponseCode.SYSTEM_EXCEPTION, ExceptionUtils.getMessage(e));
            logger.error(e.getMessage(), e);
        } else {
            model = new ResponseDataModel(ResponseCode.UNKNOWN_EXCEPTION, "系统异常，请联系客服！");
            logger.error(e.getMessage(), e);
        }
        return model;
    }

    //@ExceptionHandler(MissingServletRequestParameterException.class)
    public String exception(Exception e) {
        // 跳转到error.jsp页面
        return "error";
    }

    /**
     * 初始绑定属性编辑器
     * @param binder 数据绑定器
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new MyDateEditor());
    }

    private class MyDateEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Date date = null;
            if (StringUtils.hasText(text)) {
                try {
                    if (text.indexOf(":") > 0) {
                        if (text.length() == 5) {
                            date = DateUtils.parseDate(text, "HH:mm");
                        } else if (text.length() == 19) {
                            date = DateUtils.parseDate(text, "yyyy-MM-dd hh:mm:ss");
                        }
                    } else {
                        if (text.length() == 10) {
                            date = DateUtils.parseDate(text, "yyyy-MM-dd");
                        } else if (text.length() == 11) {
                            date = DateUtils.parseDate(text, "yyyy年MM月dd日");
                        }
                    }
                    if (date == null) {
                        String errorMsg = "Could not parse date, date format is error " + text;
                        logger.error(errorMsg);
                        throw new IllegalArgumentException(errorMsg);
                    }
                } catch (ParseException ex) {
                    IllegalArgumentException iae = new IllegalArgumentException(text + "Could not parse date: " + ex.getMessage(), ex);
                    logger.error(iae.getMessage());
                    throw iae;
                }
            }
            setValue(date);
        }
    }
}