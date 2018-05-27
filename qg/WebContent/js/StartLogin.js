function logic() {
		var pwd = document.getElementById("pwd").value
		var name = document.getElementById("name").value
		if (pwd.length<6 || pwd.length>12) {
			alert("密码必须在6—12位之间");
			return false;
		} else if (name.length > 20) {
			alert("用户名必须在20位之间");
			return false;
		} else
			return true;
}