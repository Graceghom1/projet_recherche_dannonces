<%@ page import="org.example.utils.Annonce" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resultats de Recherche</title>
</head>
<body>

<h1>Merci de cliquer sur ce lien pour voir les details de l'annonce</h1>

<%-- Récupérer l'objet Annonce depuis l'attribut de requête --%>
<% Annonce annonce = (Annonce) request.getAttribute("annonce"); %>

<%-- Afficher les annonces --%>
<table border="1">
    <thead>
    <tr>
        <th>Titre</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="annonce" items="${annonces}">
        <tr>
            <td><a href="annoncesDetails?id=${annonce.lien}" target="_blank">Voir l'annonce</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
