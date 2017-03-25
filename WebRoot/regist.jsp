<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>欢迎注册EasyMall</title>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="${app}/css/regist.css"/>
</head>
   <script type="text/javascript" src="${app}/js/jquery-1.7.2.js"></script>
   <script type="text/javascript">
   
   function isNull(name, msg) {
	sendmsg(name, "");
	//去空格的时候把密码加在里面了
	var val = $.trim($(":input[name="+name+"]").val());
	if (val == "") {
		sendmsg(name, msg);
		return false;
	}
	return true;
	}

	function sendmsg(name, msg) {
	 $("#"+name+"_span").html("<font color='red' size='-1'>" + msg + "</font>");
	}
	
	function isNullOrSame(name, msg) {
		isNull(name, msg);// 里面有致空，要注意
		var psw_val = $(":input[name=password]").val();
		var psw2_val = $(":input[name=password2]").val();
		if (psw_val!=""&&psw_val !== psw2_val) {
			sendmsg(name, "两次密码输入不一致");
			return false;
		}
		return true;
	}
	
	function isNullOrRight(name, msg) {// 邮箱
		isNull(name, msg);
		var valistr_val = $(":input[name=valistr]").val();
		var reg = /^\w+@\w+(\.\w+)+/;
		if (!reg.test(valistr_val) && valistr_val != "") {
			sendmsg(name, "邮箱格式不正确");
			return false;
		}
		return true;
	}
		
	 
	function onSubmit(){
	     var flag = true;
	      flag = isNull("username", "用户名不能为空")&&flag;
	      flag = isNull("password", "密码不能为空")&&flag;
	      flag = isNullOrSame("password2", "密码不能为空")&&flag;
	      flag = isNull("nickname", "昵称不能为空")&&flag;
	      flag = isNullOrRight("email", "邮箱不能为空")&&flag;
	      flag = isNull("valistr", "验证码不能为空")&&flag;
	      return flag;
	}
   
    function changeImage(obj){
    obj.src="${app}/servlet/ValiImage?time="+new Date().getTime();
    }
    
    function isNullOrExit(obj){
     var username = $.trim(obj.value);
     if(username==""){
        sendmsg("username", "用户名不能为空");
        return false;
     }
    $("#username_span").load("${app}/servlet/ValidateAjax"
                  ,{"username":username,"time":new Date()});
    }
   </script>
<body>
	<h1>欢迎注册EasyMall</h1>
	<form action="${app}/servlet/Regist" method="post" onsubmit="return onSubmit()">
		<table>
		    <tr>
		      <td class="tds" colspan="2" style="text-align:center; color:red">
				  ${requestScope.msg}
		      </td>
		    </tr>
			<tr>
				<td class="tds">用户名：</td>
				<td><input type="text" name="username" onblur="isNullOrExit(this)"
				value="${param.username}">
				<span id="username_span"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">密码：</td>
				<td><input type="password" name="password" onblur="isNull('password', '密码不能为空')">
				 <span id="password_span"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">确认密码：</td>
				<td><input type="password" name="password2" onblur="isNullOrSame('password2', '密码不能为空')">
				 <span id="password2_span"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">昵称：</td>
				<td><input type="text" name="nickname" onblur="isNull('nickname', '昵称不能为空')"
				value="${param.nickname}">
				 <span id="nickname_span"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">邮箱：</td>
				<td><input type="text" name="email" onblur="isNullOrRight('email', '邮箱不能为空')"
				value="${param.email}">
				 <span id="email_span"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">验证码：</td>
				<td><input type="text" name="valistr" onblur="isNull('valistr','验证码不能为空')">
				<img id="yzm_img" src="${app}/servlet/ValiImage" 
				style="cursor: pointer" onclick="changeImage(this)"/>
				<span id="valistr_span"></span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="注册用户"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
