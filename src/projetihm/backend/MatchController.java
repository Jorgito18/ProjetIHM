package projetihm.backend;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author jaespes
 */
public class MatchController implements Initializable {

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
                int j =0;
                for (int i = 0; i < 60; i++) {
                    try {
                        Thread.sleep(1000);
                        incrementSecondes(i);
                        if(i == 59){
                            j++;
                            incrementMinutes(j);    
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MatchController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void incrementSecondes(int i) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                seconde.setText(String.valueOf(i));
            }
        });
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
