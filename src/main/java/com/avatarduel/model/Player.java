package com.avatarduel.model;

import java.util.*;
import com.avatarduel.model.*;
import com.avatarduel.util.*;

public class Player {
    public List<Land> landCards;
    public List<Skill> skillCards;
    public List<Chargame> characterCards;
    public Map<Element, Integer> num_land;
    public Map<Element, Integer> power;

    private Deck deck;
    public int health;

    public Player() throws Exception {
        this.landCards = new ArrayList<Land>();
        this.skillCards = new ArrayList<Skill>();
        this.characterCards = new ArrayList<Chargame>();
        this.num_land = new HashMap<Element,Integer>();
        this.power = new HashMap<Element,Integer>();
        this.health = 80;
        for (Element temp : Element.values()){
            this.num_land.put(temp, 0);
            this.power.put(temp,0);
        }
        this.deck = new Deck();
    }

    public void addLand(Land new_l){
        this.landCards.add(new_l);
    }

    public void addSkill(Skill new_s){
        this.skillCards.add(new_s);
    }

    public void addChar(Chargame new_c){
        this.characterCards.add(new_c);
    }

    public void takeFromDeck(){
        if (this.deck.getJumlahKartu() == 0){
            return;
        }
        Card newCard = deck.pickCard();
        if (newCard instanceof Land){
            addLand((Land)newCard);
        } else if (newCard instanceof Skill){
            addSkill((Skill)newCard);
        } else{
            addChar((Chargame)newCard);
        }
    }

    public void removeLand(int idx){
        Land removedLand = this.landCards.remove(idx);
        Element element = removedLand.getElement();
        int temp = num_land.get(element) + 1;
        int temp1 = power.get(element) + 1;
        num_land.put(element,temp);
        power.put(element, temp1);
    }

    public Skill removeSkill(int idx){
        Skill temp = this.skillCards.remove(idx);
        return temp;
    }

    public Chargame removeChar(int idx){
        Chargame temp = this.characterCards.remove(idx);
        return temp;
    }
}