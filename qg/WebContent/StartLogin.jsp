<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="shortcut icon" href="img/ie.ico" type="image/x-icon" />
<title>登陆网页</title>
<link href="css/StartLogin.css?v=<%=System.currentTimeMillis()%>"
	rel="stylesheet">
<script type="text/javascript" src="js/StartLogin.js"></script>
</head>
<body background="img/bj.jpg?v=<%=System.currentTimeMillis()%>" class="scaleimg">

	<div class="middle">
		<div id="header">
			<h1 white-space="pre-wrap";>登 陆</h1>
		</div>
		<form action="/QGaccess/LoginWeb" method="post">
			<div style="width: 100%; text-align: center">
				<input type="text" name="userName" required="required" id="name"
					placeholder="请输入用户名" class="userico"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />

				<input type="password" name="password" id="pwd" class="pwdico"
					required="required" onkeyup="value=value.replace(/[\W]/g,'')"
					placeholder="请输入密码" />
				<h3 style="color: red;">${msgpwd }</h3>
			</div>

			<input type="submit" value="登陆" onclick="return logic()"
				class="subico"> </br> <input type="button" value="注册"
				class="butico"
				onclick="javascrtpt:window.location.href='/QGaccess/Register.jsp'">
		</form>
		
		<div id="forgetPassword">
			<a href="http://localhost:8080/QGaccess/ForgetPassword.jsp">忘记密码？</a>
		</div>
	</div>
</body>
</html>