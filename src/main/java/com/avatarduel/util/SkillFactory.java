package com.avatarduel.util;

import java.io.*;
import java.util.*;
import java.lang.*;
import com.avatarduel.model.*;
import java.net.URISyntaxException;
import com.avatarduel.AvatarDuel;

public final class SkillFactory implements CardFactory {
    private static final String SKILL_CSV_FILE_PATH = "card/data/skill.csv";
    private static SkillFactory factory;
    private List<String[]> cards;

    /**
     * Konstruktor
     */
    private SkillFactory() throws IOException, URISyntaxException {
        CSVReader csvreader = new CSVReader(new File(AvatarDuel.class.getResource(SKILL_CSV_FILE_PATH).toURI()), ";");
        csvreader.setSkipHeader(true);

        this.cards = csvreader.read(); 
    }

    /**
     * Getter singleton
     *
     * @return instance singleton
     */
    public static SkillFactory getInstance() throws Exception {
        if (factory == null){
            factory = new SkillFactory();
        }
        return factory;
    }

    @Override
    public Card create(){
        Random rand = new Random(); 
        int len = cards.size();
        int chance = rand.nextInt(100);
        int idx;
        
        if (chance < 70){ // 70% chance to get skill aura
            idx = rand.nextInt(len - 10);
        } else{ // 30% chance to get destroy or power up
            idx = rand.nextInt(10) + len - 10;
        }

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
        } else{
            element = Element.AIR;
        }
        String description = cardInfo[3];
        String imgPath = cardInfo[4];
        int power = Integer.parseInt(cardInfo[5]);
        int atk = Integer.parseInt(cardInfo[6]);
        int def = Integer.parseInt(cardInfo[7]);

        return new Skill(name, element, description, imgPath, atk, def, power);
    }
}
