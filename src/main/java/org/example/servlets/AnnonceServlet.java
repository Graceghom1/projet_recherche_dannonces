package org.example.servlets;

import org.example.data.DatabaseHelper;
import org.example.utils.Annonce;

import javax.servlet.*;
        import javax.servlet.http.*;
        import java.io.IOException;

public class AnnonceServlet extends HttpServlet {

    // Méthode doGet pour afficher une annonce spécifique
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'ID de l'annonce depuis la requête (paramètre URL)
        //String annonceId = request.getParameter("id");

        Annonce annonce = new Annonce("Développeur Java - Télétravail", "http://example.com/annonce1", "images/tooo.jpg", "2024-11-28 12:00:00");

            // Récupérer l'annonce depuis la base de données (métode à implémenter)
            //Annonce annonce = DatabaseHelper.getAnnonceById(annonceId);

            if (annonce != null) {
                // Ajouter l'annonce à l'attribut de la requête pour l'affichage dans la JSP
                request.setAttribute("annonce", annonce);

                // Rediriger vers la page JSP pour afficher l'annonce
                RequestDispatcher dispatcher = request.getRequestDispatcher("/annonceDetail.jsp");
                dispatcher.forward(request, response);
            } else {
                // Si l'annonce n'est pas trouvée, afficher une erreur
                response.getWriter().write("Annonce non trouvée.");
            }
    }
}
