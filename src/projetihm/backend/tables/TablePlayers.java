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
public class TablePlayers {
    String name;
    Integer numero;
    Integer goals;

    public TablePlayers(String name, Integer numero, Integer goals) {
        this.name = name;
        this.numero = numero;
        this.goals = goals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

}
