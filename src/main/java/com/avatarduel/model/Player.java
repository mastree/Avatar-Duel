package com.avatarduel.model;

import java.util.*;
import com.avatarduel.model.*;
import com.avatarduel.util.*;

public class Player {
    // public List<Land> landCards;
    // public List<Skill> skillCards;
    // public List<Chargame> characterCards;

    public List<Card> cardsOnHand;

    private Deck deck;
    public int health;

    public Player() throws Exception {
        // this.landCards = new ArrayList<Land>();
        // this.skillCards = new ArrayList<Skill>();
        // this.characterCards = new ArrayList<Chargame>();
        this.cardsOnHand = new ArrayList<Card>();
        this.health = 80;
        this.deck = new Deck();

        for (int i=1;i<=7;i++){
            this.takeFromDeck();
        }
    }

    // public void addLand(Land new_l){
    //     this.landCards.add(new_l);
    // }

    // public void addSkill(Skill new_s){
    //     this.skillCards.add(new_s);
    // }

    // public void addChar(Chargame new_c){
    //     this.characterCards.add(new_c);
    // }

    public void takeFromDeck(){
        if (this.deck.getJumlahKartu() == 0){
            return;
        }
        Card newCard = deck.pickCard();
        // if (newCard instanceof Land){
        //     addLand((Land)newCard);
        // } else if (newCard instanceof Skill){
        //     addSkill((Skill)newCard);
        // } else{
        //     addChar((Chargame)newCard);
        // }
        this.cardsOnHand.add(newCard);
    }

    // public Land removeLand(int idx){
    //     Land temp = this.landCards.remove(idx);
    //     return temp;
    // }

    // public Skill removeSkill(int idx){
    //     Skill temp = this.skillCards.remove(idx);
    //     return temp;
    // }

    // public Chargame removeChar(int idx){
    //     Chargame temp = this.characterCards.remove(idx);
    //     return temp;
    // }

    public Card removeCard(int idx){
        Card temp = this.cardsOnHand.remove(idx);
        return temp;
    }
}