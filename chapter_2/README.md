rose手册第二章：配置与使用
==========================

2.1 基础环境
------------
* 普通的pc机，del 380
* ubuntu 10.04基本不升级
* java version "1.6.0_29"
* eclipse
* m2clipse
* 茶一杯

2.2 maven简介
-------------
* maven是基于项目对象模型(POM)，可以通过一小段描述信息来管理项目的构建，报告和文档的软件项目管理工具。如果你已经有十次输入同样的Ant targets来编译你的代码、jar或者war、生成javadocs，你一定会自问，是否有一个重复性更少却能同样完成该工作的方法。Maven便提供了这样一种选择，将你的注意力从作业层转移到项目管理层。Maven项目已经能够知道如何构建和捆绑代码，运行测试，生成文档并宿主项目网页。
* maven对一个项目进入了固定的默认目录定义：
 * src/main/java 写主要的java实现
 * src/main/resources 写主要的配置文件
 * src/test/java 写test case
 * src/test/resources 写test case所需要的配置文件
 * src/main/webapp [war项目特有]web项目的对外目录
 * src/main/webapp/WEB-INF [war项目特有]web项目配置web.xml目录

2.3 项目建立
------------
* 打开eclipse(需要提前安装好m2clipse插件)
* new -> other -> maven -> maven project
* create a simple project
* next
* group id:com.54chen
* artifact id:rose-example
* packaging: war
* finished

2.4 基础配置三步走
----------------------
###1）点火：基础的pom文件###
打开2.3建立好的项目，打开pom.xml，添加下面的段落到project中：
~~~~~xml
	<dependencies>
		<dependency>
			<groupId>com.54chen</groupId>
			<artifactId>paoding-rose-scanning</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>

		<dependency>
			<groupId>com.54chen</groupId>
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
			<artifactId>paoding-rose-jade</artifactId>
			<version>1.1-SNAPSHOT</version>
		</dependency>
	</dependencies>
~~~~~
上述是rose环境最基础的依赖包。再添加一点常见的编译设置：
~~~~~xml
	<build>
		<resources>
			<resource>
				<directory>src/main/resources</directory>
				<includes>
					<include>**/*.*</include>
				</includes>
			</resource>
		</resources>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-war-plugin</artifactId>
				<version>2.0.2</version>
				<configuration>
					<webResources>
						<resource>
							<targetPath>WEB-INF</targetPath>
							<filtering>true</filtering>
							<directory>src/main/resources</directory>
							<includes>
								<include>**/*.xml</include>
								<include>**/*.properties</include>
							</includes>
							<targetPath>WEB-INF</targetPath>
						</resource>
					</webResources>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>1.6</source>
					<target>1.6</target>
					<fork>true</fork>
					<verbose>true</verbose>
					<encoding>UTF-8</encoding>
					<compilerArguments>
						<sourcepath>
							${project.basedir}/src/main/java
                        </sourcepath>
					</compilerArguments>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<configuration>
					<!-- 忽略测试 -->
					<skip>true</skip>
				</configuration>
			</plugin>
		</plugins>
	</build>
~~~~~
上述是编译设置，也是放在project段落里。

###2）松离合：必不可少的web.xml###
在src/main/webapp/WEB-INF文件夹下建立web.xml:
~~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	id="WebApp_ID" version="2.5">
	<display-name>rose-example</display-name>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>index.jsp</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>default.jsp</welcome-file>
	</welcome-file-list>
	
	 
	<context-param>
		<param-name>log4jConfigLocation</param-name>
		<param-value>/WEB-INF/log4j.xml</param-value>
	</context-param>

	<listener>
		<listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
	</listener>
	<filter>
		<filter-name>roseFilter</filter-name>
		<filter-class>net.paoding.rose.RoseFilter</filter-class>
	</filter> 
	<filter-mapping>
		<filter-name>roseFilter</filter-name>
		<url-pattern>/*</url-pattern>
		<dispatcher>REQUEST</dispatcher>
		<dispatcher>FORWARD</dispatcher>
		<dispatcher>INCLUDE</dispatcher>
	</filter-mapping> 
</web-app>
~~~~~
###3）踩油门：applicationContext.xml###
src/main/resources/applicationContext.xml是spring环境的油门，所有包的扫描和启动都在这里定义：
~~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans  
    http://www.springframework.org/schema/beans/spring-beans-2.5.xsd  
    http://www.springframework.org/schema/context  
    http://www.springframework.org/schema/context/spring-context-2.5.xsd"
	default-lazy-init="true">

	<!-- 自动扫描 -->
	<context:annotation-config />
	<context:component-scan base-package="com.54chen">
	</context:component-scan>
	 
</beans>
~~~~~

2.5 hello world
----------------
* 在src/main/java上右键 -> new -> package -> name: com.chen
* 在com.chen上右键 -> new -> package -> com.chen.controllers [controllers是rose框架默认的加载controller的package name]
* 在com.chen.controllers上右键 -> new -> class -> HelloController [*Controller是rose框架默认的controller层的class后缀]
* 打开HelloController这个类
* 在public class HelloController添加注解@Path("")  [Path注解是rose框架提供的标识每个controller的对外访问时的基础路径]
* 在HelloController中添加方法
~~~~~java
/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-4-10 上午11:14:46
 */
package com.chen.controllers;

import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("")
public class HelloController {

    @Get("")
    public String index() {
        return "@hello world";
    }
}
~~~~~
[Get注解是rose框架提供的标识一个http访问是get还是post或者是其他，并且会将path与get中的字符串连接成一个url]
上述代码可以从浏览器访问：http://localhost/。
下述代码可以从浏览器访问：http://localhost/hello/world [注意path与get中的参数]。
~~~~~java
/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-4-10 上午11:14:46
 */
package com.chen.controllers;

import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("/hello/")
public class HelloController {

    @Get("world")
    public String index() {
        return "@hello world";
    }
}

~~~~~
2.6 从数据库的hello world
--------------------------

2.7 更多特性简介
----------------
