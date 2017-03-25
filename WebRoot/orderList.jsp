<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link href="${app}/css/orderList.css" rel="stylesheet" type="text/css">
</head>
 <%@include file="/head.jsp" %>
<body style="text-align:center;">
     <br>${requestScope.msg}
     <c:forEach items="${requestScope.orderMaps}" var="outEntry">
		<dl class="Order_information">
			<dt>
				<h3>订单信息</h3>
			</dt>
			<dd>
				订单编号：${outEntry.key.id}<br/>
				 下单时间：${outEntry.key.ordertime}<br /> 
				 订单金额：${outEntry.key.money}<br /> 
				 支付状态：
				 <c:if test="${outEntry.key.paystate==1}">
					<font color="blue">已支付</font>
				 </c:if>
				 <c:if test="${outEntry.key.paystate==0}">
				 	<font color="blue">未支付</font>
				 </c:if>&nbsp;&nbsp;&nbsp;
					<a href="${app}/servlet/DelOrderMapByOrderId?id=${outEntry.key.id}">
					 <img src="${app}/img/orderList/sc.jpg" width="69" height="19"></a> 
			 		<a href="${app}/pay.jsp?id=${outEntry.key.id}&money=${outEntry.key.money}"> 
			 		 <img src="${app}/img/orderList/zx.jpg" width="69" height="19"></a><br/> 
				 所属用户：${sessionScope.user.username}<br/> 
				 收货地址：${outEntry.key.receiverAddress}<br/> 
				支付方式：在线支付
			</dd>
		</dl>
	
		<table width="1200" border="0" cellpadding="0"
			cellspacing="1" style="background:#d8d8d8;color:#333333">
			<tr>
				<th width="276" height="30" align="center" valign="middle" bgcolor="#f3f3f3">商品图片</th>
				<th width="247" align="center" valign="middle" bgcolor="#f3f3f3">商品名称</th>
				<th width="231" align="center" valign="middle" bgcolor="#f3f3f3">商品单价</th>
				<th width="214" align="center" valign="middle" bgcolor="#f3f3f3">购买数量</th>
				<th width="232" align="center" valign="middle" bgcolor="#f3f3f3">总价</th>
			</tr>
		  <c:set var="money" scope="page" value="0"></c:set>
		  <c:forEach items="${outEntry.value}" var="entry">
			<tr>
				<td align="center" valign="middle" bgcolor="#FFFFFF">
					<img src="${app}/servlet/ProdImg?id=${entry.key.id}" width="90" height="105">
				</td>
				<td align="center" valign="middle" bgcolor="#FFFFFF">${entry.key.name}</td>
				<td align="center" valign="middle" bgcolor="#FFFFFF">${entry.key.price}</td>
				<td align="center" valign="middle" bgcolor="#FFFFFF">${entry.value}</td>
				<td align="center" valign="middle" bgcolor="#FFFFFF">${entry.key.price*entry.value}</td>
			</tr>
			<c:set var="money" scope="page" value="${money+entry.key.price*entry.value}"></c:set>
		  </c:forEach>
		</table>
		 <div class="Order_price"> <font color="black" size="5px">订单总价:</font>${money}</div>
	</c:forEach>
		<%@include file="/foot.jsp" %>
</body>
</html>
