<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP page</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link href="${app}/css/page.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${app}/js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
       
       function changePage(i){
          $("#tp").val(i) ;
          $("form").submit();
          return false;
       }
       
       function submitForm(){//不要取名为submit()的方法跟系统冲突
       	  $("#tp").val(1) ;
          $("form").submit();
       }
       
       
       function PageAnywhere(obj){
          var val = obj.value;
          var reg = /^[1-9][0-9]*$/;
          if(!reg.test(val)||val>${page.sumpage}){
              alert("输入有误或是超出总页面");
              return;
          }
          changePage(val);
       }
       
       $(function(){
       	  $("a[class=AddProdToCart]").click(function(){
       	  	var url = this.href;
       	  	$("#msg_span").load(url); 
       	  	return false;
       	  });
       
       });
      
           
    </script>
  </head>
  <body>
    <%@include file="/head.jsp" %>
    <span id="msg_span"></span>
    <div id="content">
		<div id="search_div">
			<form id="form" method="post" action="${app}/servlet/ProductPage">
			    <input type="hidden" name="thispage" id="tp" value="${page.thispage}"/>
			    <input type="hidden" name="rowperpage" id="rpp" value="${page.rowperpage}"/>
				<span class="input_span">商品名：
				     <input type="text" name="name" value="${page.name}"/></span>
				<span class="input_span">商品种类：
				     <input type="text" name="category" value="${page.category}"/></span>
				<span class="input_span">商品价格区间:
				     <input type="text" name="minprice" value="${page.min}"/> 
				   - <input type="text" name="maxprice" value="${page.max}"/></span>
				<input type="button" value="查询" onclick="submitForm()"/>
			</form>
		</div>
		<div id="prod_content">
	      <c:forEach items="${requestScope.page.list}" var="prod">
	        <div id="prod_div">
	          <a href="${app}/servlet/ProdInfo?id=${prod.id}&thispage=${page.thispage}
	                   &rowperpage=${page.rowperpage}">
				<img src="${app}/servlet/ProdImg?id=${prod.id}"></img>
	          </a>
				<div id="prod_name_div">
					${prod.name}
				</div>
				<div id="prod_price_div">
					￥${prod.price}元
				</div>
				<div>
					<div id="gotocart_div">
					 <a class="AddProdToCart"
					 href="${app}/servlet/AddProdToCart?id=${prod.id}">加入购物车</a>
					</div>					
					<div id="say_div">
						库存：${prod.pnum}
					</div>					
				</div>
			</div>
	      </c:forEach>		
		</div>
		<div style="clear: both"></div>
	</div>
	<div id="fy_div">
		共${page.sumrow}条记录 共${page.sumpage}页
		<a onclick="changePage(1)">首页</a>
		<a onclick="changePage(${page.backpage})">上一页</a>
		
		<%-- 分页逻辑开始 --%>
		<c:set var="begin" scope="page" value="1"></c:set>
		<c:set var="end" scope="page" value="5"></c:set>
		<c:if test="${page.sumpage<=5}" var="pct" scope="page">
			<c:set var="begin" scope="page" value="1"></c:set>
			<c:set var="end" scope="page" value="${page.sumpage}"></c:set>
        </c:if>
        <c:if test="${!pct}">
        	<c:choose>
		   	  <c:when test="${page.thispage<3}">
		   	  <c:set var="begin" scope="page" value="1"></c:set>
			  <c:set var="end" scope="page" value="5"></c:set>
		   	  </c:when>
		   	  <c:when test="${page.thispage>page.sumpage-2}">
		   	  <c:set var="begin" scope="page" value="${page.sumpage-4}"></c:set>
			  <c:set var="end" scope="page" value="${page.sumpage}"></c:set>	
		   	  </c:when>
		   	  <c:otherwise>
		   	  <c:set var="begin" scope="page" value="${page.thispage-2}"></c:set>
			  <c:set var="end" scope="page" value="${page.thispage+2}"></c:set>
		   	  </c:otherwise>
   			</c:choose>
        </c:if>	
		<%-- 分页逻辑结束 --%>
		<c:forEach begin="${pageScope.begin}" end="${pageScope.end}" var="i">
		<c:if test="${i!=page.thispage}" var="iIsthispage">
		  <a onclick="changePage(${i})">${i}</a>
		</c:if>
		<c:if test="${!iIsthispage}">${i}</c:if>
		</c:forEach>
		<a onclick="changePage(${page.nextpage})">下一页</a>
		<a onclick="changePage(${page.sumpage})">尾页</a>
		跳转到<input type="text" onblur="PageAnywhere(this)"/>页
	</div>
	<%@include file="/foot.jsp" %>
  </body>
</html>
