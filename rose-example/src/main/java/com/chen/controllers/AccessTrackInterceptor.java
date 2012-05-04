/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-5-4 下午09:57:18
 */
package com.chen.controllers;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import java.lang.annotation.Annotation;

public class AccessTrackInterceptor extends ControllerInterceptorAdapter {
    public AccessTrackInterceptor() {
        setPriority(29600);
    }

    @Override
    public Class<? extends Annotation> getRequiredAnnotationClass() {
        return PriCheckRequired.class; // 这是一个注解，只有标过的controller才会接受这个拦截器的洗礼。
    }

    @Override
    public Object before(Invocation inv) throws Exception {
        // TODO ....
        return super.before(inv);
    }

    @Override
    public void afterCompletion(final Invocation inv, Throwable ex) throws Exception {
        // TODO ....
    }
}