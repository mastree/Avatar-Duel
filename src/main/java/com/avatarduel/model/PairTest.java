package com.avatarduel.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;

class PairTest {
	private Pair pair;
	private Pair otherTrue;
	private Pair otherFalse;
	
	@BeforeEach
	void initEach() {
		pair = new Pair(1, 2);
		otherTrue = new Pair(1, 2);
		otherFalse = new Pair(3, 4);
	}
	@Nested
	class EqualsTest {
		@Test
		void testEqualsTrue() {
			assertEquals(true, pair.equals(1, 2), "equals(int a, int b) should return true if pair.first = a and pair.second = b, else return false");
		}
		
		@Test
		void testEqualsFalse() {
			assertEquals(false, pair.equals(3,4), "equals(int a, int b) should return true if pair.first = a and pair.second = b, else return false")
		}
	}
	
	@Nested
	class EqualsOtherTest {
		@Test
		void testEqualsOtherTrue() {
			assertEquals(true, pair.equals(otherTrue), "equals(Pair other) should return true if pair.first = other.first and pair.second = other.second, else return false");
		}
		
		@Test
		void testEqualsOtherFalse() {
			assertEquals(false, pair.equals(otherFalse), "equals(Pair other) should return true if pair.first = other.first and pair.second = other.second, else return false");
		}
	}
}
