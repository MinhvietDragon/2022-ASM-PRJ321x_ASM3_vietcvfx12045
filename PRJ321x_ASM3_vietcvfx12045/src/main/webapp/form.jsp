<div class="loginpage">
	<img src="Media/avatarAdmin.jpg" style="width:10%;">
	<h2>Sign in</h2>
	
	<form name="loginform" action="LoginController" onsubmit="return validate()" method="post">
	<%
	//Xử lý lấy username từ cookie.(Về cơ bản là lấy user đã đăng nhập đặt sẵn vào input login trong phiên session) 
	//B1: Lấy sẵn trong danh sách cookie nếu đã có lưu
	Cookie [] cookie = request.getCookies();
	String value = "";
		if(cookie != null){
			for(Cookie retrievedCookies: cookie){
				if(retrievedCookies.getName().equals("username")){
					value = retrievedCookies.getValue();
				}
			}
		}
	//B2: Nếu trong danh sách trống thì đặt ô trống
	if (value == "") {%>
	<input type="text" id="username" name="username" placeholder="Enter Username" ><br><br>
		
	<%//B3: Nếu danh sách đã có lưu thì gọi ra để sử dụng
	} else { %>
	<input type="text" id="username" name ="username" value= <%=value %> > <br><br>
	<% } %>
	
	<%--Mục input password không đặt cookie nên để trống--%>
	<input type="password" id="password" name ="password" placeholder="Enter Password" ><br>
	
	<div class="error">
		<% 
			//Thong bao error neu sai ten hoac mat khau, lấy theo session đã lưu
			String error = (String) session.getAttribute("error");
			if(error != null){
				out.println(error);
			}
		%>
	</div>
		
	<hr>
	
	<div class="bottomlogin">
		<a class="forgotpassword" href="/PRJ321x_Asm2_vietcvfx12045/URLController?action=forgotpassword">Forgot Password</a><br>
		<input type="checkbox" id="remember" name="remember" value="on" checked="checked" />
		<label for="remember">Remember me</label>
	</div>	
	
		<input class="greenbutton" type="submit" value="LOGIN">	
		
	</form>

</div>

