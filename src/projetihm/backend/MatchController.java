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
import projetihm.backend.tables.TeamOneTableModel;
import projetihm.backend.tables.TeamTwoTableModel;

/**
 *
 * @author jaespes
 */
public class MatchController implements Initializable {
    public static final String LOGIN = "/projetihm/frontend/Login.fxml";
    public static final String CLASEMENTS = "/projetihm/frontend/Classements.fxml";
    final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    private int localGoal = 0;
    private int visitorGoal = 0;
    private boolean threadFlag = false;
    private String lastMinuteValue = null;
    private String lastSecondsValue = null;
    
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

    @FXML private TableView<TeamOneTableModel> tableLocal;
    @FXML private TableColumn<TeamOneTableModel, String> localColName;
    @FXML private TableColumn<TeamOneTableModel, String> localColinGame;
    @FXML private TableColumn<TeamOneTableModel, Integer> localColNumber;
    @FXML private TableColumn<TeamOneTableModel, String> localColCard;
    
    @FXML private TableView<TeamTwoTableModel> tableVisitor;
    @FXML private TableColumn<TeamTwoTableModel, String> visitorColName;
    @FXML private TableColumn<TeamTwoTableModel, String> visitorColinGame;
    @FXML private TableColumn<TeamTwoTableModel, Integer> visitorColNumber;
    @FXML private TableColumn<TeamTwoTableModel, String> visitorColCard;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelTm.setVisible(false);
        retrieveDataLocal("jdbc:sqlite:PROLIGUE_DB.db");
        retrieveDataVisitor("jdbc:sqlite:PROLIGUE_DB.db");
        
    }
    
    @FXML
    public void openAsClassement() {
        redirectFromMatch(CLASEMENTS);
    }
    
    @FXML
    public void openAsLogin() {
        redirectFromMatch(LOGIN);
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
    
    public void retrieveDataLocal(String driverPath) {
        ObservableList<TeamOneTableModel> list = FXCollections.observableArrayList();

        /* establish connection */
        try (Connection connection = DriverManager.getConnection(driverPath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from TABLE_PARIS")) {

            /* get data from database */
            while (resultSet.next()) {
                list.add(new TeamOneTableModel(resultSet.getString("NOM"), resultSet.getInt("NUMERO"),
                    resultSet.getString("EN_JEU"), resultSet.getString("CARTES")));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        /* display retrieved data from database in TableView columns */
        try {
            localColName.setCellValueFactory(new PropertyValueFactory<>("name"));
            localColNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
            localColinGame.setCellValueFactory(new PropertyValueFactory<>("inGame"));
            localColCard.setCellValueFactory(new PropertyValueFactory<>("cartes"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        tableLocal.setItems(list); 
    }
   
    public void retrieveDataVisitor(String driverPath) {
        ObservableList<TeamTwoTableModel> list = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(driverPath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from TABLE_TOULOUSE")) {

            while (resultSet.next()) {
                list.add(new TeamTwoTableModel(resultSet.getString("NOM"), resultSet.getInt("NUMERO"),
                    resultSet.getString("EN_JEU"), resultSet.getString("CARTES")));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        try {
            visitorColName.setCellValueFactory(new PropertyValueFactory<>("name"));
            visitorColNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
            visitorColinGame.setCellValueFactory(new PropertyValueFactory<>("inGame"));
            visitorColCard.setCellValueFactory(new PropertyValueFactory<>("cartes"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableVisitor.setItems(list); 
    } 
    
    @FXML
    public void giveYellowCardLocal() {
        TeamOneTableModel teamOne = tableLocal.getSelectionModel().getSelectedItem();
        Text text = new Text(" " + teamOne.getName() + " N" + teamOne.getNumber() + " - " + minute.getText() + ":" + seconde.getText() + "\n");
        ImageView imageView = new ImageView("projetihm/images/yellow-card.png");

        textFlowLocal.getChildren().addAll(imageView, text);
    }
    
    @FXML
    public void giveRedCardLocal() {
        TeamOneTableModel teamOne = tableLocal.getSelectionModel().getSelectedItem();
        Text text = new Text(" " + teamOne.getName() + " N" + teamOne.getNumber() + " - " + minute.getText() + ":" + seconde.getText() + "\n");
        ImageView imageView = new ImageView("projetihm/images/red-card.png");

        textFlowLocal.getChildren().addAll(imageView, text);
    }
    
    @FXML
    public void expulserLocal() {
        TeamOneTableModel teamOne = tableLocal.getSelectionModel().getSelectedItem();
        Text text = new Text(" " + teamOne.getName() + " N" + teamOne.getNumber() + " - " + minute.getText() + ":" + seconde.getText() + "\n");
        ImageView imageView = new ImageView("projetihm/images/stopwatch.png");

        textFlowLocal.getChildren().addAll(imageView, text);
    }
    
    @FXML
    public void giveYellowCardVisitor() {
        TeamTwoTableModel teamOne = tableVisitor.getSelectionModel().getSelectedItem();
        Text text = new Text(" " + teamOne.getName() + " N" + teamOne.getNumber() + " - " + minute.getText() + ":" + seconde.getText() + "\n");
        ImageView imageView = new ImageView("projetihm/images/yellow-card.png");

        textFlowVisitor.getChildren().addAll(imageView, text);
    }
    
    @FXML
    public void giveRedCardVisitor() {
        TeamTwoTableModel teamOne = tableVisitor.getSelectionModel().getSelectedItem();
        Text text = new Text(" " + teamOne.getName() + " N" + teamOne.getNumber() + " - " + minute.getText() + ":" + seconde.getText() + "\n");
        ImageView imageView = new ImageView("projetihm/images/red-card.png");

        textFlowVisitor.getChildren().addAll(imageView, text);
    }
    
    @FXML
    public void expulserVisitor() {
        TeamTwoTableModel teamOne = tableVisitor.getSelectionModel().getSelectedItem();
        Text text = new Text(" " + teamOne.getName() + " N" + teamOne.getNumber() + " - " + minute.getText() + ":" + seconde.getText() + "\n");
        ImageView imageView = new ImageView("projetihm/images/stopwatch.png");

        textFlowVisitor.getChildren().addAll(imageView, text);
    }
}
