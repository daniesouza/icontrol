<%@page import="br.com.icontrol.util.Constantes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    </head>
    <body>
 
        <sec:authorize ifAnyGranted="<%=Constantes.ADMINISTRADOR %>">
            <jsp:forward page="/paginas/admin/principal.jsf"/>
        </sec:authorize>
        
        <sec:authorize ifAnyGranted="<%=Constantes.ADMIN_CLIENTE %>">
            <jsp:forward page="/paginas/adminClient/principal.jsf"/>
        </sec:authorize>        
 
        <sec:authorize ifAnyGranted="<%=Constantes.CLIENT %>">
            <jsp:forward page="/paginas/client/principal.jsf"/>
        </sec:authorize>
        
        <jsp:forward page="/paginas/public/home_old.jsf"/>

    </body>
</html>