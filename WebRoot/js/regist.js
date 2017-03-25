
function isNull(name, msg) {
	sendmsg(name, "");
    var obj = document.getElementsByName(name)[0];
	var val = obj.value
	var reg = /^\s+||\s+$/;
	//去前后空格
	if (reg.test(val) && obj.name != "密码") {// 密码可以有空格！！！
		val = val.replace(reg, "");
	}
	if (val == "") {
		sendmsg(name, msg);
		return false;
	}
	return true;
}

function sendmsg(name, msg) {
	 var obj = document.getElementsByName(name)[0];
     var span = obj.parentNode.getElementsByTagName("span")[0];
	 span.innerHTML = "<font color='red' size='-1'>" + msg + "</font>";
}
	
function isNullOrSame(name, msg) {
	isNull(name, msg);// 里面有致空，要注意
	var obj = document.getElementsByName(name)[0];
	var psw1 = document.getElementsByName("password")[0].value;
	var psw2 = obj.value;
	if (psw1!=""&&psw1 !== psw2) {
		sendmsg(name, "两次密码输入不一致");
		return false;
	}
	return true;
}
	
function isNullOrRight(name, msg) {// 邮箱
	isNull(name, msg);
	var obj = document.getElementsByName(name)[0];
	var reg = /^\w+@\w+(\.\w+)+/;
	if (!reg.test(obj.value) && obj.value != "") {
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
    


