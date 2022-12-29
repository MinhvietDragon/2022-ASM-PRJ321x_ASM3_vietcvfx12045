<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- Hàm function fn --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%> <%--Chèn thư viện tag tự tạo --%>  


<c:import url="header.jsp">
	<c:param name="title" value="Tạo tài khoản thành công!"></c:param> 
</c:import>

<center>
<h2>Bạn đã tạo tài khoản mới thành công!!!</h2>
<div id="message">
	<p>Thông tin tài khoản vừa khởi tạo được phép hiển thị:</p>
	<p>Email:${email}</p>
	<p>Password: ${password}</p>
	<p>Địa chỉ: ${addressRegister}</p>	
	<p>Số điện thoại: ${phoneRegister}</p>
	<p></p>
	<p>Thông báo: ${message}</p>
</div>
</center>

<%@ include file="footer.jsp" %>