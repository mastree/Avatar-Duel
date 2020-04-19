package com.avatarduel.controller;

import com.avatarduel.AvatarDuel;
import com.avatarduel.model.Card;
import com.avatarduel.model.Field;
import com.avatarduel.model.Pair;
import com.avatarduel.model.Player;
import com.avatarduel.renderer.CardRender;
import com.avatarduel.renderer.FieldRender;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
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

    public AnchorPane cardHoverStatus;

    public ProgressBar myHealth, urHealth, theHealth[];

    public Label labelPhase;

    public Pair lastPicked;
    public String pickedSource;
    public boolean lock;
    
    public boolean putLand;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // VARIABLES
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
    }
    /**
     * Menginisialisasi field yang akan menjadi arena permainan
     *
     * @param field field yang akan dijadikan arena permainan
     */
    public void initField(Field field){
        this.field = field;
        renderAll();
    }
    /**
     * Mereset kartu yang sedang dipilih (termasuk menghilangkan glow effect)
     */
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
        renderLand(field.turn);
    }
    /**
     * Memindahkan fokus kartu yang sedang dipilih
     *
     * @param source jenis yang akan dipilih ("hand", "activeChara", "activeSkill")
     * @param playerId pemilik kartu
     * @param idx index posisi kartu yang dipilih
     */
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
    /**
     * Menampilkan kartu secara hover
     *
     * @param kartu kartu yang akan dihover
     */
    public void setCardHover(Card kartu){
        if (kartu == null) return;
        Pane pane = CardRender.cardHover(kartu);
        upperLeftPanel.getChildren().clear();
        upperLeftPanel.getChildren().add(pane);
        if (kartu.getCardType().equals("Character") && pickedSource.equals("activeChara")){
            VBox stats = CardRender.statusOfChara(field, lastPicked.first, lastPicked.second);
            cardHoverStatus.getChildren().clear();
            cardHoverStatus.getChildren().add(stats);
        } else if (kartu.getCardType().equals("Skill") && pickedSource.equals("activeSkill") && lock == false){
            VBox stats = CardRender.statusOfSkill(field, lastPicked.first, lastPicked.second);
            cardHoverStatus.getChildren().clear();
            cardHoverStatus.getChildren().add(stats);
        } else{
            cardHoverStatus.getChildren().clear();
        }
    }
    /**
     * Untuk ke main menu
     */
    public void goToMainMenu(){
        Parent temp;
        Scene gamePlay;
        try{
            temp = FXMLLoader.load(AvatarDuel.class.getResource("fxml/MainMenu.fxml"));
            gamePlay = new Scene(temp, 800, 600);

            Stage app_stage = (Stage) myCard1.getScene().getWindow();
            app_stage.setScene(gamePlay);
            app_stage.centerOnScreen();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Untuk ke draw phase
     */
    public void goToDrawPhase(){
        FXMLLoader loader;
        Parent temp;
        Scene gamePlay;
        try {
            loader = new FXMLLoader(AvatarDuel.class.getResource("fxml/DrawPhase.fxml"));
            temp = loader.load();
            gamePlay = new Scene(temp, 1000, 650);
            DrawPhaseController controller = loader.<DrawPhaseController>getController();
            controller.initField(field);

            Stage app_stage = (Stage) myCard1.getScene().getWindow();
            app_stage.setScene(gamePlay);
            app_stage.centerOnScreen();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Untuk ke main phase
     */
    public void goToMainPhase(){
        FXMLLoader loader;
        Parent temp;
        Scene gamePlay;
        try {
            loader = new FXMLLoader(AvatarDuel.class.getResource("fxml/MainPhase.fxml"));
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
    /**
     * Untuk ke battle phase
     */
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
    /**
     * Untuk ke end game
     *
     * @param playerId id player yang memenangkan duel
     */
    public void goToEndGame(int playerId){
        FXMLLoader loader;
        Parent temp;
        Scene gamePlay;
        try {
            loader = new FXMLLoader(AvatarDuel.class.getResource("fxml/EndGame.fxml"));
            temp = loader.load();
            gamePlay = new Scene(temp, 600, 400);
            EndGameController controller = loader.<EndGameController>getController();
            controller.init(playerId);

            Stage app_stage = (Stage) myCard1.getScene().getWindow();
            app_stage.setScene(gamePlay);
            app_stage.centerOnScreen();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Merender kartu yang sedang ditangan
     *
     * @param playerId id player pemilik kartu
     * @param idx posisi kartu
     */
    public void renderOnHand(int playerId, int idx){
        if (playerId != field.turn) return;
        FieldRender.renderOnHand(cardOnHand[playerId][idx], field.getCardOnHand(playerId, idx));
    }
    /**
     * Merender character yang di dalam arena
     *
     * @param playerId id player pemilik kartu
     * @param idx posisi kartu
     */
    public void renderCharaOnField(int playerId, int idx){
        FieldRender.renderCharaOnField(activeChara[playerId][idx], field, playerId, idx);
    }
    /**
     * Merender skill yang di dalam arena
     *
     * @param playerId id player pemilik kartu
     * @param idx posisi kartu
     */
    public void renderSkillOnField(int playerId, int idx){
        FieldRender.renderSkillOnField(activeSkill[playerId][idx], field.skill[playerId][idx]);
    }
    /**
     * Merender deck
     *
     * @param playerId id player pemilik kartu
     */
    public void renderDeck(int playerId){
        FieldRender.renderDeck(theDeck[playerId], field, playerId);
    }
    /**
     * Merender land
     *
     * @param playerId id player pemilik kartu
     */
    public void renderLand(int playerId){
        FieldRender.renderLand(theLand[playerId], field, playerId);
    }
    /**
     * Merender progress bar indikator health
     *
     * @param playerId id player pemilik kartu
     */
    public void renderHealth(int playerId){
        FieldRender.renderHealth(theHealth[playerId], hPIndicator[playerId], field, playerId);
    }
    /**
     * Merender semua
     */
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
}
