<%@ page import="org.example.utils.Annonce" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails de l'Annonce</title>
</head>
<body>

<h1>Détails de l'Annonce</h1>

<%-- Afficher les détails de l'annonce --%>
<%-- On suppose que l'attribut "annonce" contient l'objet de l'annonce à afficher --%>
<% Annonce annonce = (Annonce) request.getAttribute("annonce"); %>
<table border="1">
    <tr>
        <th>Titre</th>
        <td>${annonce.titre}</td>
    </tr>

    <tr>
        <th>Image</th>
        <td><img src="${annonce.image}" alt="Image" width="100" height="100"></td>
    </tr>
    <tr>
        <th>Date de récupération</th>
        <td>${annonce.dateRecuperation}</td>
    </tr>
</table>

</body>
</html>
