/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm;

/**
 *
 * @author jaespes
 */
public class Joueurs {
    private String nom;
    private String prenom;
    private String position;
    private String carte;
    private int numero;
    private int minutesAffectes;
    
    public Joueurs(String nom, String prenom, String position, String carte, int numero, int minutesAffectes) {
        this.nom = nom;
        this.prenom = prenom;
        this.position = position;
        this.carte = carte;
        this.numero = numero;
        this.minutesAffectes = minutesAffectes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCarte() {
        return carte;
    }

    public void setCarte(String carte) {
        this.carte = carte;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getMinutesAffectes() {
        return minutesAffectes;
    }

    public void setMinutesAffectes(int minutesAffectes) {
        this.minutesAffectes = minutesAffectes;
    }
}
