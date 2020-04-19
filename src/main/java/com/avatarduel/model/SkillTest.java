package com.avatarduel.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SkillTest {
	private Skill skill;
	
	@BeforeEach
	void initEach() {
		skill = new Skill("Air Funnel", Element.AIR,
				"echnique to create a cannon for small projectiles",
				"src/main/resources/com/avatarduel/card/image/skill/Air Funnel.png",
				2,
				4,
				0
				);
	}
	
	@Test
	void testGetAtk() {
		assertEquals(2, skill.getAtk(), "getAtk method should return 2");
	}
	
	@Test
	void testGetDef() {
		assertEquals(4, skill.getDef(), "getDef method should return 4");
	}
	
	@Test
	void testGetPower() {
		assertEquals(0, skill.getPower(), "getPower method should return 0");
	}
	
	@Test
	void testGetSkillType() {
		assertEquals("Aura", skill.getSkillType(), "getSkillType method should return Aura")
	}

}
