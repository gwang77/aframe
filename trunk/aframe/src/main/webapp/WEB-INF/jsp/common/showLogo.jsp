<%@ page import="sz.internal.common.component.code.CodeConstant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="code" tagdir="/WEB-INF/tags/common/code" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/common/widget" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld" %>

<div class="row">
	<div class="col-md-12">
		<nav class="navbar navbar-default navbar-fixed-top header-top" role="navigation"></nav>

		<nav class="navbar navbar-default navbar-fixed-top header-menu " role="navigation">
			<div class="navbar-header" style="position: relative; z-index: 999">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<div class="nav navbar-nav header-logo">
					<i></i><img src="../image/logo.png" />&nbsp;
					<div style="margin-left: 330px; margin-top: -15px">
						V<code:code_desc codeType="<%=CodeConstant.CODE_TYPE_SYSTEM_CONFIG%>" value="<%=CodeConstant.SYSTEM_CONFIG_SYSTEM_VERSION%>"/>
					</div>
				</div>
			</div>

			<div class="collapse navbar-collapse navbar-responsive-collapse  dropdown menu-box"></div>
		</nav>

		<div class="col-md-16" style="text-align: right; margin-bottom: 0px; padding-top: 102px"></div>
	</div>
</div>