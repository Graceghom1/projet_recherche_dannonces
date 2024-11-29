package org.example.wrappers;

import org.example.utils.Annonce;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Wrapper {
    public List<Annonce> recupererAnnonces(String motsCles, String siteChoisi) {
        List<Annonce> annonces = new ArrayList<>();

        try {
            String url = "";

            // Choisir le site à scraper
            if ("books".equals(siteChoisi)) {
                url = "http://books.toscrape.com/";
            } else if ("quotes".equals(siteChoisi)) {
                url = "http://quotes.toscrape.com/";
            } else if ("w3schools".equals(siteChoisi)) {
                url = "https://www.w3schools.com/html/html_images.asp";  // URL du site w3schools
            } else {
                System.out.println("Site inconnu");
                return annonces;  // Retourner une liste vide si le site n'est pas valide
            }

            // Se connecter à la page et récupérer le contenu
            Document doc = Jsoup.connect(url).get();

            // Sélectionner tous les éléments contenant des annonces en fonction du site
            Elements annoncesElements = new Elements();

            if ("books".equals(siteChoisi)) {
                annoncesElements = doc.select(".product_pod");  // Sélectionner les livres
            } else if ("quotes".equals(siteChoisi)) {
                annoncesElements = doc.select(".quote");  // Sélectionner les citations
            } else if ("w3schools".equals(siteChoisi)) {
                annoncesElements = doc.select("img");  // Sélectionner les images du site W3Schools
            }

            System.out.println("Nombre d'annonces trouvées: " + annoncesElements.size());  // Débogage

            // Parcourir chaque annonce et extraire les informations
            for (Element annonce : annoncesElements) {
                String titre = "";
                String lien = "";
                String image = "";
                String dateRecuperation = java.time.LocalDate.now().toString();

                // Extraire les informations en fonction du site
                if ("books".equals(siteChoisi)) {
                    titre = annonce.select("h3 a").attr("title");
                    lien = "http://books.toscrape.com/" + annonce.select("h3 a").attr("href");
                    image = annonce.select("img").attr("src");
                } else if ("quotes".equals(siteChoisi)) {
                    titre = annonce.select(".text").text();  // Citation
                    lien = "http://quotes.toscrape.com" + annonce.select("a").attr("href");  // Lien vers la citation
                    image = "";  // Pas d'image sur ce site, donc laisser vide
                } else if ("w3schools".equals(siteChoisi)) {
                    titre = annonce.attr("alt");  // Le texte alternatif de l'image
                    lien = annonce.attr("src");  // Lien de l'image
                    image = annonce.attr("src");  // Récupérer le lien de l'image
                }

                // Sauvegarder l'annonce dans la base de données ou les retourner
                annonces.add(new Annonce(titre, lien, image, dateRecuperation));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return annonces;
    }

        /*try {
            // URL de la page de recherche d'annonces (ici Books to Scrape comme exemple)
            String url = "http://books.toscrape.com/";

            // Se connecter à la page et récupérer le contenu
            Document doc = Jsoup.connect(url).get();

            // Sélectionner tous les éléments contenant une annonce
            Elements annoncesElements = doc.select(".product_pod");
            System.out.println("Nombre d'annonces trouvées: " + annoncesElements.size());  // Débogage

            // Parcourir chaque annonce et extraire les informations
            for (Element annonce : annoncesElements) {
                String titre = annonce.select("h3 a").attr("title"); // Titre de l'annonce
                //String lien = annonce.select("h3 a").attr("href");
                String lien = "http://books.toscrape.com" + annoncesElements.attr("href");// Lien vers le livre
                String image = annonce.select("img").attr("src");
                String dateRecuperation = java.time.LocalDate.now().toString();

                // Sauvegarder l'annonce dans la base de données ou les retourner
                annonces.add(new Annonce(titre, lien, image, dateRecuperation));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return annonces;
    }*/

    /*public List<Annonce> rechercherAnnonces(String motsCles) {
        List<Annonce> annonces = new ArrayList<>();

        //Simuler les annonces récupérées
        if (motsCles.toLowerCase().contains("java")) {
            annonces.add(new Annonce("Développeur Java - Télétravail",
                    "https://www.leboncoin.fr/ad/photo_audio_video/2891721066",
                    "image1.jpg",
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            annonces.add(new Annonce("Emploi JavaScript - Télétravail",
                    "https://www.leboncoin.fr/ad/jeux_video/2891678493",
                    "image1.jpg",
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        } else {
            annonces.add(new Annonce("Autre annonce",
                    "http://leboncoin.com/annonce3",
                    "image1.jpg",
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

        }
        return annonces;
    }*/
}
