<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP page</title>
    <link href="${app}/css/cart.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${app}/js/cart.js"></script>
    <script type="text/javascript" src="${app}/js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
    
    /* Math.formatFloat = function(f, digit) { 
    var m = Math.pow(10, digit); 
    return parseInt(f * m, 10) / m; 
	} 解决精度问题
	
	var numA = 0.1; 
	var numB = 0.2;
	alert(Math.formatFloat(numA + numB, 1) === 0.3); */
    
    	function changeNum(i,id){
		   //i为+1或者是-1
		   var val = $("input[name='"+id+"_num']").val();
		   var j = val*1;//输入框的数值j
		   if(j==1&&i==-1){
		      i=0;
		   }
		   var newBuyNum = i+j;//输入框改变后的数值 
		 $("input[name='"+id+"_num']").val(i+j);
	   	 var price = $("span[class='"+id+"_price']").text();
	     var money = (newBuyNum*price).toFixed(1);
	    // alert(money.toFixed(2));
		 $("span[class='"+id+"_money']").text(money);
		 //alert(Number($("#span_2").text()));//无法转化为double类型??????
	     var total_money =  ($("#span_2").text()*1+i*price*1).toFixed(1);
		 $("#span_2").text(total_money);	
		 	
		 $.get("${app}/servlet/UpdateCart",{"id":id,"newBuyNum":newBuyNum})
		   return false;
		}
		
		function updateNum(obj,id,oldBuyNum,pnum){
			var newBuyNum=obj.value;
			var reg = /^[1-9][0-9]*$/;
			if(!reg.test(newBuyNum)||newBuyNum>pnum){
				alert("输入有误或是超出库存");
				obj.value = oldBuyNum;
				return;
			}
			window.location.href = "${app}/servlet/UpdateCart?id="+id
			                       +"&newBuyNum="+newBuyNum;
		}
		
    	function delprod(id){
			if(window.confirm("确定要删除吗？")){
			   window.location.href = "${app}/servlet/DelProdInCart?id="+id;
				}
			return false;
		}
    
    </script>
  </head>
  
  <body>
     <%@include file="/head.jsp" %>
     <span>${requestScope.msg}</span>
     <div class="warp">
			<!-- 标题信息 -->
			<div id="title">
				<input name="checkall" type="checkbox" value="" onclick="allchecked(this)"/>
				<span id="title_checkall_text">全选</span>
				<span id="title_name">商品</span>
				<span id="title_price">单价（元）</span>
				<span id="title_buynum">数量</span>
				<span id="title_money">小计（元）</span>
				<span id="title_del">操作</span>
			</div>
			<!-- 购物信息 -->
			<c:forEach items="${sessionScope.cart}" var="entry">
			    <div id="prods" name="div_prod">
					<input name="prodC" type="checkbox" value="" onclick="chooseOne()"/>
					<img src="${app}/servlet/ProdImg?id=${entry.key.id}" width="90" height="90" />
					<span id="prods_name">${entry.key.name}</span>
					<span id="prods_price" class="${entry.key.id}_price">${entry.key.price}</span>
					<span id="prods_buynum" class="changeNum"> 
						<a id="delNum" style="cursor:pointer" onclick="changeNum(-1,'${entry.key.id}')">-</a>
						<input id="buyNumInp" name="${entry.key.id}_num" type="text" value="${entry.value}" 
						  onblur="updateNum(this,'${entry.key.id}',${entry.value},${entry.key.pnum})"/>
						<a id="addNum" style="cursor:pointer" onclick="changeNum(1,'${entry.key.id}')">+</a>
					</span>
					<span id="prods_money" class="${entry.key.id}_money">
					${entry.key.price*entry.value}</span>
					<span id="prods_del"><a onclick="delprod('${entry.key.id}')" style="cursor:pointer">删除</a></span>
					<c:set var="money" scope="page" value="${money+entry.key.price*entry.value}"></c:set>
			    </div>                     
			</c:forEach>
			<!-- 总计条 -->
			<div id="total">
				<div id="total_1">
					<input name="inverse" type="checkbox" onclick="inverse()"/> 
					<span>反选</span>
					<a id="del_a" onclick="delChecked()" style="cursor:pointer">删除选中的商品</a>&nbsp;&nbsp;
					<span id="span_1">总价：￥</span>
					<span id="span_2">${money}</span>
				</div>
				<div id="total_2">	
					<a id="goto_order" href="${app}/addOrder.jsp">去结算</a>
				</div>
			</div>
		</div>
		<%@include file="/foot.jsp" %>
  </body>
</html>
