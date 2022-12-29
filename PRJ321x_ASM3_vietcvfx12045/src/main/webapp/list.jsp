<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%>

<c:import url="header.jsp">
	<c:param name="title" value="Lịch sử mua hàng"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>
<p/>

<h2 style="text-align: center">Lịch sử mua hàng</h2>

<table class="cartlistASM3" style="border: 1px solid; padding: 10px; text-align: center; margin: auto;">
	<tr><td colspan="8" style="text-align: center">${member.user}</td></tr>	
		<tr>
			<th style="border: 1px solid; padding: 10px;">ID đơn hàng</th>
			<th style="border: 1px solid; padding: 10px;">ID sản phẩm</th>
			<th style="border: 1px solid; padding: 10px;">Hình ảnh</th>
			<th style="border: 1px solid; padding: 10px;">Tên điện thoại</th>
			<th style="border: 1px solid; padding: 10px;">Giá sản phẩm (VNĐ)</th>
			<th style="border: 1px solid; padding: 10px;">Số lượng mua</th>			
			<th style="border: 1px solid; padding: 10px;">Tổng tiền</th>
			<th style="border: 1px solid; padding: 10px;">Ngày đặt mua</th>
		</tr>
	<c:forEach var="ListHangDaMua" items="${sessionScope.ListHangDaMua}">
		<tr>
			<td style="border: 1px solid; padding: 10px">${ListHangDaMua.orderId}</td>	
			<td style="border: 1px solid; padding: 10px">${ListHangDaMua.productId}</td>	
			<td style="border: 1px solid; padding: 10px"><img src="${ListHangDaMua.producImgSource}" width="100px"></td>
			<td style="border: 1px solid; padding: 10px">${ListHangDaMua.productName}</td>		
			<td style="border: 1px solid; padding: 10px"><fmt:formatNumber value="${ListHangDaMua.priceProduct*23500}" maxFractionDigits="0"/></td>	
			<td style="border: 1px solid; padding: 10px">${ListHangDaMua.amountProduct}</td>
			<td style="border: 1px solid; padding: 10px"><fmt:formatNumber value="${ListHangDaMua.priceProductSum*23500}" maxFractionDigits="0"/></td>
			<td style="border: 1px solid; padding: 10px">${ListHangDaMua.orderDate}</td>			
		</tr>
	</c:forEach>
		<tr>
			<c:if test="${ListHangDaMua == null}">
				<td colspan="8" style="text-align: center; padding: 20px">Bạn chưa đặt mua sản phẩm nào! Vui lòng lựa chọn đơn hàng mới!</td>
			</c:if>
		</tr>
	
</table>

<%@ include file="footer.jsp"%>