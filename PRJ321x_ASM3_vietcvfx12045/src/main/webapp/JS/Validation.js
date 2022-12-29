function validate() {
 var u = document.getElementById("username").value;
 var p = document.getElementById("password").value;

	if(u == ""){
		alert("Vui lòng điền tên đăng nhập");
		return false;
	}
	
	if (p == ""){
		alert("Vui lòng điền mật khẩu");
		return false;
	}
}