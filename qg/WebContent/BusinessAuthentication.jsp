<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="img/ie.ico" type="image/x-icon" />
<title>注册商铺</title>
<link href="css/BusinessAuthentication.css?v=<%=System.currentTimeMillis()%>"
	rel="stylesheet">
<script type="text/javascript" src="js/BusinessAuthentication.js"></script>

</head>
<body background="img/bj5.jpg" class="scaleimg">

	<div class="middle">

		<div id="header">
			<h1 white-space="pre-wrap">注 册 商 铺</h1>
		</div>

		<form action="/QGaccess/BusinessAuthenticationWeb" method="post"
			enctype="multipart/form-data">

			<input type="text" name="id" required="required" id="id"
				placeholder="请输入身份证号" class="intext" value="${realId }"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\@\.]/g,'')" />
			<h3 style="color: red;">${msgid }</h3>

			<h3 color="white">上传手持证件照</h3>
			<input type="file" name="file" value="file">
			<h3 style="color: red;">${msgfile }</h3>
			
			<input type="text" name="apay" required="required" id="apay"
				placeholder="请输入支付宝账号" class="intext" value="${apay }"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\@\.]/g,'')" />
			<h3 style="color: red;">${msgapay }</h3>
			
			<input type="text" name="checkcode" class="userico"
				placeholder="请输入验证码"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
			<img src="/QGaccess/Checkcode" id="myimg" class="checkcode"
				onclick="change();" style="cursor: pointer;" />
			<h3 class="fontSet">${yzm }</h3>
			
			<div style="text-align: center;">
				<input type="submit" value="下一步" onclick="return logic()"
					class="subico" />
			</div>
		</form>
	</div>
</body>
</html>