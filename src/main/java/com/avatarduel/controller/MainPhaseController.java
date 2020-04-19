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

public class MainPhaseController extends GameController implements Initializable {
    @Override
    public void initField(Field field){
        super.initField(field);
        labelPhase.setText("Main Phase");
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
            goToBattlePhase();
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
    /**
     * Handler jika skill di arena diklik
     */
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
    /**
     * Handler jika deck diklik
     */
    @FXML
    public void handleDeck(MouseEvent event){
        renderDeck(0);
    }
    /**
     * On click handler untuk menghapus kartu di arena yang dipilih
     */
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
    /**
     * Untuk mengganti status posisi(menyerang/ bertahan) character
     *
     * @param playerId id player pemilik
     * @param idx posisi character
     */
    public void changeCharaStance(int playerId, int idx){
        field.changeCharacterStance(idx);
        setPicked("activeChara", playerId, idx);
    }
}
