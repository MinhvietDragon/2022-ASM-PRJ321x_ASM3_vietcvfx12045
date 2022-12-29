<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- Hàm function fn --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%> <%--Chèn thư viện tag tự tạo --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/CSS/mystyle.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<title>${param.title}</title> <%-- Sẽ tự get tiêu đề của từng trang khi chuyển, sử dụng trang đó. Vì header.jsp là dùng chung --%>
</head>
<body>

<c:set var="logout" value=""></c:set>

<div class="header">
	<center>
		<%@ include file="banner.jsp" %>		
	</center>
	<div style="float: right; border: 0px solid; padding: 10px">${userWelcome}
		
	</div>
</div>



<div class="topnav" style="width:100%">
	<div class="navleft" style="float:left;">
	<a href="/PRJ321x_ASM3_vietcvfx12045/URLController?action=home">Trang chủ</a>
	<a href="/PRJ321x_ASM3_vietcvfx12045/URLController?action=gotophone">Xem điện thoại</a>
	 
	<a href="/PRJ321x_ASM3_vietcvfx12045/LoginController?action=gotologin" >Login</a>
	<a href="/PRJ321x_ASM3_vietcvfx12045/LoginController?action=createAccount">Register</a>
 	 
	<a href="cart.jsp"><input type="image" src="Media/giohang5.png" style="width: 40px; height:24px;"/></a>
	
	</div>
  	<div class="navright" style="float:right; margin-top: 10px; margin-right: 20px">
  		<form name="searchform" action="SearchController2" method="get">  		
		  	<input type="search" name="searchInput" placeholder="What're you looking for?" >
		  	<input type="submit" value="Search"/>
	  	</form>
	</div>		  	 	
</div>
