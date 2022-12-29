<%-- Trang được tiếp dẫn đến bởi Controller --%>

<%@ page import="javax.security.auth.message.callback.PrivateKeyCallback.Request"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- Hàm function fn --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%> <%--Chèn thư viện tag tự tạo --%>

<c:import url="header.jsp">
<c:param name="title" value="Mục show riêng chi tiết từng ảnh"></c:param>
</c:import>

<%-- Gọi database, truy vấn ảnh có imageId chuyển tiếp sang image.jsp --%>
<sql:transaction dataSource="jdbc/ShoppingDB">
	
	<sql:query sql="SELECT * from Products where product_id=?" var="results" >
	<sql:param>${param.imageId}</sql:param>
	</sql:query>

	<c:set scope="page" var="oneImage" value="${results.rows[0]}"/>
	
	<%-- Trình bày trang của 1 ảnh --%>
	<div style="display: table; clear: both; text-align: center; margin: auto;">
		<h3 style="margin-top: 15px">${oneImage.product_name}</h3>
		<hr>
		
		<div style="text-align: center; border: 0px solid; margin: auto; float: left; width:33.33%;">
			<thetutao:image widthTag="400" imageURL="${oneImage.product_img_source}"></thetutao:image>		
		</div>
		
		<div style="float: left;  width: 50%;">
			<h3><fmt:formatNumber value="${oneImage.product_price}" maxFractionDigits="5"/> $ USD</h3>
			<p style="margin-top: 20px">${oneImage.product_des}</p>
			<p>	
				<a href="<c:url value="/AddToCartController?action=addToCart&imageId=${param.imageId}"/>" >
					<input type="submit" value="Add to cart" style="color: white; background: orange; padding: 15px"/>
				</a>
			</p>
		</div>
	</div>
	
</sql:transaction>

<c:import url="footer.jsp"/>