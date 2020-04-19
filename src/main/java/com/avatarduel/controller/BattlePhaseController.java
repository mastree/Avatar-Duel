package com.avatarduel.controller;

import com.avatarduel.model.Card;
import com.avatarduel.model.Field;
import com.avatarduel.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class BattlePhaseController extends GameController implements Initializable {
    @Override
    public void initField(Field field){
        super.initField(field);
        labelPhase.setText("Battle Phase");
    }
    /**
     * On click handler untuk kembali ke main menu
     */
    @FXML
    public void backToMainMenu(ActionEvent event){
        goToMainMenu();
    }
    /**
     * On click handler untuk ke phase selanjutnya
     */
    @FXML
    public void handleNextPhase(ActionEvent event){
        try{
            field.changeTurn();
            goToDrawPhase();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Handler jika kartu di tangan diklik
     */
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
    /**
     * Handler jika character di arena diklik
     */
    @FXML
    public void handleCharaOnField(MouseEvent event){
        int playerId = 0, idx = 0;
        Pane pane = (Pane) event.getSource();
        for (playerId=0;playerId<2;playerId++){
            for (idx=0;idx<Field.COL;idx++){
                if (activeChara[playerId][idx] == pane) break;
            }
            if (idx != Field.COL) break;
        }
        Card kartu = field.chara[playerId][idx];
        if (pickedSource.equals("activeChara") && lastPicked.first == field.turn){
            if (!lastPicked.equals(-1, -1)){
                if (playerId != field.turn){
                    if (field.attack(lastPicked.second, idx)){
                        resetPicked();
                        renderAll();
                        if (field.player[playerId].health <= 0){
                            goToEndGame(field.turn);
                        }
                    } else{
                        resetPicked();
                    }
                } else{
                    if (kartu != null) setPicked("activeChara", playerId, idx);
                    else resetPicked();
                }
            } else{
                if (kartu == null){
                    resetPicked();
                    return;
                }
                setPicked("activeChara", playerId, idx);
            }
        } else{
            if (kartu != null){
                setPicked("activeChara", playerId, idx);
            } else resetPicked();
        }
    }
    /**
     * Handler jika skill di arena diklik
     */
    @FXML
    public void handleSkillOnField(MouseEvent event){
        int playerId = 0, idx = 0;
        Pane pane = (Pane) event.getSource();
        for (playerId=0;playerId<2;playerId++){
            for (idx=0;idx<Field.COL;idx++){
                if (activeSkill[playerId][idx] == pane) break;
            }
            if (idx != Field.COL) break;
        }
        Card kartu = field.skill[playerId][idx];
        if (kartu != null){
            setPicked("activeSkill", playerId, idx);
        } else resetPicked();
    }
    /**
     * Handler jika deck diklik
     */
    @FXML
    public void handleDeck(MouseEvent event){
        renderDeck(0);
    }
}
