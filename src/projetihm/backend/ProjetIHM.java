package projetihm.backend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


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
        stage.getIcons().add(new Image("projetihm/images/lnh-logo_petit.png"));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
