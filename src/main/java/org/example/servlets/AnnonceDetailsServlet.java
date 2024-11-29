package org.example.servlets;

import org.example.utils.Annonce;
import org.example.data.DatabaseHelper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AnnonceDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String annonceId = request.getParameter("id");

        if (annonceId != null) {
            // Récupérer l'annonce spécifique depuis la base de données
            Annonce annonce = DatabaseHelper.getAnnonceById(annonceId);

            if (annonce != null) {
                // Passer l'annonce à la JSP pour afficher les détails
                request.setAttribute("annonce", annonce);
                RequestDispatcher dispatcher = request.getRequestDispatcher("annonceDetails.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().write("Annonce non trouvée.");
            }
        } else {
            response.getWriter().write("ID de l'annonce manquant.");
        }
    }
}
