<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>


<html>
<head>
    <meta charset="UTF-8">
    <title>Bienvenue sur le site d'annonces</title>
</head>
<body>
<h1>Bienvenue sur le site de recherche d'annonces</h1>
<form action="recherche" method="get">
    <label for="mots_cles">Entrez un mot-clé pour rechercher des annonces :</label>
    <input type="text" id="mots_cles" name="mots_cles" required placeholder="ex : Java" />
    <button type="submit">Rechercher</button>
</form>
</body>
</html>
