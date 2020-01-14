package com.zkyking.springaop.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo
{
    private Long id;
    private String name;
    private String token;
    /**
     * 存入redis做区分
     * salse ===>：RedisConst.USER_TOKEN_PREFIX+sales+sales_id
     * user ===> RedisConst.USER_TOKEN_PREFIX+user+user_id
     * company ===>RedisConst.USER_TOKEN_PREFIX+user+company_id
     * 防止键值重复而导致的token覆盖问题
     * <p>
     * 类型：
     * sales
     * user
     * company
     */
    private String type;

    public UserInfo(Long id, String name, String type)
    {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
