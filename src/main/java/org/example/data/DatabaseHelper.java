package org.example.data;

import org.example.utils.Annonce;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper {
    //String strClassName = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/rechercheannonce";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Méthode pour formater la date en format MySQL compatible
    public static String formatDateToMySQL(java.util.Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);  // Formater la date en chaîne
    }

    public static void main(String[] args) {
        Date date = new Date(System.currentTimeMillis());  // Utiliser la date actuelle
        String formattedDate = formatDateToMySQL(date);
        System.out.println(formattedDate);
    }


    public static void loadDriver() {
        try {
            // Charger dynamiquement le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");  // Charger le driver MySQL
            System.out.println("Driver MySQL chargé avec succès.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Erreur : Le driver MySQL n'a pas été trouvé.");
        }
    }


    // Sauvegarder une annonce dans la base de données
    public static void sauvegarderAnnonce(String titre, String lien, String image, String site, String dateRecuperation) {
        System.out.println("Tentative d'insertion de l'annonce...");
        System.out.println("Titre: " + titre + ", Lien: " + lien + ", Image: " + image + ", Site: " + site + ", Date: " + dateRecuperation);

        // Vérifie si les données sont valides
        if (titre == null || lien == null || image == null || site == null || dateRecuperation == null) {
            System.out.println("Les paramètres d'insertion contiennent des valeurs nulles.");
            return; // Si des paramètres sont null, on arrête l'insertion
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            loadDriver();
            String sql = "INSERT INTO annonces (titre, lien, image, site, date_recuperation) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, titre);
                stmt.setString(2, lien);
                stmt.setString(3, image);
                stmt.setString(4, site);
                stmt.setString(5, dateRecuperation);


                int rowsAffected = stmt.executeUpdate();  // Exécuter la requête d'insertion
                if (rowsAffected > 0) {
                    System.out.println("Annonce insérée avec succès.");
                } else {
                    System.out.println("Aucune annonce insérée.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur d'insertion : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Sauvegarder une recherche dans la base de données
    public static void sauvegarderRecherche(String motsCles, String sites, int frequence) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO recherches (mots_cles, sites, frequence) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, motsCles);
                stmt.setString(2, sites);
                stmt.setInt(3, frequence);
                stmt.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir la connexion à la base de données
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Charger le driver MySQL si nécessaire
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL chargé.");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la connexion à la base de données.");
            e.printStackTrace();
        }
        return conn;
    }

    // Récupérer toutes les recherches de la base
    public static List<String> recupererRecherches() {
        List<String> recherches = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM recherches";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    recherches.add(rs.getString("mots_cles"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recherches;
    }

    // Ajouter la méthode rechercherAnnonces
    public static List<Annonce> rechercherAnnonces(String motsCles) {
        List<Annonce> annonces = new ArrayList<>();

        // Connexion à la base de données
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Requête SQL pour rechercher des annonces par mots-clés dans le titre ou la description
            String sql = "SELECT * FROM annonces WHERE titre LIKE ? OR titre LIKE ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Utiliser les mots-clés pour effectuer une recherche partielle (LIKE)
                stmt.setString(1, "%" + motsCles + "%");
                stmt.setString(2, "%" + motsCles + "%");

                // Exécuter la requête
                ResultSet rs = stmt.executeQuery();

                // Parcourir les résultats et créer des objets Annonce
                while (rs.next()) {
                    String titre = rs.getString("titre");
                    String lien = rs.getString("lien");
                    String image = rs.getString("image");
                    String dateRecuperation = rs.getString("date_recuperation");

                    // Ajouter l'annonce à la liste
                    Annonce annonce = new Annonce(titre, lien, image, dateRecuperation);
                    annonces.add(annonce);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retourner la liste des annonces trouvées
        return annonces;
    }

    // Ajouter la méthode getAnnonceById à DatabaseHelper
    public static Annonce getAnnonceById(String id) {
        Annonce annonce = null;
        Connection conn = getConnection();

        if (conn == null) {
            System.err.println("Erreur : La connexion à la base de données n'a pas pu être établie.");
            return null; // Retourner null si la connexion échoue
        }

        String sql = "SELECT * FROM annonces WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);  // Passer l'ID de l'annonce
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Créer un objet Annonce avec les données de la base de données
                String titre = rs.getString("titre");
                String lien = rs.getString("lien");
                String image = rs.getString("image");
                String dateRecuperation = rs.getString("date_recuperation");

                annonce = new Annonce(titre, lien, image, dateRecuperation);
            } else {
                System.out.println("Annonce non trouvée pour l'ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la récupération de l'annonce par ID : " + e.getMessage());
        }

        return annonce;
    }

    // Supprimer une annonce par son lien
    public static boolean supprimerAnnonceParLien(String lien) {
        boolean isDeleted = false;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "DELETE FROM annonces WHERE lien = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, lien);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    isDeleted = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }


}

