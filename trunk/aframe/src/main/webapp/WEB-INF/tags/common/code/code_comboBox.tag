<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="codeType" type="java.lang.String" required="false"%>
<%@ attribute name="items" type="java.util.List" required="false"%>
<%@ attribute name="id" type="java.lang.String" required="false"%>
<%@ attribute name="id_prefix" type="java.lang.String" required="false"%>
<%@ attribute name="id_suffix" type="java.lang.String" required="false"%>
<%@ attribute name="onchange" type="java.lang.String" required="false"%>
<%@ attribute name="value" type="java.lang.String" required="false"%>
<%@ attribute name="firstOption" type="java.lang.String" required="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="code" tagdir="/WEB-INF/tags/common/code"%>
<tags:local_init />
<div class="select-box">
    <div class="select-content"><code:code_desc codeType="${codeType}" value="${value}" item="${items}"/></div>
    <i class="arrow-down"></i>
 	<ul>
		<c:if test="${firstOption!=null}">
			<li class="first-li">${firstOption}&nbsp;</li>
		</c:if>
		<c:if test="${items==null}">
			<c:set var="items" value="${common:codeFilterDescs(codeType,language_define,id_prefix,id_suffix)}" />
		</c:if>
		<c:forEach items="${items}" var="codeTO">
			<li>${codeTO.code_desc}</li>
		</c:forEach>
	</ul>
	
	<select name="${name}" id="${id}" onchange="${onchange}" class="form-control">
		<c:if test="${firstOption!=null}">
			<option value="">${firstOption}</option>
		</c:if>
		<c:forEach items="${items}" var="codeTO">
			<option value="${codeTO.code_id}" ${codeTO.code_id==value?"selected":""}>${codeTO.code_desc}</option>
		</c:forEach>
	</select>
</div>