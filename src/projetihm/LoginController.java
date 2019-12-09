/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
    public static final String CLASEMENTS = "Classements.fxml";
    public static final String MATCH_DIRECT = "Match.fxml";

    @FXML
    public void openAsClassement() {
        redirectFromLogin(CLASEMENTS);
    }

    @FXML
    public void openAsDirect() {
        redirectFromLogin(MATCH_DIRECT);
    }

    @FXML
    public void redirectFromLogin(String windowPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(windowPath));
            fxmlLoader.load();

            Parent parent = fxmlLoader.getRoot();
            Stage stage = new Stage(StageStyle.DECORATED);

            stage.setTitle("Association Française d'Handball");
            stage.setScene(new Scene(parent));
            stage.setResizable(false);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
