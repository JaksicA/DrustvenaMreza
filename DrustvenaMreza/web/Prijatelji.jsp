<%@page import="java.util.ArrayList"%>
<%@page import="Model.Korisnici"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css"  href="Izgled/newcss.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prijatelji</title>
    </head>
    <body>
        <%String Greska = (String) request.getAttribute("Greska");

            ArrayList<Korisnici> Lista = (ArrayList<Korisnici>) request.getAttribute("Lista");
        %>
        <% if (Greska != null) {

        %><%=Greska%><%

    }%>
    <table class="Lista1">
        <tr class="Naziv">
            <th>Ime</th>
            <th>Prezime</th>
            <th>Godine</th>
            <th>Pol</th> 
        </tr>
        <%for(Korisnici korisnik:Lista){
            %><tr class="Slovo">
            <td><%=korisnik.getIme()%></td>
            <td><%=korisnik.getPrezime()%></td>
            <td><%=korisnik.getGodine()%></td>
            <td><%=korisnik.getPol()%></td>
            
        </tr><%
        }      
        %>           
    </table>

    </body>
</html>
