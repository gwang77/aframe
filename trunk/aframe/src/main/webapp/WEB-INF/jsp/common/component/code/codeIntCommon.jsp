<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>
<html>
<script type="text/javascript">
 var flag = false;
 var bool=true;
	function doSubmit() {
		clearError();
		if(codeTypeCheck()&&codeSequenceCheck()){
		document.forms[0].action = "${actionPath}";
		document.forms[0].submit();	}
	}
	function doback(ctId) {
		document.forms[0].codetype_id.value=ctId;
		document.forms[0].action = "${pageContext.request.contextPath}/common/codeint/doBack";
		document.forms[0].submit();
	}
	function codeTypeCheck() {
		var codetype = document.getElementById("code_id").value;
		if (codetype.match(/^\w+$/))
			return true;
		else
			showError("<spring:message code='err.codeInt.code_id'/>");
		    bool=false;
	}
	function codeSequenceCheck(){
		var code_seq = document.getElementById("code_seq").value;
		if (code_seq.match(/^[0-9]*$/)&&code_seq!=null&&code_seq!='')
			return true;
		else
			showError("<spring:message code='err.codeInt.code_seq'/>");
		    bool=false;
	}
	 function checkBeforeSubmit(){
	    	clearError();
		    bool=true;
		    codeTypeCheck();
		    codeSequenceCheck();
		    return bool;
	    }
</script>
<body style="height:600px">
	<div class="wrapper">
		<div class="table-title">
		<c:choose>
			<c:when test="${codeint.code_id==null||codeint.code_id==''}">
				<h4>
					<spring:message code="codeInt.create" />
				</h4>
			</c:when>
			<c:otherwise>
				<h4>
					<spring:message code="codeInt.update" />
				</h4>
			</c:otherwise>
		</c:choose>
        </div>
	</div>
	<div id="msgPlace"></div>
	<div id="errPlace">
	<tags:errorInfo commandName="codeint" />
	</div>
    <br>
	<form:form action="${actionPath}" commandName="codeint" cssClass="form-horizontal" >
		<input type="hidden" name="codetype_id" value="${codeint.codetype_id}">		
		<c:if test="${codeint.code_id!=null&&codeint.code_id!=''}">
		<input type="hidden" name="code_id" value="${codeint.code_id}">
		</c:if>	
			<div class="form-group">
				<label class="control-label"><spring:message code="codeInt.codetype_id" />:</label>               
				<div class="col-md-4 col-lg-4">
					<c:out value="${codeint.codetype_id}"/>
				</div>				
			</div>	
			<div class="form-group">
				<label class="control-label" ><spring:message code="codeInt.code_id" />:</label>
				<c:if test="${codeint.code_id!=null&&codeint.code_id!=''}">	
				<div class="input-control cl">			
					<form:input path="code_id" maxlength="20" class="form-control"  disabled="true"/>
				</div>
				</c:if>
				<c:if test="${codeint.code_id==null||codeint.code_id==''}">	
				<div class="input-control cl">			
					<form:input path="code_id" maxlength="20" class="form-control" />
				</div>
				</c:if>
				<tags:essential />
			</div>		
			<div class="form-group">
				<label class="control-label"><spring:message code="codeInt.code_desc" />:</label>
				<div class="input-control cl">
					<form:input path="code_desc" maxlength="200" class="form-control"/>
				</div>
			</div>								
			<div class="form-group">
				<label class="control-label"><spring:message code="codeInt.status" />:</label>
				<div class="input-control cl">
					<code:code_radio codeType="<%=CodeConstant.CODE_TYPE_STATUS%>" name="status" value="${codeint.status}" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label" ><spring:message code="codeInt.locale" />:</label>
				<div class="input-control cl">
					<code:code_radio codeType="<%=CodeConstant.CODE_TYPE_LOCAL%>" name="locales" value="${codeint.locales}" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label" ><spring:message code="codeInt.code_seq" />:</label>
				<div class="input-control cl">
					<form:input path="code_seq" maxlength="2" class="form-control col-md-2 col-lg-2"/>
				</div>
				<tags:essential />
			</div>
	<div class="row">
		<div class="btn-box form-btn-box">
			<button type="button" name="submitFrm" class="btn btn-default "  onclick="checkBeforeSubmit() ? doSubmit() : {}">
				<spring:message code="button.submit" />
			</button>
			<button type="button" name="back" class="btn btn-default btn-back"  onclick="doback('${codeint.codetype_id}')">
				<spring:message code="button.back" />
			</button>
		</div>
	</div>
</form:form>
<br>
</body>
</html>