<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- Hàm function fn --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%> <%--Chèn thư viện tag tự tạo --%>    

<c:import url="header.jsp">
	<c:param name="title" value="Đăng nhập tài khoản"></c:param> 
</c:import>

<center>
<img src="Media/avatarAdmin.jpg" style="width:15%;">
<p>Vui lòng điền thông tin đăng nhập!</p>
<p/>
<div id="login">
	<form action="${pageContext.request.contextPath}/LoginController" method="post">
	<input type="hidden" name="action" value="dologin" />
	
	<table>
		<tr>
			<td class="lign-right">Email address </td>
			<%--Dat san email va password theo gia tri da nhap neu phai nhap lai (da luu vao request hoac session tai LoginController) --%>
			<td><input type="email" name="email" value="${email}" maxlength="100" required/></td>
		</tr>
		<tr>
			<td class="lign-right">Password </td>
			<td><input type="password" name="password" value="${password}" maxlength="64" required/></td>
		</tr>
		<tr>
			<td class="lign-right" colspan="2" style="text-align: center; padding: 10px;"><input style="padding: 10px" type="submit" value="LOGIN"/></td>
		</tr>
	</table>
	
	<p class="login-error"> Thông báo: ${message} </p>
	</form>

</div>
</center>

<%@ include file="footer.jsp" %>
