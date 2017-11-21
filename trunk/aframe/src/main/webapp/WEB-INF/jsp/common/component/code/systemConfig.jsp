<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld"%>
<html>
<body>
	<script type="text/javascript">
		function doSubmit() {
			document.forms[0].action = "${pageContext.request.contextPath}/common/systemConfig/update";
			document.forms[0].submit();
		}
	</script>
	<div class="wrapper">
		<div class="table-title">
		<h4>
			<spring:message code="systemConfig.systemConfig" />
		</h4>
		</div>
	</div>
	<tags:errorInfo commandName="systemConfigTO" />
	<form:form action="systemConfigSearch" commandName="systemConfigTO" cssClass="form-horizontal">
			<c:forEach items="${systemTO}" var="CodeTO" varStatus="code">
			<div class="form-group">
			<label class="col-md-1 col-lg-1 control-label" >
			<c:out value="${CodeTO.label}" />
			<form:hidden path="valueList[${code.index}].code_id" value="${CodeTO.code_id}"></form:hidden>:</label>					
			<c:if test="${CodeTO.value_codetype_id==''}">
				<div class="col-md-3 col-lg-3" style="margin-top: 8px;">
				<c:if test="${CodeTO.editable=='N'}">
				<div class="input-control cl">
					<form:input path="valueList[${code.index}].code_desc" maxlength="50" size="80" value="${CodeTO.code_desc}" class="form-control"  readonly="true"/>
				</div>
				</c:if>
				<c:if test="${CodeTO.editable!='N'}">
				<div class="input-control cl">
					<form:input path="valueList[${code.index}].code_desc" maxlength="50" size="80" value="${CodeTO.code_desc}" class="form-control"/>
				</div>
				</c:if>
				</div>
			</c:if> 
			<c:if test="${CodeTO.value_codetype_id!=''}">
				<div class="col-md-3 col-lg-8" style="margin-top: 8px;">
				<div class="input-control cl">
					<code:code_radio codeType="${CodeTO.value_codetype_id}" name="valueList[${code.index}].code_desc" value="${CodeTO.code_desc}" />
				</div>
				</div>
			</c:if>
			</div>			
		    </c:forEach>
	    <div class="row">
		    <div class="btn-box form-btn-box">		
		         <button type="button" name="submitFrm" class="btn btn-default" style="margin-right: 8px" onclick="showConfirmModalDialog('<spring:message code="label.update" />', doSubmit)">
			        <spring:message code="button.submit" />
		         </button>
		   </div>
	  </div>
	</form:form>
	<br>
</body>
</html>