package org.example.gui;

import org.example.data.DatabaseHelper;
import org.example.utils.Annonce;
import org.example.wrappers.Wrapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.Timer;
import java.util.TimerTask;

public class Mainframe extends JFrame {
    private JTextField motsClesField;
    private JComboBox<String> sitesComboBox;
    public JTextField frequenceField;
    private JButton rechercheButton;
    private JTable tableauResultats;
    private JButton supprimerButton;
    private List<Annonce> annonces;

    public Mainframe() {
        setTitle("Recherche d'Annonces");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Panneau de saisie
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Mots-clés :"));
        motsClesField = new JTextField();
        panel.add(motsClesField);

        panel.add(new JLabel("Sélectionner le site :"));
        sitesComboBox = new JComboBox<>(new String[] {"books", "quotes", "w3schools"});
        panel.add(sitesComboBox);

        panel.add(new JLabel("Frequence (min) :"));
        frequenceField = new JTextField("5");
        panel.add(frequenceField);

        rechercheButton = new JButton("Lancer la recherche");
        rechercheButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                lancerRecherche();
            }
        });
        panel.add(rechercheButton);

        // Ajout d'un bouton pour supprimer une annonce
        supprimerButton = new JButton("Supprimer l'annonce sélectionnée");
        supprimerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                supprimerAnnonce();  // Supprimer l'annonce sélectionnée
            }
        });
        panel.add(supprimerButton);

        add(panel, BorderLayout.NORTH);

        //Tableau pour afficher les résultats
        String[] columnNames = {"Titre", "Lien", "Image", "Date"};
        Objects[][] data = {};
        tableauResultats = new JTable(data, columnNames);
        tableauResultats.setFillsViewportHeight(true);
        add(new JScrollPane(tableauResultats), BorderLayout.CENTER);
    }

    //Methode pour lancer la recherche d'annonces
    private void lancerRecherche() {
        String motscles = motsClesField.getText();
        String siteSelectionn = (String) sitesComboBox.getSelectedItem();
        int frequence = Integer.parseInt(frequenceField.getText());


        //Logique pour récupérer les annonces
        System.out.println("Recherche sur " + siteSelectionn + " avec les mots-clés: " + motscles);
        annonces = new ArrayList<>();

        // Appel du wrapper pour récupérer les annonces (peut être conditionné selon le site choisi)
        Wrapper wrapper = new Wrapper();
        annonces = wrapper.recupererAnnonces(motscles, siteSelectionn);

        //Mettre à jour le tableau avec les annonces récupérées
        Object[][] data = new Object[annonces.size()][4];
        for (int i = 0; i < annonces.size(); i++) {
            Annonce annonce = annonces.get(i);
            data[i][0] = annonce.getTitre();
            data[i][1] = annonce.getLien();
            data[i][2] = annonce.getImage();
            data[i][3] = annonce.getDateRecuperation();
        }
        tableauResultats.setModel(new DefaultTableModel(data, new String[]{"Titre", "Lien", "Image", "Date"}));

        // Logique pour récupérer les annonces (simulation ici)
        List<Annonce> annonces = new ArrayList<>();

        // Sauvegarder les annonces récupérées
        for (Annonce annonce : annonces) {
            DatabaseHelper.sauvegarderAnnonce(annonce.getTitre(), annonce.getLien(), annonce.getImage(), siteSelectionn, annonce.getDateRecuperation());
        }

        // Sauvegarder la recherche effectuée
        DatabaseHelper.sauvegarderRecherche(motscles, siteSelectionn, frequence);

        //ici
        // Mettre en place une tâche planifiée pour la mise à jour périodique
        schedulePeriodicUpdate(frequence, motscles, siteSelectionn);
    }

    // Méthode pour planifier la mise à jour périodique
    private void schedulePeriodicUpdate(int frequence, String motscles, String siteSelectionn) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Mise à jour périodique sur " + siteSelectionn + " avec les mots-clés: " + motscles);
                Wrapper wrapper = new Wrapper();
                annonces = wrapper.recupererAnnonces(motscles, siteSelectionn);

                // Mettre à jour le tableau avec les nouvelles annonces
                Object[][] data = new Object[annonces.size()][4];
                for (int i = 0; i < annonces.size(); i++) {
                    Annonce annonce = annonces.get(i);
                    data[i][0] = annonce.getTitre();
                    data[i][1] = annonce.getLien();
                    data[i][2] = annonce.getImage();
                    data[i][3] = annonce.getDateRecuperation();
                }
                tableauResultats.setModel(new DefaultTableModel(data, new String[]{"Titre", "Lien", "Image", "Date"}));

                // Sauvegarder les nouvelles annonces dans la base de données
                for (Annonce annonce : annonces) {
                    DatabaseHelper.sauvegarderAnnonce(annonce.getTitre(), annonce.getLien(), annonce.getImage(), siteSelectionn, annonce.getDateRecuperation());
                }
            }
        }, 0, frequence * 60 * 1000);  // Exécuter toutes les `frequence` minutes

    }

    // Méthode pour supprimer une annonce
    private void supprimerAnnonce() {
        int selectedRow = tableauResultats.getSelectedRow();
        if (selectedRow != -1) {
            String lienAnnonce = (String) tableauResultats.getValueAt(selectedRow, 1); // Récupérer le lien de l'annonce sélectionnée
            boolean deleted = DatabaseHelper.supprimerAnnonceParLien(lienAnnonce);

            if (deleted) {
                JOptionPane.showMessageDialog(this, "Annonce supprimée avec succès.");
                // Réactualiser le tableau des annonces
                lancerRecherche();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'annonce.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucune annonce sélectionnée.");
        }
    }

    // Méthode pour filtrer les annonces
    private void filtrerAnnonces() {
        String motscles = motsClesField.getText();
        String siteSelectionn = (String) sitesComboBox.getSelectedItem();

        // Filtrer les annonces selon les critères (par mots-clés et site)
        annonces = DatabaseHelper.rechercherAnnonces(motscles);

        // Mettre à jour le tableau avec les annonces filtrées
        Object[][] data = new Object[annonces.size()][4];
        for (int i = 0; i < annonces.size(); i++) {
            Annonce annonce = annonces.get(i);
            data[i][0] = annonce.getTitre();
            data[i][1] = annonce.getLien();
            data[i][2] = annonce.getImage();
            data[i][3] = annonce.getDateRecuperation();
        }
        tableauResultats.setModel(new DefaultTableModel(data, new String[]{"Titre", "Lien", "Image", "Date"}));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                new Mainframe().setVisible(true);
            }
        });
    }
}
