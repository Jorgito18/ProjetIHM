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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projetihm.backend.tables.LigueTableModel;
import projetihm.backend.tables.TablePlayers;

/**
 * FXML Controller class
 *
 * @author Jorge Ara
 */
public class ClassementsController implements Initializable {
    @FXML private TableView<LigueTableModel> tableProligue;
    @FXML private TableColumn<LigueTableModel, String> colNameProligue;
    @FXML private TableColumn<LigueTableModel, Integer> colPtsProligue;
    @FXML private TableColumn<LigueTableModel, Integer> colVictProligue;
    
    @FXML private TableView<LigueTableModel> tableStarligue;
    @FXML private TableColumn<LigueTableModel, String> colNameStarligue;
    @FXML private TableColumn<LigueTableModel, Integer> colPtsStarligue;
    @FXML private TableColumn<LigueTableModel, Integer> colVictStarligue;
    @FXML private ImageView retour;
    @FXML private ImageView toCalendriers;
    
    @FXML private TableView<TablePlayers> tablePlayers;
    @FXML private TableColumn<TablePlayers, String> colPlayersOne;

    @FXML private TableView<TablePlayers> tablePlayers2;
    @FXML private TableColumn<TablePlayers, String> colPlayersTwo;
    
    @FXML private TableColumn<TablePlayers, String> colNumeroOne;
    @FXML private TableColumn<TablePlayers, String> colButsOne;
    @FXML private TableColumn<TablePlayers, String> colNumeroTwo;
    @FXML private TableColumn<TablePlayers, String> colButsTwo;
    
    @FXML private ImageView help;
    
    public static final String PROLIGUE_DRIVER_PATH = "jdbc:sqlite:PROLIGUE_DB.db";
    public static final String STARLIGUE_DRIVER_PATH = "jdbc:sqlite:STARLIGUE_DB.db";
    public static final String LOGIN = "/projetihm/frontend/Login.fxml";
    public static final String CALENDRIERS = "/projetihm/frontend/Calendriers.fxml";
    public static final String MATCH_DIRECT = "/projetihm/frontend/Match.fxml";
    public static final String STATISTICS = "/projetihm/frontend/Stats.fxml";
    
    @FXML
    public void openAsStatistics() {
        redirectFromClassements(STATISTICS);
    }

    @FXML
    public void openAsCalendriers() {
        redirectFromClassements(CALENDRIERS);
    }
    
    @FXML
    public void openAsDirect() {
        redirectFromClassements(MATCH_DIRECT);
    }
    @FXML
    public void openAsLogin() {
        redirectFromClassements(LOGIN);
    }
    
    @FXML
    public void redirectFromClassements(String windowPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(windowPath));
            fxmlLoader.load();

            Parent parent = fxmlLoader.getRoot();
            Stage stage = new Stage(StageStyle.DECORATED);
            Stage mainStage = (Stage) retour.getScene().getWindow();
            
            stage.setTitle("Association Française d'Handball");
            stage.getIcons().add(new Image("projetihm/images/lnh-logo_petit.png"));
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
        String s ="Classement des equipes de differentes ligues. \n \n"
                + "Montre aussi les statistiques des joueurs de chaque équipe et les meilleurs joueurs de chaque ligue";
        alert.setContentText(s);
        alert.show();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("projetihm/images/lnh-logo_petit.png"));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        help.setOnMouseClicked(e -> showHelp());
        
        retrieveDataProligue(PROLIGUE_DRIVER_PATH);
        retrieveDataStarligue(STARLIGUE_DRIVER_PATH);
        retrieveTablePlayers(PROLIGUE_DRIVER_PATH);
        retrieveTablePlayersTwo(PROLIGUE_DRIVER_PATH);
    }
    
    public void retrieveDataProligue(String driverPath) {
        ObservableList<LigueTableModel> list = FXCollections.observableArrayList();

        /* establish connection */
        try (Connection connection = DriverManager.getConnection(driverPath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from EQUIPES")) {

            /* get data from database */
            while (resultSet.next()) {
                list.add(new LigueTableModel(resultSet.getString("NOM_EQUIPE"), resultSet.getInt("PTS"),
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
        ObservableList<LigueTableModel> list = FXCollections.observableArrayList();

        /* establish connection */
        try (Connection connection = DriverManager.getConnection(driverPath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from EQUIPES")) {

            /* get data from database */
            while (resultSet.next()) {
                list.add(new LigueTableModel(resultSet.getString("NOM_EQUIPE"), resultSet.getInt("PTS"),
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
    
    
    public void retrieveTablePlayers(String driverPath) {
        ObservableList<TablePlayers> list = FXCollections.observableArrayList();

        /* establish connection */
        try (Connection connection = DriverManager.getConnection(driverPath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select NOM, NUMERO, BUTS from TABLE_TOULOUSE")) {

            /* get data from database */
            while (resultSet.next()) {
                list.add(new TablePlayers(resultSet.getString("NOM"), resultSet.getInt("NUMERO"), resultSet.getInt("BUTS")));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        /* display retrieved data from database in TableView columns */
        try {

            colPlayersOne.setCellValueFactory(new PropertyValueFactory<>("name"));
            colNumeroOne.setCellValueFactory(new PropertyValueFactory<>("numero"));
            colButsOne.setCellValueFactory(new PropertyValueFactory<>("goals"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tablePlayers.setItems(list); 
    }
    
        public void retrieveTablePlayersTwo(String driverPath) {
        ObservableList<TablePlayers> list = FXCollections.observableArrayList();

        /* establish connection */
        try (Connection connection = DriverManager.getConnection(driverPath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select NOM, NUMERO, BUTS from TABLE_PARIS")) {

            /* get data from database */
            while (resultSet.next()) {
                list.add(new TablePlayers(resultSet.getString("NOM"), resultSet.getInt("NUMERO"), resultSet.getInt("BUTS")));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        /* display retrieved data from database in TableView columns */
        try {
            colPlayersTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
            colNumeroTwo.setCellValueFactory(new PropertyValueFactory<>("numero"));
            colButsTwo.setCellValueFactory(new PropertyValueFactory<>("goals"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tablePlayers2.setItems(list); 
    }
}

