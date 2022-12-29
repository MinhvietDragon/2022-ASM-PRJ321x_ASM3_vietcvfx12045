<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="thetutao"%>

<c:import url="header.jsp">
	<c:param name="title" value="ASM3 - Home"></c:param> <%--Set tiêu đề cho header.jsp khi ở trang này (home) --%>
</c:import>

<c:import url="body.jsp" />

<%@ include file="footer.jsp"%>
