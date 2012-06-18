3.9  controller层：上传文件
===========================

3.9.1 其实很简单
================
* 添加依赖包:commons-io.jar
* html中使用 enctype="multipart/form-data",method="POST" 
* 直接看后端代码吧。

~~~~~java

    @Post("/doUpload")
    public String doUpload(@Param("file") MultipartFile file) {
        return "@ upload ok!" + file.getOriginalFilename();
    }

~~~~~

3.9.2 其他
==========

* 可以同时接收所有的文件

~~~~~java
// 不声明@Param
// files可以是一个数组或者List
public String upload(MultipartFile[] files) {
    return "@ok-" + Arrays.toString(files);
}
~~~~~

* 同时也可以使用@Param传递不同的name。
