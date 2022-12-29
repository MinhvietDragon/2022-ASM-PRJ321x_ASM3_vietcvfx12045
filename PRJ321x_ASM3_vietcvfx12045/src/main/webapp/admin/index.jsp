<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%>

<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ASM3- PRJ - Cao Văn Việt</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/CSS/adminstyle.css" rel="stylesheet" type="text/css">	

</head>

<body>

<div>
	<%-- Gọi session đã tạo ở Controller để sử dụng --%>
	Welcome Admin: ${userAdmin}
	<form name="logoutform" action="${pageContext.request.contextPath}/LogoutController" method="post">
		<input class="greenbutton" type="submit" value="Logout">
	</form>
</div>

<hr>

<div class="containeradmin">
	<div class="adminleft">
		<h2 style="background-color: rgb(165, 0, 0)">2022 team</h2>
		<a href="#"><p>Dashboard</p></a>
		<a href="#"><p>Staff Manager</p></a>
	</div>
	
	<div class="adminright">
		<img src="${pageContext.request.contextPath}/Media/BANNER1.png" style="width:100%">
		<table class="admintable">
			<caption><h2>Danh sách cửa hàng</h2></caption>
			<tr>
				<th>ID</th>
				<th>Chủ cửa hàng</th>
				<th>Mã cửa hàng</th>
				<th>Vị trí</th>
			</tr>
			<tr>
				<td>1</td>
				<td>Member1</td>
				<td>vietcv1</td>
				<td>Thanh Hóa</td>
			</tr>	
			<tr>
				<td>2</td>
				<td>Member2</td>
				<td>vietcv2</td>
				<td>Hà Nội</td>
			</tr>		
		</table>
	</div>

</div>
</body>
</html>