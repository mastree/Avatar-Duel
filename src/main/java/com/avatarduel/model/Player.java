package com.avatarduel.model;

import java.util.*;
import com.avatarduel.model.*;
import com.avatarduel.util.*;

public class Player {
    public static final int MAX_CARD = 7;
    public Card cardsOnHand[];

    public Deck deck;
    public int health;

    public Player() throws Exception {
        // this.landCards = new ArrayList<Land>();
        // this.skillCards = new ArrayList<Skill>();
        // this.characterCards = new ArrayList<Chargame>();
        this.cardsOnHand = new Card[MAX_CARD];
        this.health = 80;
        this.deck = new Deck();

        for (int i=1;i<=7;i++){
            this.takeFromDeck();
        }
    }

    public void takeFromDeck(){
        int slot = -1;
        for (slot=0;slot<MAX_CARD;slot++){
            if (cardsOnHand[slot] == null) break;
        }
        if (slot == -1) return;
        if (this.deck.getJumlahKartu() == 0){
            return;
        }
        Card newCard = deck.pickCard();
        this.cardsOnHand[slot] = newCard;
    }

    public Card removeCard(int idx){
        if (idx < 0 || idx >= 7) return null;
        Card temp = this.cardsOnHand[idx];
        this.cardsOnHand[idx] = null;
        return temp;
    }
}