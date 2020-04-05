package com.avatarduel.model;

public class Card {
  private String name;
  private Element element;
  private String description;
  private String imgPath;

  public Card() {
    this.name = "";
    this.element = Element.AIR;
    this.description = "";
    this.imgPath = "";
  }

  public Card(String name, Element element, String description, String imgPath) {
    this.name = name;
    this.element = element;
    this.description = description;
    this.imgPath = imgPath;
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