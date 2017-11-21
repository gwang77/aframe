<%@ tag import="java.util.*" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="category" type="java.lang.String" required="true" %>
<%@ attribute name="value" type="java.lang.String" required="true" %>
<%
    Map style_map = (Map) session.getAttribute("style_map");
    if (style_map == null) {
        style_map = new HashMap();
        session.setAttribute("style_map", style_map);
    }
    category = category == null || category.equals("") ? "style_category" : category;
    List value_list = (List) style_map.get(category);
    if (value_list == null) {
        value_list = new ArrayList();
        style_map.put(category, value_list);
    }
    if (value_list.indexOf(value) < 0) {
        value_list.add(value);
    }
%>code_style_<%=value_list.indexOf(value)%>