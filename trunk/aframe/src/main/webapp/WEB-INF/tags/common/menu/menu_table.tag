<%@ tag import="sz.internal.common.component.code.CodeConstant" %>
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld" %>
<%@ taglib prefix="common_menu" uri="/WEB-INF/menu.tld" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="menu" tagdir="/WEB-INF/tags/common/menu" %>
<%@ taglib prefix="code" tagdir="/WEB-INF/tags/common/code" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="item" type="sz.internal.common.component.menu.to.MenuTO" required="false" %>
<%@ attribute name="caption" type="java.lang.String" required="false" %>
<%@ attribute name="level" type="java.lang.Integer" required="false" %>


<c:if test="${empty pageScope.item}">
	<c:set var="menu" value="${common_menu:showMenu(caption)}"/>
	<tr>
			<td><c:out value="${menu.caption}"/></td>
			<td><c:out value="${menu.style}"/></td>
			<td><c:out value="${menu.url}"/></td>
			<td><c:out value="${menu.permission}"/></td>
			<td> <code:code_desc codeType="<%=CodeConstant.CODE_TYPE_YES_NO%>" value="${menu.available}" />
		</td>
			<td><c:out value="${menu.seq_no}"/></td>
			<td>
				<button type="button" name="create" class="btn btn-default btn-xs" onclick="docreateInit('${menu.id}')">
					 <spring:message code="button.create" />
				</button>
				
			</td>
		</tr>
</c:if>
<c:if test="${!empty pageScope.item}">
	<c:set var="menu" value="${pageScope.item}"/>
</c:if>
<c:forEach var="cur" items="${menu.list}" >
		<tr>
			<td>
				<c:forEach begin="0" end="${pageScope.level}" step="1">
						&nbsp;&nbsp;&nbsp;&nbsp;
				</c:forEach>
				<a  onclick="doEdit('${cur.id}','${cur.parent_id}')" target="main"><c:out value="${cur.caption}"/></a>
			</td>
		<td><c:out value="${cur.style}" /></td>
		<td><c:out value="${cur.url}"/></td>
			<td><c:out value="${cur.permission}"/></td>
			<td><code:code_desc codeType="<%=CodeConstant.CODE_TYPE_YES_NO%>" value="${cur.available}" /></td>
			<td><c:out value="${cur.seq_no}"/></td>
			<td>
				<button type="button" name="create" class="btn btn-default btn-xs" onclick="docreateInit('${cur.id}')">
					 <spring:message code="button.create" />
				</button>
				<c:if test="${fn:length(cur.list)<=0}">
					<button type="button" name="delete" class="btn btn-default btn-xs btn-delete" onclick="showConfirmModalDialog('<spring:message code="label.confirmDel" />', dodelete, '${cur.id}')" data-toggle="tooltip" title="<spring:message code="button.delete" />">
						<span class="glyphicon glyphicon-remove" ></span>&nbsp;
						<spring:message code="button.delete" />
					</button>
				</c:if>
			</td>
		</tr>
	
		<c:if test="${fn:length(cur.list)>0}">
			<menu:menu_table item="${cur}" level="${pageScope.level+1}"/>
		</c:if>
		
</c:forEach>






