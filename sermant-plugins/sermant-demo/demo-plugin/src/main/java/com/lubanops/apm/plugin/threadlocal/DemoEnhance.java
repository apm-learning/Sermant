package com.lubanops.apm.plugin.threadlocal;

import com.huawei.sermant.core.agent.definition.EnhanceDefinition;
import com.huawei.sermant.core.agent.definition.MethodInterceptPoint;
import com.huawei.sermant.core.agent.matcher.ClassMatcher;
import com.huawei.sermant.core.agent.matcher.ClassMatchers;
import com.lubanops.apm.plugin.common.Constant;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * ScheduledThreadpool 增强
 *
 * @author yiwei
 * @since 2021/10/21
 */
public class DemoEnhance implements EnhanceDefinition, Constant {
    /**
     * 增强类的全限定名
     */
    private static final String ENHANCE_CLASS = "com.example.demo.TestController";

    @Override
    public ClassMatcher enhanceClass() {
        return ClassMatchers.named(ENHANCE_CLASS);
    }

    @Override
    public MethodInterceptPoint[] getMethodInterceptPoints() {
        return new MethodInterceptPoint[]{MethodInterceptPoint.newInstMethodInterceptPoint(INTERCEPT_CLASS,
                ElementMatchers.namedOneOf("index"))};
    }
}
