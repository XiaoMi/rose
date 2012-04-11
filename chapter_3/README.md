rose手册第三章：框架功能参考
===========================

3.1 controller层：url对照规则与返回结果规则
---------------
###3.1.1) url对照规则——最简单的例子
先看看怎样把url和某个方法对应起来。为了方便说明，现在我们来一起完成一个极简版的贴吧。  
####1)贴吧需要什么功能？
贴吧中当然会有很多“主帖”（topic），“主帖”下会有很多“跟帖”（comment）。  
一般，贴吧中最基本的，会有下面这几个功能需要我们完成：  
* 显示主帖列表
* 显示单个主帖和它的跟贴
* 显示单个跟贴
* 创建一个主帖
* 创建一个跟贴
####2)设计 web API
然后让我们来规划一个[REST](http://zh.wikipedia.org/wiki/REST)风格的 web API :  
（“GET”和“POST”是指[HTTP1.1](http://zh.wikipedia.org/wiki/%E8%B6%85%E6%96%87%E6%9C%AC%E4%BC%A0%E8%BE%93%E5%8D%8F%E8%AE%AE)中的请求方法）  
* 显示主帖列表
 * GET http://github.com/myforum/topic
* 显示单个主帖和它的跟贴
 * GET http://github.com/myforum/topic/123
* 显示单个跟贴
 * GET http://github.com/myforum/topic/123/comment/456
* 创建一个主帖
 * POST http://github.com/myforum/topic
* 创建一个跟贴
 * POST http://github.com/myforum/topic/123/comment
可以发现一个共同点，所有API中，URI部分的第一级都是“/myforum”（但这并不是规定，仅仅为了演示）。  
####3)实现 web API
首先新建一个类，这个类的类名_必须_以“Controller”结尾：  

    @Path("myforum")
    public class ForumController {
    }

注意标注在类(class)上的注解“@Path("myforum")”，这意味着，这个类中定义的所有API的URI，都必须以“myforum”开头，比如“/myforum/xxx”和“/myforum/yyy”等（但“myforum”不一定是整个URI的第一级，比如“/aaa/myforum/bbb”）。  

接着，实现第一个API——“GET http://github.com/myforum/topic”：
    @Path("myforum")
    public class ForumController {
    
        @Get("topic")
        public String getTopics() {
            //显示主帖列表
            return "topiclist";
        }
    }
因为是“GET”方法，所以在该方法上标注“@Get("")”，URI“/myforum/topic”中的“myforum”已经在“@Path("myforum")”中定义过了，所以只剩下“topic”，于是写“@Get("topic")”。  

再看第二个API——“GET http://github.com/myforum/topic/123”，以前一个的唯一区别是，后面多了个“/123”，表示主帖id，而这个id当然不是固定的，只有用户点击链接发来请求时才能知道，肿么办？  
没关系，rose支持正则表达式！可以这么写：

    @Get("topic/{topicId:[0-9]+}")
    public String showTopic(@Param("topicId") int topicId) {
        //显示单个主帖和它的跟贴
        return "topic";
    }

与前一个API相比，多了段“/{topicId:[0-9]+}”。正则表达式被大括号"{}"包围，格式为“{ paramName : regularExpression }”，只有请求的URI能被正则表达式匹配时，才会执行这个方法，而被匹配的值将被保存在名为“topicId”的参数中。  

同理，实现第三个API，稍微复杂一点：
    @Get("topic/{topicId:[0-9]+}/comment/{commentId:[0-9]+}")
    public String showComment(@Param("topicId") int topicId, @Param("commentId") int commentId) {
        //显示单个跟贴
        return "comment";
    }

最后两个API使用POST方法，其他与前面相同：

    @Post("topic")
    public String createTopic(){
        //创建一个主帖
        return "topic";
    }
    @Post("topic/{topicId:[0-9]+}/comment")
    public String createComment(@Param("topicId") int topicId){
        //创建一个跟贴
        return "comment";
    }

完整的代码如下（省略了import语句）：  

    @Path("myforum")
    public class ForumController {
    
        @Get("topic")
        public String getTopics() {
            //显示主帖列表
            return "topiclist";
        }
    
        @Get("topic/{topicId:[0-9]+}")
        public String showTopic(@Param("topicId") int topicId) {
            //显示单个主帖和它的跟贴
            return "topic";
        }
    
        @Get("topic/{topicId:[0-9]+}/comment/{commentId:[0-9]+}")
        public String showComment(@Param("topicId") int topicId, @Param("commentId") int commentId) {
            //显示单个跟贴
            return "comment";
        }

        @Post("topic")
        public String createTopic(){
            //创建一个主帖
            return "topic";
        }

        @Post("topic/{topicId:[0-9]+}/comment")
        public String createComment(@Param("topicId") int topicId){
            //创建一个跟贴
            return "comment";
        }
    }

至此，一个贴吧功能的Controller就编写完成了。
####4) 更多细节
* 关于URI路径的映射
除了上面例子中的做法（@Path("")，@Get("")和@Post("")），还可以通过包路径来规划URI。  
比如前面例子中的Controller，在API不变的前提下，还可以这么做：
 * 1.在controllers路径下新建一个叫做“myforum”的文件夹。
 * 2.将ForumController从“xxx.controllers”移动到“xxx.controllers.myforum”，并改成下面这样：

    @Path("")
    public class ForumController {
    
        @Get("topic")
        public String getTopics() {
            //显示主帖列表
            return "topiclist";
        }
        ... ...
    }
只是将“@Path("myforum")”改成了“@Path("")”。  
这样做的好处是可以让项目中的代码组织清晰。

###3.1.2) 返回结果规则
####1) 渲染页面并返回
web开发中最常规的做法是，运行Servlet中的方法，最后将渲染好的页面内容返回。下面说说rose是怎么做的。  
上面的贴吧例子中，每个方法的返回值都是一个普通字符串，比如“comment”，意思是，找到web项目中“webapp/views”路径下名叫“comment”的视图文件，比如“comment.jsp”，用这个视图文件来渲染网页结果并返回。  
comment.jsp的代码如下：

    ...
    <body>
        昵称：${name}<br>
        回复内容：${commentContent}
    </body>
    ...
页面中有两个变量——name和commentContent，变量的值是在java代码中设置的，如下：

    @Get("topic/{topicId:[0-9]+}/comment/{commentId:[0-9]+}")
    public String showComment(Model model, @Param("topicId") int topicId, @Param("commentId") int commentId) {
        //显示单个跟贴
        model.add("name", "郭德纲");
        model.add("commentContent", "今天来人不少，我很欣慰啊！");
        return "comment";
    }

总结一句话，通过rose提供类net.paoding.rose.web.var.Model来设置变量名和变量值，然后在视图文件中用“${paramName}”的方式得到变量值。  
变量的值可以是String，boolean，数字，数组，对象(JavaBean)。  

* 如果是对象，使用方法如下：

    javaBean：
    public class Bean{
        private String beanValue;
        public String getBeanValue(){...}
        public String setBeanValue(String beanValue){...}
    }
    ==================
    controller中的方法：
    @Get("test")
    public String test(Model model) {
        Bean bean = new Bean();
        bean.setBeanValue("this_is_a_bean");
        model.add("mybean", bean);
        return "test";
    }
    ==================
    test.jsp文件：
    ...
    <body>
        bean里的值：${mybean.beanValue}
    </body>
    ...
    ==================
    输出为：
    bean里的值：this_is_a_bean

* 如果是个数组，可以结合JSTL对数组循环访问：

    controller中的方法：
    @Get("test")
    public String test(Model model) {
        String[] array = {"111","222","333"};
        model.add("array", array);
        return "test";
    }
    ==================
    test.jsp文件：
    ...
    <body>
    <c:forEach var="item" items="${array}" varStatus="status"> 
        打印：${item}<br>
    </c:forEach>
    </body>
    ...
    ==================
    输出为：
    111
    222
    333

####2) 还有几种规则？
rose中，controller方法的返回值有下面几种规则：
* 1.返回普通字符串，如上所述，最常用的做法，渲染视图文件并返回。
* 2.以“@”开头的字符串，比如“return "@HelloWorld";”，会将“@”后面的字符串“HelloWorld”作为结果返回；
* 3.以“@json:”开头的字符串，比如:

    @Get("json")
    public String returnJson(){
        JSONObject jo = new JSONObject();
        return "@json:"+jo.toString();
    }

将会返回一个字符串（jo.toString()），并自动将“HttpServletResponse”中的“contentType”设置为“application/json”。
* 4.【不推荐使用】以“r:”开头的字符串，比如“return "r:/aaa";”，等效于调用“javax.servlet.http.HttpServletResponse.sendRedirect("/aaa")”，将执行301跳转。
* 5.【不推荐使用】以“a:”开头的字符串，比如“return "a:/bbb";”，将会携带参数再次匹配roseTree，找到controller中某个方法并执行，相当于“javax.servlet.RequestDispatcher.forward(request, response)”。
###3.1.3) 原理
Rose 是一个基于Servlet规范、Spring“规范”的WEB开发框架。  

Rose 框架通过在web.xml配置过滤器拦截并处理匹配的web请求，如果一个请求应该由在Rose框架下的类来处理， 该请求将在Rose调用中完成对客户端响应. 如果一个请求在Rose中没有找到合适的类来为他服务，Rose将把该请求移交给web容器的其他组件来处理。  

Rose使用过滤器而非Servlet来接收web请求，这有它的合理性以及好处。  

Servlet规范以“边走边看”的方式来处理请求， 当服务器接收到一个web请求时，并没有要求在web.xml必须有相应的Servlet组件时才能处理，web请求被一系列Filter过滤时， Filter可以拿到相应的Request和Response对象 ，当Filter认为自己已经能够完成整个处理，它将不再调用chain.doNext()来使链中下个组件(Filter、Servlet、JSP)进行处理。  

使用过滤器的好处是，Rose可以很好地和其他web框架兼容。这在改造遗留系统、对各种uri的支持具有天然优越性。正是使用过滤器，Rose不再要求请求地址具有特殊的后缀。  

为了更好地理解，可以把Rose看成这样一种特殊的Servlet：它能够优先处理认定的事情，如无法处理再交给其它Filter、Servlet或JSP来处理。这个刚好是普通Servlet无法做到的 ： 如果一个请求以后缀名配置给他处理时候 ，一旦该Servlet处理不了，Servlet规范没有提供机制使得可以由配置在web.xml的其他正常组件处理 (除404，500等错误处理组件之外)。  

一个web.xml中可能具有不只一个的Filter，Filter的先后顺序对系统具有重要影响，特别的，Rose自己的过滤器的配置顺序更是需要讲究 。如果一个请求在被Rose处理前，还应该被其它一些过滤器过滤，请把这些过滤器的mapping配置在Rose过滤器之前。  

像前面提到过的，RoseFilter的配置，建议按以下配置即可：  

~~~~~xml
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
~~~~~

大多数请况下，filter-mapping 应配置在所有Filter Mapping的最后。 不能将 FORWARD、INCLUDE 的 dispatcher 去掉，否则forward、 include的请求Rose框架将拦截不到。  

Rose框架内部采用"匹配->执行"两阶段逻辑。Rose内部结构具有一个匹配树， 这个数据结构可以快速判断一个请求是否应该由Rose处理并进行， 没有找到匹配的请求交给过滤器的下一个组件处理。匹配成功的请求将进入”执行“阶段。 执行阶段需要经过6个步骤处理：“参数解析 -〉 验证器 -〉 拦截器 -〉 控制器 -〉 视图渲染 -〉渲染后"的处理链。  

匹配树: 匹配树是一个多叉树，下面是一个例子：  

    ROOT

        GET="HomeController#index" package="com.xiaonei.xxx.controllers" 

    /about

        GET="HomeController#about" package="com.xiaonei.xxx.controllers" 

    /book

        GET="BookController#list" package="com.xiaonei.xxx.controllers.sub" 

        POST="BookController#add" package="com.xiaonei.xxx.controllers.sub" 

    /book/

        /book/{id}

            GET="BookController#show" package="com.xiaonei.xxx.controllers.sub" 

    /help

        GET="HomeController#help" package="com.xiaonei.xxx.controllers"


ROOT代表这是一个根地址，也就是 http://localhost/ 代表的地址；  

ROOT的下级有个GET结点，代表对该地址支持GET访问，不支持POST等其它访问，如果进行POST访问将以405错误回应。  

/book代表这是一个/book地址，也就是 http://localhost/book 代表的地址；  

/book下级有GET、POST两个结点，说明它支持GET和POST方法，根据HTTP语义，GET代表浏览，POST代表追加(向一个集合中追加一个条目)。  

/book下还有/book/地址，这个地址有点特别，它以'/'结尾，但实际它不会被任何地址访问到，rose对http://localhost/book/的处理会将它等价于 http://localhost/book 。  

这个特别的地址的存在完全是匹配树结构所需导致的，但不对实际匹配有任何坏的影响，所以也没有任何GET、POST等子结点。  

/book/{id}代表是一个/book/123456、/book/654321这样的地址，当然这可以支持正则表达式的。  

大部分情况下，匹配树的结构和实际的URI结构会一致，也因此匹配树的深度并不固定，每一个中间结点或叶子节点都有可能代表一个最终的URI地址，可以处理GET、POST等请求。对于那些匹配树存在的地址，但没有GET、POST、DELETE等子结点的，一旦用户请求了该地址，rose将直接把该请求转交给web容器处理，如果容器也不能处理它，最终用户将得到404响应。  

**匹配过程**: Rose以请求的地址作为处理输入(不包含Query串，即问号后的字符串)。如果匹配树中存在对应的地址，且含有对应请求方法(GET、POST、PUT、DELETE)的，则表示匹配成功；如果含有其他方法的，但没有当前方法的（比如只支持GET，但当前是POST的），则也表示匹配成功，但最后会以405响应出去；如果所给的地址没有任何支持的方法或者没有找到匹配地址的，则表示匹配失败。1.0.1不支持回朔算法，1.0.2将支持部分回朔算法(待发布时再做详细介绍)。  

**参数解析**: 在调用验证器、拦截器 控制器之前，Rose完成2个解析：解析匹配树上动态的参数出实际值，解析控制器方法中参数实际的值。参数可能会解析失败(例如转化异常等等 )，此时该参数以默认值进行代替，同时Rose解析失败和异常记录起来放到专门的类中，继续下一个过程而不打断执行。  


