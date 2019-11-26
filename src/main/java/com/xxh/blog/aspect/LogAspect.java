package com.xxh.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志记录切面类
 */
@Aspect
@Component //组件
public class LogAspect {
    //获取日志
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    /**
     *  记录日志内容
     *      * 请求url
     *      * 访问者ip
     *      * 调用方法classMethod
     *      * 参数 args
     *      * 返回内容
     */
    //切入点
    @Pointcut("execution(* com.xxh.blog.controller.*.*(..))") //切入点
    public void log(){}

    //前置通知
    @Before("log()") //log()为切入点
    //插入 加入切面对象 获取请求方法 和 参数
    public void doBefore(JoinPoint joinPoint){
        //获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取url ip
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //获取请求方法 和 参数
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        //封装 对象
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        //输出到日志的
        logger.info("Request: {}",requestLog);

    }

    //后置通知
    @After("log()")
    public void doAfter(){
        logger.info("-----doAfter-----");
    }

    //执行后置通知时返回内容
    @AfterReturning(returning = "result",pointcut = "log()") //定义返回值
    public void doAfterReturn(Object result){
        logger.info("Result: {}",result);
    }

    //日志记录内部类
    /**
     *  封装信息
     *      * 请求url
     *      * 访问者ip
     *      * 调用方法classMethod
     *      * 参数 args
     */
    public class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
