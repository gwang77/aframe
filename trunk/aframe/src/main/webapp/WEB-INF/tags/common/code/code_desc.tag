<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="codeType" type="java.lang.String" required="false" %>
<%@ attribute name="value" type="java.lang.String" required="true" %>
<%@ attribute name="item" type="java.util.List" required="false" %>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/common" %>
<tags:local_init/>
<c:if test="${item==null}">
    ${common:codeDesc(codeType,value,language_define)}
</c:if>
<c:if test="${item!=null}">
    <c:forEach items="${item}" var="codeIntTO">
        <c:if test="${codeIntTO.code_id==value}">${codeIntTO.code_desc}</c:if>
    </c:forEach>
</c:if>
