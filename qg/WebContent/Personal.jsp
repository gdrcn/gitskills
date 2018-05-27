<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="img/ie.ico" type="image/x-icon" />
<title>个人中心</title>
<link href="css/Personal.css?v=<%=System.currentTimeMillis()%>"
	rel="stylesheet">
<script type="text/javascript" src="js/Personal.js"></script>
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

	<br>
	<br>
	<br>
	<br>
	<h1>用户: ${user.userName }, 您好！</h1>

	<div class="middle">
		<input type="button" value="消息回复"
			onclick="javascrtpt:window.location.href='/QGaccess/PersonnalFunctionWeb?info=dope'"
			class="butico"> <input type="button" value="基本信息"
			onclick="javascrtpt:window.location.href='/QGaccess/PersonnalFunctionWeb?info=basic'"
			class="butico"> <input type="button" value="修改信息"
			onclick="javascrtpt:window.location.href='/QGaccess/PersonnalFunctionWeb?info=modify'"
			class="butico"><input type="button" value="申诉系统"
			onclick="javascrtpt:window.location.href='/QGaccess/PersonnalFunctionWeb?info=complaint'"
			class="butico"><input type="button" value="我的申诉"
			onclick="javascrtpt:window.location.href='/QGaccess/PersonnalFunctionWeb?info=myComplaint'"
			class="butico"><input type="button" value="聊天室"
			onclick="javascrtpt:window.location.href='/QGaccess/PersonnalFunctionWeb?info=chat'"
			class="butico">

	</div>

	<div id="person" class="mod">

		<c:if test="${not empty dope }">
			<c:forEach var="item" items="${myTrade }">
				<div class="thefont">

					<h5>订单id： ${item.id } 我评论内容： ${item.comment }</h5>
					<h5 style="color: red;">
						<c:if test="${not empty item.reply }">
							商家回复：${item.reply }
					</c:if>
					</h5>
					<h5 style="color: red;">
						<c:if test="${empty item.reply }">
							商家尚未回复
						</c:if>
					</h5>

				</div>
			</c:forEach>
		</c:if>

		<c:if test="${not empty basic }">
			<div class="mainmod">
				<c:if test="${not empty userBasic }">
					<c:forEach items="${sessionScope.userBasic }" var="item"
						varStatus="status">
						<h5>昵称：${item.userName }</h5>
						<h5>性别：${item.gender }</h5>
						<h5>手机号码：${item.phoneNumber }</h5>
						<h5>qq号：${item.qq }</h5>
						<h5>微信号：${item.wechat }</h5>
						<h5>地址：${item.address }</h5>
						<h5>电子邮箱：${item.email }</h5>
						<h5>积分：${item.integral }</h5>
						<h5>经验：${item.experience }</h5>
						<h5>用户等级：${item.degree }</h5>
					</c:forEach>
				</c:if>
				<c:if test="${not empty businessmanBasic }">
					<c:forEach items="${sessionScope.businessmanBasic }" var="item"
						varStatus="status">
						<h5>昵称：${item.userName }</h5>
						<h5>性别：${item.gender }</h5>
						<h5>手机号码：${item.phoneNumber }</h5>
						<h5>qq号：${item.qq }</h5>
						<h5>微信号：${item.wechat }</h5>
						<h5>地址：${item.address }</h5>
						<h5>电子邮箱：${item.email }</h5>
						<h5>积分：${item.integral }</h5>
						<h5>经验：${item.experience }</h5>
						<h5>用户等级：${item.degree }</h5>
						<h5>商铺id：${item.shop }</h5>
					</c:forEach>
				</c:if>
			</div>
		</c:if>

		<c:if test="${not empty modify }">
			<h3>修改信息</h3>
			<h5 style="color: red;">${msg }</h5>
			<form action="/QGaccess/PersonnalFunctionWeb?info=alter"
				method="post">
				<input type="text" name="userName" required="required"
					class="intext" onBlur="vilidate()" id="name"
					value="${newUserName }" placeholder="请输入修改的用户名"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
				<h5 style="color: red;">${msgusername }</h5>
				<input type="text" name="phoneNumber" required="required"
					class="intext" value="${newPhoneNumber }" id="num"
					placeholder="请输入修改的手机号码"
					onkeyup="value=value.replace(/[^0-9]/g,'')" />
				<h5 style="color: red;">${newQq }</h5>
				<input type="text" name="QQ" required="required" id="qq"
					placeholder="请输入修改的qq账号" class="intext" value="${qq }"
					onkeyup="value=value.replace(/[^0-9]/g,'')" />
				<h5 style="color: red;">${msgqq }</h5>
				<input type="text" name="wechat" required="required"
					placeholder="请输入修改的微信账号" value="${newWechat }" id="wechat"
					onBlur="vilidate()" class="intext"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\@\.]/g,'')" />
				<h5 style="color: red;">${msgwechat }</h5>
				<input type="text" name="email" required="required" class="intext"
					id="email" value="${newEmail }" placeholder="请输入修改的电子邮箱"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\@\.]/g,'')" />
				<h5 style="color: red;">${msgemail }</h5>
				<input type="submit" value="提交" class="aico"
					onclick="return logic()" />
			</form>
		</c:if>

		<c:if test="${not empty complaint }">
			<h3>交易维权</h3>
			<h3 style="color: red;">${msg }</h3>
			<form action="/QGaccess/BusinessAuthenticationWeb" method="post"
				enctype="multipart/form-data">
				<input type="text" name="tradeId" required="required"
					placeholder="请输入相应订单id号" class="intext"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\@\.]/g,'')" />
				<h3></h3>
				<h3 color="white">上传投诉相应记录</h3>
				<input type="file" name="file" value="file">
				<h3></h3>
				<input type="text" name="theComplaint" required="required"
					placeholder="请描述你的投诉请求" class="intext" value="${apay }"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
				<h3></h3>
				<input type="submit" value="提交" class="aico"
					onclick="return logic()" />
			</form>
		</c:if>

		<c:if test="${not empty myComplaint }">
			<div class="mod1">
				<c:forEach items="${sessionScope.allMyComplaint }" var="item"
					varStatus="status">
					<h5>我的图片证据</h5>
					<div>
						<img
							src="/QGaccess/ShowImageUtils?complaintId=${item.complaintId}"
							background-size=100% width='150px' height='80px'>
					</div>
					<h5>我的交易id：${item.id}</h5>
					<h5>申诉内容：${item.complaintTxt}</h5>
					<c:if test="${item.completed eq 'yes'}">
						<h5 style="color: red;">申诉已完成</h5>
					</c:if>
					<c:if test="${item.completed eq 'no'}">
						<h5 style="color: red;">申诉未完成</h5>
					</c:if>
				</c:forEach>
			</div>
		</c:if>

		<c:if test="${not empty chat }">
		<div class="table1">
				<div class="mod2">
				 <c:forEach items="${applicationScope.messages }" var="item"
					varStatus="status">
					游客 ：<c:out value="${item }"></c:out>	
					<br>	
				 </c:forEach>
				</div>
		
				<form action="/QGaccess/PersonnalFunctionWeb?info=realChat" method="post" name="chats">
				<br>
				<div>
					<textarea rows="10" cols="60" name="say"></textarea>
				</div>
				<input type="submit" value="发言" class="aico"
						onclick="return Judge()"></input>
				</form>
				
			</div>
		</c:if>
				
	</div>

</body>
</html>