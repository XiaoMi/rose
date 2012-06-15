3.8 controller层：门户必备pipe支持
====================================

3.8.1 什么是pipe?
------------------
* pipe起源于facebook的工程师对他们网页提速的方案：将网页分解为Pagelets的小块（在rose叫做window的小块），然后通过后端多重管道运行，以达到性能的最佳。
* pipe巧妙使用了http 1.1连接有timeout的机制，充分使用一次http连接来传递数据。
* pipe可使用户在大多数浏览器中感受到延迟减少了一半。

3.8.2 与facebook的bigpipe相比rose pipe如何？
--------------------------------------------
* fb并未在开源项目中公布过使用方法
* bigpipe神似是php+js搞定的
* rose pipe可以自由选择线程池大小，完全出自上一节的portal的基础
* 完全实现bigpipe功能，天然的对业务开发者透明

3.8.3 看实例
------------

HelloController.java

~~~~~java

    @Get("/3.8")
    public String pipe(Pipe pipe) {
        pipe.addWindow("p1", "/wp1");
        pipe.addWindow("p2", "/wp2");
        return "pipe";
    }

~~~~~

 * 长得是不是很像上一节里提供的action？
 * 不同在于jsp文件中：

~~~~~jsp

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://paoding.net/rose/pipe" prefix="rosepipe"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>portal/pipe演示信息</title> 
<script type='text/javascript' src='/js/rosepipe.js'></script>
</head>
<body>

portal/pipe演示信息：
<br>
<div id="p1"></div>
<br>
<div id="p2"></div>

</body>
</html>
<rosepipe:write>${p1}</rosepipe:write>
<rosepipe:write>${p2}</rosepipe:write>

~~~~~

* 当使用jsp文件时，需要在尾部使用rosepipe:write标签
* 如果是使用vm文件，可以不写这个标签

3.8.4 总结
----------

* 上述代码中p1 p2两个window会同时在多个线程中执行，如果是portal，那会多个线程执行完成一起返回，而pipe则会用js反写的方式，一个线程一个线程地返回给用户。
* pipe是个好物件
* 使用时jsp一定不要忘记尾部的标签
* 使用时web.xml一定不要忘记声明使用的线程池大小
* 久经考验

 文中所提及代码均在 https://github.com/XiaoMi/rose/tree/master/rose-example 提供。
