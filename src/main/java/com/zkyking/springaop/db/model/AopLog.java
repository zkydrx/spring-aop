package com.zkyking.springaop.db.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: zky
 * Date: 2019-12-15
 * Time: 18:52:14
 * Description:
 */
@Data
@Table(name = "aop_log")
public class AopLog
{
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 方法运行耗时
     */
    @Column(name = "elapsed_time")
    private Long elapsedTime;

    /**
     * 请求时间
     */
    @Column(name = "request_date")
    private Date requestDate;

    @Column(name = "request_url")
    private String requestUrl;

    /**
     * 返回结果
     */
    @Column(name = "response")
    private String response;

    @Column(name = "operation_name")
    private String operationName;

    /**
     * 错误类型：0-代表正常，1-代表访问异常
     */
    @Column(name = "error")
    private byte error;

    @Column(name = "request_uri")
    private String requestUri;

    @Column(name = "query_string")
    private String queryString;

    @Column(name = "remote_addr")
    private String remoteAddr;

    @Column(name = "remote_host")
    private String remoteHost;

    @Column(name = "remote_port")
    private Integer remotePort;

    @Column(name = "local_addr")
    private String localAddr;

    @Column(name = "local_name")
    private String localName;

    @Column(name = "`method`")
    private String method;

    @Column(name = "headers")
    private String headers;

    @Column(name = "`parameters`")
    private String parameters;

    @Column(name = "class_method")
    private String classMethod;

    @Column(name = "args")
    private String args;

    /**
     * 异常的堆栈信息
     */
    @Column(name = "statck")
    private String statck;
}