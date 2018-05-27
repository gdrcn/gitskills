function logic() {
	var num = document.getElementById("num").value
	var qq = document.getElementById("qq").value
	var wechat = document.getElementById("wechat").value
	var email = document.getElementById("email").value
    if (num.length < 9 || num.length > 15) {
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
}