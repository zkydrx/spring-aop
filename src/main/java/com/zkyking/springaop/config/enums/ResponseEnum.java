package com.zkyking.springaop.config.enums;

/**
 * @author wugy 2018年1月9日 下午5:27:14
 */
public enum ResponseEnum
{

    /**
     * 成功
     */
    M200(200, "success"),
    /**
     * 信息异常
     */
    M500(500, "信息错误"),
    /**
     * 无效token
     */
    M501(501, "无效的TOKEN"),
    /**
     * 不存在该用户
     */
    M502(502, "不存在该用户"),
    /**
     * 密码错误
     */
    M503(503, "密码错误"),
    /**
     * 该用户没有登录
     */
    M504(504, "该用户没有登录"),
    /**
     * 手机号或者密码为空
     */
    M505(505, "账号或者密码为空"),
    /**
     * 获取角色信息异常
     */
    M506(506, "获取角色信息异常");
    private Integer code;

    private String message;

    ResponseEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
