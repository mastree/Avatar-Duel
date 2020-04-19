package com.avatarduel.model;

import com.avatarduel.AvatarDuel;

public class Card {
  private String name;
  private Element element;
  private String description;
  private String imgPath;
  private String cardType;

 /**
   * Creates a new Card 
   */
  public Card() {
    this.name = "";
    this.element = Element.AIR;
    this.description = "";
    this.imgPath = "";
    this.cardType = "";
  }

  /**
   * Creates a card, with the name, element, description, path for image, and card type that is specified
   * @param name name of Card
   * @param element Element of Card
   * @param description description of card
   * @param imgPath the path for image of card
   * @param cardType type of card
   */
  public Card(String name, Element element, String description, String imgPath, String cardType) {
    this.name = name;
    this.element = element;
    this.description = description;
    this.imgPath = imgPath;
    this.cardType = cardType;
  }

  /**
   * @return the name of card which is a string
   */
  public String getName(){
    return this.name;
  }

  /**
   * @return the element of card
   */
  public Element getElement(){
    return this.element;
  }

  /**
   * @return the description of card which is a string
   */
  public String getDesc(){
    return this.description;
  }

  /**
   * @return the path image for card which is a string 
   */
  public String getPath(){
    int len = imgPath.length();
    String _cut = imgPath.substring(34, len);
    String _path = AvatarDuel.class.getResource(_cut).toString();
    return _path;
  }

  /**
   * @return the type of card which is a string
   */
  public String getCardType(){
    return this.cardType;
  }

}