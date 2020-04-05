package com.avatarduel.util;

import java.io.*;
import java.util.*;
import java.lang.*;
import com.avatarduel.model.*;

public final class LandFactory implements CardFactory {
    private static final String LAND_CSV_FILE_PATH = "../card/data/land.csv";
    private static LandFactory factory;
    private List<String[]> cards;

    private LandFactory() throws IOException {
        CSVReader csvreader = new CSVReader(new File(LAND_CSV_FILE_PATH), ";");
        csvreader.setSkipHeader(true);

        this.cards = csvreader.read(); 
    }

    public static LandFactory getInstance() throws IOException {
        if (factory == null){
            factory = new LandFactory();
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

        return new Land(name, element, description, imgPath);
    }
}
