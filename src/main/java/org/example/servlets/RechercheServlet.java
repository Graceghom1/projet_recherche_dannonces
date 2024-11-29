package org.example.servlets;

import org.example.data.DatabaseHelper;
import org.example.utils.Annonce;

import javax.servlet.*;
        import javax.servlet.http.*;
        import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RechercheServlet extends HttpServlet {

   /* private List<Annonce> annoncesFictives = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // Initialiser les annonces fictives pour la démonstration
        annoncesFictives.add(new Annonce("Développeur Java - Télétravail", "http://example.com/annonce1", "tooo.jpg", "2024-11-28 12:00:00"));
        annoncesFictives.add(new Annonce("Développeur Python - Full Stack", "http://example.com/annonce2", "tooo.jpg", "2024-11-28 12:00:00"));
        annoncesFictives.add(new Annonce("Chef de projet - Gestion d'équipe", "http://example.com/annonce3", "tooo.jpg", "2024-11-28 12:00:00"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String motsCles = request.getParameter("mots_cles");
        if (motsCles == null) {
            motsCles = "";
        }

        // Si un mot-clé est fourni, filtrer les annonces en fonction du mot-clé
        List<Annonce> resultats = new ArrayList<>();
        for (Annonce annonce : annoncesFictives) {
            if (annonce.getTitre().toLowerCase().contains(motsCles.toLowerCase())) {
                resultats.add(annonce);
            }
        }

        // Si un id d'annonce est fourni, afficher les détails
        String annonceId = request.getParameter("id");
        if (annonceId != null) {
            for (Annonce annonce : annoncesFictives) {
                if (annonce.getLien().equals(annonceId)) {
                    request.setAttribute("annonce", annonce);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/annonceDetail.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            }
        }

        // Ajouter la liste des résultats de recherche comme attribut à la requête
        request.setAttribute("annonces", resultats);

        // Rediriger vers la page JSP pour afficher les résultats
        RequestDispatcher dispatcher = request.getRequestDispatcher("/annonces.jsp");
        dispatcher.forward(request, response);
    }*/

    // Méthode doGet pour gérer les requêtes GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String motsCles = request.getParameter("mots_cles");
        if (motsCles == null || motsCles.isEmpty()) {
            motsCles = "";
        }

        // Liste d'annonces fictives
        List<Annonce> annonces = new ArrayList<>();
        annonces.add(new Annonce("Développeur Java - Télétravail", "https://fr.search.yahoo.com/search?fr=mcafee&type=E210FR714G0&p=leboncoin", "images/tooo.jpg", "2024-11-28 12:00:00"));
        annonces.add(new Annonce("Développeur Python - Full Stack", "https://fr.search.yahoo.com/search?fr=mcafee&type=E210FR714G0&p=leboncoin", "http://example.com/image2.jpg", "2024-11-28 12:30:00"));

        // Filtrer les annonces par mot-clé
        List<Annonce> resultats = new ArrayList<>();
        for (Annonce annonce : annonces) {
            if (annonce.getTitre().toLowerCase().contains(motsCles.toLowerCase())) {
                resultats.add(annonce);
            }
        }


        // Rechercher les annonces avec les mots-clés
      // List<Annonce> annonces = DatabaseHelper.rechercherAnnonces(motsCles);

        // Ajouter les annonces à l'attribut de la requête pour l'affichage dans la JSP
        request.setAttribute("annonces", annonces);

        // Rediriger vers la page JSP pour afficher les résultats
        RequestDispatcher dispatcher = request.getRequestDispatcher("/annonces.jsp");
        dispatcher.forward(request, response);
    }
}
