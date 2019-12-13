
package projetihm.backend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StatsController implements Initializable {
    @FXML private PieChart chartLocal;
    @FXML private PieChart chartVisitor;
    @FXML private ImageView retour;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       initChartLocal();
       initChartVisitor();
    }    
    
         @FXML
    public void openAsClassements() {
        redirectFromStats(Constants.CLASEMENTS);
    }
    
    @FXML
    public void redirectFromStats(String windowPath) {
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
        alert.setHeaderText("Page d'accueil");
        String s ="Les 2 pie charts montrent les statistiques plus remarcables de chaque équipe pour un match";
        alert.setContentText(s);
        alert.show();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("projetihm/images/lnh-logo_petit.png"));
    }
    
    private void initChartLocal() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                                    new PieChart.Data("Tirs", 35),
                                    new PieChart.Data("Buts", 7),
                                    new PieChart.Data("Cartes jaunes", 2),
                                    new PieChart.Data("Expulsions", 2),
                                    new PieChart.Data("Disqualifications", 1));
        chartLocal.setData(pieChartData);
        chartLocal.setTitle("PARIS");
        
        pieChartData.forEach(data ->
            data.nameProperty().bind(
                Bindings.concat(
                    data.getName(), " ", data.pieValueProperty(), ""
                    )
            )
        );     
    }       
    
    private void initChartVisitor() {
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                                    new PieChart.Data("Tirs", 27),
                                    new PieChart.Data("Buts", 5),
                                    new PieChart.Data("Cartes jaunes", 4),
                                    new PieChart.Data("Expulsions", 5),
                                    new PieChart.Data("Disqualifications", 1));
        chartVisitor.setData(pieChartData);
        chartVisitor.setTitle("TOULOUSE");
        
        pieChartData.forEach(data ->
            data.nameProperty().bind(
                Bindings.concat(
                    data.getName(), " ", data.pieValueProperty(), ""
                    )
            )
        );   
    }
    
}
