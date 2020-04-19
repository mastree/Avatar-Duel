package com.avatarduel.model;

public class Chargame extends Card {
  private int atk;
  private int def;
  private int power;

	/**
   * creates a new card that is a character
   */
  public Chargame(){
		super();
		this.atk = 0;
		this.def = 0;
		this.power = 0;
  }

  /**
   * creates a new character card with name, element, description, path for image, number of attack, number of defense, and number of power needed
   * @param name name of character
   * @param element element of character
   * @param description description of character
   * @param imgPath path for image 
   * @param atk number of attack 
   * @param def number of defense
   * @param power number of power needed 
   */
  public Chargame(String name, Element element, String description, String imgPath, int atk, int def, int power) {
		super(name, element, description, imgPath, "Character");
		this.atk = atk;
		this.def = def;
		this.power = power;
	}

	/**
	 * @return the number of attack
	 */
	public int getAtk(){
		return this.atk;
	}

	/**
	 * @return number of defense 
	 */
	public int getDef(){
		return this.def;
	}

	/**
	 * @return number of power needed 
	 */
	public int getPower(){
		return this.power;
	}
}