<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mt" uri="http://www.tedu.cn/itag" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${app}/css/login.css"/>
		<title>EasyMall欢迎您登陆</title>
	</head>
	<body>
		<h1>欢迎登陆EasyMall</h1>
		<form action="${app}/servlet/Login" method="POST" >
			<table>
			    <tr>
					<td class="tdx" colspan="2" style="color:red; text-align:center" >
						${requestScope.msg}
					</td>
				</tr>
				<tr>
					<td class="tdx">用户名:</td>       <!--${cookie.username.value}-->  
					<td><input type="text" name="username" value="<mt:urlDec/>"/>
					</td>
				</tr>
				<tr>
					<td class="tdx">密码:</td>
					<td><input type="password" name="password"/>
					<span></span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="checkbox" name="remname" value="true"
						<c:if test="${cookie.username.value!=null}">checked="checked"</c:if>
						/>记住用户名
						<input type="checkbox" name="autologin" value="true"/>30天内自动登陆
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="登陆"/>
					</td>
				</tr>
			</table>
		</form>		
	</body>
</html>

