<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="commandName" type="java.lang.String" required="true" %>
<%@ attribute name="errorPosition" type="java.lang.String" required="false" description="message position, can be topLeft, topRight, bottomLeft, centerRight, bottomRight" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld" %>
<c:if test="${empty errorPosition}">
    <c:set var="errorPosition" value="topRight"/>
</c:if>
<div id="msgPlace"></div>
<c:if test="${business_messages.messageInfoCount > 0}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <ul>
            <c:forEach items="${business_messages.messageInfoList}" var="message">
                <li class="text-info"><spring:message code="${message.message}" arguments="${common:msgConvertAll(message.arguments)}"/></li>
            </c:forEach>
        </ul>
    </div>
</c:if>
<c:if test="${business_errors.messageInfoCount > 0}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <ul>
            <c:forEach items="${business_errors.messageInfoList}" var="error">
                <li class="text-danger"><spring:message code="${error.message}" arguments="${common:msgConvertAll(error.arguments)}"/></li>
            </c:forEach>
        </ul>
    </div>
</c:if>
<div id="errPlace"></div>
<spring:hasBindErrors name="${commandName}">
    <c:if test="${errors.globalErrorCount > 0}">
        <div class="alert alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            <ul>
                <c:forEach items="${errors.globalErrors}" var="error">
                    <li class="text-danger"><spring:message code="${error.defaultMessage}" arguments="${error.arguments}"/></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${errors.fieldErrorCount > 0}">
        <div class="alert alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            <ul>
                <c:forEach items="${errors.fieldErrors}" var="error">
                    <li class="text-danger"><spring:message code="${error.defaultMessage}" arguments="${error.arguments}"/></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
</spring:hasBindErrors>
<script type="text/javascript">
    var err_content = "<div class='alert alert-warning alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><ul id='err_ul'></ul></div>";
    var msg_content = "<div class='alert alert-warning alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><ul id='msg_ul'></ul></div>";
    function showError(errContent) {
        var ul = document.getElementById("err_ul");
        if (ul == null) {
            document.getElementById("errPlace").innerHTML = err_content;
            ul = document.getElementById("err_ul");
        }
        var li = document.createElement("li");
        li.class = "text-danger";
        li.innerHTML = errContent;
        ul.appendChild(li);
    }
    function clearError() {
        document.getElementById("errPlace").innerHTML = "";
    }
    function showMessage(msgContent) {
        var ul = document.getElementById("msg_ul");
        if (ul == null) {
            document.getElementById("msgPlace").innerHTML = msg_content;
            ul = document.getElementById("msg_ul");
        }
        var li = document.createElement("li");
        li.class = "text-info";
        li.innerHTML = msgContent;
        ul.appendChild(li);
    }
    function clearMessage() {
        document.getElementById("msgPlace").innerHTML = "";
    }
</script>