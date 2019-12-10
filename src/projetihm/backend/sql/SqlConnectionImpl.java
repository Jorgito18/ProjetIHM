/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm.backend.sql;

import java.sql.*;
/**
 *
 * @author Nikolay
 */
public class SqlConnectionImpl implements SqlConnection {
    
    public void createTable(String tableName) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL_DIRECT_PART);
                Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName + " (ID INTEGER PRIMARY KEY, NOM VARCHAR(60),"
                    + " NUMERO INTEGER, EN_JEU VARCHAR(5), CARTES VARCHAR(20))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void createEquipe(String tableName) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL_DIRECT_PART);
                Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName + " (ID INTEGER PRIMARY KEY, NOM_EQUIPE VARCHAR(60),"
            + " PTS INTEGER, VICT INTEGER)");
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
}
