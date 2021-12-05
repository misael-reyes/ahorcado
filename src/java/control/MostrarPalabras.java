/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/**
 * Servlet para mostrar todas las palabras que hay en la base de datos
 */

package control;

import acceso_datos.PalabrasJpaController;
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
import modelo.Palabras;

/**
 *
 * @author Misael
 */
@WebServlet(name = "MostrarPalabras", urlPatterns = {"/MostrarPalabras"})
public class MostrarPalabras extends HttpServlet {
    
    /**
     * al igual que mostrar tipos, necesitamos de los siguientes atributos
     */
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
            //inicializamos los atributos
            emf = Persistence.createEntityManagerFactory("juego2PU");
            PalabrasJpaController controlPalabras = new PalabrasJpaController(utx, emf);
            //retornamos las palabras
            List<Palabras> palabras = controlPalabras.findPalabrasEntities();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Mostrar palabras</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h3>Palabras</h3>");
            out.println("<table aling='center' width='60%' border=1>");
            //encabezado de la tabla
            out.println("<tr>"
                + "<td class='datos'>ID</td>"
                + "<td class='datos'>Palabra</td>"
                + "<td class='datos'>Nivel</td>"
                + "<td class='datos'>Tipo</td>"
                + "</tr>");
            for (Palabras p : palabras) {
                out.println("<tr>");
                out.println("<td class='datos'>"+p.getIdpalabra()+"</td>");
                out.println("<td class='datos'>"+p.getDescription()+"</td>");
                out.println("<td class='datos'>"+p.getNivel()+"</td>");
                out.println("<td class='datos'>"+p.getTipo().getDescription()+"</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</br>");
            out.println("<a href=\"agregarPalabras.jsp\"> Seguir agregando </a>");
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
