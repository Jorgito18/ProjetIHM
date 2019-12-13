/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm.backend;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projetihm.backend.sql.SqlConnectionImpl;
import projetihm.backend.tables.AdministrationTableModel;
import projetihm.backend.tables.TeamTableModel;

/**
 *
 * @author jaespes
 */
public class MatchController implements Initializable {
    final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    private int localGoal = 0;
    private int visitorGoal = 0;
    private boolean threadFlag = false;
    private String lastMinuteValue = null;
    private String lastSecondsValue = null;
    
    private int cjaunesLocal = 0;
    private int crougesLocal = 0;
    private int nbtirsLocal = 0;
    private int nbexpulsionLocal = 0;
    
    private int cjaunesVisitor = 0;
    private int crougesVisitor = 0;
    private int nbtirsVisitor = 0;
    private int nbexpulsionVisitor = 0;
    
    @FXML private ImageView retour;
    @FXML private Label minute;
    @FXML private Label seconde;
    @FXML private Label labelLocalGoal;
    @FXML private Label labelVisitorGoal;
    @FXML private Label labelTm;
    @FXML private TextFlow textFlowLocal;
    @FXML private TextFlow textFlowVisitor;
    @FXML private Pane tirsPane;
    @FXML private ImageView but;

    @FXML private TableView<TeamTableModel> tableLocal;
    @FXML private TableColumn<TeamTableModel, String> localColName;
    @FXML private TableColumn<TeamTableModel, String> localColinGame;
    @FXML private TableColumn<TeamTableModel, Integer> localColNumber;
    @FXML private TableColumn<TeamTableModel, String> localColCard;
    
    @FXML private TableView<TeamTableModel> tableVisitor;
    @FXML private TableColumn<TeamTableModel, String> visitorColName;
    @FXML private TableColumn<TeamTableModel, String> visitorColinGame;
    @FXML private TableColumn<TeamTableModel, Integer> visitorColNumber;
    @FXML private TableColumn<TeamTableModel, String> visitorColCard;
    
    @FXML TableView<AdministrationTableModel> tableAdministration;
    @FXML private TableColumn<AdministrationTableModel, String> colPoste;
    @FXML private TableColumn<AdministrationTableModel, String> colNameAdminstration;
    
