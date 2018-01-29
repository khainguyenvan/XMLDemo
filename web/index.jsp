<%-- 
    Document   : index
    Created on : Jan 26, 2018, 3:12:34 PM
    Author     : khai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <form action="CenterServlet" method="POST">
            Username <input type="text" name="username" />
            Password <input type="password" name="password" />
            <input type="submit" value="Login" name="btAction" />
        </form>
    </body>
</html>
