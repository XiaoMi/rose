controller层：一闪而过的信息，flash支持
=======================================

3.6.1 需求描述
---------------

* 历史上，做web的需求时，经常遇到一个情况：在A页面修改/添加/删除了信息，提交，提示“修改/添加/删除成功！”。
* rose的flash（并非你所想象的adobe的flash）建设性地使这一需求在开发过程中简单快捷化。

3.6.2 使用过程
---------------

* 使用过程会很愉快，在两个action之间，通过return "r:/xxx"来跳转（实际是301），只需要在第一个action里使用flash.put，在第二个action里使用flash.get即可。

~~~~~java

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

~~~~~

* 上述两个action中，当访问flash1时，一句flash信息被写入，快速跳转到flash2的地址。
* flash2地址中接收到这个flash信息后写到model中。
* 还需要在flash2的模板里去显示这个变量。

~~~~~jsp

<%@ page contentType="text/html;charset=UTF-8"%>
提示信息：${info}

~~~~~

3.6.3 注意事项
--------------

* flash功能利用了浏览器的cookies功能，如果用户的环境不能使用cookies将不会有任何效果。

 文中所提及代码均在 https://github.com/XiaoMi/rose/tree/master/rose-example 提供。
