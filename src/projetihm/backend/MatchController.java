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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 *
 * @author jaespes
 */
public class MatchController implements Initializable {

    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private int localGoal = 0;
    private int visitorGoal = 0;

    @FXML private Label minute;
    @FXML private Label seconde;
    @FXML private Label labelLocalGoal;
    @FXML private Label labelVisitorGoal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    public void chronoDemarrer() {
        executorService.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
                for (int j = 1; j < 60; j++) {
                    for (int i = 0; i <= 60; i++) {
                        try {
                           Thread.sleep(1000);
                           incrementSecondes(i);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    incrementMinutes(j); 
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void incrementSecondes(int i) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(i < 10){
                    seconde.setText(0 + String.valueOf(i));
                }else{
                    seconde.setText(String.valueOf(i));
               }
            }
        });
    }
    
    private void incrementMinutes(int i) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(i < 10){
                    minute.setText(0 + String.valueOf(i));
                }else{
                    minute.setText(String.valueOf(i));
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
                break;
            }
        }
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