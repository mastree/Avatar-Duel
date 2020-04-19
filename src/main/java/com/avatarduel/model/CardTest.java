package com.avatarduel.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardTest {
	
	private Card kartu;
	
	@BeforeEach
	void initEach() {
		kartu = new Card("Katara", Element.WATER,
				 "Waterbending master from Southern Water Tribe, "
				 + "sister of Sokka, and friend of Aang", 
				 "src/res/image/character/Katara.png", 
				 "Character");
	}
		
	@Test
	void testGetName() {
		assertEquals("Katara", kartu.getName(), "getName method should return Katara"));
	}
	
	@Test
	void testGetElement() {
		assertEquals(String.valueOf(Element.WATER), String.valueOf(kartu.getElement()), "getElement method should return WATER" );
	}
	
	@Test
	void testGetDesc() {
		assertEquals("Waterbending master from Southern Water Tribe, "
				 + "sister of Sokka, and friend of Aang", kartu.getDesc(), "getDesc method should return Waterbending master from Southern Water Tribe, sister of Sokka, and friend of Aang");
	}
	
	@Test
	void testGetPath() {
		assertEquals("src/res/image/character/Katara.png", kartu.getPath(), "getPath method should return src/res/image/character/Katara.png");
	}
	
	@Test
	void testGetCardType() {
		assertEquals("Character", kartu.getCardType(), "getCardType method should return Character");
	}
}
