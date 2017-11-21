<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/htmlHead.jsp" %>
<%@ page import="org.springframework.util.StringUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld" %>
<html>
<style>
    .title {
        margin-top: 8px;
        margin-bottom: 8px;
        margin-left: 16px;
        font-family: Segoe Script;
        font-size: 24px;
    }
</style>
<head>
</head>

<body>
<div class="main">
    <div class="row">
        <div class="col-md-12">
            <nav class="navbar navbar-default navbar-fixed-top header-top" role="navigation">
                <div class="nav navbar-nav navbar-right  hidden-xs langtype" style="margin-right: 8px">
                    <p class="text-muted">
                        <button type="button" name="view"
                                class="btn btn-link btn-xs <%if("en".equals(request.getParameter("langType")) || StringUtils.isEmpty(request.getParameter("langType"))){%>selected<%}%>"
                                onclick="window.location='index?langType=en'">
                            <span class="badge">English</span>
                        </button>
                        <button type="button" name="view"
                                class="btn btn-link btn-xs <%if("zh".equals(request.getParameter("langType"))){%>selected<%}%>"
                                onclick="window.location='index?langType=zh'">
                            <span class="badge">中文</span>
                        </button>
                    </p>
                </div>
                <div class="header-user">
                    <a href="#"><i class="icon-user"></i> <spring:message code="title.acm.use.current.user"/>
                        <span>${curr_user_name}</span></a> <a href="${pageContext.request.contextPath}/acm/logout"><i
                        class="icon-logout"></i> <spring:message code="title.acm.use.logout"/></a> <a
                        href="${pageContext.request.contextPath}/acm/changePwdInit"><i class="icon-password"></i>
                    <spring:message code="title.acm.use.change.pwd"/></a>
                </div>
            </nav>

            <nav class="navbar navbar-default navbar-fixed-top header-menu" role="navigation">
                <div class="navbar-header" style="position: relative; z-index: 999">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target=".navbar-responsive-collapse">
                        <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span
                            class="icon-bar"></span> <span class="icon-bar"></span>
                    </button>
                    <div class="nav navbar-nav header-logo">
                        <i></i> <img src="image/logo.png"/>&nbsp;
                        <div style="margin-left:330px;margin-top:-21px">V<code:code_desc
                                codeType="<%=CodeConstant.CODE_TYPE_SYSTEM_CONFIG%>"
                                value="<%=CodeConstant.SYSTEM_CONFIG_SYSTEM_VERSION%>"/></div>
                    </div>
                </div>

                <tags:local_init/>
            </nav>
            <div class="col-md-16" style="text-align: right; margin-bottom: 0px; padding-top: 102px"></div>
        </div>
    </div>

    <div class="row">
        <a href="${pageContext.request.contextPath}/common/codeint/codeIntSearch">Code Maintain</a><br>
        <a href="${pageContext.request.contextPath}/auditLog/auditLogSearch">Audit Log View</a>
    </div>
</div>
</body>
</html>