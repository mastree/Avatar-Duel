package com.avatarduel.util;

import java.io.*;
import java.util.*;
import java.lang.*;
import com.avatarduel.model.*;

public final class SkillFactory implements CardFactory {
    private static final String SKILL_CSV_FILE_PATH = "card/data/skill.csv";
    private static SkillFactory factory;
    private List<String[]> cards;

    private SkillFactory() throws IOException {
        CSVReader csvreader = new CSVReader(new File(SKILL_CSV_FILE_PATH), ";");
        csvreader.setSkipHeader(true);

        this.cards = csvreader.read(); 
    }

    public static SkillFactory getInstance() throws IOException {
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
        
        if (chance < 60){ // 60% chance to get skill aura
            idx = rand.nextInt(len - 2);
        } else{ // 40% chance to get destroy or power up
            idx = rand.nextInt(2) + len - 2;
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
        } else{
            element = Element.AIR;
        }
        String description = cardInfo[3];
        String imgPath = cardInfo[4];
        int atk = Integer.parseInt(cardInfo[5]);
        int def = Integer.parseInt(cardInfo[6]);
        int power = Integer.parseInt(cardInfo[7]);

        return new Skill(name, element, description, imgPath, atk, def, power);
    }
}
