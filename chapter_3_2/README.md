controller层：拦截器支持
========================

3.2.1 拦截器作用
----------------
* 面向切面编程（AOP）方法可以让一个项目更加关注核心逻辑，常见的一些最佳实践包括
 * 权限
 * 缓存
 * 错误处理
 * 延时加载
 * 调试
 * 持久化
 * 资源池
 * 等等。。。
* 而此处的拦截器目标是在controller层提供各种在controller执行前、执行后的代码切入，以达到各种可AOP的目标。
* 简单地说，拦截器能干的事情就是当你的项目写了一半时发现缺少啥全局要做的事情（比如需要验证权限），不用担心，搞一个拦截器就是了。

3.2.2 拦截器例子
----------------

~~~~~java
public class AccessTrackInterceptor extends ControllerInterceptorAdapter {
    public AccessTrackInterceptor() {
	setPriority(29600);
    }
    @Override
    public Class<? extends Annotation> getRequiredAnnotationClass() {
       	return PriCheckRequired.class; // 这是一个注解，只有标过的controller才会接受这个拦截器的洗礼。
    }
    @Override
    public Object before(Invocation inv) throws Exception {
       	// TODO ....
	return super.before(inv);
    }

    @Override
    public void afterCompletion(final Invocation inv, Throwable ex) throws Exception {
	// TODO ....
    }
}
~~~~~

需要注意几点：
* 拦截器要放在controllers下(高级用法:打在rose-jar包里，参见5.1)
* 继承net.paoding.rose.web.ControllerInterceptorAdapter
* 按照实现的方法名，在controller执行前、中、后执行：
 * before：在controller执行前执行。
 * after：在controller执行中（后）执行，如果一个返回抛出了异常，则不会进来。
 * afterCompletion：在controller执行后执行，不论是否异常，都会进来。
 * isForAction：定义满足某条件的才会被拦截。

3.2.3 拦截器可动的位置细节
--------------------------
* 上面都讲得差不多了，实际上还有不少地方可以拦截的：
 * isForDispatcher：根据响应的情况判断是否拦截，比如说是正常请求、内部forward、还是include （但是没用过）
 * setPriority：设置一个数字表示拦截优先级，当有多个拦截器时，要精准控制，数字小的内层，大的在外层，在最外层的before方法最先执行，大家都执行完后它的after才最后执行。
 * round：这才是真正的controller执行中执行，不过用得很少。
 * getRequiredAnnotationClass：返回一个Annotation class name，表示这个拦截器只对此Annotation标过的controller才生效。常用。

3.2.4 实际应用场景
------------------
* 全站是否登录判断相关的逻辑，写在一个拦截器里，一次完成后，其他地方不再关心这个代码，在需要登录才能做的controller上注解一下，表示需要被执行拦截。
* 日志收集的逻辑，在一个拦截器里进行当前的access log记录。
* 权限体系的逻辑，写在一个拦截器里，在对应的操作上作注解，拦截器中进行细节的判断，新加的api也只是需要一次注解就得到了权限的判断。
