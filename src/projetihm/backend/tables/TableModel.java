/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm.backend.tables;

/**
 *
 * @author Nikolay
 */
public class TableModel {
    Integer id; 
    String nom;
    String prenom;
    Integer numero;
    Boolean enJeu;
    String cartes;
    
    public TableModel(Integer givenId, String givenNom, String givenPrenom, Integer givenNumero, Boolean estEnJeu, String givenCartes) {
        this.id = givenId;
        this.nom = givenNom;
        this.prenom = givenPrenom;
        this.numero = givenNumero;
        this.enJeu = estEnJeu;
        this.cartes = givenCartes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Boolean getEnJeu() {
        return enJeu;
    }

    public void setEnJeu(Boolean enJeu) {
        this.enJeu = enJeu;
    }

    public String getCartes() {
        return cartes;
    }

    public void setCartes(String cartes) {
        this.cartes = cartes;
    }
    
}
