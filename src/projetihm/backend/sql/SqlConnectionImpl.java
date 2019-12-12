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
                    + " NUMERO INTEGER, TIRS INTEGER, BUTS INTEGER, CJAUNES, CROUGES, EXPULSIONS)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertData(String tableName, String nom, int numero, int tirs, int buts, int cjaunes, int crouge,
                            int nbexpulsions) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL_DIRECT_PART);
                Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO " + tableName + " (NOM, NUMERO, TIRS, BUTS, CJAUNES, CROUGES) VALUES "
                    + "(" + nom + ", " + numero + ", " + tirs + ", " + buts + ", " + cjaunes + ", " + crouge + "," + nbexpulsions + ")");
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
