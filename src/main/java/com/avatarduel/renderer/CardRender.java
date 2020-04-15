package com.avatarduel.renderer;

import com.avatarduel.AvatarDuel;
import com.avatarduel.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CardRender{
  public static final String NOT_FOUND_PATH = "card/image/ImageNotFound.png";

  public static final double MAX_FONT_SIZE = 9;
  public static final double MAX_TEXT_BOX_WIDTH = 285 / 2;
  public static final double MAX_TEXT_BOX_HEIGHT = 90 / 2;
  public static final String WATER_COLOR = "#0000FF";
  public static final String FIRE_COLOR = "#FF0000";
  public static final String AIR_COLOR = "#D0E6FA";
  public static final String EARTH_COLOR = "#FFFF00";

  public static HBox cardIdentity(Card kartu) {
    HBox hbox = new HBox();

    Text cardName = new Text(kartu.getName());
    Text cardElement;
    Rectangle elementColor = new Rectangle(10.0, 25.0 / 2);
    if (kartu.getElement() == Element.WATER){
      cardElement = new Text("WATER   ");
      elementColor.setStroke(Color.web(WATER_COLOR));
      elementColor.setFill(Color.web(WATER_COLOR));
    } else if (kartu.getElement() == Element.FIRE){
      cardElement = new Text("FIRE   ");
      elementColor.setStroke(Color.web(FIRE_COLOR));
      elementColor.setFill(Color.web(FIRE_COLOR));
    } else if (kartu.getElement() == Element.AIR){
      cardElement = new Text("AIR   ");
      elementColor.setStroke(Color.web(AIR_COLOR));
      elementColor.setFill(Color.web(AIR_COLOR));
    } else{
      cardElement = new Text("EARTH   ");
      elementColor.setStroke(Color.web(EARTH_COLOR));
      elementColor.setFill(Color.web(EARTH_COLOR));
    }
    StackPane stack = new StackPane();
    
    cardElement.setFont(Font.font("Verdana", FontWeight.BOLD, MAX_FONT_SIZE));
    cardName.setFont(Font.font("Verdana", FontWeight.BOLD, MAX_FONT_SIZE));
    
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
    cardType.setFont(Font.font("Verdana", MAX_FONT_SIZE + 1));

    StackPane stack = new StackPane();
  
    stack.getChildren().add(cardType);
    stack.setAlignment(Pos.CENTER_RIGHT);
    
    if (kartu.getCardType().equals("Skill")){
      Text cardSkillType = new Text(((Skill)kartu).getSkillType());
      cardSkillType.setFont(Font.font("Verdana", MAX_FONT_SIZE + 1));
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
    Rectangle border = new Rectangle(MAX_TEXT_BOX_WIDTH, MAX_TEXT_BOX_HEIGHT);
    border.setFill(Color.TRANSPARENT);
    border.setStroke(Color.BLACK);
    
    cardDescription.setFont(Font.font("Verdana", MAX_FONT_SIZE));
    
    StackPane stack = new StackPane();
    
    stack.getChildren().addAll(border, cardDescription);
    cardDescription.wrappingWidthProperty().bind(border.widthProperty());
    // stack.setAlignment(Pos.CENTER);
    StackPane.setMargin(border, new Insets(3, 3, 3, 3));

    boolean add = hbox.getChildren().add(stack);
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
      
      cardAtk.setFont(Font.font("Verdana", FontWeight.BOLD, MAX_FONT_SIZE + 1));
      cardDef.setFont(Font.font("Verdana", FontWeight.BOLD, MAX_FONT_SIZE + 1));
      cardPower.setFont(Font.font("Verdana", FontWeight.BOLD, MAX_FONT_SIZE + 1));
      
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

      cardAtk.setFont(Font.font("Verdana", FontWeight.BOLD, MAX_FONT_SIZE + 1));
      cardDef.setFont(Font.font("Verdana", FontWeight.BOLD, MAX_FONT_SIZE + 1));
      cardPower.setFont(Font.font("Verdana", FontWeight.BOLD, MAX_FONT_SIZE + 1));
      
      String stype = ((Skill)kartu).getSkillType(); 
      if (stype.equals("Destroy") || stype.equals("Power Up")) hbox.getChildren().addAll(cardPower);
      else hbox.getChildren().addAll(cardAtk, cardDef, cardPower);
      hbox.setAlignment(Pos.CENTER_RIGHT);
    }
    
    hbox.setStyle("-fx-background-color: #D0D0D0;");

    return hbox;
  }

  public static Pane cardHover(Card kartu){
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(5, 10, 5, 10));
    Image image;
    ImageView imageView = new ImageView();
    try{
      image = new Image(kartu.getPath());
    } catch (Exception e){
      System.out.println("Foto kartu tidak ditemukan");
      System.out.println(e.getMessage());
      image = new Image(AvatarDuel.class.getResource(NOT_FOUND_PATH).toString());
    }
    imageView.setImage(image);
//    imageView.setFitHeight(300);
    imageView.setFitWidth(MAX_TEXT_BOX_WIDTH + 10);
    imageView.setPreserveRatio(true);
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
//    imageView.fitWidthProperty().bind(grid.widthProperty());
    Pane res = new Pane();
    res.getChildren().add(grid);
    return res;
  }

  public static VBox cardNotHover(Card kartu){
    VBox res = new VBox();
    String cssLayout;
    if (kartu == null){
      cssLayout = "-fx-border-color: black;\n";
      res.setStyle(cssLayout);
      res.setSpacing(2);
      res.setMinWidth(50);
      res.setMinHeight(60);
      return res;
    }
    cssLayout = "-fx-border-color: black;\n" +
            "-fx-background-color: ";
    Element element = kartu.getElement();
    if (element == Element.WATER){
      cssLayout = cssLayout + "#8181F7";
    } else if (element == Element.FIRE){
      cssLayout = cssLayout + "#FA5858";
    } else if (element == Element.AIR){
      cssLayout = cssLayout + "#E0F2F7";
    } else{
      cssLayout = cssLayout + "#F7D358";
    }

    res.setStyle(cssLayout);
    res.setSpacing(2);
    res.setMinWidth(50);
    res.setMinHeight(60);
    res.setAlignment(Pos.CENTER_LEFT);

    Label cardType;
    Label power;
    Label attack;
    Label defense;

    if (kartu.getCardType().equals("Land")){
      cardType = new Label(kartu.getCardType().toUpperCase());
      cardType.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));
      res.getChildren().add(cardType);
    } else if (kartu.getCardType().equals("Character")){
      power = new Label("PWR " + ((Chargame) kartu).getPower());
      attack = new Label("ATK " + ((Chargame) kartu).getAtk());
      defense = new Label("DEF " + ((Chargame) kartu).getDef());
      power.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));
      attack.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));
      defense.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));
      res.getChildren().addAll(power, attack, defense);
    } else{
      power = new Label("PWR " + ((Skill) kartu).getPower() + "");
      attack = new Label("ATK " +((Skill) kartu).getAtk() + "");
      defense = new Label("DEF " + ((Skill) kartu).getDef() + "");
      String temp = ((Skill) kartu).getSkillType().toUpperCase();
      cardType = new Label(temp);

      cardType.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));
      power.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));
      attack.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));
      defense.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));

      if (temp.equals("DESTROY")){
        res.getChildren().addAll(cardType, power);
      } else if (temp.equals("POWER UP")){
        res.getChildren().addAll(cardType, power);
      } else{
        res.getChildren().addAll(cardType, power, attack, defense);
      }
    }
    return res;
  }
  public static VBox cardNotHoverandGlow(Card kartu){
    VBox res = cardNotHover(kartu);
    DropShadow borderGlow = new DropShadow();
    borderGlow.setColor(Color.DARKGREEN);
    borderGlow.setOffsetX(0f);
    borderGlow.setOffsetY(0f);
    borderGlow.setWidth(25);
    borderGlow.setHeight(25);

    res.setEffect(borderGlow);
    return res;
  }

  public static VBox activeChara(Card kartu, int addAtk, int addDef, boolean isDef){
    VBox res = new VBox();
    String cssLayout;
    if (kartu == null){
      cssLayout = "-fx-border-color: black;\n";
      res.setStyle(cssLayout);
      res.setSpacing(2);
      res.setMinWidth(50);
      res.setMinHeight(60);
      return res;
    }
    addAtk -= ((Chargame) kartu).getAtk();
    addDef -= ((Chargame) kartu).getDef();
    cssLayout = "-fx-border-color: black;\n" +
            "-fx-background-color: ";
    Element element = kartu.getElement();
    if (element == Element.WATER){
      cssLayout = cssLayout + "#8181F7";
    } else if (element == Element.FIRE){
      cssLayout = cssLayout + "#FA5858";
    } else if (element == Element.AIR){
      cssLayout = cssLayout + "#E0F2F7";
    } else{
      cssLayout = cssLayout + "#F7D358";
    }

    res.setStyle(cssLayout);
    res.setSpacing(2);
    if (isDef){
      res.setMinWidth(80);
      res.setMinHeight(50);
      res.setAlignment(Pos.CENTER);
    } else{
      res.setMinWidth(50);
      res.setMinHeight(60);
      res.setAlignment(Pos.CENTER_LEFT);
    }

    Label power;
    Label attack;
    Label defense;

    power = new Label("PWR " + ((Chargame) kartu).getPower());
    if (addAtk > 0) attack = new Label("ATK " + ((Chargame) kartu).getAtk() + "+" + addAtk);
    else if (addAtk < 0) attack = new Label("ATK " + ((Chargame) kartu).getAtk() + addAtk);
    else attack = new Label("ATK " + ((Chargame) kartu).getAtk());
    if (addDef > 0) defense = new Label("DEF " + ((Chargame) kartu).getDef() + "+" + addDef);
    else if (addDef < 0) defense = new Label("DEF " + ((Chargame) kartu).getDef() + addDef);
    else defense = new Label("DEF " + ((Chargame) kartu).getDef());
    power.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));
    attack.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));
    defense.setFont(Font.font("Arial", MAX_FONT_SIZE + 1));
    res.getChildren().addAll(power, attack, defense);

    return res;
  }

  public static VBox activeCharaandGlow(Card kartu, int addAtk, int addDef, boolean isDef){
    VBox res = activeChara(kartu, addAtk, addDef, isDef);
    DropShadow borderGlow = new DropShadow();
    borderGlow.setColor(Color.DARKGREEN);
    borderGlow.setOffsetX(0f);
    borderGlow.setOffsetY(0f);
    borderGlow.setWidth(25);
    borderGlow.setHeight(25);

    res.setEffect(borderGlow);
    return res;
  }
}