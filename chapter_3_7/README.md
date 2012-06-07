3.7 controller层：门户必备portal支持
====================================

3.7.1 什么是portal?
--------------------

*字面意思，做门户用的。
*简单来说，把一个网页分成了N个区域，每个区域由不同的action去执行，多线程并行提高cpu使用率。

3.7.2 使用例子
-----------

*要使用portal，必须先在web.xml里声明所使用的线程池大小：

~~~~~xml
	<context-param>
		<param-name>portalExecutorCorePoolSize</param-name>
		<param-value>1024</param-value>
	</context-param>
~~~~~

*然后看示例代码：

~~~~~java
    @Get("/3.7")
    public String portal(Portal portal) {
        portal.addWindow("p1", "/wp1");
        portal.addWindow("p2", "/wp2");
        return "portal";
    }

    @Get("/wp1")
    public String portal1() {
        return "@this is p1";
    }

    @Get("/wp2")
    public String portal2() {
        return "@this is p2";
    }
~~~~~

*然后在第一个action中的portal.jsp中写到：

~~~~~jsp
<%@ page contentType="text/html;charset=UTF-8"%>
portal演示信息：
<br>
${p1}
<br>
${p2}
~~~~~

*当我们部属好了之后，访问http://127.0.0.1/3.7
*将从浏览器中得到：
 *portal演示信息：
 *this is p1
 *this is p2

3.7.3 这样子做的好处
--------------------

*更加充分地使用多核cpu。
*更加方便多人协作时对项目进行模块划分，搞的时候，按照url一分，一个url一个模块，所有的页面都可以切成小的豆腐块，所以，你懂的。

3.7.4 过去的经典事迹
--------------------

* 2010年的6月9日晚上7点"圣战"
* http://www.54chen.com/architecture/rose-open-source-portal-framework.html

 文中所提及代码均在 https://github.com/XiaoMi/rose/tree/master/rose-example 提供
。
