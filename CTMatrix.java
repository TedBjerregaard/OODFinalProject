package model;

import java.util.List;


/**
 * A 3x3 matrix of doubles that correspond to equations that will be applied via matrix
 * multiplication to the current red, green, and blue values in order to find the final,
 * color-transformed red, green, blue values for the final model.Image.
 */
public class CTMatrix {

  private final List<Double> red;
  private final List<Double> green;
  private final List<Double> blue;


  /**
   * Constructor for the model.CTMatrix class. Ensures that the size of the matrix is a 3x3.
   *
   * @param red   Matrix of values to determine new red value.
   * @param green Matrix of values to determine new green value.
   * @param blue  Matrix of values to determine new blue value.
   * @throws IllegalArgumentException Thrown if any of the given lists are not of size 3.
   */
  public CTMatrix(List<Double> red, List<Double> green, List<Double> blue)
      throws IllegalArgumentException {
    if (red.size() != 3 ||
        green.size() != 3 ||
        blue.size() != 3) {
      throw new IllegalArgumentException("invalid color transformation matrix");
    }

    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Returns the matrix of values corresponding to the red part of the color transformation.
   *
   * @return Returns the red matrix of values.
   */
  public List<Double> getRedMatrix() {
    return this.red;
  }

  /**
   * Returns the matrix of values corresponding to the green part of color transformation.
   *
   * @return Returns the green matrix of values
   */
  public List<Double> getGreenMatrix() {
    return this.green;
  }

  /**
   * Returns the matrix of values corresponding to the blue part of color transformation.
   *
   * @return Returns the blue matrix of values
   */
  public List<Double> getBlueMatrix() {
    return this.blue;
  }

}
