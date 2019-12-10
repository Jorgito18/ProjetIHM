package projetihm.backend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
    public static final String CLASEMENTS = "/projetihm/frontend/Classements.fxml";
    public static final String MATCH_DIRECT = "/projetihm/frontend/Match.fxml";
    @FXML private Button classements;
    
    @FXML
    public void openAsClassement() {
        redirectFromLogin(CLASEMENTS);
    }

    @FXML
    public void openAsDirect() {
        redirectFromLogin(MATCH_DIRECT);
    }
    @FXML
    public void showHelp(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Aide");
        alert.setHeaderText("Page d'accueil");
        String s ="Chosir entre le mode classements nationaux pour voir les clasemets et statistique des equipes et jouers, "
                + "ou bien match en direct pour manipuler les donnes en direct ";
        alert.setContentText(s);
        alert.show();
    }
    @FXML
    public void redirectFromLogin(String windowPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(windowPath));
            fxmlLoader.load();

            Parent parent = fxmlLoader.getRoot();
            Stage stage = new Stage(StageStyle.DECORATED);
            Stage mainStage = (Stage) classements.getScene().getWindow();

            stage.setTitle("Association Française d'Handball");
            stage.setScene(new Scene(parent));
            //stage.setResizable(false);
            
            stage.show();
            mainStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
