<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%>

<c:import url="header.jsp">
	<c:param name="title" value='Tìm kiếm: "${mySearch}"'></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này (home) --%>
</c:import>

<%--Gọi và truy vấn Bảng ShoppingDB từ CSDL --%>
<sql:transaction dataSource="jdbc/ShoppingDB">
<c:set scope="page" var="lenhtruyvan" value="SELECT * FROM Products WHERE product_name LIKE '%${mySearch}%' or product_brand LIKE '%${mySearch}%' or product_type LIKE '%${mySearch}%'"></c:set>
<sql:query sql="${lenhtruyvan}" var="results" > 	<%--Truy vấn tìm kiếm trong các cột của bảng Products trả về một bảng tạm results --%>
</sql:query>
<p/>
<%--Tạo bảng lưới hình ảnh từ dữ liệu truy vấn được --%>
<center>
	<table id="luoihinhanh">
		<c:set var="tableWidth" value="4"/>
		
		<c:forEach var="myImage" items="${results.rows}" varStatus="row"> <%-- myImage là từng ảnh (hàng) sẽ được gọi của results nhờ vòng lặp forEach. Có thể coi là từng ảnh chính thức gọi được từ SQL --%>
			<c:if test="${row.index % tableWidth == 0 }">
				<tr>
			</c:if>
			
			<td>
				<a href="<c:url value="/Controller?action=imageController&imageId=${myImage.product_id}"/>" > <%-- Ở đây URL dẫn đến Controller và chuyển tiếp đến image.jsp với tham số action=imageController và imageId=${myImage.product_id}: Là imageId được điền = id cụ thể cho từng ảnh, lấy từ CSDL--%>
					<thetutao:image widthTag="300" imageURL="${myImage.product_img_source}"></thetutao:image>
				</a>
				<div style="text-align: center;">
					${myImage.product_type}<br>
					<b>${myImage.product_name}</b><br>
					<b>${myImage.product_price} $ </b><br>
				</div>
			</td>
		
			<c:if test="${row.index +1 % tableWidth == 0 }">
				</tr>
				<hr>
			</c:if>
		
		</c:forEach>
	</table>
	
	<%-- Thông báo nếu không có dữ liệu được tìm thấy (bảng kết quả truy vấn results có hàng đầu tiên là trống) --%>
	<c:if test="${results.rows[0] == null}">
		<p>Không có sản phẩm với từ khoá <b>"${mySearch}"</b> nào được tìm thấy </p>
	</c:if>
	
</center>
</sql:transaction>


<%@ include file="footer.jsp"%>
