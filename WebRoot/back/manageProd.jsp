<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" href="${app}/css/manageProd.css"/>
<script type="text/javascript" src="${app}/js/ajax.js"></script>
<script type="text/javascript">

	function changePnum(id,obj,oldPnum){
	   var newPnum = obj.value;
  	   if(isNaN(newPnum)){
	      alert("输入的数据不合法，请输入数字");
 	      obj.value = oldPnum;
	   }else if(newPnum<0){
	       if(window.confirm("数量小于或等于0,将执行删除操作，您确定删除吗？")){
			window.location.href="${app}/servlet/BackProdDelete?id="+id;
			}else{
				obj.value = oldPnum;
			}
	   }else if(newPnum!=oldPnum){//不刷新页面会造成oldPnum一直为第一次的值
		  var xmlHttp = ajaxFunction();
		  xmlHttp.open("post", "${app}/servlet/BackAjaxUpdatePnum", true);
		  xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		  xmlHttp.send("id="+id+"&newPnum="+newPnum);
		  xmlHttp.onreadystatechange = function(){
		     if(xmlHttp.readyState == 4){
		        if(xmlHttp.status == 200 || xmlHttp.status == 304){
		           var data = xmlHttp.responseText;
		             if(data=="true"){
		                alert("修改成功");
		             }else{
		                alert("修改失败");
		             }
		        }
		     
		     }
		  
		  };
	   }
	   
	}  
	
	function submitForm(){
		document.getElementsByName("thispage")[0].value=1;
		document.getElementById("form").submit();
	}
	
	
	function changePage(i){
	    document.getElementsByName("thispage")[0].value=i;
	    document.getElementById("form").submit();
		return false;
	}
	
	function PageAnywhere(obj){
          var val = obj.value;
          var reg = /^[1-9][0-9]*$/;
          if(!reg.test(val)||val>${page.sumpage}){
              alert("输入有误或是超出库存");
              return;
          }
          changePage(val);
       }
</script>
</head>
<body>
	<h1>商品管理</h1>
	<a href="${app}/back/manageAddProd.jsp">添加商品</a>
	<a href="${app}/index.jsp">返回主页面</a><br><br>
	<div id="search_div">
			<form id="form" method="post" action="${app}/servlet/BackProdList">
			    <input type="hidden" name="thispage" id="tp" value="${page.thispage}"/>
			    <input type="hidden" name="rowperpage" id="rpp" value="${page.rowperpage}"/>
				<span class="input_span">商品名：
				     <input type="text" name="name" value="${page.name}"/></span>
				<span class="input_span">商品种类：
				     <input type="text" name="category" value="${page.category}"/></span>
				<%-- <span class="input_span">商品价格区间:
				     <input type="text" name="minprice" value="${page.min}"/> 
				   - <input type="text" name="maxprice" value="${page.max}"/></span> --%>
				<input type="button" value="查询" onclick="submitForm()"/>
			</form>
	</div>
	<hr>
	<table bordercolor="black" border="1" width="95%" cellspacing="0px" cellpadding="5px">
		<tr>
			<th>商品图片</th>
			<th>商品id</th>
			<th>商品名称</th>
			<th>商品种类</th>
			<th>商品单价</th>
			<th>库存数量</th>
			<th>描述信息</th>
		</tr>
		<c:forEach items="${requestScope.page.list}" var="prod">
			<tr>
				<td><img width="120px" height="120px" src="${app}/servlet/ProdImg?id=${prod.id}"/></td>
				<td>${prod.id}</td>
				<td>${prod.name}</td>
				<td>${prod.category}</td>
				<td>${prod.price}</td>
				<td><input type="text" value="${prod.pnum }" style="width: 50px" 
				onblur="changePnum('${prod.id}',this,${prod.pnum})"/></td>
				<td>${prod.description}</td>
				<td><a href="${app}/servlet/BackProdDelete?id=${prod.id}">删&nbsp;除</a></td>
			</tr>
		</c:forEach>
	</table><br>
	<div id="fy_div">
                  共${page.sumrow}条记录 共${page.sumpage}页
         <a onclick="changePage(1)">首页</a>
         <a onclick="changePage(${page.backpage})">上一页</a>
         <%-- 分页逻辑开始 --%>
         <c:set var="begin" scope="page" value="1"></c:set>
         <c:set var="end" scope="page" value="4"></c:set>
         <c:if test="${page.sumpage<4}" var="flag1" scope="page">
         	<c:set var="begin" scope="page" value="1"></c:set>
            <c:set var="end" scope="page" value="${page.sumpage}"></c:set>
         </c:if>
         <c:if test="${!flag1}">
           <c:choose>
         	 <c:when test="${page.thispage<2}">
         	   <c:set var="begin" scope="page" value="1"></c:set>
               <c:set var="end" scope="page" value="4"></c:set>
         	 </c:when>
         	 <c:when test="${page.thispage>page.sumpage-2}">
         	   <c:set var="begin" scope="page" value="${page.sumpage-3}"></c:set>
               <c:set var="end" scope="page" value="${page.sumpage}"></c:set>
         	 </c:when>
         	 <c:otherwise>
         	   <c:set var="begin" scope="page" value="${page.thispage-1}"></c:set>
               <c:set var="end" scope="page" value="${page.thispage+2}"></c:set>
         	 </c:otherwise>
           </c:choose>
         </c:if>
         <%-- 分页逻辑结束 --%>
         <c:forEach begin="${begin}" end="${end}" var="i">
         	<c:if test="${page.thispage!=i}" var="flag" scope="page">
         		<a onclick="changePage(${i})">${i}</a>
         	</c:if>
         	<c:if test="${!flag}">${i}</c:if>
         </c:forEach>
         <a onclick="changePage(${page.nextpage})">下一页</a>
         <a onclick="changePage(${page.sumpage})">尾页</a>
                              跳转到<input type="text" value="${page.thispage}" onblur="PageAnywhere(this)"/>页
    </div>
</body>
</html>
