package com.itheima.health.constant;

public interface RedisMessageConstant {
    String SENDTYPE_ORDER = "001";//用于缓存体检预约时发送的验证码
    String SENDTYPE_LOGIN = "002";//用于缓存手机号快速登录时发送的验证码
    String SENDTYPE_GETPWD = "003";//用于缓存找回密码时发送的验证码
    String SENDTYPE_FOR_CHANGEPASSWORD = "_004";//用于缓存体检预约时发送的验证码
}