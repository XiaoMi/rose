rose手册第五章：FAQ 常见问题
============================

5.1 如何打一个可被rose识别的jar包
----------------------------------

 如果你使用maven，在pom中添加如果定义之后，你打出来的jar包就会被rose扫描到并且引入到上下文环境中：

```xml

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<configuration>
					<archive>
						<manifestEntries>
							<Rose>*</Rose>
						</manifestEntries>
					</archive>
				</configuration>
			</plugin>

```

* 用途：
 * 比如说你做了一个拦截器，在多个项目里需要相同的逻辑，那只需要把这个拦截器做在一个jar包里，声明是rose支持的包即可被使用。

5.2 会被认成batch执行的sql返回
------------------------------

 如果你的sql在执行update insert delete，并且dao的第一个参数是list类的多个值，那这条sql会被拆成多条sql依次执行，执行的结果会以各条sql的返回组成的数组返回。

5.3 一个良好的大型WEB项目架构实践
---------------------------------

 我们一般会把项目规定为：controller/service/biz/dao层，不能跨层调用，只在service层允许同时调用子层多个方法。
