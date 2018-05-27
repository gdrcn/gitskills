function change() {
	// 切换验证码
	document.getElementById("myimg").src = "/QGaccess/Checkcode?"
			+ new Date().getTime();
}

function logic() {
	var id = document.getElementById("id").value
	if (id.length != 18) {
		alert("身份证号必须为18位");
		return;
	}
}