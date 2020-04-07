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

public class CardHover{
  public static HBox cardIdentity(Card kartu) {
    HBox hbox = new HBox();

    Text cardName = new Text(kartu.getName());
    Text cardElement;
    Rectangle elementColor = new Rectangle(10.0, 25.0);
    if (kartu.getElement() == Element.WATER){
      cardElement = new Text("WATER   ");
      elementColor.setStroke(Color.web("#0000FF"));
      elementColor.setFill(Color.web("#0000FF"));
    } else if (kartu.getElement() == Element.FIRE){
      cardElement = new Text("FIRE   ");
      elementColor.setStroke(Color.web("#FF0000"));
      elementColor.setFill(Color.web("#FF0000"));
    } else if (kartu.getElement() == Element.AIR){
      cardElement = new Text("AIR   ");
      elementColor.setStroke(Color.web("#D0E6FA"));
      elementColor.setFill(Color.web("#D0E6FA"));
    } else{
      cardElement = new Text("EARTH   ");
      elementColor.setStroke(Color.web("#FFFF00"));
      elementColor.setFill(Color.web("#FFFFBA"));
    }
    StackPane stack = new StackPane();
    
    cardElement.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
    cardName.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
    
    stack.getChildren().addAll(elementColor, cardElement);
    stack.setAlignment(Pos.CENTER_RIGHT);
    StackPane.setMargin(cardElement, new Insets(0, 5, 0, 0));
    
    hbox.getChildren().addAll(cardName, stack);
    HBox.setHgrow(stack, Priority.ALWAYS);
    hbox.setStyle("-fx-background-color: #D0D0D0;");
    hbox.setAlignment(Pos.CENTER);

    return hbox;
  }

  public static HBox cardType(Card kartu){
    HBox hbox = new HBox();
    Text cardType = new Text("[ " + kartu.getCardType() + " ]");
    cardType.setFont(Font.font("Verdana", 12));

    StackPane stack = new StackPane();
  
    stack.getChildren().add(cardType);
    stack.setAlignment(Pos.CENTER_RIGHT); 
    // StackPane.setMargin(cardType, new Insets(0, 0, 0, 0)); 
    
    if (kartu.getCardType().equals("Skill")){
      Text cardSkillType = new Text(((Skill)kartu).getSkillType());
      cardSkillType.setFont(Font.font("Verdana", 12));
      hbox.getChildren().add(cardSkillType);
    } 
    hbox.getChildren().add(stack);
    HBox.setHgrow(stack, Priority.ALWAYS);
    hbox.setStyle("-fx-background-color: #E5E5E5;");

    return hbox;
  }

  public static HBox cardDesc(Card kartu){
    HBox hbox = new HBox();
    Text cardDescription = new Text(kartu.getDesc());
    Rectangle border = new Rectangle(285, 90);
    border.setFill(Color.TRANSPARENT);
    border.setStroke(Color.BLACK);
    
    cardDescription.setFont(Font.font("Verdana", 10));
    
    StackPane stack = new StackPane();
    
    stack.getChildren().addAll(border, cardDescription);
    cardDescription.wrappingWidthProperty().bind(border.widthProperty());
    // stack.setAlignment(Pos.CENTER);
    StackPane.setMargin(border, new Insets(10, 10, 10, 10));

    hbox.getChildren().add(stack);
    HBox.setHgrow(stack, Priority.ALWAYS);
    hbox.setStyle("-fx-background-color: #E5E5E5;");

    return hbox;
  }

  public static HBox cardAttr(Card kartu){
    HBox hbox = new HBox();
    if (kartu instanceof Chargame){
      Text cardAtk = new Text("Atk/" + ((Chargame)kartu).getAtk() + " ");
      Text cardDef = new Text("Def/" + ((Chargame)kartu).getDef() + " ");
      Text cardPower = new Text("Pow/" + ((Chargame)kartu).getPower() + " ");
      
      cardAtk.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
      cardDef.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
      cardPower.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
      
      hbox.getChildren().addAll(cardAtk, cardDef, cardPower);
      hbox.setStyle("-fx-background-color: #E0E0E0;");
      hbox.setAlignment(Pos.CENTER_RIGHT);
    } else if (kartu instanceof Skill){
      String temp = ((Skill)kartu).getAtk() + "";
      if (((Skill)kartu).getAtk() > 0) temp = "+" + temp;
      Text cardAtk = new Text(temp + " Atk" + " ");
      temp = ((Skill)kartu).getDef() + "";
      if (((Skill)kartu).getDef() > 0) temp = "+" + temp;
      Text cardDef = new Text(temp + " Def" + "  ");
      Text cardPower = new Text("Pow/" + ((Skill)kartu).getPower() + " ");

      cardAtk.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
      cardDef.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
      cardPower.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
      
      String stype = ((Skill)kartu).getSkillType(); 
      if (stype.equals("Destroy") || stype.equals("Power Up")) hbox.getChildren().addAll(cardPower);
      else hbox.getChildren().addAll(cardAtk, cardDef, cardPower);
      hbox.setAlignment(Pos.CENTER_RIGHT);
    }
    
    hbox.setStyle("-fx-background-color: #D0D0D0;");

    return hbox;
  }

  public static GridPane cardHover(Card kartu){
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(5, 10, 5, 10));
    Image image = new Image(kartu.getPath());
    ImageView imageView = new ImageView();
    imageView.setImage(image);
    imageView.setFitHeight(300);
    imageView.setFitWidth(300);
    GridPane.setConstraints(imageView, 0, 3);
    HBox hboxIdKartu = cardIdentity(kartu);
    HBox hboxTipeKartu = cardType(kartu);
    HBox hboxDesc = cardDesc(kartu);
    GridPane.setConstraints(hboxIdKartu, 0, 1);
    GridPane.setConstraints(hboxTipeKartu, 0, 2);
    GridPane.setConstraints(hboxDesc, 0, 4);
    
    if (kartu.getCardType() != "Land"){
      HBox hboxAttr = cardAttr(kartu);
      GridPane.setConstraints(hboxAttr, 0, 5);
      grid.getChildren().addAll(hboxIdKartu, hboxTipeKartu, hboxDesc, hboxAttr, imageView);
      return grid;
    }
    grid.getChildren().addAll(hboxIdKartu, hboxTipeKartu, hboxDesc, imageView);
    return grid;
  }
}