<%-- 
    Document   : inicio_juego
    Created on : 15 nov 2021, 8:30:48
    Author     : Misael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jugar</title>
    </head>
    <body>
    <center>
        <br/>
        <h2>Antes de jugar</h2>
        <form action="acceso.jsp" method="post">
            Participante:
            <input type="text" name="nombre"/>
            Num.Intentos
            <input type="number" name="nintento"/>
            <br/>
            <br/>
            Nivel:
            <select name="nivel">
                <option value='1'>Básico</option>
                <option value='2'>Intermedio</option>
                <option value='3'>Alto</option>
            </select>
            Tipo:
            <select name="tipo">
                <option value='7'>Cosas</option>
                <option value='8'>Lugares</option>
                <option value='9'>Emociones</option>
                <option value='10'>Comida</option>
                <option value='11'>Articulos Escolares</option>
                <option value='12'>Flores</option>
                <option value='13'>Muebles</option>
                <option value='14'>Cocina</option>
            </select>
            <br/>
            <br/>
            <br/>
            <input type="submit" value="Acceder"/>
        </form>
        <br/>
        <a href="index.jsp">Regresar</a>
    </center>

</body>
</html>
