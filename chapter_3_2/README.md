controller层：拦截器支持
========================
3.2.1 拦截器作用
----------------
* 面向切面编程（AOP）方法可以让一个项目更加关注核心逻辑，常见的一些最佳实践包括
** 权限
** 缓存
** 错误处理
** 延时加载
** 调试
** 持久化
** 资源池
** 等等。。。
而此处的拦截器目标是在controller层提供各种在controller执行前、执行后的代码切入，以达到各种可AOP的目标。
* 简单地说，拦截器能干的事情就是当你的项目写了一半时发现缺少啥全局要做的事情（比如需要验证权限），不用担心，搞一个拦截器就是了。
3.2.2 拦截器例子
----------------

~~~~~java
public class AccessTrackInterceptor extends ControllerInterceptorAdapter {
	public AccessTrackInterceptor() {
		setPriority(29600);
	}

	@Override
    public Object before(Invocation inv) throws Exception {
        DecisionResolver.getDecision(inv);
        return super.before(inv);
    }

    @Override
    public void afterCompletion(final Invocation inv, Throwable ex) throws Exception {
	// TODO ....
    }
}
~~~~~

3.2.3 拦截器可动的位置细节
--------------------------
