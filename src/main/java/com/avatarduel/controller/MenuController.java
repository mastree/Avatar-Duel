package com.avatarduel.controller;

import com.avatarduel.AvatarDuel;
import com.avatarduel.model.Field;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public Button startNewGame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    /**
     * Untuk memulai game
     */
    public void StartGame(ActionEvent event) {
        FXMLLoader loader;
        Parent temp;
        Scene gamePlay;
        try {
            loader = new FXMLLoader(AvatarDuel.class.getResource("fxml/DrawPhase.fxml"));
            temp = (Parent) loader.load();

            gamePlay = new Scene(temp, 1000, 650);
            DrawPhaseController controller = loader.<DrawPhaseController>getController();
            controller.initField(new Field());

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(gamePlay);
            app_stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
    }

    /**
     * Untuk keluar dari game
     */
    public void QuitGame(ActionEvent event) {
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.close();
    }
}
