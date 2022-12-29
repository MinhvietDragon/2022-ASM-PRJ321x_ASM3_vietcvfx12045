<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%>

<c:import url="header.jsp">
	<c:param name="title" value="Danh sách hàng vừa đặt thành công!"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<p/>
<h2 style="text-align: center">Đặt hàng thành công!</h2>

	<%-- Hiển thị giỏ hàng Cart, Bảng sản phẩm mua hàng --%>
	
	<table class="cartlistASM3" style="border: 1px solid; padding: 10px; text-align: center; margin: auto;">
	<td colspan="6" style="text-align: center">Thông tin chi tiết đơn hàng đã đặt</td>	
		<tr>
			<th style="border: 1px solid; padding: 10px;">Hình ảnh</th>
			<th style="border: 1px solid; padding: 10px;">Tên điện thoại</th>
			<th style="border: 1px solid; padding: 10px;">Giá (VNĐ)</th>			
			<th style="border: 1px solid; padding: 10px;">Thương hiệu</th>
			<th style="border: 1px solid; padding: 10px;">Số lượng mua</th>
			<th style="border: 1px solid; padding: 10px;">Tổng</th>
		</tr>
	<c:forEach var="dsProduct" items="${sessionScope.cart.items}">
		<tr>
			<td style="border: 1px solid; padding: 10px"><img src="${dsProduct.src}" width="100px"></td>
			<td style="border: 1px solid; padding: 10px">${dsProduct.name}</td>		
			<td style="border: 1px solid; padding: 10px"><fmt:formatNumber value="${dsProduct.price*23500}" maxFractionDigits="0"/></td>	
			<td style="border: 1px solid; padding: 10px">${dsProduct.brand}</td>
			<td style="border: 1px solid; padding: 10px"><b>${dsProduct.number}</b></td>
			<td style="border: 1px solid; padding: 10px"><fmt:formatNumber value="${dsProduct.price*23500*dsProduct.number}" maxFractionDigits="0"/></td>			
		</tr>
	</c:forEach>
	
		<tr>
			<td colspan="5" style="border: 1px solid; padding: 10px"> Tổng tiền của đơn hàng </td>
			<td style="border: 1px solid; padding: 10px"><b><fmt:formatNumber value="${TongTien*23500}" maxFractionDigits="0"/></b></td>
		</tr>
				
		<%--Nhập thông tin địa chỉ mua hàng --%>		
		
			<tr><td colspan="8" style="border: 1px solid; padding: 10px;">
				
				<table style="margin: auto;">
					<th colspan="2">Thông tin, địa chỉ nhận hàng:</th>
					<tr>
						<td style="float: right;">Customer name: </td><td>${nameFn}</td>
					</tr>
					<tr>
						<td style="float: right;">Customer address: </td><td>${addressFn}</td>
					</tr>
					<tr>
						<td style="float: right;">Phone Number: </td><td>${phoneNumberFn}</td>
					</tr>
					<tr>
						<td style="float: right;">Discount code (if any): </td><td>${discountFn}</td>
					</tr>
				</table>
				<p/>
			</td></tr>			
	
	</table>


<%@ include file="footer.jsp"%>
