package com.avatarduel.model;

import com.avatarduel.util.*;
import java.util.*;
import java.lang.*;

public class Field{
    private static final int COL = 8; // Column of field

    public static Player[] player;
    public static Chargame[][] chara;
    public static boolean[][] isDef;
    public static Skill[][] skill;
    public static int[][] skillPointer;

    public static Map<Element, Integer>[] currentLand, maxLand;

    private static int turn;

    public static void initGame(){
        player = new Player[2];

        chara = new Chargame[2][COL];
        isDef = new boolean[2][COL];
        skill = new Skill[2][COL];

        skillPointer = new int[2][COL];
        currentLand = new Map[2];
        currentLand[0] = new HashMap<Element, Integer>();
        currentLand[1] = new HashMap<Element, Integer>();
        maxLand = new Map[2];
        maxLand[0] = new HashMap<Element, Integer>();
        maxLand[1] = new HashMap<Element, Integer>();
        for (int i=0;i<2;i++){
            for (Element temp : Element.values()){
                currentLand[i].put(temp, 0);
                maxLand[i].put(temp,0);
            }
        }
        turn = 0;
    }

    public static void changeTurn(){
        turn = (turn + 1) % 2;
        for (Element temp : Element.values()){
            int mval = maxLand[turn].get(temp);
            currentLand[turn].put(temp, mval);
        }
    }

    public static void placeLand(Land Card){
        Element temp = Card.getElement();
        int nval = currentLand[turn].get(temp);
        currentLand[turn].put(temp, nval + 1);
        nval = maxLand[turn].get(temp);
        maxLand[turn].put(temp, nval + 1);
    }

    public static void placeCharacter(Chargame card, int idx){
        int curPower = currentLand[turn].get(card.getElement());
        if (curPower >= card.getPower()){
            chara[turn][idx] = card;
            isDef[turn][idx] = false;
            currentLand[turn].put(card.getElement(), curPower - card.getPower());
        } else{
            // notify player
        }
    }

    public static void changeCharacterStance(int idx){
        isDef[turn][idx] = !isDef[turn][idx];
    }

    public static void placeSkillwithTarget(Skill card, int idx1, int idx2){
        int curPower = currentLand[turn].get(card.getElement());
        if (curPower >= card.getPower()){
            skill[turn][idx1] = card;
            skillPointer[turn][idx1] = idx2;
            currentLand[turn].put(card.getElement(), curPower - card.getPower());
            
            if (card.getSkillType() == "Destroy"){
                int turn2 = (turn + 1) % 2;
                killChara(turn2, idx2);
            } 
        } else{
            // notify player
        }
    }

    public static Card getCardOnHand(int idx){
        return player[turn].cardsOnHand.get(idx);
    }

    public static int getCharaAtk(int playerId, int idx){
        int ret = chara[playerId][idx].getAtk();

        for (int i=0;i<COL;i++){
            if (skill[playerId][i] != null && skillPointer[playerId][i] == idx){
                if (skill[playerId][i].getSkillType() == "Aura"){
                    ret += skill[playerId][i].getAtk();
                }
            }
        }
        return ret;
    }

    public static int getCharaDef(int playerId, int idx){
        int ret = chara[playerId][idx].getDef();

        for (int i=0;i<COL;i++){
            if (skill[playerId][i] != null && skillPointer[playerId][i] == idx){
                if (skill[playerId][i].getSkillType() == "Aura"){
                    ret += skill[playerId][i].getDef();
                }
            }
        }
        return ret;
    }

    public static boolean getCharaPowerUpStatus(int playerId, int idx){
        for (int i=0;i<COL;i++){
            if (skill[playerId][i] != null && skillPointer[playerId][i] == idx){
                if (skill[playerId][i].getSkillType() == "Power Up"){
                    return true;
                }
            }
        }
        return false;
    }

    public static void killChara(int playerId, int idx){
        int otherPlayer = (playerId + 1) % 2;
        chara[playerId][idx] = null;
        for (int i=0;i<COL;i++){
            if (skill[playerId][i] != null && skillPointer[playerId][i] == idx){
                if (skill[playerId][i].getSkillType() != "Destroy"){
                    // removeSkill(int playerId, int idx);
                    skill[playerId][i] = null;
                    skillPointer[playerId][i] = -1;
                }
                
            }

            if (skill[otherPlayer][i] != null && skillPointer[otherPlayer][i] == idx){
                if (skill[otherPlayer][i].getSkillType() == "Destroy"){
                    // removeSkill(int playerId, int idx);
                    skill[otherPlayer][i] = null;
                    skillPointer[otherPlayer][i] = -1;
                }
            }
        }
    }

    public static void substractHealth(int playerId, int delta){
        player[playerId].health -= delta;
        if (player[playerId].health <= 0){
            endGame((playerId + 1) % 2);
        }
    }   

    public static void attack(int idx1, int idx2){
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

    public static void endGame(int winnerId){
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