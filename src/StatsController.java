/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm.backend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author jaespes
 */
public class StatsController implements Initializable {
    @FXML private PieChart chartLocal;
    @FXML private PieChart chartVisitor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       initChartLocal();
       initChartVisitor();
    }    
    
    private void initChartLocal() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                                    new PieChart.Data("Tirs", 35),
                                    new PieChart.Data("Buts", 7),
                                    new PieChart.Data("Cartes jaunes", 2),
                                    new PieChart.Data("Expulsions", 2),
                                    new PieChart.Data("Disqualifications", 1));
        chartLocal.setData(pieChartData);
        chartLocal.setTitle("Equipe Locale");
        
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
        chartVisitor.setTitle("Equipe Visitant");
        
        pieChartData.forEach(data ->
            data.nameProperty().bind(
                Bindings.concat(
                    data.getName(), " ", data.pieValueProperty(), ""
                    )
            )
        );   
    }
    
}
