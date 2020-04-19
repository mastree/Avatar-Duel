package com.avatarduel.model;

/**
 * creates a card of skill
 */
public class Skill extends Card {
	private int atk;		
  private int def;
	private int power;
	private String skillType;

	/**
	 * Creates a skill card with type destory
	 */
  public Skill(){
		super();
		this.atk = 0;
		this.def = 0;
		this.power = 0;
		this.skillType = "Destroy";
  }
  
  /**
   * Creates a skill card 
   * @param name name of skill
   * @param element element of skill
   * @param description description of skill
   * @param imgPath the path of image for skill card
   * @param atk the number of attack of skill
   * @param def the number of defense of skill
   * @param power the number of power needed 
   */
  public Skill(String name, Element element, String description, String imgPath, int atk, int def, int power) {
		super(name, element, description, imgPath, "Skill");
		this.atk = atk;
		this.def = def;
		this.power = power;
		if (name.equals("Destroy")){
			this.skillType = "Destroy";
		} else if (name.equals("Power Up")){
			this.skillType = "Power Up";
		} else{
			this.skillType = "Aura";
		}
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

	public String getSkillType(){
		return this.skillType;
	}
}