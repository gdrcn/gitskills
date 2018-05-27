function addAttach() {
	var addGoods = document.getElementById("addGoods");
	addGoods.innerHTML += "<div><form action='/QGaccess/GoodsWeb' method='post' enctype='multipart/form-data'><div><span id='tags'></span><b>商品类型: </b> <input type='radio' name='goodsTags' value='服装配饰' checked />服装配饰<input type='radio' name='goodsTags' value='零食饮料' />零食饮料<input type='radio' name='goodsTags' value='生鲜水果'/>生鲜水果<input type='radio' name='goodsTags' value='书籍玩具'/>书籍玩具</div><input type='text' name='price' size='20' id='price' value='${price }'class='intext' placeholder='请输入商品价格' required='required' /><h5 class='fontSet'>${msgprice }</h5><h3 color='white'>上传商品照片</h3><input type='file' name='file' value='file'><h3 style='color: red;'>${msgfile }</h3><input type='text' name='num' size='20' id='num' value='${num }'class='intext' placeholder='请输入商品数量' required='required'  /><h5 class='fontSet'>${msgnum }</h5><input type='text' name='function' required='required' id='fun' value='${function }' placeholder='请输入商品功能信息' class='extext' /><div style='text-align: center;'><h5 class='fontSet'>${msgfun }</h5><input type='submit' value='添加'  class='subico' onclick='return logic()'/><h5 class='fontSet'>${msg }</h5></div></form></div>";
}

function logic() {
	var price = document.getElementById("price").value
	var num = document.getElementById("num").value
	var fun = document.getElementById("fun").value
	if (price.length > 12 ) {
		alert("请设置正常的价格！");
		return false;
	} else if (num.length > 20) {
		alert("数量过大！");
		return false;
	} else if (fun.length > 30) {
		alert("描述保持在30字以内");
		return false;
	} else
		return true;
}