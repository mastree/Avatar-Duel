package com.avatarduel.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChargameTest {

	private Chargame karakter;
	
	@BeforeEach
	void initEach() {
		karakter = new Chargame("Katara", Element.WATER,
				 "Waterbending master from Southern Water Tribe, "
				 + "sister of Sokka, and friend of Aang", 
				 "src/res/image/character/Katara.png", 13, 7, 5);
	}
	
	@Test
	void testGetAtk() {
		assertEquals(13, karakter.getAtk(), "getAttack method should return 13");
	}
	
	@Test
	void testGetDef() {
		assertEquals(7, karakter.getDef(), "getDef method should return 7");
	}
	
	@Test
	void testGetPower() {
		assertEquals(5, karakter.getPower(), "getPower method should return 5");
	}

}
