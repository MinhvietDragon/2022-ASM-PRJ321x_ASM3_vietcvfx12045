<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%>

<c:import url="header.jsp">
	<c:param name="title" value="Danh sách giỏ hàng Cart"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>
<p/>

<div style="text-align: center">
<h2>Thông tin giỏ hàng</h2>
<form action="AddToCartController" method="get">
	<input type="hidden" name="action" value="resetCart">
	<input type="submit" value="Reset Cart">
</form>
<p/>
<form action="ListController" method="get">
	<input type="hidden" name="action" value="xemLaiHang">
	<input type="submit" value="Xem lại hàng đã mua">
</form>

</div>
<p/>	
	<%-- Hiển thị giỏ hàng Cart, Bảng sản phẩm mua hàng --%>
	
	<table class="cartlistASM3" style="border: 1px solid; padding: 10px; text-align: center; margin: auto;">
		<tr>
			<th style="border: 1px solid; padding: 10px;">Hình ảnh</th>
			<th style="border: 1px solid; padding: 10px;">Tên điện thoại</th>
			<th style="border: 1px solid; padding: 10px;">Giá (VNĐ)</th>			
			<th style="border: 1px solid; padding: 10px;">Thương hiệu</th>
			<th style="border: 1px solid; padding: 10px;">Số lượng tự điền</th>
			<th style="border: 1px solid; padding: 10px;">Số lượng tăng giảm 1</th>
			<th style="border: 1px solid; padding: 10px;">Tổng</th>
			<th style="border: 1px solid; padding: 10px;">Xoá</th>
		</tr>
	<c:forEach var="dsProduct" items="${sessionScope.cart.items}">
		<tr>
			<td style="border: 1px solid; padding: 10px"><img src="${dsProduct.src}" width="100px"></td>
			<td style="border: 1px solid; padding: 10px">${dsProduct.name}</td>		
			<td style="border: 1px solid; padding: 10px"><fmt:formatNumber value="${dsProduct.price*23500}" maxFractionDigits="0"/></td>	
			<td style="border: 1px solid; padding: 10px">${dsProduct.brand}</td>
			<td style="border: 1px solid; padding: 10px">
				<form action="AddToCartController" method="get">
					<input type="hidden" name="action" value="updateNumber">
					<input type="hidden" name="imageId" value="${dsProduct.id}">
					<input type="number" name="soLuongSanPham" value="${dsProduct.number}"  min="1" max="1000"/>
					<input type="submit" value="update"/>
				</form>
			</td>
			<td style="border: 1px solid; padding: 10px">
					<a href="<c:url value="/AddToCartController?action=minusProduct&imageId=${dsProduct.id}"/>">
					<input type="button" value="-" style="padding: 0px 7px"/></a>
				<b>${dsProduct.number}</b>
					<a href="<c:url value="/AddToCartController?action=addToCart&imageId=${dsProduct.id}"/>">
					<input type="button" value="+" style="padding: 0px 5px"/></a>
			</td>	
			<td style="border: 1px solid; padding: 10px"><fmt:formatNumber value="${dsProduct.price*23500*dsProduct.number}" maxFractionDigits="0"/></td>			
			<td style="border: 1px solid; padding: 10px"> 
				<a href="<c:url value="/AddToCartController?action=delete&imageId=${dsProduct.id}"/>" >
				<input type="button" value="remove" style="padding: 0px 5px"/></a>
			</td>
		</tr>
	</c:forEach>
	
		<tr>
			<td colspan="6" style="border: 1px solid; padding: 10px"> Tổng tiền của đơn hàng: </td> <%--có thể tìm hiểu kiểu inner js để hiển thị ngay khi mỗi số được điều chỉnh --%>
			<td style="border: 1px solid; padding: 10px"><b><fmt:formatNumber value="${TongTien*23500}" maxFractionDigits="0"/></b></td>
			<td style="border: 1px solid; padding: 10px"></td>
		</tr>
				
		<%--Nhập thông tin địa chỉ mua hàng --%>		
		<form action="PayController" method="get">
			<tr><td colspan="8" style="border: 1px solid; padding: 10px; text-align: center;">
				<p>${messageCart}</p>
				<table style="margin: auto;">
					<th colspan="2">Thông tin, địa chỉ nhận hàng:</th>
					
					<%-- <tr><td style="float: right;">Member Email (user):</td><td><input type="hidden" name="userLogin" size="50" value="${sessionScope.member.user}"/></td></tr>  --%>
					
					<tr>
						<td style="float: right;">Customer Name: </td><td><input type="text" name="customerName" size="50" value="${sessionScope.member.name}" maxlength="50"/></td>
					</tr>
					<tr>
						<td style="float: right;">Customer Address: </td><td><input type="text" name="address" size="50" value="${sessionScope.member.address}" maxlength="255"/></td> 
					</tr>
					<tr>
						<td style="float: right;">Phone Number: </td><td><input type="text" name="phoneNumber" size="50" value="${sessionScope.member.phone}" maxlength="64"/></td>
					</tr>
					<tr>
						<td style="float: right;">Discount code (if any): </td><td><input type="text" name="discount" size="50"/></td>
					</tr>
				</table>
				<p/>
			</td></tr>
			<tr><td colspan="8">				
				<input type="submit" value="Đặt hàng">				
			</td></tr>
		</form>
	</table>


<%@ include file="footer.jsp"%>
