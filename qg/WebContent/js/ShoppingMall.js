function logic() {
	var searching = document.getElementById("searching").value
	if (searching.length < 1 ) {
		alert("搜索内容不能为空");
		return false;
	}else if(searching.length > 8){
		alert("搜索内容不得大于8位");
		return false;
	}
		return true;
}