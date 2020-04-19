package com.avatarduel.model;

/**
 * Create a new cars that is a land
 */
public class Land extends Card {
  public Land(){
    super();
  }

  /**
   * creates a new card of land with name, element, description, and path of image
   * @param name the name of land
   * @param element the element of land
   * @param description the description of land  
   * @param imgPath the path for image of land
   */
  public Land(String name, Element element, String description, String imgPath) {
    super(name, element, description, imgPath, "Land");
  }
}