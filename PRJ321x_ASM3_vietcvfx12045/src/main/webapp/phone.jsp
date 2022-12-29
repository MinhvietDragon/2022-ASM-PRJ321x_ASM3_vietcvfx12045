<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%>

<c:import url="header.jsp">
	<c:param name="title" value="Danh sách điện thoại"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này (home) --%>
</c:import>
<p/>
<%--Gọi và truy vấn Bảng ShoppingDB từ CSDL --%>
<sql:transaction dataSource="jdbc/ShoppingDB">
<sql:query sql="SELECT * from Products" var="results" > 	<%--Truy vấn bảng Products để lấy ảnh --%>
</sql:query>

<%--Tạo bảng lưới hình ảnh từ dữ liệu truy vấn được --%>
<center>

<div class="phonelist">

	<c:forEach var="myImage" items="${results.rows}" varStatus="row"> <%-- myImage là từng ảnh (hàng) sẽ được gọi của results nhờ vòng lặp forEach. Có thể coi là từng ảnh chính thức gọi được từ SQL --%>
		<div style="float:left; margin-top: 15px;">
				<a href="<c:url value="/Controller?action=imageController&imageId=${myImage.product_id}"/>" > <%-- Ở đây URL dẫn đến Controller và chuyển tiếp đến image.jsp với tham số action=imageController và imageId=${myImage.product_id}: Là imageId được điền = id cụ thể cho từng ảnh, lấy từ CSDL--%>
					<thetutao:image widthTag="250" imageURL="${myImage.product_img_source}"></thetutao:image>
				</a>
				<div style="text-align: center;">
					${myImage.product_type}<br>
					<b>${myImage.product_name}</b><br>
					<b>${myImage.product_price} $ </b><br>
				</div>
	
		</div>	
	</c:forEach>
	
</div>

</center>
</sql:transaction>


<c:import url="footer.jsp"/>

