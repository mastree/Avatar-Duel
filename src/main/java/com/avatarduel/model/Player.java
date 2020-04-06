package com.avatarduel.model;

import java.util.*;

public class Player {
    private List<Land> l;
    private List<Skill> s;
    private List<Chargame> c;
    private Map<Element, Integer> num_land;
    private Map<Element, Integer> power;

    public Player(){
        this.l = new ArrayList<Land>();
        this.s = new ArrayList<Skill>();
        this.c = new ArrayList<Chargame>();
        this.num_land = new HashMap<Element,Integer>();
        this.power = new HashMap<Element,Integer>();
        for (Element temp : Element.values()){
            this.num_land.put(temp, 0);
            this.power.put(temp,0);
        }

    }

    public void addLand(Land new_l){
        this.l.add(new_l);
    }

    public void addSkill(Skill new_s){
        this.s.add(new_s);
    }

    public void addChar(Chargame new_c){
        this.c.add(new_c);
    }

    public void removeLand(int idx){
        Land removedLand = l.remove(idx);
        Element element = removedLand.getElement();
        int temp = num_land.get(element) + 1;
        int temp1 = power.get(element) + 1;
        num_land.put(element,temp);
        power.put(element, temp1);
    }

    public Skill removeSkill(int idx){
        Skill temp = s.remove(idx);
        return temp;
    }

    public Chargame removeChar(int idx){
        Chargame temp = c.remove(idx);
        return temp;
    }
}