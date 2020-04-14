package com.avatarduel.controller;

import com.avatarduel.AvatarDuel;
import com.avatarduel.renderer.CardRender;
import com.avatarduel.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
//import jdk.tools.jaotc.Main;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    public Field field;

    @FXML private SplitPane spDummy;
    public Button backToMainMenu;

    public AnchorPane myField;
    public AnchorPane myDeck, myLand, theDeck[];
    public AnchorPane urDeck, urLand, theLand[];
    public Label myHPIndicator, urHPIndicator, hPIndicator[];
    public Pane upperLeftPanel;
    public Pane myCard1, myCard2, myCard3, myCard4, myCard5, myCard6, myCard7;
    public Pane urCard1, urCard2, urCard3, urCard4, urCard5, urCard6, urCard7;
    public Pane cardOnHand[][];

    public Pane myActiveChara1, myActiveChara2, myActiveChara3, myActiveChara4, myActiveChara5, myActiveChara6;
    public Pane urActiveChara1, urActiveChara2, urActiveChara3, urActiveChara4, urActiveChara5, urActiveChara6;
    public Pane activeChara[][];

    public Pane myActiveSkill1, myActiveSkill2, myActiveSkill3, myActiveSkill4, myActiveSkill5, myActiveSkill6;
    public Pane urActiveSkill1, urActiveSkill2, urActiveSkill3, urActiveSkill4, urActiveSkill5, urActiveSkill6;
    public Pane activeSkill[][];

    public ProgressBar myHealth, urHealth, theHealth[];

    public Pair onHandPicked, onFieldSkillPicked;
    public boolean lock;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // VARIABLES
        lock = false;
        cardOnHand = new Pane[2][7];
        cardOnHand[0][0] = myCard1;
        cardOnHand[0][1] = myCard2;
        cardOnHand[0][2] = myCard3;
        cardOnHand[0][3] = myCard4;
        cardOnHand[0][4] = myCard5;
        cardOnHand[0][5] = myCard6;
        cardOnHand[0][6] = myCard7;
        cardOnHand[1][0] = urCard1;
        cardOnHand[1][1] = urCard2;
        cardOnHand[1][2] = urCard3;
        cardOnHand[1][3] = urCard4;
        cardOnHand[1][4] = urCard5;
        cardOnHand[1][5] = urCard6;
        cardOnHand[1][6] = urCard7;

        activeChara = new Pane[2][6];
        activeChara[0][0] = myActiveChara1;
        activeChara[0][1] = myActiveChara2;
        activeChara[0][2] = myActiveChara3;
        activeChara[0][3] = myActiveChara4;
        activeChara[0][4] = myActiveChara5;
        activeChara[0][5] = myActiveChara6;
        activeChara[1][0] = urActiveChara1;
        activeChara[1][1] = urActiveChara2;
        activeChara[1][2] = urActiveChara3;
        activeChara[1][3] = urActiveChara4;
        activeChara[1][4] = urActiveChara5;
        activeChara[1][5] = urActiveChara6;

        activeSkill = new Pane[2][6];
        activeSkill[0][0] = myActiveSkill1;
        activeSkill[0][1] = myActiveSkill2;
        activeSkill[0][2] = myActiveSkill3;
        activeSkill[0][3] = myActiveSkill4;
        activeSkill[0][4] = myActiveSkill5;
        activeSkill[0][5] = myActiveSkill6;
        activeSkill[1][0] = urActiveSkill1;
        activeSkill[1][1] = urActiveSkill2;
        activeSkill[1][2] = urActiveSkill3;
        activeSkill[1][3] = urActiveSkill4;
        activeSkill[1][4] = urActiveSkill5;
        activeSkill[1][5] = urActiveSkill6;

        theDeck = new AnchorPane[2];
        theLand = new AnchorPane[2];
        theHealth = new ProgressBar[2];
        hPIndicator = new Label[2];

        theDeck[0] = myDeck;
        theDeck[1] = urDeck;
        theLand[0] = myLand;
        theLand[1] = urLand;
        theHealth[0] = myHealth;
        theHealth[1] = urHealth;
        hPIndicator[0] = myHPIndicator;
        hPIndicator[1] = urHPIndicator;

        onHandPicked = new Pair(-1, -1);
        onFieldSkillPicked = new Pair(-1, -1);

        // RENDER
