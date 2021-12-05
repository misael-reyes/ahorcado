<%-- 
    Document   : agregar
    Created on : 7 nov 2021, 13:34:33
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
        <h3>Agregar palabra</h3>
    <center>
        <table with="50%">
            <form action="Agregar" method="post">
                <tr>
                    <td colspan="2">Datos de la palabra</td>
                </tr>
                <tr>
                    <td>Palabra:</td>
                    <td><input type="text" name="palabra" /></td>
                </tr>
                <tr>
                    <td>Tipo:</td>
                    <td><input type="number" name="tipo"/></td>
                </tr>
                <td></td>
                <td><input type="submit" name="aceptar" title="Aceptar" /></td>

            </form>
        </table>
    </center>
</body>
</html>
