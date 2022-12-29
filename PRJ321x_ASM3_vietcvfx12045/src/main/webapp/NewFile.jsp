<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%>

<c:import url="header.jsp">
	<c:param name="title" value="test"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này --%>
</c:import>

<form name="searchform" action="SearchController2" method="get">
		<select name="categories" class="selectcategories">
			<option value="category1">Category 1</option>
			<option value="category2">Category 2</option>
		</select>
		<input class="itemname" type="text" name="s" placeholder="What are you looking for?">
		<input class="searchbutton" type="button" src="" alt="Submit" value="Search"/>
</form>
	
<p>TEST</p>
${sessionScope.member.address}
<p>${sessionScope.member.user}</p>
</body>
</html>