<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="codeType" type="java.lang.String" required="true" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="id" type="java.lang.String" required="false" %>
<%@ attribute name="onclick" type="java.lang.String" required="false" %>
<%@ attribute name="value" type="java.lang.String" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/common/widget" %>
<tags:local_init/>
<c:forEach items="${common:codeDescs(codeType,language_define)}" var="codeTO">
<widget:radio name="${name}" id="${id}" value="${codeTO.code_id}" onclick="${onclick}" checkedVal="${value}"/><p>${codeTO.code_desc}&nbsp;&nbsp;</p>
</c:forEach>
             


