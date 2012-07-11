3.C  DAO层：分表设置
====================

 欢迎顺利进入本章，如果您的企业需要这一节的内容，那么说明用户量很有前途，如果使用了本节的内容，不防向czhttp@gmail.com发信一封以表谢意，我们会很高兴收到各种反馈。 

3.C.1 mysql分表的常规做法
-------------------------

 以下是个人从业经验中的分表规则：

* 按照 id % 100 分为一百份
* 按照 id % 16 分为十六份
* 按照 id/10 % 10 分为十份
* 按照 id%10 分为十份

 以上分表规则特别在mysql中使用机会比较多，各有优势，没有对错，只有最好与最不好用。

3.C.2 使用分表第一步：添加新的依赖
----------------------------------

 要使用分表，需要添加新的依赖，由bmw提供的bmwutils。

```xml

<dependency>
    <groupId>com.54chen</groupId>
    <artifactId>bmwutils</artifactId>
    <version>0.0.2</version>
</dependency>

``` 

3.C.3 使用分表第二步：设置applicationContext.xml分表规则
--------------------------------------------------------

 在开写代码之前，需要告诉DAO是哪个表需要分表，按照什么规则分，分多少份。

```xml

     <!-- 以下配置为分表设置 -->
     <bean id="jade.routerInterpreter" class="com.xiaomi.common.service.dal.routing.RewriteSQLInterpreter">
             <property name="routingConfigurator" ref="jade.routingConfigurator" />
     </bean>
     <bean id="jade.routingConfigurator" class="com.xiaomi.common.service.dal.routing.RoutingConfigurator">
             <property name="partitions">
                   <list> 
                        <value>hash:test:id:test_{0}:100</value> 
                   </list>
             </property>
     </bean>

```

* 此处配置中，partitions参数为一个list，可以对多个table进行定义。
 * hash:test:id:test_{0}:100  表示：使用hash这种办法，将test这个表，按照id的值，分成100份，每份的表名为test_x


3.C.4 使用分表第三步：bmwutils支持的分表办法
--------------------------------------------

* (hash)上例中的hash: 最常用的 id % 100 就是这种办法。该办法会把传入的值先进行转为数字后与定义的份数进行取模（%）。
* (direct)最直接的一种：用的少一些，没有什么规则，直接根据第四个正则，与第三位传入的值进行替换。假设有个人名表，按照字母分表可以用。name_A,name_B,name_C...
* (round)轮循：根据设置，按照调用sql的情况，轮流使用各个表。
* (range)范围：一般用来做日期范围的分表，比如说微博类的，可变值为一个时间，当时间传入时，第三位支持log_{yyyy} log_{yyyy_MM}等时间格式的替换，可轻松做到按周、月、年分表。
* (xm-hash)小米hash：一种古怪的办法，按照传入值的十位进行取模的分表方案。
* (xm-str-hash)小米字符串hash：将字符串按照固定算法变成long之后，再按照小米hash逻辑处理。
* (hex-hash)16进制分表：固定256份以内，传入的值按照16进制转换后按hash求模。

3.C.5 使用分表第四步：写DAO代码@ShardBy
---------------------------------------

```java

    @SQL("SELECT user_id, device_token FROM test where user_id =:id")
    public List<Test> getTestsById(@ShardBy @SQLParam("id") int id);

```

 与不分表的dao相比，只多了一个shardBy，标识按照这个参数值分表。

* 文中所提及代码均在 https://github.com/XiaoMi/rose/tree/master/rose-example 提供。

