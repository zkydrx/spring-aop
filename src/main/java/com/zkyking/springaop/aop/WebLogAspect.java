package com.zkyking.springaop.aop;

import com.zkyking.springaop.annotation.ControllerWebLog;
import com.zkyking.springaop.db.mapper.WebLogMapper;
import com.zkyking.springaop.db.model.AopLog;
import com.zkyking.springaop.db.model.WebLog;
import com.zkyking.springaop.service.AopLogService;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口日志切面
 *
 * @author zky
 */
@Aspect
@Component
@Order(100)
public class WebLogAspect
{

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Autowired
    private WebLogMapper webLogMapper;
    @Resource
    private AopLogService aopLogService;

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    private static final String START_TIME = "startTime";

    private static final String REQUEST_PARAMS = "requestParams";

    @Pointcut("execution(* com.zkyking.springaop.controller.*.*(..))")
    public void webLog()
    {
    }

    @Before(value = "webLog()&& @annotation(controllerWebLog)")
    public void doBefore(JoinPoint joinPoint, ControllerWebLog controllerWebLog)
    {
        // 开始时间。
        long startTime = System.currentTimeMillis();
        // 获取HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        AopLog aopLog = getAopLog(request, joinPoint);
        Map<String, Object> threadInfo = new HashMap<>();
        threadInfo.put(START_TIME, startTime);
        threadInfo.put("aopLog", aopLog);
        threadLocal.set(threadInfo);
        logger.info("{}接口开始调用:requestData={}", controllerWebLog.name(), threadInfo.get(REQUEST_PARAMS));
    }

    @AfterReturning(value = "webLog()&& @annotation(controllerWebLog)", returning = "res")
    public void doAfterReturning(ControllerWebLog controllerWebLog, Object res)
    {
        Map<String, Object> threadInfo = threadLocal.get();
        long takeTime = System.currentTimeMillis() - (long) threadInfo.getOrDefault(START_TIME, System.currentTimeMillis());
        AopLog aopLog = (AopLog) threadInfo.get("aopLog");
        aopLog.setElapsedTime(takeTime);
        aopLog.setError((byte) 0);
        aopLog.setResponse(JSON.toJSONString(res));
        aopLog.setOperationName(controllerWebLog.name());
        if (controllerWebLog.intoDb())
        {
            insertResult(aopLog);
            // insertResult(controllerWebLog.name(), (String) threadInfo.getOrDefault(REQUEST_PARAMS, ""), JSON.toJSONString(res), takeTime);
        }
        threadLocal.remove();
        logger.info("{}接口结束调用:耗时={}ms,result={}", controllerWebLog.name(), takeTime, res);
    }

    @AfterThrowing(value = "webLog()&& @annotation(controllerWebLog)", throwing = "throwable")
    public void doAfterThrowing(ControllerWebLog controllerWebLog, Throwable throwable)
    {
        Map<String, Object> threadInfo = threadLocal.get();
        long takeTime = System.currentTimeMillis() - (long) threadInfo.getOrDefault(START_TIME, System.currentTimeMillis());
        AopLog aopLog = (AopLog) threadInfo.get("aopLog");
        aopLog.setElapsedTime(takeTime);
        aopLog.setError((byte) 1);

        /**
         * 把堆栈信息转成string
         */
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        /**
         * 存入堆栈信息
         */
        aopLog.setStatck(sw.toString());
        aopLog.setOperationName(controllerWebLog.name());
        if (controllerWebLog.intoDb())
        {
            insertError(aopLog);
        }
        threadLocal.remove();
        logger.error("{}接口调用异常，异常信息{}", controllerWebLog.name(), throwable);
    }


    public void insertResult(String operationName, String requestStr, String responseStr, long takeTime)
    {
        WebLog webLog = new WebLog();
        webLog.setCreateTime(new Date());
        webLog.setError(false);
        webLog.setOperationName(operationName);
        webLog.setRequest(requestStr);
        webLog.setResponse(responseStr);
        webLog.setTaketime(takeTime);
        webLogMapper.insert(webLog);
    }


    public void insertError(String operationName, String requestStr, Throwable throwable)
    {
        WebLog webLog = new WebLog();
        webLog.setCreateTime(new Date());
        webLog.setError(true);
        webLog.setOperationName(operationName);
        webLog.setRequest(requestStr);
        webLog.setStack(throwable.getStackTrace().toString());
        webLogMapper.insert(webLog);
    }


    private AopLog getAopLog(HttpServletRequest request, JoinPoint joinPoint)
    {
        // 基本信息
        AopLog aopLog = new AopLog();
        aopLog.setRequestUrl(request.getRequestURL().toString());
        aopLog.setRequestDate(new Date());
        aopLog.setRequestUri(request.getRequestURI());
        aopLog.setQueryString(request.getQueryString());
        aopLog.setRemoteAddr(request.getRemoteAddr());
        aopLog.setRemoteHost(request.getRemoteHost());
        aopLog.setRemotePort(request.getRemotePort());
        aopLog.setLocalAddr(request.getLocalAddr());
        aopLog.setLocalName(request.getLocalName());
        aopLog.setMethod(request.getMethod());
        aopLog.setHeaders(String.valueOf(getHeadersInfo(request)));
        aopLog.setParameters(String.valueOf(JSON.toJSON(request.getParameterMap())));
        aopLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        aopLog.setArgs(Arrays.toString(joinPoint.getArgs()));
        return aopLog;
    }


    /**
     * 获取头信息
     *
     * @param request
     * @return
     */
    private Map<String, String> getHeadersInfo(HttpServletRequest request)
    {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements())
        {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }


    /**
     * 正常返回结果插入数据库
     * @param aopLog
     */
    public void insertResult(AopLog aopLog)
    {
        aopLogService.insertOrUpdate(aopLog);
    }

    /**
     * 把异常的请求信息以及错误堆栈存入数据库
     * @param aopLog
     */
    public void insertError(AopLog aopLog)
    {
        aopLogService.insertOrUpdate(aopLog);
    }

}
