package com.avatarduel;

import javafx.application.*;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javafx.scene.text.Text;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

import com.avatarduel.model.*;
import com.avatarduel.util.*;

public class AvatarDuel extends Application {

  Stage window;
  Scene gamePlay;
  Scene sceneCard;
  Scene mainMenu;

  public static void main(String[] args) {
      launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
      window = primaryStage;

      Parent temp;
      try{
          temp = FXMLLoader.load(getClass().getResource("fxml/MainMenu.fxml"));
          mainMenu = new Scene(temp, 800, 800);
      } catch (Exception e){
          System.out.println("Error :" + e.getMessage());
          return;
      }
      window.setScene(mainMenu);
      window.setTitle("Avatar-Duel");
      window.show();
    }
}