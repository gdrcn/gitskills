<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="img/ie.ico" type="image/x-icon" />
<title>忘记密码</title>
<link href="css/ForgetPassword.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet">
<script type="text/javascript" src="js/ForgetPassword.js"></script>
<script>
function change() {
	// 切换验证码
	document.getElementById("myimg").src = "/QGaccess/Checkcode?"
			+ new Date().getTime();
}
</script>
</head>
<body background="img/bj2.jpg?v=<%=System.currentTimeMillis()%>" class="scaleimg">

	<h3 style="color: red;">${msg }</h3>

	<div class="rightA">
	<input type="button" class="right" text-align="right"
		onclick="javascrtpt:window.location.href='/QGaccess/StartLogin.jsp'">
		</div>

	<div class="middle">

		<div id="header">
			<h1 white-space="pre-wrap">找 回 密 码</h1>
		</div>

		<form action="/QGaccess/ForgetPasswordWeb" method="post">
			<div class="emailico">
				<input type="text" name="email" required="required" id="email"
					value="${email }" placeholder="请输入找回邮箱"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\@\.]/g,'')" />
			</div>
			<h3 style="color: red;">${msgemail }</h3>
			<div>
				<select name="qpwd">
					<option value="fName">您父亲的姓名</option>
					<option value="mName">您母亲的姓名</option>
					<option value="sName">您配偶的姓名</option>
				</select>
			</div>
			<div class="pwdico">
				<input type="text" name="qname" required="required" id="qname"
					value="${qname }" onBlur="vilidate()" placeholder="密保信息"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
			</div>
			<h3 style="color: red;">${msgqname }</h3>

			<input type="text" name="checkcode" class="userico"
				placeholder="请输入验证码"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
			<img src="/QGaccess/Checkcode" id="myimg" class="checkcode"
				onclick="change();" style="cursor: pointer;" />
			<h5 class="fontSet">${yzm }</h5>

			<div style="text-align: center;">
				<input type="submit" value="下一步" onclick="return logic()"
					class="subico" />
			</div>
	
	</form>
	</div>
</body>
</html>