

function validate() {
	var pwd1 = document.getElementById("pwd1").value;
	var pwd2 = document.getElementById("pwd2").value;
	if (pwd1 == pwd2) {
		document.getElementById("tips").innerHTML = "<font color='green'>两次密码相同</font>";
		document.getElementById("submit").disabled = false;
	} else {
		document.getElementById("tips").innerHTML = "<font color='red'>两次密码不相同</font>";
		document.getElementById("submit").disabled = true;
	}
}

function logic() {
	var pwd1 = document.getElementById("pwd1").value
	var num = document.getElementById("num").value
	var qq = document.getElementById("qq").value
	var wechat = document.getElementById("wechat").value
	var email = document.getElementById("email").value
	if (pwd1.length < 6 || pwd1.length > 12) {
		alert("密码必须在6—12位之间");
		return false;
	} else if (num.length < 9 || num.length > 15) {
		alert("电话号码必须在9-15位之间");
		return false;
	} else if (qq.length < 6 || qq.length > 15) {
		alert("qq必须在6-15位之间");
		return false;
	}

	else if (wechat.length < 6 || wechat.length > 15) {
		alert("wechat必须在6-15位之间");
		return false;
	} else if (email.length < 9 || email.length > 20) {
		alert("email必须在9-20位之间");
		return false;
	} else
		return true;
}

function vilidate() {
	var urn = document.getElementById("name").value
	if (urn.length > 15) {
		alert("用户名必须在15位之间");
		return;
	}

	var wechat = document.getElementById("wechat").value
	if (wechat.length > 20) {
		alert("wechat必须在20位之间");
		return;
	}

	var qname = document.getElementById("qname").value
	if (qname.length > 10) {
		alert("密保信息必须在10位之间");
		return;
	}
}

