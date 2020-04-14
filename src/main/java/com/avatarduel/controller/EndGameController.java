package com.avatarduel.controller;

import com.avatarduel.AvatarDuel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {
    public Label pesanKemenangan;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(int playerId){
        pesanKemenangan.setText("Player " + (playerId + 1) + " Berhasil Memenangkan Duel!");
    }
    @FXML
    public void backToMainMenu(ActionEvent event){
        Parent temp;
        Scene gamePlay;

        try{
            temp = FXMLLoader.load(AvatarDuel.class.getResource("fxml/MainMenu.fxml"));
            gamePlay = new Scene(temp, 800, 600);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(gamePlay);
            app_stage.centerOnScreen();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
