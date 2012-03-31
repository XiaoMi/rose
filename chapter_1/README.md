rose手册第一章：入门指引
========================

1.1 简介：
---------
人人网、糯米网释出的、开源的高效Java web开发框架。在小米米聊服务端再次被验证和使用。一个从零开始的创业公司，在大家技术背景不一的情况下，rose很简单快速地传达到了大家中间。本手册致力于让php开发人员也能快速使用上java开发高性能服务。
###1）rose是什么？###

* 基于IoC容器 (使用Spring 2.5.6).
* 收集最佳实践，形成规范和惯例，引导按规范惯例，简便开发.
* 收集通用功能，形成一些可使用的组件，提高生产效率.
* 特性的插拔，使用基于组合而非继承的设计.
* 提供可扩展的点，保持框架的可扩展性.
* 注重使用简易性的同时，注重内部代码设计和实现.
 
如果你是一个创业公司在选择php还是java，同时如果你的团队有一个人写过一年java其他人都没写过。如果你想选择一个更加大型的系统框架，请使用rose，它收集了来自人人网、糯米网、小米科技的众多工程师的经验，你可以免费拥有这些。

###2）rose能做什么？###
* 初级rose用户：
 * rose可以用来完成一个网站。
* 中级rose用户：
 * rose可以用来完成一个大型网站，它提供的jade功能使得你的项目可以快速开发，自然切入连接池；它提供的portal功能，可以将一个网页分多个线程发起向DB的请求，节省用户的时间；它提供的pipe功能类似facebook的bigpipe，让前端加速，与此同时还有portal多线程的优势。
* 高级rose用户：
 * rose可以自由加入spring任何特性，比如定时执行（去TM的crontab）；比如拦截器做统一权限控制。可以自由配置主库从库，分表规则。配置thrift、zookeeper可以得到牛B的高可用性高性能服务集群。

1.2 简明教程：
-------------
*下面开始来进入rose框架下的开发。只需要有一个感性的认识即可。下一章里会专门详细的手把手教你搭建hello world项目。
###1）需要些什么？###
* 提前学习什么是maven：简单地说，maven是个build工具，用一个pom.xml来定义项目的依赖。通过一个命令mvn，可以自由地build compile（知道make吧，类似，或者类似ant）。
* 也许还需要一个nexus，用来搭建自己的maven仓库（这些都是门槛啊，知道为什么java用的人在全球多，而在中国php的人似乎更多，因为我们的基础设施太落后了）。nexus的作用是配合maven工作。（54chen正在向sonatype申请将rose项目push到sonatype的官方库中，成功后这将省略掉这一步）

* 然后需要在你的项目的pom文件中添加：

<div class="chen-hl"><div class="bar"></div><ol class="dp-xml" start="1"><li class="alt"><span><span class="tag">&lt;</span><span class="tag-name">dependency</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class=""><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">groupId</span><span class="tag">&gt;</span><span>net.paoding</span><span class="tag">&lt;/</span><span class="tag-name">groupId</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class="alt"><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">artifactId</span><span class="tag">&gt;</span><span>paoding-rose</span><span class="tag">&lt;/</span><span class="tag-name">artifactId</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class=""><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">version</span><span class="tag">&gt;</span><span>1.0-SNAPSHOT</span><span class="tag">&lt;/</span><span class="tag-name">version</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class="alt"><span><span class="tag">&lt;/</span><span class="tag-name">dependency</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class=""><span><span class="tag">&lt;</span><span class="tag-name">dependency</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class="alt"><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">groupId</span><span class="tag">&gt;</span><span>net.paoding</span><span class="tag">&lt;/</span><span class="tag-name">groupId</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class=""><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">artifactId</span><span class="tag">&gt;</span><span>paoding-rose-jade</span><span class="tag">&lt;/</span><span class="tag-name">artifactId</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class="alt"><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">version</span><span class="tag">&gt;</span><span>1.1-SNAPSHOT</span><span class="tag">&lt;/</span><span class="tag-name">version</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class=""><span><span class="tag">&lt;/</span><span class="tag-name">dependency</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class="alt"><span><span class="tag">&lt;</span><span class="tag-name">dependency</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class=""><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">groupId</span><span class="tag">&gt;</span><span>net.paoding</span><span class="tag">&lt;/</span><span class="tag-name">groupId</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class="alt"><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">artifactId</span><span class="tag">&gt;</span><span>paoding-rose-scanning</span><span class="tag">&lt;/</span><span class="tag-name">artifactId</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class=""><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">version</span><span class="tag">&gt;</span><span>1.0-SNAPSHOT</span><span class="tag">&lt;/</span><span class="tag-name">version</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li><li class="alt"><span><span class="tag">&lt;/</span><span class="tag-name">dependency</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li></ol></div>



* 这是三个最基础的框架包。

###2）一个controller长什么样？###
<code>
@Path("/")  
public class TestController {  
    @Get("hello")  
    public String test(){  
        return "@a";  
    }  
}  
</code>
* http://localhost/hello

* 将会返回:a。就是这么简单。

下一节预告：rose手册第二章：配置与使用
=======================================
