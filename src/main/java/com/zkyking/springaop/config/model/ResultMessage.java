package com.zkyking.springaop.config.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息返回
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultMessage
{

    private static final long serialVersionUID = 1L;
    private Integer errCode;
    private String desc;
    private Object data;

    public ResultMessage(Object data)
    {
        this.data = data;
    }

    public Integer getErrCode()
    {
        return this.errCode;
    }

    public void setErrCode(Integer errCode)
    {
        this.errCode = errCode;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public Object getData()
    {
        return this.data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "BIRes{" + "errCode='" + errCode + '\'' + ", desc='" + desc + '\'' + ", data=" + data + '}';
    }
}
