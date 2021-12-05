/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package juego;

import acceso_datos.PalabrasJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import modelo.Palabras;

/**
 *
 * @author Misael
 */
@WebServlet(name = "Ahorcado", urlPatterns = {"/Ahorcado"})
public class Ahorcado extends HttpServlet {
    
    /**
     * estos atributos necesitamos para poder acceder a la base de datos
     */
    @PersistenceUnit
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    
    private List<Palabras> palabrasEncontradas;
    
    private final static String[] PALABRAS = {"UNO", "ABCD", "OAXACA", "HAMACA"};
    //private Palabrass palabras;

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
        
        //LOGICA
        HttpSession sesion = request.getSession();
        //sesion.setAttribute("maxint", request.getParameter("nintento"));
        int maxint = Integer.parseInt( (String) sesion.getAttribute("maxint"));
        //atributos para la búsqueda
        int tipo = Integer.parseInt( (String) sesion.getAttribute("tipoo"));
        int nivel = Integer.parseInt( (String) sesion.getAttribute("nivell"));
        
        String palabra = (String) sesion.getAttribute("palabra");
        String aciertos;
        String errados;
        int acier = 0;
        int falla = 0;
        if (palabra == null) {
            Random oran = new Random();
            //inicimaos el emf para que eliga una palabra
            emf = Persistence.createEntityManagerFactory("juego2PU");
            PalabrasJpaController controlPalabras = new PalabrasJpaController(utx, emf);
            //hacemos la búsqueda de las palabras
            palabrasEncontradas = controlPalabras.findPalabras(tipo, nivel);
            if (palabrasEncontradas.size() == 0) 
                palabra = "DEFAULT";
            else
                palabra = palabrasEncontradas.get(oran.nextInt(palabrasEncontradas.size())).getDescription();
            
            aciertos = "";
            errados = "";
            sesion.setAttribute("palabra", palabra);
            sesion.setAttribute("aciertos", aciertos);
            sesion.setAttribute("errados", errados);
        } else {
            aciertos = (String) sesion.getAttribute("aciertos");
            errados = (String) sesion.getAttribute("errados");
            String letra = request.getParameter("letra");
            if (palabra.indexOf(letra) >= 0) {
                aciertos += letra;
            } else {
                errados += letra;
                falla ++;
                acier = maxint - falla;
            }
            sesion.setAttribute("aciertos", aciertos);
            sesion.setAttribute("errados", errados);
        }

        //VISTA
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet juego ahorcado </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h1> Suerte </h1>");
            out.println("<h2>Selecciona una letra </h2>");
            boolean terminado = true;
            out.println("<h2>");
            for (int i = 0; i < palabra.length(); i++) {
                String letra = palabra.substring(i, i + 1);
                if (aciertos.indexOf(letra) >= 0) {
                    out.println("" + letra);
                } else {
                    out.println("" + "_");
                    terminado = false;
                }
            }
            out.println("</h2>");
            if (maxint > errados.length()) {
                out.println("<br/><br/><br/>");
                for (char car = 'A'; car <= 'Z'; car++) {
                    if (aciertos.indexOf(car) == -1 && errados.indexOf(car) == -1) {
                        out.println("<a href=Ahorcado?letra=" + car + ">" + car + "</a>");
                    }
                }
                out.println("<h3>Total de intentos: " + maxint + "</h3>");
                out.println("<h3>Total de intentos fallados: " + falla + "</h3>");
                out.println("<h3>Total de intentos restantes: " + acier + "</h3>");
            } else {
                sesion.invalidate();
                out.println("<br /><h1> JUEGO TERMINADO </h1>");
                out.println("<br /><a href='index.jsp'>regresar</a>");
            }
            if (terminado) {
                sesion.invalidate();
                out.println("<br/><h1>JUEGO COMPLETO</h1>");
                out.println("<br/> <a href='index.jsp'>regresar</a>");
            }
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
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
