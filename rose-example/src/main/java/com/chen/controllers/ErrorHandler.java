/**
 * @author chenzhen@xiaomi.com
 * 2010-12-1 
 */

package com.chen.controllers;

import net.paoding.rose.web.ControllerErrorHandler;
import net.paoding.rose.web.Invocation;

public class ErrorHandler implements ControllerErrorHandler {

    public Object onError(Invocation inv, Throwable ex) throws Throwable {

        // TODO logger.error("handle err:", ex);
        if (ex instanceof RuntimeException) {
            return "@runtime";
        }
        return "@error";
    }
}
