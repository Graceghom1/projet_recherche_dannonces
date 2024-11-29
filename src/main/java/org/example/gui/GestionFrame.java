package org.example.gui;

import org.example.utils.Annonce;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GestionFrame extends JFrame {
    private JButton supprimerButton;
    private JTable tableauResultats;
    private List<Annonce> annonces;

    public GestionFrame(List<Annonce> annonces) {
        this.annonces = annonces;
        setTitle("Gestion des Annonces");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Tableau des résultats à gérer
        String[] columnNames = {"Titre", "Lien", "Image", "Date"};
        Object[][] data = new Object[annonces.size()][4];
        for (int i = 0; i < annonces.size(); i++) {
            Annonce annonce = annonces.get(i);
            data[i][0] = annonce.getTitre();
            data[i][1] = annonce.getLien();
            data[i][2] = annonce.getImage();
            data[i][3] = annonce.getDateRecuperation();
        }
        tableauResultats = new JTable(data, columnNames);
        add(new JScrollPane(tableauResultats), BorderLayout.CENTER);

        //Panneau de gestion
        JPanel panlGestion = new JPanel();
        supprimerButton = new JButton("Supprimer l'annonce");
        supprimerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                supprimerAnnonce();
            }
        });
        panlGestion.add(supprimerButton);
        add(panlGestion, BorderLayout.SOUTH);
    }

    private void supprimerAnnonce() {
        int row = tableauResultats.getSelectedRow();
        if (row != -1) {
            annonces.remove(row);
            ((DefaultTableModel) tableauResultats.getModel()).removeRow(row);
        }
    }

    public static void main(String[] args) {
        List<Annonce> annonces = new ArrayList<>();
        annonces.add(new Annonce("Annonce 1", "https://www.leboncoin.fr/ad/photo_audio_video/2891721066", "image1.jpg", "2024-11-25 00:00:00"));
        annonces.add(new Annonce("Annonce 2", "https://www.leboncoin.fr/ad/jeux_video/2891678493", "image2.jpg", "2024-11-25 00:00:00"));

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GestionFrame(annonces).setVisible(true);
            }
        });
    }
}
