package com.avatarduel.util;

import java.io.*;
import java.util.*;
import java.lang.*;
import com.avatarduel.model.*;

public final class CharacterFactory implements CardFactory {
    private static final String CHARA_CSV_FILE_PATH = "../card/data/character.csv";
    private static CharacterFactory factory;
    private List<String[]> cards;

    private CharacterFactory() throws IOException {
        CSVReader csvreader = new CSVReader(new File(CHARA_CSV_FILE_PATH), ";");
        csvreader.setSkipHeader(true);

        this.cards = csvreader.read(); 
    }

    public static CharacterFactory getInstance() throws IOException {
        if (factory == null){
            factory = new CharacterFactory();
        }
        return factory;
    }

    @Override
    public Card create(){
        Random rand = new Random(); 
        int len = cards.size();
        int idx = rand.nextInt(len);

        String[] cardInfo = cards.get(idx);
        String name = cardInfo[1];
        Element element;
        if (cardInfo[2] == "WATER"){
            element = Element.WATER;
        } else if (cardInfo[2] == "FIRE"){
            element = Element.FIRE;
        } else if (cardInfo[2] == "EARTH"){
            element = Element.EARTH;
        } else{
            element = Element.AIR;
        }
        String description = cardInfo[3];
        String imgPath = cardInfo[4];
        int atk = Integer.parseInt(cardInfo[5]);
        int def = Integer.parseInt(cardInfo[6]);
        int power = Integer.parseInt(cardInfo[7]);

        return new Chargame(name, element, description, imgPath, atk, def, power);
    }
}
