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
public class LigueTableModel {
    String teamName; 
    Integer pts;
    Integer vict;

    public LigueTableModel(String teamName, Integer pts, Integer vict) {
        this.teamName = teamName;
        this.pts = pts;
        this.vict = vict;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public Integer getPts() {
        return pts;
    }
    
    public void setPts(Integer pts) {
        this.pts = pts;
    }
    
    public Integer getVict() {
        return vict;
    }
        
    public void setVict(Integer vict) {
        this.vict = vict;
    }

    public void retrieveData() {

    }
  
}
