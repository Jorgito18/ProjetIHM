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
public interface TableModel {
    public String getTeamName();
    public void setTeamName(String teamName);
    public Integer getPts();
    public void setPts(Integer pts);
    public Integer getVict();
    public void setVict(Integer vict);
    public void retrieveData();
}
