<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="codeType" type="java.lang.String" required="true" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="id" type="java.lang.String" required="false" %>
<%@ attribute name="onclick" type="java.lang.String" required="false" %>
<%@ attribute name="values" type="java.lang.String" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/common/widget" %>
<tags:local_init/>
<c:forEach items="${common:codeDescs(codeType, language_define)}" var="codeTO">
<widget:checkBox id="${id}" name="${name}" value="${codeTO.code_id}" onclick="${onclick}" checkedVal="${fn:contains(values, codeTO.code_id)?codeTO.code_id:' '}"/><p>${codeTO.code_desc}&nbsp;&nbsp;</p>
</c:forEach>
             


