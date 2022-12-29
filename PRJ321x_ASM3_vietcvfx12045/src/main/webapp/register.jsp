<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- Hàm function fn --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%> <%--Chèn thư viện tag tự tạo --%>    

<c:import url="header.jsp">
	<c:param name="title" value="Đăng ký tài khoản mới"></c:param> 
</c:import>

<center>
<p/>
<h3>Đăng ký tài khoản mới!</h3>
<p/>
<div id="login">
	<form action="${pageContext.request.contextPath}/LoginController" method="post">
	<input type="hidden" name="action" value="createAccount" />	
	<table>
		<tr>
			<td class="lign-right">Email login*</td>
			<%--Dat san theo gia tri da nhap neu phai nhap lai (da luu vao request hoac session) --%>
			<td><input type="email" name="email" value="${email}" size="40" maxlength="100"/></td>
		</tr>
		<tr>
			<td class="lign-right">Name*</td>
			<td><input type="text" name="nameForm" value="${nameFromRequest}" size="40" maxlength="50"/></td>
		</tr>
		<tr>
			<td class="lign-right">Password*</td>
			<td><input type="password" name="password" value="${password}" size="40" maxlength="64"/></td>
		</tr>
		<tr>
			<td class="lign-right">Repeat Password*</td>
			<td><input type="password" name="repeatpassword" value="${repeatpassword}" size="40" maxlength="64"/></td>
		</tr>
		<tr>
			<td class="lign-right">Địa chỉ</td>
			<td><input type="text" name="addressRegister" value="${addressRegister}" size="40" maxlength="255"/></td>
		</tr>
		<tr>
			<td class="lign-right">Số điện thoại</td>
			<td><input type="text" name="phoneRegister" value="${phoneRegister}" size="40" maxlength="11"/></td>
		</tr>
		<tr>
			<td class="lign-right" colspan="2" style="text-align: center; padding: 10px;"><input style="padding: 10px" type="submit" value="Register"/></td>
		</tr>
	</table>
	
	<p class="login-error"> Thông báo: ${message} </p>
	</form>

</div>
</center>

<%@ include file="footer.jsp" %>
