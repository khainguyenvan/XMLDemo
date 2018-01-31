<%-- 
    Document   : search
    Created on : Jan 29, 2018, 2:28:57 PM
    Author     : khai
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        <form action="CenterServlet">
            <input type="text" name="txtSearchValue"/>
            <input type="submit" value="Search" name="btAction" />
        </form>

        <c:set var="searchValue" value="${param.txtSearchValue}"/>

        <c:if test="${not empty searchValue}">
            ${param.txtSearchValue}
            <c:set var="result" value="${requestScope.SEARCHRESULT}"/>
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Class</th>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Sex</th>
                            <th>Status</th>                            
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter" >
                            <tr>
                                <td>${counter.count}</td>
                                <td>${dto.sClass}</td>
                                <td>${dto.lastname} ${dto.firstname} ${dto.middlename}</td>
                                <td>${dto.address}</td>
                                <td><c:if test="${fn:trim(dto.sex) == '1'}" >male</c:if><c:if test="${fn:trim(dto.sex) == '0'}" >female</c:if></td>
                                <td>${dto.status}</td>
                                <td><c:url var="deleteLink" value="CenterServlet">
                                        <c:param name="btAction" value="Delete"></c:param>
                                        <c:param name="id" value="${dto.id}"></c:param>
                                        <c:param name="lastSearchValue" value="${param.txtSearchValue}"></c:param>
                                    </c:url><a href="${deleteLink}">Delete</a> </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>
            <c:if test="${empty result}">
                no result
            </c:if>
        </c:if>
        <c:if test="${empty searchValue}">
            empty search value
        </c:if>


    </body>
</html>