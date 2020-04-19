package com.avatarduel.controller;

import com.avatarduel.model.Card;
import com.avatarduel.model.Field;
import com.avatarduel.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DrawPhaseController extends GameController implements Initializable {
    @Override
    public void initField(Field field){
        super.initField(field);
        labelPhase.setText("Draw Phase");
        if (field.player[field.turn].deck.getJumlahKartu() == 0){
            goToEndGame((field.turn + 1) % 2);
        }
    }
    @FXML
    public void backToMainMenu(ActionEvent event){
        goToMainMenu();
    }
    @FXML
    public void handleNextPhase(ActionEvent event){
        try{
            field.player[field.turn].takeFromDeck();
            goToMainPhase();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void handleCardOnHand(MouseEvent event){
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
        resetPicked();
        int playerId = 0, idx = 0;
        Pane temp = (Pane) event.getSource();
        for (playerId=0;playerId<2;playerId++) {
            for (idx = 0; idx < Field.COL; idx++) {
                if (temp == activeChara[playerId][idx]) {
                    break;
                }
            }
            if (idx != Field.COL) {
                break;
            }
        }
        Card kartu = field.chara[playerId][idx];
        if (kartu == null){
            return;
        }
        setPicked("activeChara", playerId, idx);
    }
    @FXML
    public void handleSkillOnField(MouseEvent event){
        resetPicked();
        int playerId = 0, idx = 0;
        Pane temp = (Pane) event.getSource();
        for (playerId=0;playerId<2;playerId++) {
            for (idx = 0; idx < Field.COL; idx++) {
                if (temp == activeSkill[playerId][idx]) {
                    break;
                }
            }
            if (idx != Field.COL) {
                break;
            }
        }
        Card kartu = field.skill[playerId][idx];
        if (kartu == null){
            return;
        }
        setPicked("activeSkill", playerId, idx);
    }
    @FXML
    public void handleDeck(MouseEvent event){
        int idx;
        AnchorPane apane = (AnchorPane) event.getSource();
        for (idx=0;idx<2;idx++){
            if (theDeck[idx] == apane) break;
        }
        if (idx != field.turn) return;
        field.player[field.turn].takeFromDeck();
        this.goToMainPhase();
    }
    @FXML
    public void changeWithNewCard(ActionEvent event){
        if (pickedSource.equals("hand") && lastPicked.first == field.turn){
            if (field.player[lastPicked.first].cardsOnHand[lastPicked.second] != null){
                field.player[lastPicked.first].removeCard(lastPicked.second);
                field.player[lastPicked.first].takeFromDeck();
                goToMainPhase();
            }
        }
    }
}
