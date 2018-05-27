<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="img/ie.ico" type="image/x-icon" />
<title>商品信息</title>
<link
	href="css/CommodityInformation.css?v=<%=System.currentTimeMillis()%>"
	rel="stylesheet">
<script type="text/javascript" src="js/CommodityInformation.js"></script>
</head>
<body>
<embed src="music/mu.wav" loop="100" autostar="true" hidden="true" controls="controls"></embed> 
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

	<div>
		<img src="/QGaccess/ShowImageUtils?goodsId=${thisGoods.goodsId }"
			class="alrtimg">
	</div>

	<div class="goodsmod">
		<h5>[IT商城] : IT出品 [${thisGoods.tags }] [${thisGoods.function }]
			[店家编号：${thisGoods.shopId }] [好评度：${thisGoods.starLevel }]</h5>
		<div class="pricemod">
			商品价格：${thisGoods.price }￥
              <h5></h5>
			<c:if test="${thisGoods.discount eq 'discount1' }">
                            商品优惠：商品九折
				</c:if>
			<c:if test="${thisGoods.discount eq 'discount2' }">
                              商品优惠：满200减20
				</c:if>

			<c:if test="${thisGoods.discount eq 'none'  }">
		 最近无优惠活动
		 </c:if>

		</div>
		<div class="font">宝贝剩余件数：${thisGoods.num }</div>
		<div class="tform">
			<form action="/QGaccess/AddBuyCar?&discount=${thisGoods.discount }" method="post">
				<input type="text" name="buyNum" required="required" id="buyNum"
					placeholder="请输入购买件数" class="intext" value="${buyNum }"
					onkeyup="value=value.replace(/[^0-9]/g,'')" />
				<h3 style="color: red;">${msgNum }</h3>
				<input type="text" name="phone" required="required" id="phone"
					placeholder="请输入手机号码" class="intext" value="${phone }"
					onkeyup="value=value.replace(/[^0-9]/g,'')" />
				<h3 style="color: red;">${msgphone }</h3>
				<input type="text" name="apay" required="required" id="apay"
					placeholder="请输入支付宝账号" class="intext" value="${apay }"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\@\.]/g,'')" />
				<h3 style="color: red;">${msgapay }</h3>
				<input type="text" name="address" required="required" id="address"
					value="${address }" placeholder="请输入收件地址" class="intext"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
				<h3 style="color: red;">${msgaddress }</h3>
				<input type="submit" value="加入购物车" class="subico"
					onclick="return logic()" />
				<h3 style="color: red;">${msg }</h3>
			</form>
		</div>
	</div>

	<div class="evaluatemod">
		<h2>评论区</h2>
		<c:forEach var="item" items="${allComment }">
			<div class="font1">

				用户： ${item.userName } 评论内容： ${item.comment }
				<c:if test="${not empty item.reply }">
					<div class="thefont">商家回复：${item.reply }</div>
				</c:if>
				<c:if test="${empty item.reply }">
					<div class="thefont">商家尚未回复</div>
				</c:if>
			</div>
		</c:forEach>
	</div>

</body>
</html>