package com.avatarduel.model;

public class Field{
    private Player p;
    private Chargame[] karakter;
    private boolean[] posisi;
    private Skill[] skillfield;
    private int[] skillpos;
    private int neffkar;
    private int neffskill;

    public Field(Player play){
        karakter = new Chargame[7];
        posisi = new boolean[7];
        skillfield = new Skill[7];
        skillpos = new int[7];
        neffkar = 0;
        neffskill = 0;
        p = play;
    }

    public void playChar(int idx){
        if (neffkar < 7){
            Chargame c = p.removeChar(idx);
            karakter[neffkar] = c;
            posisi[neffkar] = attack;
            karakter[posisi[neffkar]]
            neffkar++;
        }
        else{
            //throw exception atau langsung print error?
        }
    }

    public void playSkill(int idx){
        if (neffskill < 7) {
            Skill s = p.removeSkill(idx);
            skillfield[neffskill] = s;
            skillpos[neffskill] = posisiChar;
            neffskill++;
        }
        else{
            //throw exception atau langsung print error?
        }
    }

    public void changePos(int pos){
        if (pos < neffkar){
            posisi[pos] = !(posisi[pos]);
        }
        else {
            //throw exception atau langsung print error?
        }
    }

    public void discardSpell(int pos){
        if (pos < neffskill && neffskill>0){
            Skill temp = skillfield[pos];
            int atk = temp.getAtk();
            int def = temp.getDef();
            int idx = skillpos[pos];
            int kar = karakter[idx];
            for (int i=pos; i<5;i++){
                skillfield[i] = skillfield[i+1];
                skillpos[i] = skillpos[i+1];
            }
            neffskill--;
        }
        else{
            //throw exception atau langsung print error?
        }
    }

    public int getTotalAtk(int pos){
        int temp = karakter[pos].getAtk();
        for (int i=0;i<7;i++){
            if (skillpos[i] == pos){
                temp+=skillfield[i].getAtk();
            }
        }
        return temp;
    }

    public int getTotalDef(int pos){
        int temp = karakter[pos].getDef();
        for (int i=0;i<7;i++){
            if (skillpos[i] == pos){
                temp+=skillfield[i].getDef();
            }
        }
        return temp;
    }
}