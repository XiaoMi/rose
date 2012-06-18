<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://paoding.net/rose/pipe" prefix="rosepipe"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload</title> 
<script type='text/javascript' src='/js/rosepipe.js'></script>
</head>
<body>
upload例子
<br>
<form action="/doUpload" method="post" enctype="multipart/form-data">
<input type="file" name="file">
<input type="submit" value="提交"> 
</form>
</body>
</html> 