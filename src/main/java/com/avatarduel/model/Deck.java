package main.java.com.avatarduel.model;

import java.util.*;
import com.avatarduel.model.*;
import com.avatarduel.util.CharacterFactory;

public class Deck {
    private static final int TOTAL_CHARACTER = 48;
    private static final int TOTAL_LAND = 16;
    private static final int TOTAL_SKILL = 28;
    private static final int KARTU_MAX = 60;
    private List<Card> isiDeck;
    private int jumlahKartu;

    public Deck() {
        this.isiDeck = new ArrayList<>();
        this.jumlahKartu = 0;
        int x, y, z = 0;
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
    }

    public int getJumlahKartu() {
        return this.jumlahKartu;
    }

    public Card pickCard() {
        Card tempCard = isiDeck.get(0);
        isiDeck.remove(0);
        return tempCard;
    }

}