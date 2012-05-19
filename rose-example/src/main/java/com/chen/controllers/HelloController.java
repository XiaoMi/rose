/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-4-10 上午11:14:46
 */
package com.chen.controllers;

import com.chen.model.Chen;

import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("")
public class HelloController {

    @Get("")
    public String index() {
        return "@hello world";
    }

    @Get("/exception")
    public String index2() throws Exception {
        return "@hello world";
    }

    @Get("/param")
    public String param(Chen chen) throws Exception {
        return "@hello world:" + chen.getChen1() + ":" + chen.getChen2();
    }
}

