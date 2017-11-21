<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <title>CAS &#8211; Central Authentication Service</title>

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>


    <spring:theme code="standard.custom.css.file" var="customCssFile"/>
    <link rel="stylesheet" href="<c:url value="${customCssFile}" />"/>
    <link rel="icon" href="<c:url value="/favicon.ico" />" type="image/x-icon"/>

</head>
<body id="cas">

<div id="container" align="center">
    <div id="content" style="margin-top:150px;width:40%">

        <div id="cookiesDisabled" class="errors" style="display:none;">
            <h2><spring:message code="screen.cookies.disabled.title"/></h2>
            <p><spring:message code="screen.cookies.disabled.message"/></p>
        </div>

        <div class="box" id="login" align="left">
            <form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">

                <form:errors path="*" id="msg" cssClass="errors" element="div" htmlEscape="false"/>

                <h2><spring:message code="screen.welcome.instructions"/></h2>

                <section class="row">
                    <label for="username"><spring:message code="screen.welcome.label.netid"/></label>
                    <c:choose>
                        <c:when test="${not empty sessionScope.openIdLocalId}">
                            <strong><c:out value="${sessionScope.openIdLocalId}"/></strong>
                            <input type="hidden" id="username" name="username" value="<c:out value="${sessionScope.openIdLocalId}" />"/>
                        </c:when>
                        <c:otherwise>
                            <spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey"/>
                            <form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="off" htmlEscape="true"/>
                        </c:otherwise>
                    </c:choose>
                </section>

                <section class="row">
                    <label for="password"><spring:message code="screen.welcome.label.password"/></label>
                    <spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey"/>
                    <form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password" accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off"/>
                    <span id="capslock-on" style="display:none;"><p><img src="images/warning.png" valign="top"> <spring:message code="screen.capslock.on"/></p></span>
                </section>

                <section class="row btn-row">
                    <input type="hidden" name="lt" value="${loginTicket}"/>
                    <input type="hidden" name="execution" value="${flowExecutionKey}"/>
                    <input type="hidden" name="_eventId" value="submit"/>

                    <input class="btn-submit" name="submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="6" type="submit"/>
                    <input class="btn-reset" name="reset" accesskey="c" value="<spring:message code="screen.welcome.button.clear" />" tabindex="7" type="reset"/>
                </section>
            </form:form>
        </div>
    </div> <!-- END #content -->
</div> <!-- END #container -->
</body>
</html>
