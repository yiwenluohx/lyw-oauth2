package com.lyw.cloud.lyw.oauth2.gateway.api;

/**
 * @Author: luohx
 * @Description: 封装API的错误码
 * @Date: 2020/7/30 14:43
 * @Version: 1.0
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