    SqlConnectionImpl connectionImpl = new SqlConnectionImpl();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelTm.setVisible(false);
        List<TableColumn> columnsLocal = new ArrayList<>();
        columnsLocal.add(localColName);
        columnsLocal.add(localColinGame);
        columnsLocal.add(localColNumber);
        List<TableColumn> columnsVisitor = new ArrayList<>();
        columnsVisitor.add(visitorColName);
        columnsVisitor.add(visitorColinGame);
        columnsVisitor.add(visitorColNumber);
        retrieveData(Constants.TABLE_PARIS, columnsLocal, tableLocal);
        retrieveData(Constants.TABLE_TOULOUSE, columnsVisitor, tableVisitor);
        retrieveAdministrationTable(Constants.PROLIGUE_DRIVER_PATH);
    }
    
    @FXML
    public void openAsClassement() {
        redirectFromMatch(Constants.CLASEMENTS);
    }
    
    @FXML
    public void openAsLogin() {
        redirectFromMatch(Constants.LOGIN);
    }
    
    @FXML
    public void redirectFromMatch(String windowPath) {
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
    public void chronoDemarrer() {
        threadFlag = true;
        labelTm.setVisible(false);
        executorService.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
            while (threadFlag) {
                int minutes = 0;
                int seconds = 0;

                if (lastMinuteValue != null) {
                    minutes = Integer.valueOf(lastMinuteValue);
                    seconds = Integer.valueOf(lastSecondsValue);
                    
                    for (int j = 1; j < 60; j++) {
                            minutes++;
                        for (int i = 0; i <= 60; i++) {
                            try {
                                seconds++;
                                Thread.sleep(1000);
                                incrementTimer(seconds, minutes);
                                if (seconds == 60) 
                                    seconds = 0;
                                } catch (InterruptedException ex) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                } else {
                    for (int j = 1; j < 60; j++) {
                        for (int i = 0; i <= 60; i++) {
                            try {
                                Thread.sleep(1000);
                                incrementTimer(i, j);
                                } catch (InterruptedException ex) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                    }
                }
           }
        }, 0, 1, TimeUnit.SECONDS);
    }
    
    private void incrementTimer(int i, int j) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(i < 10) {
                    seconde.setText(0 + String.valueOf(i));
                } else {
                    seconde.setText(String.valueOf(i));
                }
                if (i == 60) {
                    if(j < 10){
                    minute.setText(0 + String.valueOf(j));
                } else {
                    minute.setText(String.valueOf(j));
                    }  
                }
            }
        });
    }

    @FXML
    public void showHelp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aide");
        alert.setHeaderText("Page d'accueil");
        String s ="Le match en direct pour effectuer la sauvegarde de statistiques a partir des donnees enregistrees."
                + "Contient des tableaux avec les joueurs des 2 equipes et les événements pour chacun."
                + "";
        alert.setContentText(s);
        alert.show();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("projetihm/images/lnh-logo_petit.png"));
    }
    
    @FXML
    private void pauseTimer() {
        if (threadFlag) {
            threadFlag = false;
            /* get active threads */
            int count = Thread.activeCount();
            Thread th[] = new Thread[count];
            /* return the number of threads put into the array */
            Thread.enumerate(th);

            /* interrupt the JavaFX thread pool that was launched by the timer */
            for (int i = 0; i < count; i++) {     
                if (th[i].getName().startsWith("pool")) {
                    th[i].interrupt();
                }
            }
        }
        lastMinuteValue = minute.getText();
        lastSecondsValue = seconde.getText();
        labelTm.setVisible(true);
    }
    
    @FXML
    private void stopTimer() {
        /* get active threads */
        int count = Thread.activeCount();
        Thread th[] = new Thread[count];
        /* return the number of threads put into the array */
        Thread.enumerate(th);
        
        /* interrupt the JavaFX thread pool that was launched by the timer */
        for (int i = 0; i < count; i++) {     
            if (th[i].getName().startsWith("pool")) {
                th[i].interrupt();
            }
        }
        minute.setText("00");
        seconde.setText("00");
        threadFlag = false;
        lastMinuteValue = null;
        lastSecondsValue = null;
        labelTm.setVisible(false);
    }
    
    @FXML
    public void incrementLocalGoal() {
        localGoal++;
        labelLocalGoal.setText(String.valueOf(localGoal));
    }
    
    @FXML
    public void decrementLocalGoal() {
        if (localGoal <= 0) {
            labelLocalGoal.setText(String.valueOf(0));
        } else {
            localGoal--;
            labelLocalGoal.setText(String.valueOf(localGoal));
        }
    }
    
    @FXML
    public void incrementVisitorGoal() {
        visitorGoal++;
        labelVisitorGoal.setText(String.valueOf(visitorGoal));
    }
    
    @FXML
    public void decrementVisitorGoal() {
        if (visitorGoal <= 0) {
            labelVisitorGoal.setText(String.valueOf(0));
        } else {
            visitorGoal--;
            labelVisitorGoal.setText(String.valueOf(visitorGoal));
        }
    }

    public void retrieveData(String tableName, List<TableColumn> columns, TableView table) {
        ObservableList<TeamTableModel> list = FXCollections.observableArrayList();
        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("number");
        columnNames.add("inGame");

        /* establish connection */
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:PROLIGUE_DB.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from " + tableName)) {

            /* get data from database */
            while (resultSet.next()) {
                list.add(new TeamTableModel(resultSet.getString("NOM"), resultSet.getInt("NUMERO"),
                    resultSet.getString("EN_JEU"), resultSet.getString("CARTES")));
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
   

    public void retrieveAdministrationTable(String driverPath) {
        ObservableList<AdministrationTableModel> list = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(driverPath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from TABLE_ADMINISTRATION")) {

            while (resultSet.next()) {
                list.add(new AdministrationTableModel(resultSet.getString("POSTE"), resultSet.getString("NOM")));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        try {
            colPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
            colNameAdminstration.setCellValueFactory(new PropertyValueFactory<>("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableAdministration.setItems(list); 
    } 
    
    @FXML
    public void registerAction(TableView table, String actionType, TextFlow textFlow) {
        TeamTableModel team = (TeamTableModel) table.getSelectionModel().getSelectedItem();
        Text text = new Text(" № " +  + team.getNumber() + " " + team.getName() + " - " + minute.getText() + ":" + seconde.getText() + "\n");

        ImageView image = null;
        
        if (actionType.equalsIgnoreCase("yellowCard")) {
            image = new ImageView("projetihm/images/yellow-card.png");
        } else if (actionType.equalsIgnoreCase("redCard")) {
            image = new ImageView("projetihm/images/red-card.png");
        } else if (actionType.equalsIgnoreCase("expulsion")) {
            image = new ImageView("projetihm/images/stopwatch.png");
        }

        textFlow.getChildren().addAll(image, text);
    }
    
    @FXML
    public void giveYellowCardLocal() {
        registerAction(tableLocal, "yellowCard", textFlowLocal);
    }
    
    @FXML
    public void giveRedCardLocal() {
        registerAction(tableLocal, "redCard", textFlowLocal);
    }
    
    @FXML
    public void expulserLocal() {
        registerAction(tableLocal, "expulsion", textFlowLocal);
    }
    
    @FXML
    public void giveYellowCardVisitor() {
        registerAction(tableVisitor, "yellowCard", textFlowVisitor);
    }
    
    @FXML
    public void giveRedCardVisitor() {
        registerAction(tableVisitor, "redCard", textFlowVisitor);
    }
    
    @FXML
    public void expulserVisitor() {
        registerAction(tableVisitor, "expulsion", textFlowVisitor);
    }

}
