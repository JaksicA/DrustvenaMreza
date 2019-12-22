package servlet;

import Model.Korisnici;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Jaksa
 */
public class SviKorisnici extends HttpServlet {

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
        
        ArrayList<Korisnici> ListaKorisnika = new ArrayList<Korisnici>();
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection konekcija = DriverManager.getConnection("jdbc:mysql://localhost:3306/DrustvenaMreza","root","");
            
            Statement stmt = konekcija.createStatement();
            
            String upit = "SELECT * FROM KORISNIK";
            
            ResultSet rs = stmt.executeQuery(upit);
            
            while(rs.next()){
                
                ListaKorisnika.add(new Korisnici(rs.getInt("ID"), rs.getString("Ime"), rs.getString("Prezime"), rs.getInt("Godine"),rs.getString("Pol"),
                        rs.getString("Prijatelji")));
            }
            
            request.setAttribute("Lista", ListaKorisnika);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            
            
            
            
            
        }
        
        catch(ClassNotFoundException e){
            request.setAttribute("Greska", e.toString());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        catch(SQLException sqle){
            request.setAttribute("Greska", sqle.toString());
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
