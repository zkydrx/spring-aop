package com.zkyking.springaop.config.model;

import lombok.Data;

/**
 * 请求
 */
@Data
public class RequestBean
{
    /**
     * id
     */

    private Long id;
    /**
     * 浏览器版本
     */
    private String ua;
    /**
     * 请求时间
     */
    private Long requestTime;
    /**
     * 用户token
     */
    private String token;

}
