package com.avatarduel;

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
  Scene scene2;

  public static void main(String[] args) {
      launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
      window = primaryStage;
      Card kartu = SkillFactory.getInstance().create();

      GridPane grid = CardHover.cardHover(kartu);
      scene2 = new Scene(grid, 600, 600);

      window.setScene(scene2);
      window.setTitle("Kartu");
      window.show();
  }
}