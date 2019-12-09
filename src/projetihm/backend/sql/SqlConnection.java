/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm.backend.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Nikolay
 */
public interface SqlConnection {
    public String CONNECTION_URL_DIRECT_PART = "jdbc:sqlite:PROLIGUE_DB.db";
    
    public void createTable(String tableName);
}