//        for (int i=0;i<2;i++){
//            for (int j=0;j<7;j++){
//                renderOnHand(i, j);
//            }
//        }
//        for (int i=0;i<2;i++){
//            for (int j=0;j<6;j++){
//                renderCharaOnField(i, j);
//                renderSkillOnField(i, j);
//            }
//        }
//        for (int i=0;i<2;i++){
//            renderDeck(i);
//            renderLand(i);
//            renderHealth(i);
//        }

        System.out.println("View is now loaded!");
    }
    public void initField(Field field){
        this.field = field;
        renderAll();
//        goToDrawPhase();
    }
    public void noOnHandPicked(){
        if (!onHandPicked.equals(-1, -1)){
            int playerId = onHandPicked.first;
            int idx = onHandPicked.second;
            onHandPicked = new Pair(-1, -1);

            Pane pane = cardOnHand[playerId][idx];
            Card kartu = field.getCardOnHand(playerId, idx);
            pane.getChildren().clear();
            pane.getChildren().add(CardRender.cardNotHover(kartu));
        }
    }
    public void SetCardHover(Card kartu){
        if (kartu == null) return;
        Pane pane = CardRender.cardHover(kartu);
        upperLeftPanel.getChildren().clear();
        upperLeftPanel.getChildren().add(pane);
    }
    public void BackToMainMenu(ActionEvent event){
        Parent temp;
        Scene gamePlay;

        try{
            temp = FXMLLoader.load(AvatarDuel.class.getResource("MainMenu.fxml"));
            gamePlay = new Scene(temp, 800, 600);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(gamePlay);
            app_stage.centerOnScreen();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void goToDrawPhase(){
        FXMLLoader loader;
        Parent temp;
        Scene gamePlay;

        if (backToMainMenu == null || ((Stage) backToMainMenu.getScene().getWindow()) == null) System.out.println("Nani");
        try {
            loader = new FXMLLoader(AvatarDuel.class.getResource("DrawPhase.fxml"));
            temp = loader.load();
            gamePlay = new Scene(temp, 1000, 650);
            DrawPhaseController controller = loader.<DrawPhaseController>getController();
            controller.initField(field);

            Stage app_stage = (Stage) backToMainMenu.getScene().getWindow();
            app_stage.close();
            app_stage.setScene(gamePlay);
            app_stage.centerOnScreen();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void goToMainPhase(){
        FXMLLoader loader;
        Parent temp;
        Scene gamePlay;
        try {
            loader = new FXMLLoader(AvatarDuel.class.getResource("MainPhase.fxml"));
            temp = loader.load();
            gamePlay = new Scene(temp, 1000, 650);
            MainPhaseController controller = loader.<MainPhaseController>getController();
            controller.initField(field);

            Stage app_stage = (Stage) myCard1.getScene().getWindow();
            app_stage.setScene(gamePlay);
            app_stage.centerOnScreen();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void handleCardOnHand(MouseEvent event){
        noOnHandPicked();
        int playerId = 0, idx = 0;
        Pane temp = (Pane) event.getSource();
        for (playerId=0;playerId<2;playerId++) {
            for (idx = 0; idx < 7; idx++) {
                if (temp == cardOnHand[playerId][idx]) {
                    break;
                }
            }
            if (idx != 7) {
                break;
            }
        }
        if (playerId != field.turn){
            noOnHandPicked();
            return;
        }
        Card kartu = field.getCardOnHand(playerId, idx);
        if (kartu == null){
            noOnHandPicked();
            return;
        }
        SetCardHover(kartu);
        onHandPicked = new Pair(playerId, idx);
        Pane pane = cardOnHand[playerId][idx];
        pane.getChildren().clear();
        pane.getChildren().add(CardRender.cardNotHoverandGlow(kartu));
    }
    @FXML
    public void handleCharaOnField(MouseEvent event){

    }
    @FXML
    public void handleSkillOnField(MouseEvent event){

    }
    public void renderOnHand(int playerId, int idx){
        if (playerId != field.turn) return;

        Card kartu = field.getCardOnHand(playerId, idx);
        Pane pane = cardOnHand[playerId][idx];

        pane.getChildren().clear();
        pane.getChildren().add(CardRender.cardNotHover(kartu));
    }
    public void renderCharaOnField(int playerId, int idx){
        Card kartu = field.chara[playerId][idx];
        Pane pane = activeChara[playerId][idx];

        pane.getChildren().clear();
        pane.getChildren().add(CardRender.activeChara(kartu, field.getCharaAtk(playerId, idx), field.getCharaDef(playerId, idx), field.isDef[playerId][idx]));
    }
    public void renderSkillOnField(int playerId, int idx){
        Card kartu = field.skill[playerId][idx];
        Pane pane = activeSkill[playerId][idx];

        pane.getChildren().clear();
        pane.getChildren().add(CardRender.cardNotHover(kartu));
    }
    public void renderDeck(int playerId){
        HBox res = new HBox();
        res.setSpacing(10);

        int a = field.player[playerId].deck.getJumlahKartu();
        int b = field.player[playerId].deck.getJumlahAwalKartu();
        Label sisa = new Label("Deck\n" + a + " / " + b);
        String _path = AvatarDuel.class.getResource(Deck.PATH).toString();
//        System.out.println(_path);
        Image image = new Image(_path);
        ImageView imageView = new ImageView(image);
        res.getChildren().addAll(sisa, imageView);

        imageView.fitHeightProperty().bind(theDeck[playerId].heightProperty());
        imageView.setPreserveRatio(true);

        theDeck[playerId].getChildren().clear();
        theDeck[playerId].getChildren().add(res);
    }
    public void renderLand(int playerId){
        VBox res = new VBox();
        res.setSpacing(3);

        String earth = "Earth : " + field.currentLand[playerId].get(Element.EARTH) + " / " + field.maxLand[playerId].get(Element.EARTH);
        String fire = "Fire : " + field.currentLand[playerId].get(Element.FIRE) + " / " + field.maxLand[playerId].get(Element.FIRE);
        String water = "Water : " + field.currentLand[playerId].get(Element.WATER) + " / " + field.maxLand[playerId].get(Element.WATER);
        String air = "Air : " + field.currentLand[playerId].get(Element.AIR) + " / " + field.maxLand[playerId].get(Element.AIR);

        Label label1 = new Label(earth);
        Label label2 = new Label(fire);
        Label label3 = new Label(water);
        Label label4 = new Label(air);

        Rectangle earthColor = new Rectangle(10.0, 25.0 / 2);
        Rectangle fireColor = new Rectangle(10.0, 25.0 / 2);
        Rectangle waterColor = new Rectangle(10.0, 25.0 / 2);
        Rectangle airColor = new Rectangle(10.0, 25.0 / 2);

        earthColor.setFill(Color.web(CardRender.EARTH_COLOR));
        fireColor.setFill(Color.web(CardRender.FIRE_COLOR));
        waterColor.setFill(Color.web(CardRender.WATER_COLOR));
        airColor.setFill(Color.web(CardRender.AIR_COLOR));

        HBox box1 = new HBox();
        HBox box2 = new HBox();
        HBox box3 = new HBox();
        HBox box4 = new HBox();
        box1.setSpacing(13);
        box2.setSpacing(13);
        box3.setSpacing(13);
        box4.setSpacing(13);

        box1.getChildren().addAll(label1, earthColor);
        box2.getChildren().addAll(label2, fireColor);
        box3.getChildren().addAll(label3, waterColor);
        box4.getChildren().addAll(label4, airColor);

        box1.setAlignment(Pos.CENTER_RIGHT);
        box2.setAlignment(Pos.CENTER_RIGHT);
        box3.setAlignment(Pos.CENTER_RIGHT);
        box4.setAlignment(Pos.CENTER_RIGHT);

        res.getChildren().addAll(box1, box2, box3, box4);
        res.setAlignment(Pos.CENTER_RIGHT);
        theLand[playerId].getChildren().clear();
        theLand[playerId].getChildren().add(res);
    }
    public void renderHealth(int playerId){
        Integer a = field.player[playerId].health;
        Integer b = 80;

        ProgressBar temp = theHealth[playerId];
        Label indicator = hPIndicator[playerId];
        indicator.setText(" " + a + " HP");
        temp.setProgress(a.doubleValue() / b.doubleValue());
    }

    @FXML
    public void HandleMyDeck(MouseEvent event){
        // TODO
        renderDeck(0);
    }
    public void renderAll(){
        for (int i=0;i<2;i++){
            for (int j=0;j<6;j++){
                renderCharaOnField(i, j);
                renderSkillOnField(i, j);
            }
        }
        for (int i=0;i<2;i++){
            renderDeck(i);
            renderLand(i);
            renderHealth(i);
            for (int j=0;j<7;j++){
                renderOnHand(i, j);
            }
        }
    }
}
