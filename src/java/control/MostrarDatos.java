/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/**
 * Servlet para mostrar los tipos de palabras que hay en la base de datos
 */

package control;

import acceso_datos.TipopalabrasJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import modelo.Tipopalabras;

/**
 *
 * @author Misael
 */
@WebServlet(name = "MostrarDatos", urlPatterns = {"/MostrarDatos"})
public class MostrarDatos extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* esto hicimos en clases */
            emf = Persistence.createEntityManagerFactory("juego2PU");
            TipopalabrasJpaController controlTipos = new TipopalabrasJpaController(utx, emf);
            //este objeto va a retornar los elementos
            List<Tipopalabras> tipos = controlTipos.findTipopalabrasEntities();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Mostrar tipos de palabras</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h3>Tipos de palabras</h3>");
            out.println("<table aling='center' width='60%' border=1>");
            out.println("<tr>");
            out.println("<td class='datos'> Id tipo </td>");
            out.println("<td class='datos'> Descripción </td>");
            out.println("</tr>");
            for (Tipopalabras tp : tipos) {
                out.println("<tr>");
                out.println("<td class='datos'>"+tp.getIdtipos()+"</td>");
                out.println("<td class='datos'>"+tp.getDescription()+"</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</br>");
            out.println("<a href=\"agregarTipos.jsp\"> Seguir agregando </a>");
            out.println("</br>");
            out.println("<a href=\"index.jsp\">Regresar al inicio</a>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
