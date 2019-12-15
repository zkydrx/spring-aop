package com.zkyking.springaop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基于：https://github.com/zkydrx/spring-tutorial/tree/master/sb-aop的修改版本
 * 1.完善了存储信息，提高了保存信息的可用性。
 */
@SpringBootApplication
public class SbAopApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SbAopApplication.class, args);
    }

}
