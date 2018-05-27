<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="img/ie.ico" type="image/x-icon" />
<title>商城首页</title>
<link href="css/ShoppingMall.css?v=<%=System.currentTimeMillis()%>"
	rel="stylesheet">
<script type="text/javascript" src="js/ShoppingMall.js"></script>

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

	<div class="leftHeadline">
		<h5 class="font">商品分类</h5>
		<ul>
			<li><a href="/QGaccess/FindGoods?search=clothing">服装配饰</a></li>
			<li><a href="/QGaccess/FindGoods?search=drinks">零食饮料</a></li>
			<li><a href="/QGaccess/FindGoods?search=fruits">生鲜水果</a></li>
			<li><a href="/QGaccess/FindGoods?search=books">书籍玩具</a></li>
			<li><a href="/QGaccess/FindGoods?search=guess">猜你喜欢</a></li>
		</ul>
	</div>
	<div class="line"></div>
	<div class="search">
		<h3 style="color: red;">${msg }</h3>
		<form action="/QGaccess/FindGoods" method="post">
			<input type="text" name="someSearch" class="searchtxt"
				placeholder="搜索商品" id="searching"
				onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" />
			<input type="submit" value="搜索" class="searchico"
				onclick="return logic()" />
		</form>
	</div>

	<div class="rightHeadline">
		<c:forEach items="${sessionScope.goods }" var="item"
			varStatus="status">
			<div class="mod">
				<img src="/QGaccess/ShowImageUtils?goodsId=${item.goodsId }"
					class="alrtimg">
				<h4 class="price">￥ : ${item.price }</h4>
				<h4>[店家编号:${item.shopId}] ：${item.goodsName }</h4>
				<h5>销量${item.salesVolume }</h5>
				<a href="/QGaccess/BuyGoodsWeb?goodsId=${item.goodsId }"
					class="color">购买</a>

			</div>
		</c:forEach>
	</div>
	<c:if test="${not empty all}">
		<div class="absolutePositioning">
			共 ${pagenum}页 当前 第${page}页
			<c:choose>
				<c:when test="${page>1}">
					<a href="/QGaccess/FindGoods?page=${page-1}&search=all"><input type="button"
						value="上一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="上一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${page!=pagenum}">
					<a href="/QGaccess/FindGoods?page=${page+1}&search=all"><input type="button"
						value="下一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="下一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
		</div>
		</c:if>
		
		<c:if test="${not empty clothing}">
		<div class="absolutePositioning">
			共 ${pagenum1 }页 当前 第${page1 }页
			<c:choose>
				<c:when test="${page1 > 1}">
					<a href="/QGaccess/FindGoods?page1=${page1 - 1}&search=clothing"><input type="button"
						value="上一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="上一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${page1 != pagenum1 }">
					<a href="/QGaccess/FindGoods?page1=${page1+1}&search=clothing"><input type="button"
						value="下一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="下一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
		</div>
		</c:if>
		
	<c:if test="${not empty drinks}">
		<div class="absolutePositioning">
			共 ${pagenum2 }页 当前 第${page2 }页
			<c:choose>
				<c:when test="${page2 > 1}">
					<a href="/QGaccess/FindGoods?page2=${page2 - 1}&search=drinks"><input type="button"
						value="上一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="上一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${page2 != pagenum2 }">
					<a href="/QGaccess/FindGoods?page2=${page2+1}&search=drinks"><input type="button"
						value="下一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="下一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
		</div>
		</c:if>
		
		<c:if test="${not empty fruits}">
		<div class="absolutePositioning">
			共 ${pagenum3 }页 当前 第${page3 }页
			<c:choose>
				<c:when test="${page3 > 1}">
					<a href="/QGaccess/FindGoods?page3=${page3 - 1}&search=fruits"><input type="button"
						value="上一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="上一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${page3 != pagenum3 }">
					<a href="/QGaccess/FindGoods?page3=${page3+1}&search=fruits"><input type="button"
						value="下一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="下一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
		</div>
		</c:if>
		
		<c:if test="${not empty books}">
		<div class="absolutePositioning">
			共 ${pagenum4 }页 当前 第${page4 }页
			<c:choose>
				<c:when test="${page4 > 1}">
					<a href="/QGaccess/FindGoods?page4=${page4 - 1}&search=books"><input type="button"
						value="上一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="上一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${page4 != pagenum4 }">
					<a href="/QGaccess/FindGoods?page4=${page4+1}&search=books"><input type="button"
						value="下一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="下一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
		</div>
		</c:if>
		
	<c:if test="${not empty books}">
		<div class="absolutePositioning">
			共 ${pagenum4 }页 当前 第${page4 }页
			<c:choose>
				<c:when test="${page4 > 1}">
					<a href="/QGaccess/FindGoods?page4=${page4 - 1}&search=books"><input type="button"
						value="上一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="上一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${page4 != pagenum4 }">
					<a href="/QGaccess/FindGoods?page4=${page4+1}&search=books"><input type="button"
						value="下一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="下一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
		</div>
		</c:if>
		
		<c:if test="${not empty guess}">
		<div class="absolutePositioning">
			共 ${pagenum5 }页 当前 第${page5 }页
			<c:choose>
				<c:when test="${page5 > 1}">
					<a href="/QGaccess/FindGoods?page5=${page5 - 1}&search=guess"><input type="button"
						value="上一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="上一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${page5 != pagenum5 }">
					<a href="/QGaccess/FindGoods?page5=${page5+1}&search=guess"><input type="button"
						value="下一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" value="下一页" disabled="disabled" />
				</c:otherwise>
			</c:choose>
		</div>
		</c:if>
</body>
</html>