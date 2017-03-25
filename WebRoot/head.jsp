<%@ page language="java" import="java.util.*,cn.tedu.domain.User" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
	<link rel="stylesheet" href="${app}/css/head.css"/>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
    <script type="text/javascript">
       function search(){
       	var val = document.getElementsByName("searchText")[0].value;
       	if(val!==""){
        window.location.href = "${app}${toProdPage}&name="+val;
       	}
       }
    
    </script>
  </head>
  <body>
	<div id="common_head">
		<div id="line1">
			<div id="content">
			<c:if test="${sessionScope.user.username==null}" var="flag">
				<a href="${app}/login.jsp">登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${app}/regist.jsp">注册</a>
			</c:if>
		    <c:if test="${!flag}">
			欢迎回来, ${sessionScope.user.username}
			<a href="${app}/servlet/Logout">注销</a>
			<a href="${app}/back/manage.jsp">后台管理</a>
		    </c:if>
			</div>
		</div>
		<div id="line2">
			<img id="logo" src="${app}/img/head/logo.jpg"/>
			<input type="text" name="searchText"/>
			<input type="button" value="搜索" onclick="search()"/>
			<span id="goto">
				<a id="goto_order" href="${app}/servlet/OrderList">我的订单</a>
				<a id="goto_cart" href="${app}/cart.jsp">我的购物车</a>
			</span>
			<img id="erwm" src="${app}/img/head/qr.jpg"/>
		</div>
		<div id="line3">
		 <div id="content">
		  <ul>
			<li><a href="${app}/index.jsp">首页</a></li>
			<li><a href="${app}${toProdPage}">全部商品</a></li>
			<li><a href="${app}${toProdPage}&category=电子数码">电子数码</a></li>
			<li><a href="${app}${toProdPage}&category=电脑平板">电脑平板</a></li>
			<li><a href="${app}${toProdPage}&category=家用电器">家用电器</a></li>
			<li><a href="${app}${toProdPage}&category=汽车用品">汽车用品</a></li>
			<li><a href="${app}${toProdPage}&category=食品饮料">食品饮料</a></li>
			<li><a href="${app}${toProdPage}&category=图书杂志">图书杂志</a></li>
			<li><a href="${app}${toProdPage}&category=服装服饰">服装服饰</a></li>
			<li><a href="${app}${toProdPage}&category=日用百货">日用百货</a></li>
		  </ul>
		 </div>
		</div>
	</div>
  </body>
</html>