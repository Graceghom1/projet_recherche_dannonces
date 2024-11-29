package org.example.data;


import org.example.utils.Annonce;
import org.example.wrappers.Wrapper;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.*;

public class RecuperationPeriodique {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = new Runnable() {
            public void run() {
                // Appeler la méthode pour récupérer les annonces
                System.out.println("Récupérer les annonces...");
                // Exemple d'appel à un wrapper
                Wrapper scraper = new Wrapper();
                List<Annonce> annonces = scraper.recupererAnnonces("science", "siteChoisi");


                // Sauvegarder les annonces récupérées dans la base de données
                for (Annonce annonce : annonces) {

                    // Créer la date actuelle
                    java.util.Date date = new Date(System.currentTimeMillis());
                    String formattedDate = DatabaseHelper.formatDateToMySQL(date);
                    DatabaseHelper.sauvegarderAnnonce(annonce.getTitre(), annonce.getLien(), annonce.getImage(), "Books to Scrape", formattedDate);
                }
            }
        };

        // Planifier la tâche toutes les 10 secondes (par exemple)
        scheduler.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
    }
}
