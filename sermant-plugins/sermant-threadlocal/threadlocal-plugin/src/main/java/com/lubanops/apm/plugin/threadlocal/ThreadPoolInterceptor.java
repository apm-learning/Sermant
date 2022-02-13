package com.lubanops.apm.plugin.threadlocal;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import com.huawei.sermant.core.agent.common.BeforeResult;
import com.huawei.sermant.core.agent.interceptor.InstanceMethodInterceptor;
import com.huawei.sermant.core.common.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * 线程池增强，替换Runnable和Callable参数
 *
 * @author y00556973
 * @since 2021/10/11
 */
public class ThreadPoolInterceptor implements InstanceMethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    @Override
    public void before(Object obj, Method method, Object[] arguments, BeforeResult beforeResult) {
        LoggerFactory.getLogger().info("进入方法："+method.getName()+"after");
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof Runnable) {
                arguments[i] = TtlRunnable.get((Runnable) arguments[i], false, true);
                continue;
            }
            if (arguments[i] instanceof Callable) {
                arguments[i] = TtlCallable.get((Callable<?>) arguments[i], false, true);
            }
        }
    }

    @Override
    public Object after(Object obj, Method method, Object[] arguments, Object result) {
        LoggerFactory.getLogger().info("退出方法："+method.getName()+"after");
        return result;
    }

    @Override
    public void onThrow(Object obj, Method method, Object[] arguments, Throwable throwable) {
    }
}
