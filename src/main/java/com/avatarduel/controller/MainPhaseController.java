package com.avatarduel.controller;

import com.avatarduel.AvatarDuel;
import com.avatarduel.renderer.CardRender;
import com.avatarduel.renderer.FieldRender;
import com.avatarduel.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class MainPhaseController implements Initializable {
    public Field field;

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

    public Label labelPhase;

    public Pair lastPicked;
    public String pickedSource;
    public boolean lock;

    public boolean putLand;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // VARIABLES
        labelPhase.setText("Main Phase");
        lock = false;
        putLand = false;
        cardOnHand = new Pane[2][Player.MAX_CARD];
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

        activeChara = new Pane[2][Field.COL];
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

        activeSkill = new Pane[2][Field.COL];
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

        lastPicked = new Pair(-1, -1);
        pickedSource = "";

        System.out.println("View is now loaded!");
        System.out.println("You are on Main Phase!");
    }
    public void initField(Field field){
        this.field = field;
        renderAll();
    }
    public void resetPicked(){
        if (!lastPicked.equals(-1, -1)){
            Card kartu = null;
            if (pickedSource.equals("hand")){
                Pane pane = cardOnHand[lastPicked.first][lastPicked.second];
                kartu = field.getCardOnHand(lastPicked.first, lastPicked.second);
                pane.getChildren().clear();
                pane.getChildren().add(CardRender.cardNotHover(kartu));
            } else if (pickedSource.equals("activeChara")){
                Pane pane = activeChara[lastPicked.first][lastPicked.second];
                kartu = field.chara[lastPicked.first][lastPicked.second];
                pane.getChildren().clear();
                pane.getChildren().add(CardRender.activeChara(kartu, field.getCharaAtk(lastPicked.first, lastPicked.second), field.getCharaDef(lastPicked.first, lastPicked.second), field.isDef[lastPicked.first][lastPicked.second]));
            } else if (pickedSource.equals("activeSkill")){
                Pane pane = activeSkill[lastPicked.first][lastPicked.second];
                kartu = field.skill[lastPicked.first][lastPicked.second];
                pane.getChildren().clear();
                pane.getChildren().add(CardRender.cardNotHover(kartu));
            }
            if (kartu != null) setCardHover(kartu);
        }
        lastPicked = new Pair(-1, -1);
        pickedSource = "";
    }
    public void setPicked(String source, int playerId, int idx){
        resetPicked();
        pickedSource = source;
        lastPicked = new Pair(playerId, idx);
        Card kartu;
        if (pickedSource.equals("hand")){
            Pane pane = cardOnHand[lastPicked.first][lastPicked.second];
            kartu = field.getCardOnHand(lastPicked.first, lastPicked.second);
            pane.getChildren().clear();
            pane.getChildren().add(CardRender.cardNotHoverandGlow(kartu));
        } else if (pickedSource.equals("activeChara")){
            Pane pane = activeChara[lastPicked.first][lastPicked.second];
            kartu = field.chara[lastPicked.first][lastPicked.second];
            pane.getChildren().clear();
            pane.getChildren().add(CardRender.activeCharaandGlow(kartu, field.getCharaAtk(lastPicked.first, lastPicked.second), field.getCharaDef(lastPicked.first, lastPicked.second), field.isDef[lastPicked.first][lastPicked.second]));
        } else if (pickedSource.equals("activeSkill")){
            Pane pane = activeSkill[lastPicked.first][lastPicked.second];
            kartu = field.skill[lastPicked.first][lastPicked.second];
            pane.getChildren().clear();
            pane.getChildren().add(CardRender.cardNotHoverandGlow(kartu));
        } else return;
        if (kartu != null) setCardHover(kartu);
    }
    public void setCardHover(Card kartu){
        if (kartu == null) return;
        Pane pane = CardRender.cardHover(kartu);
        upperLeftPanel.getChildren().clear();
        upperLeftPanel.getChildren().add(pane);
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
    public void goToBattlePhase(){
        FXMLLoader loader;
        Parent temp;
        Scene gamePlay;
        try {
            loader = new FXMLLoader(AvatarDuel.class.getResource("fxml/BattlePhase.fxml"));
            temp = loader.load();
            gamePlay = new Scene(temp, 1000, 650);
            BattlePhaseController controller = loader.<BattlePhaseController>getController();
            controller.initField(field);

            Stage app_stage = (Stage) myCard1.getScene().getWindow();
            app_stage.setScene(gamePlay);
            app_stage.centerOnScreen();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void handleNextPhase(ActionEvent event){
        try{
            goToBattlePhase();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void handleCardOnHand(MouseEvent event){
        if (lock) return;
        resetPicked();
        int playerId = 0, idx = 0;
        Pane temp = (Pane) event.getSource();
        for (playerId=0;playerId<2;playerId++) {
            for (idx = 0; idx < Player.MAX_CARD; idx++) {
                if (temp == cardOnHand[playerId][idx]) {
                    break;
                }
            }
            if (idx != Player.MAX_CARD) {
                break;
            }
        }
        if (playerId != field.turn){
            return;
        }
        Card kartu = field.getCardOnHand(playerId, idx);
        if (kartu == null){
            return;
        }
        setPicked("hand", playerId, idx);
    }
    @FXML
    public void handleCharaOnField(MouseEvent event){
        if (lock){
            int playerId = 0, idx = 0;
            Pane pane = (Pane) event.getSource();
            for (playerId=0;playerId<2;playerId++){
                for (idx=0;idx<Field.COL;idx++){
                    if (activeChara[playerId][idx] == pane){
                        break;
                    }
                }
                if (idx != Field.COL) break;
            }
            Card picked = field.chara[playerId][idx];

            if (picked == null) return;
            field.setSkillTarget(lastPicked, playerId, idx);
            lock = false;
            resetPicked();
            renderAll();
            return;
        }
        if (lastPicked.equals(-1, -1) || (pickedSource.equals("activeChara") || pickedSource.equals("activeSkill"))){
            int playerId = 0, idx = 0;
            Pane pane = (Pane) event.getSource();
            for (playerId=0;playerId<2;playerId++){
                for (idx=0;idx<Field.COL;idx++){
                    if (activeChara[playerId][idx] == pane){
                        break;
                    }
                }
                if (idx != Field.COL) break;
            }
            Card picked = field.chara[playerId][idx];
            if (picked != null){
                if (playerId == field.turn){
                    changeCharaStance(playerId, idx);
                } else{
                    setPicked("activeChara", playerId, idx);
                }
            }
            return;
        } else{
            if (pickedSource.equals("hand")){
                Card kartu = field.getCardOnHand(lastPicked.first, lastPicked.second);
                int playerId = 0, idx = 0;
                Pane pane = (Pane) event.getSource();
                for (playerId=0;playerId<2;playerId++){
                    for (idx=0;idx<Field.COL;idx++){
                        if (activeChara[playerId][idx] == pane){
                            break;
                        }
                    }
                    if (idx != Field.COL) break;
                }
                Card picked = field.chara[playerId][idx];
                if (kartu.getCardType().equals("Land") && (putLand || playerId != field.turn)){
                    if (picked != null){
                        if (playerId == field.turn){
                            changeCharaStance(playerId, idx);
                        } else{
                            setPicked("activeChara", playerId, idx);
                        }
                    }
                    else resetPicked();
                    return;
                } else if (kartu.getCardType().equals("Land")){
                    putLand = true;
                    field.placeLand((Land)kartu);
                    field.player[playerId].removeCard(lastPicked.second);

                    resetPicked();
                    return;
                }

                if (kartu.getCardType().equals("Skill")){
                    if (picked != null){
                        if (playerId == field.turn){
                            changeCharaStance(playerId, idx);
                        } else{
                            setPicked("activeChara", playerId, idx);
                        }
                    }
                    else resetPicked();
                    return;
                }
    
                if (playerId == field.turn){
                    if (picked != null){
                        changeCharaStance(playerId, idx);
                    } else{
                        if (field.placeCharacter((Chargame) kartu, idx)){
                            field.player[playerId].removeCard(lastPicked.second);
                            setPicked("activeChara", playerId, idx);
                        }
                    }
                    return;
                } else{
                    if (picked != null){
                        setPicked("activeChara", playerId, idx);
                    } else resetPicked();
                    return;
                }
            }
        }
    }
    @FXML
    public void handleSkillOnField(MouseEvent event){
        if (lock) return;
        if (lastPicked.equals(-1, -1) || (pickedSource.equals("activeChara") || pickedSource.equals("activeSkill"))){
            int playerId = 0, idx = 0;
            Pane pane = (Pane) event.getSource();
            for (playerId=0;playerId<2;playerId++){
                for (idx=0;idx<Field.COL;idx++){
                    if (activeSkill[playerId][idx] == pane){
                        break;
                    }
                }
                if (idx != Field.COL) break;
            }
            Card picked = field.skill[playerId][idx];
            if (picked != null){
                setPicked("activeSkill", playerId, idx);
            } else resetPicked();
            return;
        } else{
            Card kartu = field.getCardOnHand(lastPicked.first, lastPicked.second);
            int playerId = 0, idx = 0;
            Pane pane = (Pane) event.getSource();
            for (playerId=0;playerId<2;playerId++){
                for (idx=0;idx<Field.COL;idx++){
                    if (activeSkill[playerId][idx] == pane){
                        break;
                    }
                }
                if (idx != Field.COL) break;
            }
            Card picked = field.skill[playerId][idx];
            if (kartu.getCardType().equals("Land") && (putLand || playerId != field.turn)){
                if (picked != null){
                    setPicked("activeSkill", playerId, idx);
                } else resetPicked();
                return;
            } else if (kartu.getCardType().equals("Land")){
                putLand = true;
                field.placeLand((Land)kartu);
                field.player[playerId].removeCard(lastPicked.second);
                resetPicked();
                return;
            }

            if (kartu.getCardType().equals("Character")){
                if (picked != null){
                    setPicked("activeSkill", playerId, idx);
                } else resetPicked();
                return;
            }

            if (playerId == field.turn){
                if (picked != null){
                    setPicked("activeSkill", playerId, idx);
                } else{
                    if (field.placeSkill((Skill) kartu, idx)){
                        field.player[playerId].removeCard(lastPicked.second);
                        lock = true;
                        // onFieldSkillPicked = new Pair(playerId, idx);
                        setPicked("activeSkill", playerId, idx);
                    } else resetPicked();
                }
                return;
            } else{
                if (picked != null){
                    setPicked("activeSkill", playerId, idx);
                } else resetPicked();
                return;
            }
        }
    }
    @FXML
    public void handleDeck(MouseEvent event){
        renderDeck(0);
    }
    @FXML
    public void handleRemoveSkill(ActionEvent event){
        if (pickedSource.equals("activeSkill") && lastPicked.first == field.turn){
            if (field.skill[lastPicked.first][lastPicked.second] != null){
                field.removeSkill(lastPicked.first, lastPicked.second);
                resetPicked();
                renderAll();
            }
        }
    }
    public void renderOnHand(int playerId, int idx){
        if (playerId != field.turn) return;
        FieldRender.renderOnHand(cardOnHand[playerId][idx], field.getCardOnHand(playerId, idx));
    }
    public void renderCharaOnField(int playerId, int idx){
        FieldRender.renderCharaOnField(activeChara[playerId][idx], field, playerId, idx);
    }
    public void renderSkillOnField(int playerId, int idx){
        FieldRender.renderSkillOnField(activeSkill[playerId][idx], field.skill[playerId][idx]);
    }
    public void renderDeck(int playerId){
        FieldRender.renderDeck(theDeck[playerId], field, playerId);
    }
    public void renderLand(int playerId){
        FieldRender.renderLand(theLand[playerId], field, playerId);
    }
    public void renderHealth(int playerId){
        FieldRender.renderHealth(theHealth[playerId], hPIndicator[playerId], field, playerId);
    }
    public void renderAll(){
        for (int i=0;i<2;i++){
            for (int j=0;j<Field.COL;j++){
                renderCharaOnField(i, j);
                renderSkillOnField(i, j);
            }
        }
        for (int i=0;i<2;i++){
            renderDeck(i);
            renderLand(i);
            renderHealth(i);
            for (int j=0;j<Player.MAX_CARD;j++){
                renderOnHand(i, j);
            }
        }

        if (!lastPicked.equals(-1, -1)){
            Card kartu;
            if (pickedSource.equals("hand")){
                Pane pane = cardOnHand[lastPicked.first][lastPicked.second];
                kartu = field.getCardOnHand(lastPicked.first, lastPicked.second);
                pane.getChildren().clear();
                pane.getChildren().add(CardRender.cardNotHoverandGlow(kartu));
            } else if (pickedSource.equals("activeChara")){
                Pane pane = activeChara[lastPicked.first][lastPicked.second];
                kartu = field.chara[lastPicked.first][lastPicked.second];
                pane.getChildren().clear();
                pane.getChildren().add(CardRender.activeCharaandGlow(kartu, field.getCharaAtk(lastPicked.first, lastPicked.second), field.getCharaDef(lastPicked.first, lastPicked.second), field.isDef[lastPicked.first][lastPicked.second]));
            } else if (pickedSource.equals("activeSkill")){
                Pane pane = activeSkill[lastPicked.first][lastPicked.second];
                kartu = field.skill[lastPicked.first][lastPicked.second];
                pane.getChildren().clear();
                pane.getChildren().add(CardRender.cardNotHoverandGlow(kartu));
            } else{
                System.out.println("BUG DI RENDER ALL");
                return;
            }
            if (kartu != null) setCardHover(kartu);
        }
    }
    public void changeCharaStance(int playerId, int idx){
        field.changeCharacterStance(idx);
        setPicked("activeChara", playerId, idx);
    }
}
