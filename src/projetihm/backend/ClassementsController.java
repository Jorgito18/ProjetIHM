package projetihm.backend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import projetihm.backend.tables.ProligueTableModel;
import projetihm.backend.tables.StarligueTableModel;

/**
 * FXML Controller class
 *
 * @author Jorge Ara
 */
public class ClassementsController implements Initializable {
    @FXML
    private TableView<ProligueTableModel> tableProligue;
    @FXML
    private TableColumn<ProligueTableModel, String> colNameProligue;
    @FXML
    private TableColumn<ProligueTableModel, Integer> colPtsProligue;
    @FXML
    private TableColumn<ProligueTableModel, Integer> colVictProligue;
    
    @FXML
    private TableView<StarligueTableModel> tableStarligue;
    @FXML
    private TableColumn<StarligueTableModel, String> colNameStarligue;
    @FXML
    private TableColumn<StarligueTableModel, Integer> colPtsStarligue;
    @FXML
    private TableColumn<StarligueTableModel, Integer> colVictStarligue;
    
    public ObservableList<ProligueTableModel> list = FXCollections.observableArrayList();
    
    public static final String PROLIGUE_DRIVER_PATH = "jdbc:sqlite:PROLIGUE_DB.db";
    public static final String STARLIGUE_DRIVER_PATH = "jdbc:sqlite:STARLIGUE_DB.db";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        retrieveDataProligue(PROLIGUE_DRIVER_PATH);
        retrieveDataStarligue(STARLIGUE_DRIVER_PATH);
    }

    public void retrieveDataProligue(String driverPath) {
        ObservableList<ProligueTableModel> list = FXCollections.observableArrayList();

        /* establish connection */
        try (Connection connection = DriverManager.getConnection(driverPath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from EQUIPES")) {

            /* get data from database */
            while (resultSet.next()) {
                list.add(new ProligueTableModel(resultSet.getString("NOM_EQUIPE"), resultSet.getInt("PTS"),
                    resultSet.getInt("VICT")));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        /* display retrieved data from database in TableView columns */
        try {

            colNameProligue.setCellValueFactory(new PropertyValueFactory<>("teamName"));
            colPtsProligue.setCellValueFactory(new PropertyValueFactory<>("pts"));
            colVictProligue.setCellValueFactory(new PropertyValueFactory<>("vict"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableProligue.setItems(list); 
    }
    
        public void retrieveDataStarligue(String driverPath) {
        ObservableList<StarligueTableModel> list = FXCollections.observableArrayList();

        /* establish connection */
        try (Connection connection = DriverManager.getConnection(driverPath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from EQUIPES")) {

            /* get data from database */
            while (resultSet.next()) {
                list.add(new StarligueTableModel(resultSet.getString("NOM_EQUIPE"), resultSet.getInt("PTS"),
                    resultSet.getInt("VICT")));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        /* display retrieved data from database in TableView columns */
        try {

            colNameStarligue.setCellValueFactory(new PropertyValueFactory<>("teamName"));
            colPtsStarligue.setCellValueFactory(new PropertyValueFactory<>("pts"));
            colVictStarligue.setCellValueFactory(new PropertyValueFactory<>("vict"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableStarligue.setItems(list); 
    }
}

