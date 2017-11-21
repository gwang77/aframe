<%@ tag import="java.util.Locale" %>
<%@ tag import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@ tag import="org.springframework.util.StringUtils" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%
    Locale locale = (Locale) request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
    String language_str = locale==null?"en":locale.getLanguage();
    language_str = StringUtils.isEmpty(language_str) ? "en" : language_str;
    request.setAttribute("language_define", language_str);
%>
