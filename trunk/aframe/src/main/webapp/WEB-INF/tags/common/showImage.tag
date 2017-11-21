<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld" %>
<%@ attribute name="image_id" type="java.lang.String" required="false" %>
<%@ attribute name="image_content" type="byte[]" required="true" %>
<%@ attribute name="width" type="java.lang.String" required="false" %>
<%@ attribute name="height" type="java.lang.String" required="false" %>

<c:if test="${width!=null && width!=''}">
    <c:set var="widthStr">width='${width}'</c:set>
</c:if>
<c:if test="${height!=null && height!=''}">
    <c:set var="heightStr">height='${height}'</c:set>
</c:if>
<c:if test="${image_content!=null}">
    <img id="${image_id}" ${widthStr} ${heightStr} src="data:;base64,${common:encodeBase64(image_content)}"/>
</c:if>
