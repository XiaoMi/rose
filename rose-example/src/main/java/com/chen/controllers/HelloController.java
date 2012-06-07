/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-4-10 上午11:14:46
 */
package com.chen.controllers;

import com.chen.model.Chen;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.portal.Portal;
import net.paoding.rose.web.var.Flash;

@Path("")
public class HelloController {

    @Get("")
    public String index() {
        return "@hello world";
    }

    @PriCheckRequired
    @Get("/3.2")
    public String interceptorAction() {
        return "@this is a access track interceptor";
    }

    @Get("/exception")
    public String index2() throws Exception {
        return "@hello world";
    }

    @Get("/param")
    public String param(Chen chen) throws Exception {
        return "@hello world:" + chen.getChen1() + ":" + chen.getChen2();
    }

    @Get("/notBlank")
    public String notBlank(@NotBlank @Param("messages") String messages) throws Exception {
        return "@hello world";
    }

    @Get("/flash1")
    public String flashStep1(Flash flash) {
        flash.add("msg", "修改成功！");
        return "r:/flash2";
    }

    @Get("/flash2")
    public String flashStep2(Invocation inv, Flash flash) {
        inv.addModel("info", flash.get("msg"));
        return "flash";
    }

    @Get("/3.7")
    public String portal(Portal portal) {
        portal.addWindow("p1", "/p1");
        portal.addWindow("p2", "/p2");
        return "portal";
    }

    @Get("/p1")
    public String portal1() {
        return "@this is p1";
    }

    @Get("/p2")
    public String portal2() {
        return "@this is p2";
    }
}

