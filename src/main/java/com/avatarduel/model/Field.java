package com.avatarduel.model;

import com.avatarduel.util.*;
import java.util.*;
import java.lang.*;

public class Field{
    public static final int COL = 6; // Column of field

    public Player[] player;
    public Chargame[][] chara;
    public boolean[][] isDef;
    public Skill[][] skill;
    public Pair[][] skillPointer;

    public Map<Element, Integer>[] currentLand, maxLand;

    public int turn;

    public Field(){
        player = new Player[2];
        try {
            player[0] = new Player();
            player[1] = new Player();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        chara = new Chargame[2][COL];
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
                skillPointer[i][j] = new Pair();
            }
            for (Element temp : Element.values()){
                currentLand[i].put(temp, 0);
                maxLand[i].put(temp,0);
            }
        }
        turn = 0;
    }

    public void changeTurn(){
        turn = (turn + 1) % 2;
        for (Element temp : Element.values()){
            int mval = maxLand[turn].get(temp);
            currentLand[turn].put(temp, mval);
        }
    }

    public void placeLand(Land Card){
        Element temp = Card.getElement();
        int nval = currentLand[turn].get(temp);
        currentLand[turn].put(temp, nval + 1);
        nval = maxLand[turn].get(temp);
        maxLand[turn].put(temp, nval + 1);
    }

    public boolean placeCharacter(Chargame card, int idx){
        int curPower = currentLand[turn].get(card.getElement());
        if (curPower >= card.getPower() && chara[turn][idx] == null){
            chara[turn][idx] = card;
            isDef[turn][idx] = false;
            currentLand[turn].put(card.getElement(), curPower - card.getPower());
            return true;
        } else{
            // notify player
            return false;
        }
    }

    public boolean placeSkill(Skill card, int idx){
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

    public boolean setSkillTarget(Pair asal, int playerId, int idx){
        if (chara[playerId][idx] != null){
            skillPointer[asal.first][asal.second] = new Pair(playerId, idx);
            if (skill[asal.first][asal.second].getSkillType().equals("Destroy")){
                killChara(playerId, idx);
            }
        } else return false;
        return true;
    }

    public void changeCharacterStance(int idx){
        isDef[turn][idx] = !isDef[turn][idx];
    }

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

    public Card getCardOnHand(int playerId, int idx){
        if (idx < 0 || idx >= 7) return null;
        return player[playerId].cardsOnHand[idx];
    }

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

    public void substractHealth(int playerId, int delta){
        player[playerId].health -= delta;
        if (player[playerId].health <= 0){
            endGame((playerId + 1) % 2);
        }
    }   

    public void attack(int idx1, int idx2){
        int turn2 = (turn + 1) % 2;
        int damage = getCharaAtk(turn, idx1);
        int minimum;
        if (isDef[turn2][idx2]){
            minimum = getCharaDef(turn2, idx2);

            if (damage >= minimum){
                if (getCharaPowerUpStatus(turn, idx1)){
                    killChara(turn2, idx2);
                    substractHealth(turn2, damage - minimum);
                } else{
                    killChara(turn2, idx2);
                }
            }
        } else{
            minimum = getCharaAtk(turn2, idx2);

            if (damage >= minimum){
                killChara(turn2, idx2);
                substractHealth(turn2, damage - minimum);
            }
        }
    }

    public void endGame(int winnerId){
        // Permainan selesai
    }

}

// package com.avatarduel.model;

// public class Field{
//     private Player p;
//     private Chargame[] karakter;
//     private boolean[] posisi;
//     private Skill[] skillfield;
//     private int[] skillpos;
//     private int neffkar;
//     private int neffskill;

//     public Field(Player play){
//         karakter = new Chargame[7];
//         posisi = new boolean[7];
//         skillfield = new Skill[7];
//         skillpos = new int[7];
//         neffkar = 0;
//         neffskill = 0;
//         p = play;
//     }

//     public void playChar(int idx){
//         if (neffkar < 7){
//             Chargame c = p.removeChar(idx);
//             karakter[neffkar] = c;
//             posisi[neffkar] = attack;
//             karakter[posisi[neffkar]]
//             neffkar++;
//         }
//         else{
//             //throw exception atau langsung print error?
//         }
//     }

//     public void playSkill(int idx){
//         if (neffskill < 7) {
//             Skill s = p.removeSkill(idx);
//             skillfield[neffskill] = s;
//             skillpos[neffskill] = posisiChar;
//             neffskill++;
//         }
//         else{
//             //throw exception atau langsung print error?
//         }
//     }

//     public void changePos(int pos){
//         if (pos < neffkar){
//             posisi[pos] = !(posisi[pos]);
//         }
//         else {
//             //throw exception atau langsung print error?
//         }
//     }

//     public void discardSpell(int pos){
//         if (pos < neffskill && neffskill>0){
//             Skill temp = skillfield[pos];
//             int atk = temp.getAtk();
//             int def = temp.getDef();
//             int idx = skillpos[pos];
//             int kar = karakter[idx];
//             for (int i=pos; i<5;i++){
//                 skillfield[i] = skillfield[i+1];
//                 skillpos[i] = skillpos[i+1];
//             }
//             neffskill--;
//         }
//         else{
//             //throw exception atau langsung print error?
//         }
//     }

//     public int getTotalAtk(int pos){
//         int temp = karakter[pos].getAtk();
//         for (int i=0;i<7;i++){
//             if (skillpos[i] == pos){
//                 temp+=skillfield[i].getAtk();
//             }
//         }
//         return temp;
//     }

//     public int getTotalDef(int pos){
//         int temp = karakter[pos].getDef();
//         for (int i=0;i<7;i++){
//             if (skillpos[i] == pos){
//                 temp+=skillfield[i].getDef();
//             }
//         }
//         return temp;
//     }
// }