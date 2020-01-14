package com.zkyking.springaop.inteceptor;

import com.alibaba.fastjson.JSONObject;
import com.zkyking.springaop.annotation.LoginRequired;
import com.zkyking.springaop.config.enums.ResponseEnum;
import com.zkyking.springaop.config.model.RequestThread;
import com.zkyking.springaop.config.model.ResultMessage;
import com.zkyking.springaop.config.model.UserInfo;
import com.zkyking.springaop.config.properties.JwtProperties;
import com.zkyking.springaop.config.redis.RedisConst;
import com.zkyking.springaop.config.redis.RedisMapper;
import com.zkyking.springaop.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor
{
    public static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    RedisMapper redisMapper;

    @Autowired
    JwtProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        /**
         * 移除本地线程变量
         */
        RequestThread.remove();
        RequestThread.setRequestTime(System.currentTimeMillis());
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }
        HandlerMethod hm = (HandlerMethod) handler;
        LoginRequired lr = hm.getMethodAnnotation(LoginRequired.class);
        if (lr != null && lr.check() && !isLogin(request))
        {
            printMessage(response, new ResultMessage(ResponseEnum.M504.getCode(), ResponseEnum.M504.getMessage(), null));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        /**
         * 移除本地线程变量
         */
        RequestThread.remove();
    }

    private boolean isLogin(HttpServletRequest request)
    {
        // 判断是否存在令牌信息，如果存在，则允许登录
        String token = request.getHeader("token");
        if (org.apache.commons.lang3.StringUtils.isBlank(token) || token.equals("undefined"))
        {
            return false;
        }
        // 获取用户信息
        // 根据token从redis中查询用户信息

        UserInfo userInfo = JwtUtils.getUserInfo(properties.getPublicKey(), token);

        if (StringUtils.isEmpty(userInfo))
        {
            logger.error("token解析失败");
            return false;
        }
        String token_redis = redisMapper.get(RedisConst.USER_TOKEN_PREFIX + userInfo.getType() + userInfo.getId());
        if (!token.equals(token_redis))
        {
            return false;
        }
        redisMapper.set(RedisConst.USER_TOKEN_PREFIX + userInfo.getType() + userInfo.getId(), token_redis, 3 * 86400);
        RequestThread.setId(userInfo.getId());
        return true;
    }

    private void printMessage(HttpServletResponse response, ResultMessage message) throws IOException
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONObject.toJSONString(message));
    }
}
