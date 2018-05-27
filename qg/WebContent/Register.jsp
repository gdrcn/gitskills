<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="img/ie.ico" type="image/x-icon" />
<title>个人主页注册</title>
<link href="css/Register.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet">
<script type="text/javascript" src="js/Register.js"></script>
<script>
function change() {
	// 切换验证码
	document.getElementById("myimg").src = "/QGaccess/Checkcode?"
			+ new Date().getTime();
}
</script>
</head>
<body background="img/bj1.jpg" class="scaleimg">
<embed src="music/mu.wav" loop="100" autostar="true" hidden="true" controls="controls"></embed> 
	<h5 class="fontSet">${msg }</h5>
	<input type="button" class="right" text-align="right"
		onclick="javascrtpt:window.location.href='/QGaccess/StartLogin.jsp'">
	<div class="middle">
		<div id="header">
			<h1>个人主页注册</h1>
		</div>
		<form action="/QGaccess/RegisterWeb" method="post">

			<input type="text" name="userName" required="required" class="intext"
				onBlur="vilidate()" id="name" value="${userName }"
				placeholder="请输入用户名"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
			<h5 class="fontSet">${msgname }</h5>

			<input type="password" name="password1" size="20" id="pwd1"
				class="intext" placeholder="请输入密码" required="required"
				onkeyup="value=value.replace(/[\W]/g,'')" />
			<h5 class="fontSet">${msgpwd }</h5>

			<input type="password" name="password2" size="20"
				placeholder="请再次输入密码" class="intext" id="pwd2" required="required"
				onkeyup="validate()" />
			<div>
				<span id="tips"></span>
				<h6 class="marg"></h6>
				<b>性别： </b> <input type="radio" name="gender" value="女" checked />女
				<input type="radio" name="gender" value="男" />男
			</div>

			<input type="text" name="phoneNumber" required="required"
				class="extext" value="${phoneNumber }" id="num"
				placeholder="请输入手机号码" onkeyup="value=value.replace(/[^0-9]/g,'')" />

			<input type="text" name="QQ" required="required" id="qq"
				placeholder="请输入qq账号" class="extext" value="${qq }"
				onkeyup="value=value.replace(/[^0-9]/g,'')" /> <input type="text"
				name="wechat" required="required" placeholder="请输入微信账号"
				value="${wechat }" id="wechat" onBlur="vilidate()" class="intext"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\@\.]/g,'')" />

			<input type="text" name="address" required="required"
				value="${address }" placeholder="请输入家庭地址" class="extext"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />

			<select name="qpwd">
				<option value="fName">您父亲的姓名</option>
				<option value="mName">您母亲的姓名</option>
				<option value="sName">您配偶的姓名</option>
			</select> <input type="text" name="qname" required="required" id="qname"
				value="${qname }" onBlur="vilidate()" placeholder="请输入密保信息"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
			<h5 class="fontSet">${msgqname }</h5>

			<input type="text" name="email" required="required" class="intext"
				id="email" value="${email }" placeholder="请输入电子邮箱"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\@\.]/g,'')" />
			<h5 class="fontSet">${msgemail }</h5>

			<input type="text" name="checkcode" class="userico"
				placeholder="请输入验证码"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
			<img src="/QGaccess/Checkcode" id="myimg" class="checkcode"
				onclick="change();" style="cursor: pointer;" />
			<h5 class="fontSet">${yzm }</h5>
			<input type="submit" value="提交" class="subico"
				onclick="return logic()" />
		</form>
	</div>

</body>
</html>