package com.avatarduel.model;

public class Card {
  private String name;
  private Element element;
  private String description;
  private String imgPath;
  private String cardType;

  public Card() {
    this.name = "";
    this.element = Element.AIR;
    this.description = "";
    this.imgPath = "";
    this.cardType = "";
  }

  public Card(String name, Element element, String description, String imgPath, String cardType) {
    this.name = name;
    this.element = element;
    this.description = description;
    this.imgPath = imgPath;
    this.cardType = cardType;
  }

  public String getName(){
    return this.name;
  }

  public Element getElement(){
    return this.element;
  }

  public String getDesc(){
    return this.description;
  }

  public String getPath(){
    return this.imgPath;
  }

}