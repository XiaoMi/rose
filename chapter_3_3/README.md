controller层：ErrorHandler支持
===============================

3.3.1 ErrorHandler的作用
-----------------------
* 一般来说传统的编程都会到处去try，特别是java里，try来try去的（如果你用erlang一定就知道，已经知道的可能性，怎么能叫异常？都try了还是让它崩了算了。。。）。
* 如果打开你的项目，每个java文件中的代码都有一堆的try，那这时候就是ErrorHandle上阵的时候了。
* ErrorHanle致力于：统一捕捉和处理各种异常，可区分对待和返回；统一的出错体验。
* 非常类似做web开发时的500统一出错页面这样的东东。

3.3.2 示例
----------

~~~~~java

/**
 * @author chenzhen@xiaomi.com
 * 2010-12-1 
 */

package com.chen.controllers;

import net.paoding.rose.web.ControllerErrorHandler;
import net.paoding.rose.web.Invocation;

public class ErrorHandler implements ControllerErrorHandler {

    public Object onError(Invocation inv, Throwable ex) throws Throwable {

        // TODO logger.error("handle err:", ex);

        return "@error";
    }
}

~~~~~

这是这么简单，不用怀疑！

3.3.3 放在哪里才能生效？
------------------------

* 放在controllers目录下，和controller们在一起（幸福快乐地生活）。
* 一般来讲，ErrorHandler都是用在web项目里，在最快层起作用。
* 所有的方法都可以尽情地向处throws Exception了。
* 不需要再try了。

~~~~~java

@Path("")
public class HelloController {
    @Get("")
    public String index2() throws Exception {
        return "@hello world";
    }
}


~~~~~

3.3.3 有用的例子: 不同的异常类型做不用的事情
--------------------------------------------

~~~~~java

/**
 * @author chenzhen@xiaomi.com
 * 2010-12-1 
 */

package com.chen.controllers;

import net.paoding.rose.web.ControllerErrorHandler;
import net.paoding.rose.web.Invocation;

public class ErrorHandler implements ControllerErrorHandler {

    public Object onError(Invocation inv, Throwable ex) throws Throwable {

        // TODO logger.error("handle err:", ex);
        if (ex instanceof RuntimeException) {
            return "@runtime";
        }
        return "@error";
    }
}

~~~~~

文中所提及代码均在 https://github.com/XiaoMi/rose/tree/master/rose-example 提供。
