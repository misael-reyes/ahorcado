<%-- 
    Document   : agregarPalabras
    Created on : 7 nov 2021, 13:34:46
    Author     : Misael
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Tipopalabras"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<Tipopalabras> tipos;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Agregar Palabras</h3>
    <center>
        <table with ="50%">
            <form action="Agregar" method="post"/>
            <tr>
                <td colspan="2">Datos de la palabra</td>
            </tr>
            <tr>
                <td>Palabra:</td>
                <td><input type="text" name="description"></td>
            </tr>
            <tr>
                <td>Nivel:</td>
                <td>
                    <select name="nivel">
                        <option value='1'>Básico</option>
                        <option value='2'>Intermedio</option>
                        <option value='3'>Alto</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Tipo:</td>
                <td><input type="number" name="tipo"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" name="aceptar" title="Agregar"></td>
            </tr>
            </form>
            
        </table>
        <a href="configuracion.jsp"> Regresar </a>
    </center>
</body>
</html>
