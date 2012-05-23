controller层：统一的参数验证办法
=================================

3.5.1 用来做什么
----------------
* 我们把的参数验证办法叫ParamValidator
* 一般来说，像比如说验证http传来的参数是不是为空呀啥的（发挥你的想象力）。
* 好处在于不用再重复地写if else

3.5.2 怎么用
------------
* 来看一个例子，验证用户的参数不可为空(灰常灰常的实用)：

~~~~~java

public class NotBlankParamValidator implements ParamValidator {

    @Override
    public boolean supports(ParamMetaData metaData) {
        return metaData.getAnnotation(NotBlank.class) != null;
    }

    @Override
    public Object validate(ParamMetaData metaData, Invocation inv, Object target, Errors errors) {
        String paramName = metaData.getParamName();
        String value = inv.getParameter(paramName);
        if (StringUtils.isBlank(value)) {
            return "@参数不能为空";
        }
        return null;
    }
}

~~~~~

解读：

* 放到controllers下
* 实现ParamValidator
* 实现supports方法，这个方法用来做判断是否要验证当前得到的http参数，一般都用个注解来判断比较文艺
* 实现validate方法，这里是主要逻辑
 * metaData里放的是参数的原型
 * inv是rose的基础调用
 * target是这个参数的最后解析结果，参看上一节里提到的东西
 * errors是这个参数解析时出来的错误
* NotBlank是一个自己定义的annotation

3.5.3 使用时action长什么样？
----------------------
* 下面的代码是action中使用时长的样子:

~~~~~java
    @Get("/notBlank")
    public String notBlank(@NotBlank @Param("messages") String messages) throws Exception {
        return "@hello world";
    }
~~~~~

文中所提及代码均在 https://github.com/XiaoMi/rose/tree/master/rose-example 提供。
                                                                                      
