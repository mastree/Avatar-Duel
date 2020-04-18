package com.avatarduel.model;

import java.util.*;
import java.io.*;
import com.avatarduel.model.*;
import com.avatarduel.util.*;

public class Deck {
    public static final String PATH = "card/image/Deck.png";
    private static final int TOTAL_CHARACTER = 54;
    private static final int TOTAL_LAND = 19;
    private static final int TOTAL_SKILL = 38;
    private static final int KARTU_MAX = 55;
    private int jumlahAwalKartu;
    private List<Card> isiDeck;
    private int jumlahKartu;

    /**
     * Membuat sebuah objek Deck baru yang berisikan kartu-kartu yang dimiliki
     * 
     * @throws Exception
     */
    public Deck() throws Exception {
        this.isiDeck = new ArrayList<>();
        this.jumlahKartu = 0;
        int x = 0, y = 0, z = 0;
        while (jumlahKartu < KARTU_MAX) {
            if (x < TOTAL_CHARACTER) {
                isiDeck.add(CharacterFactory.getInstance().create());
                this.jumlahKartu++;
                x++;
            }
            if (y < TOTAL_LAND) {
                isiDeck.add(LandFactory.getInstance().create());
                this.jumlahKartu++;
                y++;
            }
            if (z < TOTAL_SKILL) {
                isiDeck.add(SkillFactory.getInstance().create());
                this.jumlahKartu++;
                z++;
            }
            Collections.shuffle(isiDeck);
        }
        this.jumlahAwalKartu = this.jumlahKartu;
    }
    
    /**
     * Mengembalikan jumlah kartu yang terdapat pada deck
     * 
     * @return integer jumlah kartu yang tersisa pada deck 
     */
    public int getJumlahKartu() {
        return this.jumlahKartu;
    }

    /**
     * Mengembalikan jumlah awal kartu saat deck dibuat
     * 
     * @return integer jumlah kartu di deck pada awal permainan
     */
    public int getJumlahAwalKartu() { return this.jumlahAwalKartu; }

    /**
     * Mengambil kartu yang berada paling depan di list isiDeck
     * 
     * @return Card.
     */
    public Card pickCard() {
        this.jumlahKartu--;
        Card tempCard = isiDeck.remove(0);
        return tempCard;
    }



}