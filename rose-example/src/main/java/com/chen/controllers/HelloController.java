/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-4-10 上午11:14:46
 */

package com.chen.controllers;

import com.chen.model.Chen;
import com.chen.model.Test;
import com.chen.service.TestService;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.RetryNTimes;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.paoding.rose.web.portal.Pipe;
import net.paoding.rose.web.portal.Portal;
import net.paoding.rose.web.var.Flash;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Path("")
public class HelloController {

    @Autowired
    private TestService tService;



    @Get("")
    public String index() throws IOException{
        String re = new Scanner(new File("/root/temp.log")).useDelimiter("\\A").next();
        BASE64Decoder d = new BASE64Decoder();
        re = new String(d.decodeBuffer(re));
        //String re = String.valueOf(Character.toChars(0x1F620));
        tService.test();
        return re;
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

//    @Get("/3.7")
//    public String portal(Portal portal) {
//        portal.addWindow("p1", "/wp1");
//        portal.addWindow("p2", "/wp2");
//        return "portal";
//    }
//
//    @Get("/3.8")
//    public String pipe(Pipe pipe) {
//        pipe.addWindow("p1", "/wp1");
//        pipe.addWindow("p2", "/wp2");
//        return "pipe";
//    }

    @Get("/wp1")
    public String portal1() {
        return "portal1";
    }

    @Get("/wp2")
    public String portal2() {
        return "portal2";
    }

    @Get("/3.9")
    public String upload() {
        return "upload";
    }

    @Post("/doUpload")
    public String doUpload(@Param("file") MultipartFile file) {
        return "@ upload ok!" + file.getOriginalFilename();
    }

    @Get("/3.10")
    public String getTest() {
        Test t = tService.getTest();
        String s = "Hello the No." + t.getId() + " is " + t.getMsg();
        return "@" + s;
    }

    @Get("/curator")
    public String testCurator() throws Exception {
        String path = "/test_path";
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("zookeeper.n.miliao.com:2181").namespace("/test1")
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(5000).build();
        // create a node
        client.create().forPath("/head", new byte[0]);

        // delete a node in background
        client.delete().inBackground().forPath("/head");

        // create a EPHEMERAL_SEQUENTIAL
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/head/child", new byte[0]);

        // get the data
        client.getData().watched().inBackground().forPath("/test");

        // check the path exits
        client.checkExists().forPath(path);

        return "@adf";
    }

    public static void main(String[] args) throws Exception {
        String path = "/test_path";
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("zookeeper.n.miliao.com:2181").namespace("/brokers").retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(5000).build();
        // 启动 上面的namespace会作为一个最根的节点在使用时自动创建
        client.start();

        // 创建一个节点
        client.create().forPath("/head", new byte[0]);

        // 异步地删除一个节点
        client.delete().inBackground().forPath("/head");

        // 创建一个临时节点
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/head/child", new byte[0]);

        // 取数据
        client.getData().watched().inBackground().forPath("/test");

        // 检查路径是否存在
        client.checkExists().forPath(path);

        // 异步删除
        client.delete().inBackground().forPath("/head");

        // 注册观察者，当节点变动时触发
        client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("node is changed");
            }
        }).inBackground().forPath("/test");

        // 结束使用
        client.close();
    }
}
