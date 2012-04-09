rose手册第二章：配置与使用
==========================

2.1 基础环境
------------
*普通的pc机，del 380
*ubuntu 10.04基本不升级
*java version "1.6.0_29"
*eclipse
*m2clipse
*茶一杯

2.2 maven简介
-------------
*Maven是基于项目对象模型(POM)，可以通过一小段描述信息来管理项目的构建，报告和文档的软件项目管理工具。如果你已经有十次输入同样的Ant targets来编译你的代码、jar或者war、生成javadocs，你一定会自问，是否有一个重复性更少却能同样完成该工作的方法。Maven便提供了这样一种选择，将你的注意力从作业层转移到项目管理层。Maven项目已经能够知道如何构建和捆绑代码，运行测试，生成文档并宿主项目网页。

2.3 基础的pom文件
-----------------
~~~~~xml
<dependency>
  <groupId>com.54chen</groupId>
  <artifactId>paoding-rose-scanning</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>

<dependency>
  <groupId>net.paoding</groupId>
  <artifactId>paoding-rose</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>

<dependency>
  <groupId>com.54chen</groupId>
  <artifactId>paoding-rose-portal</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>

<dependency>
  <groupId>com.54chen</groupId>
  <artifactId>paoding-rose-portal</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>

<dependency>
  <groupId>com.54chen</groupId>
  <artifactId>paoding-rose-jade</artifactId>
  <version>1.1-SNAPSHOT</version>
</dependency>
~~~~~
2.4 hello world
----------------

2.5 从数据库的hello world
--------------------------

2.6 更多特性简介
----------------
