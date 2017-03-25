<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<link href="${app}/css/prodInfo.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${app}/js/jquery-1.7.2.js"></script>
	<script type="text/javascript">
	
		function addCart(id){
		  var buynum = $("#buyNumInp").val();
		  $("#msg_span").load("${app}/servlet/AddProdToCart",{"id":id,"buynum":buynum});
		  return false;
		}
		
		function changeNum(i){
    	   var val = $("#buyNumInp").val();
    	   var j = parseInt(val);
    	   if(j==1&&i==-1){
    	      i=0;
    	   };
    	   $("#buyNumInp").val(i+j);
    	   return false;
    	}  
    	
    	function isRight(obj){//如果弹框了是非法输入的，提交后获取得buynum属性的值为null
    	  var val = obj.value;
          var reg = /^[1-9][0-9]*$/;
          if(!reg.test(val)||val>${prod.pnum}){
          	alert("输入有误或是超出库存");
          };
    	}
	  
	</script>
</head>
<%@include file="/head.jsp" %>
<body>
	<div id="warp">
		<div id="left">
			<div id="left_top">
				<img src="${app}/servlet/ProdImg?id=${prod.id}"/>
			</div>
			<div id="left_bottom">
				<img id="lf_img" src="${app}/img/prodInfo/lf.jpg"/>
				<img id="mid_img" src="${app}/servlet/ProdImg?id=${prod.id}" width="60px" height="60px"/>
				<img id="rt_img" src="${app}/img/prodInfo/rt.jpg"/>
			</div>
		</div>
		<div id="right">
			<div id="right_top">
				<span id="prod_name">${prod.name}<br/></span>
				<br>
				<span id="prod_desc">${prod.description}<br/></span>
			</div>
			<div id="right_middle">
				<span id="right_middle_span">
				EasyMall 价：<span class="price_red">￥${prod.price }<br/>
			            运     费：满 100 免运费<br/>
			            服     务：由EasyMall负责发货，并提供售后服务<br/>
			            库     存：${prod.pnum}<br/>        
			            购买数量：
	            <a  id="delNum" style="cursor:pointer" onclick="changeNum(-1)">-</a>
	            <input id="buyNumInp" name="buynum" type="text" value="1" onblur="isRight(this)">
		        <a  id="addNum" style="cursor:pointer" onclick="changeNum(1)">+</a>
			</div>
			<div id="right_bottom">
			  <input class="add_cart_but" type="button" 
			  onclick="addCart('${prod.id}')"/>	
			  <br><br>
			  &nbsp;&nbsp;&nbsp;&nbsp;
			  <span id="msg_span"></span>
			</div>
		</div>
	</div>
	<%@include file="/foot.jsp" %>
</body>
</html>