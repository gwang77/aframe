<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld" %>
<%@ taglib prefix="common_menu" uri="/WEB-INF/menu.tld" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="menu" tagdir="/WEB-INF/tags/common/menu" %>
<%@ attribute name="item" type="sz.internal.common.component.menu.to.MenuTO" required="false" %>
<%@ attribute name="caption" type="java.lang.String" required="false" %>
<%@ attribute name="level" type="java.lang.Integer" required="true" %>
<%@ attribute name="index" type="java.lang.String" required="false" %>
<c:if test="${pageScope.index=='show'}">
<div class="collapse navbar-collapse navbar-responsive-collapse  dropdown menu-box">
	<ul class="nav navbar-nav menu-ul" id="nav_menu">
</c:if>
<c:if test="${empty pageScope.item}">
	<c:set var="menu" value="${common_menu:showDirectory(caption)}"/>
</c:if>
<c:if test="${!empty pageScope.item}">
	<c:set var="menu" value="${pageScope.item}"/>
</c:if>

<c:forEach var="cur" items="${menu.list}" >
	<c:if test="${pageScope.level==0}">
		<c:if test="${fn:length(cur.list)>0}">
			<li ><a href="#" id="${cur.id}" class="dropdown-toggle ${cur.style}" data-toggle="dropdown"><i></i><c:out value="${cur.caption}"/><span class="caret"></span><em></em></a>
				<ul class="dropdown-menu">
					<menu:menu item="${cur}" level="${pageScope.level+1}"/>
				</ul>
			</li>
		</c:if>
		<c:if test="${fn:length(cur.list)<=0}">
			<c:if test="${!empty cur.url}">
				<li id="${cur.id}"><a href="${pageContext.request.contextPath}${cur.url}" class="${cur.style}" target="main"><i></i><c:out value="${cur.caption}"/></a></li>
			</c:if>
		</c:if>
	</c:if>
	
	<c:if test="${pageScope.level==1}">
		<c:if test="${cur.caption == '-'}">
			<li class="divider"></li>
		</c:if>
		<c:if test="${cur.caption != '-'}">
			<li id="${cur.id}"><a href="${pageContext.request.contextPath}${cur.url}" target="main"><c:out value="${cur.caption}"/></a></li>
		</c:if>
 	</c:if>
 	
</c:forEach>
<c:if test="${pageScope.index=='show'}">
		</ul>
	</div>
</c:if>




