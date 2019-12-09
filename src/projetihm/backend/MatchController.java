/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm.backend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
    public void chronoDemarrer() throws InterruptedException{
        int secondes;
        int minutes;
        for(minutes=0; minutes<60; minutes++){ 
            minute.setText(Integer.toString(minutes));
            for(secondes=0; secondes<60; secondes++){
                seconde.setText(Integer.toString(secondes));
                TimeUnit.SECONDS.sleep(1);
            }
        }
      }
}
