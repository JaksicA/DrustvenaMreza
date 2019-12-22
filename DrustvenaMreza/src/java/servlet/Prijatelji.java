package servlet;

import Model.Korisnici;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jaksa
 */
public class Prijatelji extends HttpServlet {

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
        String uzmiID = request.getParameter("id");
        String parametar = request.getParameter("friends");
        String Prijatelji = "";
        String PrijateljiNiz[] = new String[1];
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection konekcija = DriverManager.getConnection("jdbc:mysql://localhost:3306/DrustvenaMreza","root","");
            
            Statement stmt = konekcija.createStatement();
            
            String upit = "SELECT * FROM KORISNIK WHERE ID = "+uzmiID;
            
            ResultSet rs = stmt.executeQuery(upit);
            
            if(rs.next()){
                
                
                Prijatelji = rs.getString("Prijatelji");
            }
            //Direktni prijatelji
            if(parametar.equals("DirectFriends")){
                
                PrijateljiNiz = VratiNiz(Prijatelji);
                for(String frend:PrijateljiNiz){
                    String upit1 = "SELECT * From KORISNIK where id = "+frend;
                    rs = stmt.executeQuery(upit1);
                    
                if(rs.next()){
                    ListaKorisnika.add(new Korisnici(rs.getInt("id"), rs.getString("Ime"), rs.getString("Prezime"), rs.getInt("Godine"), rs.getString("pol"),
                            rs.getString("Prijatelji")));
                }
              }                
            }
            //Prijatelji prijatelja
            if(parametar.equals("Fof")){
                
                ArrayList<Korisnici> korisnici = new ArrayList<Korisnici>();
                 
                    PrijateljiNiz= VratiNiz(Prijatelji);
                    
                    for(String frend:PrijateljiNiz){
                        String upit1 ="SELECT * FROM korisnik WHERE id = "+frend;
                        rs = stmt.executeQuery(upit1);
                        
                      if(rs.next()){
                      
                          korisnici.add(new Korisnici(rs.getInt("id"), rs.getString("Ime"), rs.getString("Prezime"), rs.getInt("Godine"), rs.getString("pol"),
                            rs.getString("Prijatelji")));   
                      }
                    }
                    for(Korisnici korisnik:korisnici){
                        String upit2 = "SELECT * FROM korisnik WHERE id = " +korisnik.getId();
                        rs =stmt.executeQuery(upit2);
                        if(rs.next()){
                            Prijatelji = rs.getString("Prijatelji");                         
                        }
                        PrijateljiNiz = VratiNiz(Prijatelji);
                        for(String p:PrijateljiNiz){
                            
                            String upit3 = "SELECT * FROM korisnik WHERE id = "+ p;
                            rs = stmt.executeQuery(upit3);
                            
                            if(rs.next()){
                                ListaKorisnika.add(new Korisnici(rs.getInt("id"), rs.getString("Ime"), rs.getString("Prezime"), rs.getInt("Godine"), rs.getString("pol"),
                            rs.getString("Prijatelji")));
                            }
                        }
                        for(int i =0;i<ListaKorisnika.size();i++){
                            String NizKorisnika[]= VratiNiz(ListaKorisnika.get(i).getPrijatelji());
                                 for(String n:NizKorisnika){
                                     if(n.equals(uzmiID)){
                                        ListaKorisnika.remove(ListaKorisnika.get(i));
                                            i-=1;
                                 }
                             }     
                        }
                    }      
            }
            //Predlozeni prijatelji
            if(parametar.equals("Suggested")){
                
                ArrayList<Korisnici> OstaliKorisnici = new ArrayList<Korisnici>();
          
                rs = stmt.executeQuery(upit);
                if(rs.next()){
                    Prijatelji = rs.getString("Prijatelji"); 
                }
                PrijateljiNiz=VratiNiz(Prijatelji);
                
                String upit2 = "SELECT * FROM korisnik WHERE id != "+uzmiID;
                rs = stmt.executeQuery(upit2);
                
                while(rs.next()){
                    
                    OstaliKorisnici.add(new Korisnici(rs.getInt("id"), rs.getString("Ime"), rs.getString("Prezime"), rs.getInt("Godine"), rs.getString("pol"),
                            rs.getString("Prijatelji")));
                }
                for(Korisnici k:OstaliKorisnici){
                    String svaki = k.getPrijatelji();
                    String ostali[] = new String[1];
                    ostali = VratiNiz(svaki);
                    int brojac =0 ;
                    for(String svi:ostali){
                        for(String izabrani:PrijateljiNiz){

                            if(svi.equals(izabrani)&&!svi.equals(uzmiID)){
                                brojac++;
                            }
                        }
                    }
                   if(brojac>=2){
                       ListaKorisnika.add(k);
                       brojac=0;
                       ostali=new String[1];
                   }
                }
                
            }
            for(int i =0;i<ListaKorisnika.size();i++){
                for(int j=i+1;j<ListaKorisnika.size();j++){
                    if(ListaKorisnika.get(i).getId()==ListaKorisnika.get(j).getId()){
                        ListaKorisnika.remove(ListaKorisnika.get(i));
                        i -= 1;
                    }
                }
                if(ListaKorisnika.get(i).getId()== Integer.parseInt(uzmiID)){
                    ListaKorisnika.remove(ListaKorisnika.get(i));
                }
                String NizKorisnika[]= VratiNiz(ListaKorisnika.get(i).getPrijatelji());
                                 for(String n:NizKorisnika){
                                     if(n.equals(uzmiID)){
                                        ListaKorisnika.remove(ListaKorisnika.get(i));
                                            i-=1;
                    }
                 }
            }
           
            request.setAttribute("Lista", ListaKorisnika);
            request.getRequestDispatcher("Prijatelji.jsp").forward(request, response);  
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

    //metoda za razbijanje Stringa po zarezima iz kolone prijalji
    String[] VratiNiz(String Prijatelji){
        String NizPrijatelja[] = new String[1];
        if(Prijatelji.contains(",")){
            NizPrijatelja=Prijatelji.split(",");
            return NizPrijatelja;
        }
        else {
        
            NizPrijatelja[0]=Prijatelji;
            return NizPrijatelja;
        }
    }
}

