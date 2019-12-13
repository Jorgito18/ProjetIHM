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
    @FXML private ImageView toStatistics;
    
    @FXML private TableView<TablePlayers> tablePlayers;
    @FXML private TableColumn<TablePlayers, String> colPlayersOne;

    @FXML private TableView<TablePlayers> tablePlayers2;
    @FXML private TableColumn<TablePlayers, String> colPlayersTwo;
    
    @FXML private TableColumn<TablePlayers, String> colNumeroOne;
    @FXML private TableColumn<TablePlayers, String> colButsOne;
    @FXML private TableColumn<TablePlayers, String> colNumeroTwo;
    @FXML private TableColumn<TablePlayers, String> colButsTwo;
    
    @FXML private ImageView help;
    
    @FXML
    public void openAsStatistics() {
        redirectFromClassements(Constants.STATISTICS);
    }

    @FXML
    public void openAsCalendriers() {
        redirectFromClassements(Constants.CALENDRIERS);
    }
    
    @FXML
    public void openAsDirect() {
        redirectFromClassements(Constants.MATCH_DIRECT);
    }
    @FXML
    public void openAsLogin() {
        redirectFromClassements(Constants.LOGIN);
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
        String s ="Classement des equipes de differentes ligues.";
        alert.setContentText(s);
        alert.show();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("projetihm/images/lnh-logo_petit.png"));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        help.setOnMouseClicked(e -> showHelp());
        List<TableColumn> columnsProligue = new ArrayList<>();
        columnsProligue.add(colNameProligue);
        columnsProligue.add(colPtsProligue);
        columnsProligue.add(colVictProligue);
        List<TableColumn> columnsStarligue = new ArrayList<>();
        columnsStarligue.add(colNameStarligue);
        columnsStarligue.add(colPtsStarligue);
        columnsStarligue.add(colVictStarligue);
        List<TableColumn> columnsPlayersLocal = new ArrayList<>();
        columnsPlayersLocal.add(colPlayersOne);
        columnsPlayersLocal.add(colNumeroOne);
        columnsPlayersLocal.add(colButsOne);
        List<TableColumn> columnsPlayersVisitor = new ArrayList<>();
        columnsPlayersVisitor.add(colPlayersTwo);
        columnsPlayersVisitor.add(colNumeroTwo);
        columnsPlayersVisitor.add(colButsTwo);

        retrieveLeague(tableProligue, Constants.PROLIGUE_DRIVER_PATH, columnsProligue);
        retrieveLeague(tableStarligue, Constants.STARLIGUE_DRIVER_PATH, columnsStarligue);
        retreievePlayers("TABLE_TOULOUSE", tablePlayers, columnsPlayersLocal);
        retreievePlayers("TABLE_PARIS", tablePlayers2, columnsPlayersVisitor);
    }
    
    public void retrieveLeague(TableView table, String driverPath, List<TableColumn> columns) {
        ObservableList<LigueTableModel> list = FXCollections.observableArrayList();
        List<String> columnNames = new ArrayList<>();
        columnNames.add("teamName");
        columnNames.add("pts");
        columnNames.add("vict");

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
            for (int i = 0; i < columns.size(); i++) {
                columns.get(i).setCellValueFactory(new PropertyValueFactory<>(columnNames.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.setItems(list); 
    }
    
    public void retreievePlayers(String tableName, TableView table, List<TableColumn> columns) {
        ObservableList<TablePlayers> list = FXCollections.observableArrayList();
        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("numero");
        columnNames.add("goals");

        /* establish connection */
        try (Connection connection = DriverManager.getConnection(Constants.PROLIGUE_DRIVER_PATH);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select NOM, NUMERO, BUTS from " + tableName)) {

            /* get data from database */
            while (resultSet.next()) {
                list.add(new TablePlayers(resultSet.getString("NOM"), resultSet.getInt("NUMERO"), resultSet.getInt("BUTS")));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        /* display retrieved data from database in TableView columns */
        try {
            for (int i = 0; i < columns.size(); i++) {
                columns.get(i).setCellValueFactory(new PropertyValueFactory<>(columnNames.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.setItems(list); 
    }
}

