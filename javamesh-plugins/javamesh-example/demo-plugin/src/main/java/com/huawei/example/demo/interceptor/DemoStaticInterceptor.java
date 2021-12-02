/*
 * Copyright (C) Huawei Technologies Co., Ltd. 2021-2021. All rights reserved.
 */

package com.huawei.example.demo.interceptor;

import java.lang.reflect.Method;

import com.huawei.javamesh.core.agent.common.BeforeResult;
import com.huawei.javamesh.core.agent.interceptor.StaticMethodInterceptor;
import com.huawei.example.demo.common.DemoLogger;

/**
 * 静态函数的拦截器示例，本示例将展示如何对静态函数进行增强
 *
 * @author HapThorin
 * @version 1.0.0
 * @since 2021/10/25
 */
public class DemoStaticInterceptor implements StaticMethodInterceptor {
    @Override
    public void before(Class<?> clazz, Method method, Object[] arguments, BeforeResult beforeResult) throws Exception {
        DemoLogger.println(clazz.getSimpleName() + ": [DemoStaticInterceptor]-before");
    }

    @Override
    public Object after(Class<?> clazz, Method method, Object[] arguments, Object result) throws Exception {
        DemoLogger.println(clazz.getSimpleName() + ": [DemoStaticInterceptor]-after");
        return result;
    }

    @Override
    public void onThrow(Class<?> clazz, Method method, Object[] arguments, Throwable t) {
        DemoLogger.println(clazz.getSimpleName() + ": [DemoStaticInterceptor]-onThrow");
    }
}