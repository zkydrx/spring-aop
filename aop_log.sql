create table aop_log
(
    id             bigint auto_increment comment '主键'
        primary key,
    elapsed_time   bigint       null comment '方法运行耗时间，单位是毫秒',
    request_date   datetime     null comment '请求时间',
    request_url    longtext     null comment '请求资源具体地址',
    response       longtext     null comment '返回结果',
    operation_name varchar(255) null comment '接口名称',
    error          tinyint      null comment '错误类型：0-代表正常，1-代表访问异常',
    request_uri    longtext     null comment '请求资源标识',
    statck         longtext     null comment '异常的堆栈信息',
    query_string   longtext     null comment '查询内容',
    remote_addr    longtext     null comment '远程请求地址',
    remote_host    longtext     null comment '远程请求主机',
    remote_port    int          null comment '远程端口',
    local_addr     longtext     null comment '本机地址',
    local_name     longtext     null comment '本地主机名',
    method         longtext     null comment '请求类型',
    headers        longtext     null comment '请求头信息',
    parameters     longtext     null comment '请求参数',
    class_method   longtext     null comment '执行的具体方法名',
    args           longtext     null comment '请求参数'
);