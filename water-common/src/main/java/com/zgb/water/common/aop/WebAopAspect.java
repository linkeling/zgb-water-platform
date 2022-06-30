package com.zgb.water.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zgb.water.common.base.BaseVO;
import com.zgb.water.common.base.ResponseCode;
import com.zgb.water.common.base.ResponseDataModel;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 实现web层AOP切面
 *
 * @author wsx
 * @version 2022-06-30
 */
@SuppressWarnings("unchecked")
@ConditionalOnClass(ResponseBody.class)
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebAopAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    @Pointcut("execution(* com.zgb..rest..*.*(..))")
    private void controllerMethod() {
    }

    /**
     * 拦截所有web层的方法统一返回的数据模型
     */
    @Around(value = "controllerMethod()")
    public <T> Object invoke(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Class controller = proceedingJoinPoint.getTarget().getClass();
        //ControllerAdvice注解类不处理
        if (controller.isAnnotationPresent(ControllerAdvice.class)) {
            return proceedingJoinPoint.proceed();
        }

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        boolean isMethodWithResponseBody = methodSignature.getMethod().isAnnotationPresent(ResponseBody.class);
        boolean isControllerWithResponseBody = controller.isAnnotationPresent(ResponseBody.class) || controller.isAnnotationPresent(RestController.class);
        // 仅处理ResponseBody相关逻辑
        if (isMethodWithResponseBody || isControllerWithResponseBody) {
            // 执行前
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            ResponseDataModel<T> model;
            Object[] args = proceedingJoinPoint.getArgs();
            String classMethod = controller.getCanonicalName() + "#" + methodSignature.getName();
            long operId = request.getHeader("OPERATOR") == null ? 0L : Long.parseLong(request.getHeader("OPERATOR"));

            String requestData = "";
            StringJoiner params = new StringJoiner(",");
            if (HttpMethod.GET.toString().equals(request.getMethod())) {
                requestData = request.getQueryString();
            } else {
                if (args != null && args.length > 0) {
                    // 记录请求参数
                    for (Object arg : args) {
                        if (arg instanceof BindingResult) {
                            BindingResult result = (BindingResult) arg;
                            if (result.hasErrors()) {
                                FieldError fieldError = result.getFieldError();
                                String errorMsg;
                                if (fieldError == null) {
                                    errorMsg = "getFieldError is null!";
                                    model = new ResponseDataModel(ResponseCode.SYSTEM_EXCEPTION, errorMsg);
                                } else {
                                    errorMsg = fieldError.getField() + " " + fieldError.getDefaultMessage();
                                    model = new ResponseDataModel(ResponseCode.PARAMETER_EXCEPTION, errorMsg);
                                }
                                logger.error("classMethod=>{} : requestData=> {} : errorMsg=> {}", classMethod, requestData, errorMsg);
                                return model;
                            }
                        } else if (arg instanceof BaseVO) {
                            // 设置操作人
                            ((BaseVO) arg).setOperId(operId);
                            params.add(arg.toString());
                        } else {
                            String param = objectMapper.writeValueAsString(arg);
                            if (StringUtils.isNotBlank(param)) {
                                params.add(param);
                            }
                        }
                    }
                    requestData = params.toString();
                }
            }
            long start = System.currentTimeMillis();
            T result;
            // 执行中
            try {
                result = (T) proceedingJoinPoint.proceed();
                if (logger.isDebugEnabled()) {
                    logger.debug("return data: " + objectMapper.writeValueAsString(result));
                }
            } catch (Exception e) {
                long time = System.currentTimeMillis() - start;
                logger.error("{} : request data: {} : cost {} ms", classMethod, requestData, time);
                throw e;
            } finally {
                // 记录执行时间
                if (logger.isDebugEnabled()) {
                    long time = System.currentTimeMillis() - start;
                    logger.debug("{} : request data: {} : cost {} ms", classMethod, requestData, time);
                }
            }
            // 执行后
            model = new ResponseDataModel(ResponseCode.SUCCESS, null);
            if (result instanceof ResponseDataModel) {
                return result;
            } else {
                model.setData(result);
            }
            return model;
        } else {
            return proceedingJoinPoint.proceed();
        }
    }
}
