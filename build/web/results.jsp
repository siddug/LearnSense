<%-- 
    Document   : results
    Created on : 4 Aug, 2013, 5:07:25 AM
    Author     : varun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        String meaning = session.getAttribute("Meaning").toString();
        out.write("<p>"+meaning+"</p>");
        %>
    </body>
</html>
