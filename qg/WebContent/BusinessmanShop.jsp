<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="img/ie.ico" type="image/x-icon" />
<title>店铺管理</title>
<link href="css/BusinessmanShop.css?v=<%=System.currentTimeMillis()%>"
	rel="stylesheet">
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
	<h1>商家: ${user.userName }, 您好！</h1>
	<!-- 通过按钮实现 -->
	<div class="middle">
		<input type="button" value="我的商品"
			onclick="javascrtpt:window.location.href='/QGaccess/ShowGoods?tips=show'"
			class="butico"> <input type="button" value="添加商品"
			onclick="javascrtpt:window.location.href='/QGaccess/ShowGoods?tips=add'"
			class="butico"> <input type="button" value="修改商品"
			onclick="javascrtpt:window.location.href='/QGaccess/ShowGoods?tips=modify'"
			class="butico"> <input type="button" value="用户评价"
			onclick="javascrtpt:window.location.href='/QGaccess/ShowGoods?tips=userReviews'"
			class="butico"><input type="button" value="我的订单"
			onclick="javascrtpt:window.location.href='/QGaccess/ShowGoods?tips=manage'"
			class="butico"><input type="button" value="修改订单"
			onclick="javascrtpt:window.location.href='/QGaccess/ShowGoods?tips=alter'"
			class="butico">
	</div>

	<div id="addGoods" class="mod">

		<c:if test="${not empty show}">
			<c:forEach items='${sessionScope.myGoods }' var="item"
				varStatus='status'>
				<div class="mymod">
					<tr>
						<td><img
							src="/QGaccess/ShowImageUtils?goodsId=${item.goodsId }"
							background-size=100% width='150px' height='80px'></td>
						<td class='center'><span class='center'>名称：${item.goodsName }</span></td>
						<td>编号:${item.goodsId }&nbsp;&nbsp;</td>
						<td>标签: ${item.tags}&nbsp;&nbsp;</td>
						<td>价格:${item.price }￥&nbsp;&nbsp;</td>
						<td>功能:${item.function }&nbsp;&nbsp;</td>
						<td>剩余数量:${item.num}&nbsp;&nbsp;</td>
					</tr>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${not empty add}">
			<form action="/QGaccess/GoodsWeb" method="post"
				enctype="multipart/form-data">
				<div>
					<span id="tags"></span><b>商品类型: </b> <input type="radio"
						name="goodsTags" value="服装配饰" checked />服装配饰<input type="radio"
						name="goodsTags" value="零食饮料" />零食饮料<input type="radio"
						name="goodsTags" value="生鲜水果" />生鲜水果<input type="radio"
						name="goodsTags" value="书籍玩具" />书籍玩具
				</div>
				<h5></h5>
				<input type="text" name="goodsName" size="20" id="price"
					value="${goodsname}" class="intext" placeholder="请输入商品名称"
					required="required" />
				<h5 class="fontSet">${msgname}</h5>
				<input type="text" name="price" size="20" id="price"
					value="${price}" class="intext" placeholder="请输入商品价格"
					required="required" />
				<h5 class="fontSet">${msgprice}</h5>
				<h3 color="white">上传商品照片</h3>
				<input type="file" name="file" value="file">
				<h3 style="color: red;">${msgfile}</h3>
				<input type="text" name="num" size="20" id="num" value="${num}"
					class="intext" placeholder="请输入商品数量" required="required" />
				<h5 class="fontSet">${msgnum}</h5>
				<input type="text" name="function" required="required" id="fun"
					value="${function}" placeholder="请输入商品功能信息" class="extext" />
					<h5></h5>
				<div><select name="discount">
					<option value="none">无</option>
					<option value="discount1">九折</option>
					<option value="discount2">满200减20</option>
				</select>
				</div>
				<div style="text-align: center;">
					<h5 class="fontSet">${msgfun}</h5>
					<input type="submit" value="添加" class="aico1"
						onclick="return logic()" />
					<h5 class="fontSet">${msg}</h5>
				</div>
			</form>
		</c:if>

		<c:if test="${not empty manage}">
			<c:forEach items='${sessionScope.myGoods }' var="item"
				varStatus='status'>
				<div class="mymod">
					<tr>
						<td><img
							src='/QGaccess/ShowImageUtils?goodsId=${item.theGoodsId }'
							background-size=100% width='150px' height='80px'></td>
						<td class='center'><span class='center'>客户ID：${item.myId}&nbsp;&nbsp;</span></td>
						<td>客户地址:${item.myAddress }&nbsp;&nbsp;</td>
						<td>客户支付宝: ${item.myAlipay}&nbsp;&nbsp;</td>
						<td>客户购买数量:${item.myBuyNum }&nbsp;&nbsp;</td>
						<td>客户手机:${item.myPhoneNumber }&nbsp;&nbsp;</td>
						<td><a
							href='/QGaccess/ShowGoods?tips=pass&buyCarId=${item.id }&goodsId=${item.theGoodsId }&buyNum=${item.myBuyNum }'
							class="aico">通过</a></td>
						<td><a
							href='/QGaccess/ShowGoods?tips=refuse&buyCarId=${item.id }&goodsId=${item.theGoodsId }&buyNum=${item.myBuyNum }'
							class="aico">拒绝</a></td>
					</tr>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${not empty modify}">
			<form action="/QGaccess/GoodsWeb" method="post"
				enctype="multipart/form-data">
				<h3 style="color: red;">修改商品</h3>
				<input type="text" name="rGoodsId" size="20" id="goodsid"
					value="${rGoodsId }" class="intext" placeholder="请输入原商品id"
					required="required" />
				<h5 class="fontSet">${rmsgid }</h5>
				<div>
					<span id="tags"></span><b>商品类型: </b> <input type="radio"
						name="rGoodsTags" value="服装配饰" checked />服装配饰<input type="radio"
						name="rGoodsTags" value="零食饮料" />零食饮料<input type="radio"
						name="rGoodsTags" value="生鲜水果" />生鲜水果<input type="radio"
						name="rGoodsTags" value="书籍玩具" />书籍玩具
				</div>
				<h5></h5>
				<input type="text" name="rGoodsName" size="20" id="price"
					value="${rGoodsName}" class="intext" placeholder="请输入商品名称"
					required="required" />
				<h5 class="fontSet">${rmsgname}</h5>
				<input type="text" name="rPrice" size="20" id="price"
					value="${rPrice}" class="intext" placeholder="请输入商品价格"
					required="required" />
				<h5 class="fontSet">${rmsgprice}</h5>
				<h3 color="white">上传商品照片</h3>
				<input type="file" name="file" value="file">
				<h3 style="color: red;">${msgfile}</h3>
				<input type="text" name="rNum" size="20" id="num" value="${rNum}"
					class="intext" placeholder="请输入商品数量" required="required" />
				<h5 class="fontSet">${rmsgnum}</h5>
				<input type="text" name="rFunction" required="required" id="fun"
					value="${rFunction}" placeholder="请输入商品功能信息" class="extext" /> 
				<div style="text-align: center;">
					<h5 class="fontSet">${rmsgfun}</h5>
					<input type="submit" value="修改" class="aico"
						onclick="return logic()" />
					<h5 class="fontSet">${rmsg}</h5>
				</div>
			</form>
		</c:if>

		<c:if test="${not empty alter}">
			<form action="/QGaccess/AddBuyCar?info=alter" method="post">
				<input type="text" name="alterGoodsid" required="required"
					id="alterGoodsid" placeholder="请输入修改的订单id" class="intext"
					onkeyup="value=value.replace(/[^0-9]/g,'')" />
				<h3 style="color: red;">${msgid }</h3>
				<input type="text" name="buyNum" required="required" id="buyNum"
					placeholder="请输入修改件数" class="intext" value="${buyNum }"
					onkeyup="value=value.replace(/[^0-9]/g,'')" />
				<h3 style="color: red;">${msgNum }</h3>
				<input type="text" name="phone" required="required" id="phone"
					placeholder="请输入修改手机号码" class="intext" value="${phone }"
					onkeyup="value=value.replace(/[^0-9]/g,'')" />
				<h3 style="color: red;">${msgphone }</h3>
				<input type="text" name="apay" required="required" id="apay"
					placeholder="请输入修改支付宝账号" class="intext" value="${apay }"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\@\.]/g,'')" />
				<h3 style="color: red;">${msgapay }</h3>
				<input type="text" name="address" required="required" id="address"
					value="${address }" placeholder="请输入收件地址" class="intext"
					onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
				<h3 style="color: red;">${msgaddress }</h3>
				<input type="submit" value="修改" class="aico"
					onclick="return logic()" />
				<h3 style="color: red;">${msg }</h3>
			</form>
		</c:if>

		<c:if test="${not empty userReviews}">
			<h5 class="fontSet">${msg }</h5>
			<c:forEach var="item" items="${allComment }">
				<div class="mod1">
					<form
						action="/QGaccess/ShowGoods?tips=reply&theTradeId=${item.id }"
						method="post">
						<h5>评论用户 ：${item.userName }</h5> 
						<h5>评论内容： ${item.comment }</h5>
						<c:if test="${empty item.reply }">
							<input type="text" name="reply" required="required" id="reply"
								placeholder="请输入回复内容"
								onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
							<input type="submit" value="回复" class="aico1">
						</c:if>
						<c:if test="${not empty item.reply }">
							<h5 style="color: red;">商家回复：${item.reply }</h5>
						</c:if>
					</form>
				</div>
			</c:forEach>
		</c:if>
	</div>
</body>
</html>