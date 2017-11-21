<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>
<%@ taglib prefix="menu" tagdir="/WEB-INF/tags/common/menu" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/style_common.css" />
<html>
<head>
</head>
<script type="text/javascript">
	function dodelete(id) {
		document.forms[0].action = id + "/delete";
		document.forms[0].submit();
	}
	function docreateInit(id) {

		document.forms[0].action = id + "/createInit";
		document.forms[0].submit();
	}
	function doEdit(id, parent_id) {
		document.forms[0].id.value = id;
		document.forms[0].parent_id.value = parent_id;
		document.forms[0].action = id + "/edit";
		document.forms[0].submit();
	}
	function docreateInits() {
		document.forms[0].action = "${pageContext.request.contextPath}/menu/createInits";
		document.forms[0].submit();
	}
	function doSearch() {
		document.forms[0].action = "${pageContext.request.contextPath}/menu/menuSearch";
		document.forms[0].submit();
	}
</script>

<body>
	<tags:errorInfo commandName="menu" />
	<form:form action="menuSearch" commandName="menuTO" class="form-inline">
	
		<div class="row search-box">
			<i class="arrow-left"></i> <i class="arrow-right"></i>
		
		<input type="hidden" name="id">
		<input type="hidden" name="parent_id">
		<h4>
				<spring:message code="menu.menuMaintain" />
			</h4>
<!-- 		<div class="row"> -->
<!-- 			<div class="col-md-12"> -->
				<div class="input-group searchdiv">
					<span class="input-group-addon"><spring:message code="menu.sorts" /></span> 
					<div class="select-box">
                    <div class="select-content">
          
            <c:if test="${caption==null}">
               ${menuTO.caption}
          </c:if>
          <c:if test="${caption!=null}">
              <c:forEach items="${RootList}" var="menuTO">
               <c:if test="${menuTO.caption==caption}">${menuTO.caption}</c:if>
              </c:forEach>
               </c:if>
          
                    </div>
                    <i class="arrow-down"></i>
                    <ul>
 		<c:forEach items="${RootList}" var="menuTO"> 
 			<li>${menuTO.caption}</li> 
		</c:forEach>
                    </ul>
					<select name="caption" id="caption" class="form-control" onchange="doSearch()">

						<c:forEach items="${RootList}" var="menuTO">
							<option value="${menuTO.caption}" ${menuTO.caption==caption?"selected":""}>${menuTO.caption}</option>
						</c:forEach>
					</select>
					<tags:local_init />
				</div></div>
				<div class="input-group searchdiv">
					<button type="button" name="create" class="btn btn-default" style="margin-right: 8px" onclick="docreateInits()">
						<spring:message code="button.create" />
					</button>
				</div>
	          </div>
<!-- 	     </div> -->
<!--       </div> -->
      <div class="table-responsive">
					<table class="maintable table-menu">
					<thead>
						<tr>
							<td width="15%"><spring:message code="menu.caption" /></td>
							<td width="15%"><spring:message code="menu.style" /></td>
							<td width="15%"><spring:message code="menu.url" /></td>
							<td width="15%"><spring:message code="menu.permission" /></td>
							<td width="15%"><spring:message code="menu.available" /></td>
							<td width="15%"><spring:message code="menu.seq_no" /></td>
							<td width="15%"><spring:message code="menu.operation" /></td>
						</tr>
					</thead>
					<tags:local_init />
					<menu:menu_table caption="${caption}" level="0" />
				</table>
		</div>
	</form:form>
	<div class="row">
		<div class="col-md-12">
			</br> 
			<label class="col-md-2 col-lg-1 control-label"> </label>
		</div>
	</div>
</body>
</html>

