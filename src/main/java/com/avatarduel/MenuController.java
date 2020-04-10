package com.avatarduel;

import com.avatarduel.model.Card;
import com.avatarduel.model.Field;
import com.avatarduel.util.CharacterFactory;
import com.avatarduel.util.SkillFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public Button startNewGame;
    public Pane upperLeftPanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    public void StartGame(ActionEvent event) {
        Parent temp;
        Scene gamePlay;
        try {
            temp = FXMLLoader.load(getClass().getResource("GamePlay.fxml"));
            gamePlay = new Scene(temp, 1000, 600);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(gamePlay);
            app_stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void QuitGame(ActionEvent event) {
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.close();
    }
}
