<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="img/ie.ico" type="image/x-icon" />
<title>我的购物车</title>
<link href="css/BuyCar.css?v=<%=System.currentTimeMillis()%>"
	rel="stylesheet">
<script type="text/javascript" src="js/BuyCar.js"></script>
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
		<input type="button" value="我的购物车"
			onclick="javascrtpt:window.location.href='/QGaccess/BuyCarWeb?info=myBuyCar'"
			class="butico"> <input type="button" value="正进行中"
			onclick="javascrtpt:window.location.href='/QGaccess/BuyCarWeb?info=carry'"
			class="butico"> <input type="button" value="购买记录"
			onclick="javascrtpt:window.location.href='/QGaccess/BuyCarWeb?info=purchased'"
			class="butico"> <input type="button" value="评价商家"
			onclick="javascrtpt:window.location.href='/QGaccess/BuyCarWeb?info=assess'"
			class="butico">
	</div>

	<div id="Goods" class="mod">
		<c:if test="${not empty myGoods }">
			<h3 style="color: red;">${msg1 }</h3>
			<c:forEach items='${sessionScope.myGoodsCar }' var="item"
				varStatus='status'>
				<div class="mod1">
					<form
						action="/QGaccess/BuyCarWeb?goodsId=${item.theGoodsId }&myId=${item.myId }&sign=auditing&buyCarId=${item.id }"
						method="post">
						<img src='/QGaccess/ShowImageUtils?goodsId=${item.theGoodsId }'
							background-size=100% width='150px' height='80px'>
						<h5>名称：${item.theGoodsName}</h5>
						<h5>编号:${item.theGoodsId }</h5>
						<h5>价格:${item.theGoodsPrice }</h5>
						<h5>￥ 购买数量:${item.myBuyNum}</h5>
						<h5>当前积分：${item.integral }</h5>
						<select name="discount">
							<option value="none">无</option>
							<option value="discount1">1000积分抵10元</option>
							<option value="discount2">5000积分抵50元</option>
							<option value="discount3">10000积分抵100元</option>
						</select> 
						<h5 style="color: red;">${msg }</h5>
						<input type="submit" value="确定下单" class="subico1">
						<h5>
							<a href="/QGaccess/BuyCarWeb?sign=remove&buyCarId=${item.id }"
								class="aico">移出购物车</a>
						</h5>
					</form>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${not empty carry }">
			<h5 style="color: red;">${msg }</h5>
			<c:forEach items='${sessionScope.myGoodsCar }' var="item"
				varStatus='status'>
				<div class="mymod">
					<tr>
						<td><img
							src='/QGaccess/ShowImageUtils?goodsId=${item.theGoodsId }'
							background-size=100% width='150px' height='80px'></td>
						<td class='center'><span class='center'>名称：${item.theGoodsName}</span></td>
						<td>编号:${item.theGoodsId }&nbsp;&nbsp;</td>
						<td>价格:${item.theGoodsPrice }￥&nbsp;&nbsp;</td>
						<td>购买数量:${item.myBuyNum}&nbsp;&nbsp;</td>
						<td>商家编号:${item.theShopId}&nbsp;&nbsp;</td>
						<td><a
							href="/QGaccess/BuyCarWeb?sign=fail&buyCarId=${item.id }"
							class="aico">取消订单</a></td>
					</tr>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${not empty purchased }">
			<h3 style="color: red;">${msg }</h3>
			<c:forEach items='${sessionScope.myGoodsCar }' var="item"
				varStatus='status'>
				<div class="mymod">
					<tr>
						<c:if test="${item.situation == 'success' }">已收货</c:if>
						<c:if test="${item.situation == 'fail' }">已取消</c:if>
						<c:if test="${item.situation == 'sFail' }">商家取消</c:if>
						<c:if test="${item.situation == 'evaluated' }">已评价</c:if>
						<td><img
							src='/QGaccess/ShowImageUtils?goodsId=${item.theGoodsId }'
							background-size=100% width='150px' height='80px'></td>
						<td class='center'><span class='center'>名称：${item.theGoodsName}</span></td>
						<td>编号:${item.theGoodsId }&nbsp;&nbsp;</td>
						<td>价格:${item.theGoodsPrice }￥&nbsp;&nbsp;</td>
						<td>购买数量:${item.myBuyNum}&nbsp;&nbsp;</td>
						<td>商家编号:${item.theShopId}&nbsp;&nbsp;</td>
						<c:if test="${item.situation == 'delivery' }">
							<td><a
								href="/QGaccess/BuyCarWeb?buyCarId=${item.id }&sign=success"
								class="aico">确定收货</a></td>
						</c:if>
					</tr>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${not empty assess }">
			<h3 style="color: red;">${msg }</h3>
			<c:forEach items='${sessionScope.myGoodsCar }' var="item"
				varStatus='status'>
				<div class="mod1">
					<img src='/QGaccess/ShowImageUtils?goodsId=${item.theGoodsId }'
						background-size=100% width='150px' height='80px'>
					<h5>名称：${item.theGoodsName}</h5>
					<h5>编号:${item.theGoodsId }</h5>
					<h5>价格:${item.theGoodsPrice }￥</h5>
					<h5>购买数量:${item.myBuyNum}</h5>
					<h5>商家编号:${item.theShopId}</h5>
					<form action="/QGaccess/BuyCarWeb?buyCarId=${item.id }&sign=alter"
						method="post">
						<input type="text" name="assessTxt" required="required"
							id="assessTxt" placeholder="请输入评价内容" class="extext" /> <select
							name="star">
							<option value="five">五星</option>
							<option value="four">四星</option>
							<option value="three">三星</option>
							<option value="two">二星</option>
							<option value="one">一星</option>
							<option value="zero">无星</option>
						</select> <input type="submit" value="评价" class="subico" />
					</form>
				</div>
			</c:forEach>
		</c:if>
	</div>

</body>
</html>