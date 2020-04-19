package com.avatarduel.model;

import java.util.*;
import com.avatarduel.model.*;
import com.avatarduel.util.*;

/**
 * Creates a player with list of card, deck and health 
 */
public class Player {
    public static final int MAX_CARD = 7;
    public Card cardsOnHand[];

    public Deck deck;
    public int health;

    /**
     * Creates a player with health of 80 points, and take card from deck
     * @throws Exception
     */
    public Player() throws Exception {
        // this.landCards = new ArrayList<Land>();
        // this.skillCards = new ArrayList<Skill>();
        // this.characterCards = new ArrayList<Chargame>();
        this.cardsOnHand = new Card[MAX_CARD];
        this.health = 80;
        this.deck = new Deck();

        for (int i=1;i<=MAX_CARD;i++){
            this.takeFromDeck();
        }
    }

    /**
     * Take one card from deck to hand 
     */
    public void takeFromDeck(){
        int slot = -1;
        for (slot=0;slot<MAX_CARD;slot++){
            if (cardsOnHand[slot] == null) break;
        }
        if (slot == MAX_CARD) return;
        if (this.deck.getJumlahKartu() == 0){
            return;
        }
        Card newCard = deck.pickCard();
        this.cardsOnHand[slot] = newCard;
    }

    /**
     * remove card from hand 
     * @param idx the place of card in hand
     */
    public Card removeCard(int idx){
        if (idx < 0 || idx >= MAX_CARD) return null;
        Card temp = this.cardsOnHand[idx];
        this.cardsOnHand[idx] = null;
        return temp;
    }
}