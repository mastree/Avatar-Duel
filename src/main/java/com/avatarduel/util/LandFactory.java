package com.avatarduel.util;

import java.io.*;
import java.util.*;
import java.lang.*;
import com.avatarduel.model.*;
import java.net.URISyntaxException;
import com.avatarduel.AvatarDuel;

public final class LandFactory implements CardFactory {
    private static final String LAND_CSV_FILE_PATH = "card/data/land.csv";
    private static LandFactory factory;
    private List<String[]> cards;

    /**
     * Konstruktor
     */
    private LandFactory() throws IOException, URISyntaxException {
        CSVReader csvreader = new CSVReader(new File(AvatarDuel.class.getResource(LAND_CSV_FILE_PATH).toURI()), ";");
        csvreader.setSkipHeader(true);

        this.cards = csvreader.read(); 
    }

    /**
     * Getter singleton
     *
     * @return instance singleton
     */
    public static LandFactory getInstance() throws Exception {
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
        if (cardInfo[2].equals("WATER")){
            element = Element.WATER;
        } else if (cardInfo[2].equals("FIRE")){
            element = Element.FIRE;
        } else if (cardInfo[2].equals("EARTH")){
            element = Element.EARTH;
        } else if (cardInfo[2].equals("ENERGY")){
            element = Element.ENERGY;
        }else{
            element = Element.AIR;
        }
        String description = cardInfo[3];
        String imgPath = cardInfo[4];

        return new Land(name, element, description, imgPath);
    }
}
