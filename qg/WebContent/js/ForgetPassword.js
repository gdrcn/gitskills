
function vilidate() {
	var qname = document.getElementById("qname").value
	if (qname.length > 10) {
		alert("密保信息必须在10位之间");
		return;
	}
}

function logic() {
	var email = document.getElementById("email").value
	if (email.length < 9 || email.length > 20) {
		alert("email必须在9-20位之间");
		return false;
	} else
		return true;
}
