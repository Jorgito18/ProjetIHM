/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm.backend;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author jaespes
 */
public class MatchController implements Initializable {

    @FXML private VBox sideMenu;
    @FXML private Font x1;
    @FXML private Font x3;
    @FXML private Label minute;
    @FXML private Label seconde;
           
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    public void chronoDemarrer() {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
                for (int i = 0; i <= 60; i++) {
                    try {
                        Thread.sleep(1000);
                        incrementMinutes(i);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MatchController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void incrementMinutes(int i) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                minute.setText(String.valueOf(i));
            }
        });
    }
    
}