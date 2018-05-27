function logic() {
	var buyNum = document.getElementById("buyNum").value
	var phone = document.getElementById("phone").value
	var apay = document.getElementById("apay").value
	var address = document.getElementById("address").value
	if (buyNum.length > 10) {
		alert("商品数量不足！");
		return false;
	} else if (phone.length > 12) {
		alert("手机号码格式不对！");
		return false;
	} else if (apay.length > 12) {
		alert("支付宝账号格式有误！");
		return false;
	} else if (address.length > 40) {
		alert("地址格式有误！");
		return false;
	} else
		return true;
}
