<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://paoding.net/rose/pipe" prefix="rosepipe"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>portal/pipe演示信息</title> 
<script type='text/javascript' src='/js/rosepipe.js'></script>
</head>
<body>

portal/pipe演示信息：
<br>
<div id="p1"></div>
<br>
<div id="p2"></div>

</body>
</html>
<rosepipe:write>${p1}</rosepipe:write>
<rosepipe:write>${p2}</rosepipe:write>