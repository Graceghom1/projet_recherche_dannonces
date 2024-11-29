package org.example.utils;


public class Annonce {
    private String titre;
    private String lien;
    private String image;
    private String dateRecuperation;

    public Annonce(String titre, String lien, String image, String dateRecuperation) {
        this.titre = titre;
        this.lien = lien;
        this.image = image;
        this.dateRecuperation = dateRecuperation;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateRecuperation() {
        return dateRecuperation;
    }

    public void setDateRecuperation(String dateRecuperation) {
        this.dateRecuperation = dateRecuperation;
    }

    @Override
    public String toString() {
        return "Annonce{" +
                "titre='" + titre + '\'' +
                ", lien='" + lien + '\'' +
                ", image='" + image + '\'' +
                ", dateRecuperation='" + dateRecuperation + '\'' +
                '}';
    }
}
