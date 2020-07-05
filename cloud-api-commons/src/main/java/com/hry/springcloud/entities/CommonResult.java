package com.hry.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    /**
     * 返回结果 例如404,200
     */
    private Integer code;

    /**
     * 信息  例如success
     */
    private String message;

    /**
     * 结果集 信息体对象
     */
    private T data;

    public CommonResult(Integer code,String message){
        this(code,message,null);
    }
}
