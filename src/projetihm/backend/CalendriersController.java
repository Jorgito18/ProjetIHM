package projetihm.backend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class CalendriersController implements Initializable {
    public static final String LOGIN = "/projetihm/frontend/Login.fxml";
    public static final String CLASEMENTS = "/projetihm/frontend/Classements.fxml";
    @FXML private ImageView retour;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    public void openAsClassements() {
        redirectFromCalendriers(CLASEMENTS);
    }
    
    @FXML
    public void redirectFromCalendriers(String windowPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(windowPath));
            fxmlLoader.load();

            Parent parent = fxmlLoader.getRoot();
            Stage stage = new Stage(StageStyle.DECORATED);
            Stage mainStage = (Stage) retour.getScene().getWindow();
            
            stage.setTitle("Association Française d'Handball");
            stage.setScene(new Scene(parent));
            
            stage.show();
            mainStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    
    public void showHelp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aide");
        alert.setHeaderText("Calendriers");
        String s ="Cette fenetre montre le calendrier des match des ligues de handball.";
        alert.setContentText(s);
        alert.show();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("projetihm/images/lnh-logo_petit.png"));
    }
    
}
