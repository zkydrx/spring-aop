package com.zkyking.springaop.config.model;

/**
 * requestBean的ThreadLocal
 */
public class RequestThread
{
    private static final ThreadLocal<RequestBean> requestThread = new ThreadLocal<RequestBean>();

    public static RequestBean get()
    {
        RequestBean s = requestThread.get();
        if (s == null)
        {
            s = new RequestBean();
            requestThread.set(s);
        }
        return s;
    }

    public static void remove()
    {
        requestThread.remove();
    }

    public static Long getRequestTime()
    {
        return get().getRequestTime();
    }

    public static void setRequestTime(Long requestTime)
    {
        get().setRequestTime(requestTime);
    }

    public static Long cost()
    {
        return (System.currentTimeMillis() - get().getRequestTime());
    }

    public static String getToken()
    {
        return get().getToken();
    }

    public static void setToken(String token)
    {
        get().setToken(token);
    }

    public static Long getId()
    {
        return get().getId();
    }

    public static void setId(Long uid)
    {
        get().setId(uid);
    }

    public static String getUa()
    {
        return get().getUa();
    }

    public static void setUa(String ua)
    {
        get().setUa(ua);
    }

}


