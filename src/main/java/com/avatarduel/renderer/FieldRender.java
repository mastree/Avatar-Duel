package com.avatarduel.renderer;

import com.avatarduel.AvatarDuel;
import com.avatarduel.model.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FieldRender{
    public static final String NOT_FOUND_PATH = "card/image/ImageNotFound.png";
    
    /**
     * Add card to hand in UI
     * @param pane layout for UI
     * @param kartu card details
     */
    public static void renderOnHand(Pane pane, Card kartu){
        pane.getChildren().clear();
        pane.getChildren().add(CardRender.cardNotHover(kartu));
    }

    /**
     * Add character cards to field in UI
     * @param pane layout for UI
     * @param field Field for player
     * @param playerId id of player
     * @param idx index of character card
     */
    public static void renderCharaOnField(Pane pane, Field field, int playerId, int idx){
        Card kartu = field.chara[playerId][idx];
        pane.getChildren().clear();
        pane.getChildren().add(CardRender.activeChara(kartu, field.getCharaAtk(playerId, idx), field.getCharaDef(playerId, idx), field.isDef[playerId][idx]));
    }

    /**
     * Add skill card to field in UI
     * @param pane layout for UI
     * @param kartu card details
     */
    public static void renderSkillOnField(Pane pane, Card kartu){
        pane.getChildren().clear();
        pane.getChildren().add(CardRender.cardNotHover(kartu));
    }

    /**
     * Add card to deck in UI
     * @param theDeck deck layout
     * @param field Field for player
     * @param playerId id of player 
     */
    public static void renderDeck(AnchorPane theDeck, Field field, int playerId){
        HBox res = new HBox();
        res.setSpacing(10);

        int a = field.player[playerId].deck.getJumlahKartu();
        int b = field.player[playerId].deck.getJumlahAwalKartu();
        Label sisa = new Label("Deck\n" + a + " / " + b);
        String _path;
        Image image;
        try{
            _path = AvatarDuel.class.getResource(Deck.PATH).toString();
            image = new Image(_path);
        } catch (Exception e){
            System.out.println("Foto deck tidak ditemukan");
            System.out.println(e.getMessage());
            _path = AvatarDuel.class.getResource(NOT_FOUND_PATH).toString();
            image = new Image(_path);
        }
        ImageView imageView = new ImageView(image);
        res.getChildren().addAll(sisa, imageView);

        imageView.fitHeightProperty().bind(theDeck.heightProperty());
        imageView.setPreserveRatio(true);

        theDeck.getChildren().clear();
        theDeck.getChildren().add(res);
    }

    /**
     * Add land to field for every element in UI 
     * @param theLand layout for UI
     * @param field field of player
     * @param playerId id of player
     */
    public static void renderLand(AnchorPane theLand, Field field, int playerId){
        VBox res = new VBox();
        res.setSpacing(3);

        String earth = "Earth : " + field.currentLand[playerId].get(Element.EARTH) + " / " + field.maxLand[playerId].get(Element.EARTH);
        String fire = "Fire : " + field.currentLand[playerId].get(Element.FIRE) + " / " + field.maxLand[playerId].get(Element.FIRE);
        String water = "Water : " + field.currentLand[playerId].get(Element.WATER) + " / " + field.maxLand[playerId].get(Element.WATER);
        String air = "Air : " + field.currentLand[playerId].get(Element.AIR) + " / " + field.maxLand[playerId].get(Element.AIR);
        String energy = "Energy : " + field.currentLand[playerId].get(Element.ENERGY) + " / " + field.maxLand[playerId].get(Element.ENERGY);

        Label label1 = new Label(earth);
        Label label2 = new Label(fire);
        Label label3 = new Label(water);
        Label label4 = new Label(air);
        Label label5 = new Label(energy);

        Rectangle earthColor = new Rectangle(10.0, 25.0 / 2);
        Rectangle fireColor = new Rectangle(10.0, 25.0 / 2);
        Rectangle waterColor = new Rectangle(10.0, 25.0 / 2);
        Rectangle airColor = new Rectangle(10.0, 25.0 / 2);
        Rectangle energyColor = new Rectangle(10.0, 25.0 / 2);
        
        earthColor.setFill(Color.web(CardRender.EARTH_COLOR));
        fireColor.setFill(Color.web(CardRender.FIRE_COLOR));
        waterColor.setFill(Color.web(CardRender.WATER_COLOR));
        airColor.setFill(Color.web(CardRender.AIR_COLOR));
        energyColor.setFill(Color.web(CardRender.ENERGY_COLOR));

        HBox box1 = new HBox();
        HBox box2 = new HBox();
        HBox box3 = new HBox();
        HBox box4 = new HBox();
        HBox box5 = new HBox();
        box1.setSpacing(13);
        box2.setSpacing(13);
        box3.setSpacing(13);
        box4.setSpacing(13);
        box5.setSpacing(13);

        box1.getChildren().addAll(label1, earthColor);
        box2.getChildren().addAll(label2, fireColor);
        box3.getChildren().addAll(label3, waterColor);
        box4.getChildren().addAll(label4, airColor);
        box5.getChildren().addAll(label5, energyColor);

        box1.setAlignment(Pos.CENTER_RIGHT);
        box2.setAlignment(Pos.CENTER_RIGHT);
        box3.setAlignment(Pos.CENTER_RIGHT);
        box4.setAlignment(Pos.CENTER_RIGHT);
        box5.setAlignment(Pos.CENTER_RIGHT);

        res.getChildren().addAll(box1, box2, box3, box4, box5);
        res.setAlignment(Pos.CENTER_RIGHT);
        theLand.getChildren().clear();
        theLand.getChildren().add(res);
    }

    /**
     * Add a progressbar to show health of player
     * @param theHealth layout for prograssbar
     * @param hPIndicator the text to represent health
     * @param field field of player 
     * @param playerId id of player 
     */

    public static void renderHealth(ProgressBar theHealth, Label hPIndicator, Field field, int playerId){
        Integer a = field.player[playerId].health;
        Integer b = 80;

        ProgressBar temp = theHealth;
        Label indicator = hPIndicator;
        indicator.setText(" " + a + " HP");
        temp.setProgress(a.doubleValue() / b.doubleValue());
    }
}