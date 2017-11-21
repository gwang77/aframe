<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortField" type="java.lang.String" required="true" %>
<c:if test="${sortType=='ASC' and sortBy==sortField}">
    <c:set var="icon_class" value="glyphicon glyphicon-sort-by-alphabet sort"/>
</c:if>
<c:if test="${!(sortType=='ASC' and sortBy==sortField)}">
    <c:set var="icon_class" value="glyphicon glyphicon-sort-by-alphabet sort"/>
</c:if>

<a href="#" onclick="doSort('${sortField}')"><span id="icon_${sortField}" class="${icon_class}"></span></a>
