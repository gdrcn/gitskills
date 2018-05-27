<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="img/ie.ico" type="image/x-icon" />
<title>管理员功能页面</title>
<link
	href="css/AdministratorFunction.css?v=<%=System.currentTimeMillis()%>"
	rel="stylesheet">
<script type="text/javascript" src="js/AdministratorFunction.js"></script>
</head>
<body>
	<div class="header">
		<div class="hea_left">
			<a href="/QGaccess/ShoppingMall.jsp"><img src="img/shop.png"
				class="logo" width="50" height="50" /></a>
			<ul>
				<li><a href="/QGaccess/FindGoods?search=all">首页</a></li>
				<c:if test="${not empty user}">
					<li><a href="/QGaccess/Personal.jsp">个人中心</a></li>
					<li><a href="#">业务功能</a>
						<ul>
							<c:if test="${empty businessman }">
								<li><a href="/QGaccess/BusinessAuthentication.jsp">前去开店</a></li>
							</c:if>
							<c:if test="${user.role==1 || user.role==0 }">
								<li><a href="/QGaccess/AdministratorFunction.jsp">管理员功能</a></li>
							</c:if>
							<c:if test="${not empty businessman }">
								<li><a href="/QGaccess/ShowGoods?tips=show">我的店铺</a></li>
							</c:if>
							<li><a href="#"></a></li>
						</ul>
				</c:if>
			</ul>
		</div>

		<div class="hea_right">
			<c:if test="${empty user}">
				<a href="/QGaccess/Register.jsp"> 注 册 </a>  |  
					<a href="/QGaccess/StartLogin.jsp"> 登 录 </a>
			</c:if>

			<c:if test="${not empty user}">
				<div>
					用户:${user.userName } | <a href="/QGaccess/LoginOutWeb"> 注销 </a> <a
						href="/QGaccess/BuyCarWeb?info=myBuyCar"> <img
						src="img/buycar.ico" class="logo" width="30" height="30" /> <span>${sessionScope.userBuyCarNum }件</span>
					</a>
				</div>
			</c:if>
		</div>
	</div>

	<div class="middle">
		<input type="button" value="管理用户"
			onclick="javascrtpt:window.location.href='/QGaccess/AdministratorWeb?info=allUser'"
			class="butico"> <input type="button" value="管理商家"
			onclick="javascrtpt:window.location.href='/QGaccess/AdministratorWeb?info=allShop'"
			class="butico"> <input type="button" value="查看申诉"
			onclick="javascrtpt:window.location.href='/QGaccess/AdministratorWeb?info=check'"
			class="butico">
	</div>

	<div id="Goods" class="mod">
		<h3 style="color: red;">${msg }</h3>
		<c:if test="${not empty allUser }">
			<c:forEach items='${sessionScope.allShow }' var="item"
				varStatus='status'>
				<div class="mymod">
					<tr>
						<td>用户名:${item.userName }</td>
						<td>用户id:${item.id }&nbsp;&nbsp;</td>
						<td>用户性别:${item.gender }&nbsp;&nbsp;</td>
						<td>用户手机:${item.phoneNumber}&nbsp;&nbsp;</td>
						<td>用户qq:${item.qq}&nbsp;&nbsp;</td>
						<td>用户微信:${item.wechat}&nbsp;&nbsp;</td>
						<td>用户地址:${item.address}&nbsp;&nbsp;</td>
						<td>用户邮箱:${item.email}&nbsp;&nbsp;</td>
						<td><a
							href="/QGaccess/AdministratorWeb?sign=upgrade&hisId=${item.id }"
							class="aico">升为管理员</a></td>
						<td><a
							href="/QGaccess/AdministratorWeb?sign=delete&hisId=${item.id }"
							class="aico">删除用户</a></td>
					</tr>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${not empty allShop }">

			<c:forEach items='${sessionScope.notPassList }' var="item"
				varStatus='status'>

				<div class="mymod">
					<tr>
						<td><img src="/QGaccess/ShowImageUtils?userId=${item.id}"
							background-size=100% width='150px' height='80px'></td>
						<td>用户id: ${item.id }</td>
						<td>用户身份证: ${item.identification }&nbsp;&nbsp;</td>
						<td>用户支付宝账号: ${item.alipay }&nbsp;&nbsp;</td>
						<td><a
							href="/QGaccess/AdministratorWeb?userId=${item.id}&sign=success"
							class="aico">审核通过</a></td>
						<td><a
							href="/QGaccess/AdministratorWeb?userId=${item.id}&sign=fail"
							class="aico">审核不通过</a></td>
					</tr>
				</div>
			</c:forEach>

			<c:forEach items='${sessionScope.passList }' var="item"
				varStatus='status'>

				<div class="mymod">
					<tr>
						<td><img src="/QGaccess/ShowImageUtils?userId=${item.id}"
							background-size=100% width='150px' height='80px'></td>
						<td>用户id: ${item.id }</td>
						<td>用户身份证: ${item.identification }&nbsp;&nbsp;</td>
						<td>用户支付宝账号: ${item.alipay }&nbsp;&nbsp;</td>
						<td><a
							href="/QGaccess/AdministratorWeb?userId=${item.id}&sign=close"
							class="aico">关闭店铺</a></td>
					</tr>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${not empty check }">
			<div class="mod1">
			<c:forEach items='${sessionScope.allComplaint }' var="item"
				varStatus='status'>
					<h5>图片证据</h5><div>
					<img src="/QGaccess/ShowImageUtils?complaintId=${item.complaintId}"
						background-size=100% width='150px' height='80px'></div>
						<h5>申诉交易id：${item.id}</h5>
						<h5>申诉人id：${item.userId}</h5>
						<h5>申诉内容：${item.complaintTxt}</h5>
					<h5><a href="/QGaccess/AdministratorWeb?sign=complete&complaintId=${item.complaintId}"
						class="aico">完成申述</a></h5>
				</c:forEach>
			</div>
		</c:if>

	</div>

</body>
</html>