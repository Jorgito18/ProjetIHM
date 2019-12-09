package projetihm.backend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projetihm.backend.sql.SqlConnectionImpl;

/**
 *
 * @author jaespes
 */
public class ProjetIHM extends Application {
    private static final String TABLE_VISITORS = "visitant";
    private static final String TABLE_LOCAL = "local";
    
    
    @Override
    public void start(Stage stage) throws Exception { 
        
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Parent root = FXMLLoader.load(getClass().getResource("/projetihm/frontend/Login.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Association Française d'Handball");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
