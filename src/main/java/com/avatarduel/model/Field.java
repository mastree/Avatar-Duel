package com.avatarduel.model;

import com.avatarduel.util.*;
import java.util.*;
import java.lang.*;

public class Field{
    public static final int COL = 6; // Column of field

    public Player[] player;
    public Chargame[][] chara;
    public boolean[][] charaBaru;
    public boolean[][] isDef;
    public Skill[][] skill;
    public Pair[][] skillPointer;

    public Map<Element, Integer>[] currentLand, maxLand;

    public int turn;

    /** 
     * Creates a field with an array of player (2 players), array of array of card (character, skill), array of map of land 
    */
    public Field(){
        player = new Player[2];
        try {
            player[0] = new Player();
            player[1] = new Player();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        chara = new Chargame[2][COL];
        charaBaru = new boolean[2][COL];
        isDef = new boolean[2][COL];
        skill = new Skill[2][COL];

        skillPointer = new Pair[2][COL];
        currentLand = new Map[2];
        currentLand[0] = new HashMap<Element, Integer>();
        currentLand[1] = new HashMap<Element, Integer>();
        maxLand = new Map[2];
        maxLand[0] = new HashMap<Element, Integer>();
        maxLand[1] = new HashMap<Element, Integer>();
        for (int i=0;i<2;i++){
            for (int j=0;j<COL;j++){
                charaBaru[i][j] = false;
                skillPointer[i][j] = new Pair();
            }
            for (Element temp : Element.values()){
                currentLand[i].put(temp, 0);
                maxLand[i].put(temp, 0);
            }
        }
        turn = 0;
    }

    /**
     * Change turn of player 
     */
    public void changeTurn(){
        turn = (turn + 1) % 2;
        for (Element temp : Element.values()){
            int mval = maxLand[turn].get(temp);
            currentLand[turn].put(temp, mval);
        }
        for (int i=0;i<COL;i++){
            charaBaru[turn][i] = false;
        }
    }

    /**
     * Put land from hand to field and increase the number of current land and max number in field with specific element
     */
    public void placeLand(Land Card){
        Element temp = Card.getElement();
        int nval = currentLand[turn].get(temp);
        currentLand[turn].put(temp, nval + 1);
        nval = maxLand[turn].get(temp);
        maxLand[turn].put(temp, nval + 1);
    }

    /**
     * place the character from hand to field
     * @param card card that will be placed 
     * @param idx index on field 
     * @return true if the power is enough to place the character on field and there is no character card in that index 
     */
    public boolean placeCharacter(Chargame card, int idx){
        int curPower = currentLand[turn].get(card.getElement());
        if (curPower >= card.getPower() && chara[turn][idx] == null){
            chara[turn][idx] = card;
            isDef[turn][idx] = false;
            currentLand[turn].put(card.getElement(), curPower - card.getPower());
            charaBaru[turn][idx] = true;
            return true;
        } else{
            // notify player
            return false;
        }
    }

    /**
     * place the skill from hand to field
     * @param card skill that will be placed 
     * @param idx index on field 
     * @return true if the power is enough to place the skill on field and there is no skill card in that index 
     */
    public boolean placeSkill(Skill card, int idx){
        if (!canPlaceSkills()) return false;
        int curPower = currentLand[turn].get(card.getElement());
        if (curPower >= card.getPower() && skill[turn][idx] == null){
            skill[turn][idx] = card;
            currentLand[turn].put(card.getElement(), curPower - card.getPower());
            return true;
        } else{
            // notify player
            return false;
        }
    }

    /**
     * set that target of skill card 
     * @param asal pair
     * @param playerId id of player
     * @param idx place on field 
     * @return true if there is a character on idx of field 
     */
    public boolean setSkillTarget(Pair asal, int playerId, int idx){
        if (chara[playerId][idx] != null){
            skillPointer[asal.first][asal.second] = new Pair(playerId, idx);
            if (skill[asal.first][asal.second].getSkillType().equals("Destroy")){
                killChara(playerId, idx);
            }
        } else return false;
        return true;
    }

    /**
     * Change the character stance
     * @param idx place of character on field 
     */
    public void changeCharacterStance(int idx){
        isDef[turn][idx] = !isDef[turn][idx];
    }

    /**
     * place skill on field with target
     * @param card the detail of card
     * @param idx1 index1
     * @param target the target pair
     */
    public void placeSkillwithTarget(Skill card, int idx1, Pair target){
        int curPower = currentLand[turn].get(card.getElement());
        if (curPower >= card.getPower()){
            skill[turn][idx1] = card;
            skillPointer[turn][idx1] = target;
            currentLand[turn].put(card.getElement(), curPower - card.getPower());
            
            if (card.getSkillType().equals("Destroy")){
                killChara(target.first, target.second);
            } 
        } else{
            // notify player
        }
    }

    /**
     * To know the card on hand
     * @param playerId id of player
     * @param idx place of card on hand
     * @return the card of player on hand with idx
     */
    public Card getCardOnHand(int playerId, int idx){
        if (idx < 0 || idx >= Player.MAX_CARD) return null;
        return player[playerId].cardsOnHand[idx];
    }

    /**
     * The number of attack of character
     * @param playerId id of player
     * @param idx index
     * @return the number of attack of character, added with attack from skill (if the skill is aura)
     */
    public int getCharaAtk(int playerId, int idx){
        if (chara[playerId][idx] == null) return 0;
        int ret = chara[playerId][idx].getAtk();
        int other = (playerId + 1) % 2;

        for (int i=0;i<COL;i++){
            if (skill[playerId][i] != null && skillPointer[playerId][i].equals(playerId, idx)){
                if (skill[playerId][i].getSkillType().equals("Aura")){
                    ret += skill[playerId][i].getAtk();
                }
            }
            if (skill[other][i] != null && skillPointer[other][i].equals(playerId, idx)){
                if (skill[other][i].getSkillType().equals("Aura")){
                    ret += skill[other][i].getAtk();
                }
            }
        }
        return ret;
    }

    /**
     * The number of defense of character 
     * @param playerId id of player
     * @param idx index
     * @return the number of defense of character, added with defense from skill (if the skill is aura)
     */
    public int getCharaDef(int playerId, int idx){
        if (chara[playerId][idx] == null) return 0;
        int ret = chara[playerId][idx].getDef();
        int other = (playerId + 1) % 2;

        for (int i=0;i<COL;i++){
            if (skill[playerId][i] != null && skillPointer[playerId][i].equals(playerId, idx)){
                if (skill[playerId][i].getSkillType().equals("Aura")){
                    ret += skill[playerId][i].getDef();
                }
            }
            if (skill[other][i] != null && skillPointer[other][i].equals(playerId, idx)){
                if (skill[other][i].getSkillType().equals("Aura")){
                    ret += skill[other][i].getDef();
                }
            }
        }
        return ret;
    }

    /**
     * Get the status of power up (skill)
     * @param playerId id of player
     * @param idx index
     * @return true if the player have power up 
     */
    public boolean getCharaPowerUpStatus(int playerId, int idx){
        if (chara[playerId][idx] == null) return false;
        int other = (playerId + 1) % 2;
        for (int i=0;i<COL;i++){
            if (skill[playerId][i] != null && skillPointer[playerId][i].equals(playerId, idx)){
                if (skill[playerId][i].getSkillType().equals("Power Up")){
                    return true;
                }
            }
            if (skill[other][i] != null && skillPointer[other][i].equals(playerId, idx)){
                if (skill[other][i].getSkillType().equals("Power Up")){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * to kill character of enemy
     * @param playerId id of player
     * @param idx index
     */
    public void killChara(int playerId, int idx){
        int other = (playerId + 1) % 2;
        chara[playerId][idx] = null;
        for (int i=0;i<COL;i++){
            if (skill[playerId][i] != null && skillPointer[playerId][i].equals(playerId, idx)){
                skill[playerId][i] = null;
                skillPointer[playerId][i] = new Pair();
            }
            if (skill[other][i] != null && skillPointer[other][i].equals(playerId, idx)){
                skill[other][i] = null;
                skillPointer[other][i] = new Pair();
            }
        }
    }

    /**
     * To remove skill  
     * @param playerId id of player
     * @param idx index
     */
    public void removeSkill(int playerId, int idx){
        if (skill[playerId][idx] == null) return;
        skill[playerId][idx] = null;
        skillPointer[playerId][idx] = new Pair();
    }

    /**
     * Substract delta health of playerId 
     * @param playerId player that will be substracted
     * @param delta number of health 
     */
    public void substractHealth(int playerId, int delta){
        player[playerId].health -= delta;
        if (player[playerId].health <= 0){
            player[playerId].health = 0;
            endGame((playerId + 1) % 2);
        }
    }   

    /**
     * @param idx1 index 1
     * @param idx2 index 2
     */
    public boolean attack(int idx1, int idx2){
        int turn2 = (turn + 1) % 2;
        int damage = getCharaAtk(turn, idx1);
        int minimum;
        if (charaBaru[turn][idx1]) return false;
        if (chara[turn2][idx2] == null){
            return attack(idx1);
        }
        if (isDef[turn2][idx2]){
            minimum = getCharaDef(turn2, idx2);

            if (damage > minimum){
                if (getCharaPowerUpStatus(turn, idx1)){
                    killChara(turn2, idx2);
                    substractHealth(turn2, damage - minimum);
                } else{
                    killChara(turn2, idx2);
                }
                charaBaru[turn][idx1] = true;
                return true;
            }
        } else{
            minimum = getCharaAtk(turn2, idx2);

            if (damage > minimum){
                killChara(turn2, idx2);
                substractHealth(turn2, damage - minimum);
                charaBaru[turn][idx1] = true;
                return true;
            }
        }
        return false;
    }

    /**
     * @param idx index
     */
    public boolean attack(int idx){
        int turn2 = (turn + 1) % 2;
        int damage = getCharaAtk(turn, idx);
        boolean bisa = true;
        if (charaBaru[turn][idx]) return false;
        for (int j=0;j<COL;j++){
            if (chara[turn2][j] != null){
                bisa = false;
                break;
            }
        }
        if (bisa){
            charaBaru[turn][idx] = true;
            substractHealth(turn2, damage);
            return true;
        }
        return false;
    }

    /**
     * the game ends
     * @param winnerId the id of winner
     */
    public void endGame(int winnerId){
        // Permainan selesai
    }

    /**
     * to place skill on field 
     * @return true if there is a character on field 
     */
    public boolean canPlaceSkills(){
        for (int i=0;i<2;i++){
            for (int j=0;j<COL;j++){
                if (chara[i][j] != null) return true;
            }
        }
        return false;
    }
}