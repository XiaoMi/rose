3.B DAO层：DAO进阶：SQLParm支持和表达式SQL
==========================================

3.B.1 SQLParam介绍：DAO方法传递参数
-----------------------------------

* SQLParam作为DAO支持中的参数传递使者，可以传递一个常见的变量，也可以是一个自定义的对象。
* 比如：

```java
    @SQL("insert into test (id,msg) values (:t.id,:t.msg)")
    public void insertTest(@SQLParam("t") Test test);
```
* 上列中Test对象通过t传递到sql执行中去，并且可以分别使用其中的属性。这感觉是不是很自然？

* 当然，如果是一个int、long、String等自在不言中。

* 当是list时，会有自动的batch操作，将sql拆为多条sql执行。这个小技巧会在后面的章节里讲。平时很少用到。

3.B.2 ReturnGeneratedKeys介绍：返回刚刚插入的ID号
--------------------------------------------------

* 特别是使用mysql开发的广大劳苦大众，常常会使用到auto_increament的字段。
* 当一条insert语句在执行的时候，我们常常会去需要拿它的当前的自增id是多少。

```java

    @ReturnGeneratedKeys
    @SQL("insert into test (id,msg) values (:t.id,:t.msg)")
    public int insertTest(@SQLParam("t") Test test);

```

* 如上述代码所示，只需要加上一个@ReturnGeneratedKeys即可返回当前的id

3.B.2 表达式的支持
------------------

* 多变的业务需求决定了我们的sql是复杂的，需要有条件地执行。
* 如果每种条件都去写DAO中的SQL，那DAO的变得很大。
* 常常会有动态产生sql的需求。
* jade支持一些常规的表达式。

* 语法一：常见的变量赋值
 * 冒号（:）表示这是一个变量，比如上面的例子里的 :t.id，它会被一个值替换。

* 语法二：字符串连接
 * 连续的井号（##） 表示后面的变量作字符串连接
 * 如下例中的partition变量，还请不要误解，分表不是这样做的，下一章会介绍标准的分表设置。

```java

    @SQL("SELECT user_id, device_token FROM test_##(:partition) LIMIT :limit")
    public List<Test> getTests(@SQLParam("partition") int partition, @SQLParam("limit") int limit);

```

* 语法三：条件选择
 * 井号if（#if{}）用于表示当条件满足时sql拼接。

```java

    @SQL("SELECT user_id, device_token FROM test_##(:partition) #if(:user>0){ where user_id=:user } LIMIT :limit")
    public List<Test> getTestsIf(@SQLParam("partition") int partition, @SQLParam("limit") int limit, @SQLParam("user") int user); 

```

* 其他语法：还有for循环，实际使用少。
* 典型地，一般的select in查询，可以直接传入list<int>，例如下例中的ids变量：

```java

    @SQL("SELECT user_id, device_token FROM test_##(:partition) where user_id in(:ids)")
    public List<Test> getTestsByIds(@SQLParam("partition") int partition, @SQLParam("ids") List<Integer> ids);

```

* 文中所提及代码均在 https://github.com/XiaoMi/rose/tree/master/rose-example 提供。
