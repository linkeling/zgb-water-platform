package com.zgb.water.common.interceptor;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 生成线程执行追踪ID
 *
 * @Author
 */
@Component
public class TrackIdInterceptor implements HandlerInterceptor {
    private final static String TRACK_ID = "track_id";


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String trackId = UUID.randomUUID().toString();
        MDC.put(TRACK_ID, trackId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String trackId = MDC.get(TRACK_ID);
        MDC.remove(TRACK_ID);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}