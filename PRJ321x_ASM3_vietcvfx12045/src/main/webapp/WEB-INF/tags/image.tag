<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="widthTag" required="false" type="java.lang.Integer" description="Chiều rộng của ảnh" rtexprvalue="true" %>
<%@ attribute name="imageURL" required="true" type="java.lang.String" description="Link online ảnh" rtexprvalue="true" %>

<c:if test="${empty widthTag}">	<c:set var="widthTag" value="200"/> </c:if>

<img width="${widthTag}" src="${imageURL}"> <%-- 1 Thẻ sử dụng chính thức cho tag --%>
