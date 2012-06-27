3.A  DAO层：DAO的基本配置与使用
==================================

 本章开始进入对DB层的支持，同进也是日常开发用得最多的章节。

3.A.1 什么是jade？
-------------------

* jade大概是java access data layer的意思吧，具体的来由，在章节写到末尾的时候，我再找qieqie和liaohan大侠们写一写编年史。
* 用jade的好处在于，尽可能减少重复的从db把数据对bean进行装配的过程，统一入口，隔离业务逻辑，方便review。
* jade是在spring完成的数据层的良好实践总结，无缝接入rose中，可以算得上是rose亲密无间的好模块。

3.A.2 引入基础配置
-------------------

* 要开始使用jade，一定要先引用jade的基础包：

pom.xml

```xml
		<dependency>
			<groupId>com.54chen</groupId>
			<artifactId>paoding-rose-jade</artifactId>
			<version>1.1</version>
		</dependency>
```

* 除了需要jade的包外，还需要引入数据源连接池的jar，这里使用了dbcp，还是在pom.xml中：

```xml

		<dependency>
			<groupId>commons-dbcp</groupId>
			<artifactId>commons-dbcp</artifactId>
                        <version>1.2.2</version>
		</dependency>

```

* 在pom中引入了依赖之后，需要定义一个数据源，这里先不考虑多个数据源的情况。

在war项目的applicationContext.xml中增加数据源定义：

```xml

	 <!-- 数据源配置 dbcp -->
	<bean id="jade.dataSource.com.chen.dao" class="org.apache.commons.dbcp.BasicDataSource"
		destroy-method="close">
		<property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		<property name="url"
			value="jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=utf-8"></property>
		<property name="username" value="test"></property>
		<property name="password" value="test"></property>
		<!-- 运行判断连接超时任务的时间间隔，单位为毫秒，默认为-1，即不执行任务。 -->
		<property name="timeBetweenEvictionRunsMillis" value="3600000"></property>
		<!-- 连接的超时时间，默认为半小时。 -->
		<property name="minEvictableIdleTimeMillis" value="3600000"></property>
	</bean> 

```

* 这里假设了mysql已经安装在本地了，用户名为test，密码为test。
* jade约定了bean的id为jade.dataSource.classPackageName。
* jade约定了这个bean的有效范围为classPackageName所有的DAO。
* jade约定了除非有专门的定义，所有的子目录也受bean上的classpackageName所影响。

3.A.3 第一个读取数据库的实例
-----------------------------

* 先需要准备一下数据库：

```sql

create table test (int id, varchar(200) msg);
insert into test values(111,'adfafafasdf');

```

* 然后准备简练的DAO声明：

```java

@DAO
public interface TestDAO {
    @SQL("select id,msg from test limit 1")
    public Test getTest();
}


```

* Test是一个class，里面有标准的getter和setter。

* 然后从一个类去调用它：

```java

@Service
public class TestService {

    @Autowired
    private TestDAO testDAO;

    public Test getTest() {
        return testDAO.getTest();
    }
}

```

* 当然也可以直接用controller就调用它显示了，当然这不是一个大型项目良好的架构，架构问题在最后的章节里讲述。

3.A.4 查看演示代码
-------------------

* rose-example项目的HelloController已经准备好了一个action做为此节的总结。
* 你可以将此节发布到resin或者tomcat后访问：http://127.0.0.1/3.10 查看结果。


* 文中所提及代码均在 https://github.com/XiaoMi/rose/tree/master/rose-example 提供。
