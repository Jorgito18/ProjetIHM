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
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
    public static final String CLASEMENTS = "clasemments";
    public static final String MATCH_DIRECT = "direct";

    @FXML private Button btnOpenPompiers;

    @FXML
    public void openAsClassement() {
        launchLogIn(CLASEMENTS);
    }

    @FXML
    public void openAsDirect() {
        launchLogIn(MATCH_DIRECT);
    }

    @FXML
    public void launchLogIn(String departement) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/projetihm/FXMLDocument.fxml"));
            fxmlLoader.load();

            MatchController controller = fxmlLoader.getController();

            Parent parent = fxmlLoader.getRoot();
            Stage stage = new Stage(StageStyle.DECORATED);
            Stage loginStage = (Stage) btnOpenPompiers.getScene().getWindow();

            stage.setTitle("BCMS Application");
            stage.setScene(new Scene(parent));
            stage.setResizable(false);

            stage.show();
            loginStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
