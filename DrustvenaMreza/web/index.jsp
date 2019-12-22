<%@page import="java.util.ArrayList"%>
<%@page import="Model.Korisnici"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css"  href="Izgled/css.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Drustvena Mreza</title>
    <h2 class="Naslov">SVI KORISNICI</h2>
    </head>
    <body>

        <% String Greska = (String) request.getAttribute("Greska");

            ArrayList<Korisnici> Lista = (ArrayList<Korisnici>) request.getAttribute("Lista");
        %>
        <%if (Greska != null) {

        %><%=Greska%><%

    }%>
    
    <table class="Lista">

        <tr class="Tabele">
            <th>ID</th>
            <th>Ime</th>
            <th>Prezime</th>
            <th>Direktni Prijatelji</th>
            <th>Prijatelji Prijatelja</th>
            <th>Predlozeni</th>
        </tr>
    <% for(Korisnici korisnik:Lista){
        
%><tr class="slova">
        <td><%=korisnik.getId()%></td>
        <td><%=korisnik.getIme()%></td>
        <td><%=korisnik.getPrezime()%></td>
        <td><a href="Prijatelji?id=<%=korisnik.getId()%>&friends=DirectFriends">Direct Friends</a></td>
        <td><a href="Prijatelji?id=<%=korisnik.getId()%>&friends=Fof">Friends of Friends</a></td>
        <td><a href="Prijatelji?id=<%=korisnik.getId()%>&friends=Suggested">Suggested </a></td>
        
    </tr><%
    }
        
    %>
    </table>

        
    </body>
</html>
