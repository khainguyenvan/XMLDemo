<%-- 
    Document   : search
    Created on : Jan 29, 2018, 2:28:57 PM
    Author     : khai
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search page</title>
    </head>
    <body>
        <h1>Welcome, ${sessionScope.FULLNAME}</h1>
        Search Page
        <form action="CenterSevlet">
            <input type="text" name="searchValue" value="" />
            <input type="submit" value="Search" name="btAction" />
        </form>
        
        <c:set var="searchValue" value="${param.txtSearchValue}"/>
        
        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCHRESULT}"/>
            <c:if test="${not empty result}">
                
            </c:if>
        </c:if>
            
    </body>
</html>
