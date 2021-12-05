<%-- 
    Document   : acceso
    Created on : 7 nov 2021, 12:23:34
    Author     : Misael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <center>
        <h1>ACCEDER</h1>
        <h2> Bienvenido <%= request.getParameter("nombre")%> </h2>
        <%
            session = request.getSession();
            session.setAttribute("maxint", request.getParameter("nintento"));
            session.setAttribute("nivell", request.getParameter("nivel"));
            session.setAttribute("tipoo", request.getParameter("tipo"));
        %>
        <h3> ¿ Te gustaria jugar ?
            <a href="index.jsp">No</a>
            <a href="Ahorcado">Si</a>
        </h3>
    </center>
    </body>
</html>
