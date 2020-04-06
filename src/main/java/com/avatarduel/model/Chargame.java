package com.avatarduel.model;

public class Chargame extends Card {
  private int atk;
  private int def;
  private int power;

  public Chargame(){
		super();
		this.atk = 0;
		this.def = 0;
		this.power = 0;
  }
  
  public Chargame(String name, Element element, String description, String imgPath, int atk, int def, int power) {
		super(name, element, description, imgPath, "Character");
		this.atk = atk;
		this.def = def;
		this.power = power;
	}
	
	public int getAtk(){
		return this.atk;
	}

	public int getDef(){
		return this.def;
	}

	public int getPower(){
		return this.power;
	}
}