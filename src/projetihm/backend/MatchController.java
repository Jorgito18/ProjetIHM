/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm.backend;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author jaespes
 */
public class MatchController implements Initializable {
    public static final String LOGIN = "/projetihm/frontend/Login.fxml";
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
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
        String s ="Chosir entre le mode classements nationaux pour voir les clasemets et statistique des equipes et jouers, "
                + "ou bien match en direct pour manipuler les donnes en direct ";
        alert.setContentText(s);
        alert.show();
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
    
}
