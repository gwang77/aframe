<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="id" type="java.lang.String" required="false" %>
<%@ attribute name="onclick" type="java.lang.String" required="false" %>
<%@ attribute name="value" type="java.lang.String" required="false" %>
<%@ attribute name="checkedVal" type="java.lang.String" required="false" %>
<span class="radio-btn ${value==checkedVal?"on":""}"><input type="radio" name="${name}" id="${id}" value="${value}" onclick="${onclick}" ${value==checkedVal?"checked":""}></span>