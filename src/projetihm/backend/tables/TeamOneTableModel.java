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
public class TeamOneTableModel {
    String name; 
    Integer number;
    String inGame;
    String cartes;

    public TeamOneTableModel(String name, Integer number, String inGame, String cartes) {
        this.name = name;
        this.number = number;
        this.inGame = inGame;
        this.cartes = cartes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getInGame() {
        return inGame;
    }

    public void setInGame(String inGame) {
        this.inGame = inGame;
    }

    public String getCartes() {
        return cartes;
    }

    public void setCartes(String cartes) {
        this.cartes = cartes;
    }
}
