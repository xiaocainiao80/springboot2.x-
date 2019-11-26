package com.xxh.blog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice //控制器加强 全局
public class ControllerExceptionHandler {
    //记录错误日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //返回错误页面
    //错误拦截

    /**
     * 异常处理
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request,
                                        Exception e) throws Exception {
        logger.error("Request URL : {}, Exception : {}",request.getRequestURL(),e);
        //如果异常含有状态码 返回处理该状态码方法
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }
        ModelAndView model = new ModelAndView();
        model.addObject("url",request.getRequestURL());
        model.addObject("exception",e);
        model.setViewName("error/error");
        return model;
    }
}
