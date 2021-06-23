package model;

import java.util.List;

/**
 * Interface for a matrix for color transformations. A color transformation matrix is a 3x3 matrix
 * used for matrix multiplication to apply a color tranformation to an image.
 */
public interface ICTMatrix {

  /**
   * Returns the matrix of values corresponding to the red part of the color transformation.
   *
   * @return Returns the red matrix of values.
   */
  List<Double> getRedMatrix();

  /**
   * Returns the matrix of values corresponding to the green part of color transformation.
   *
   * @return Returns the green matrix of values
   */
  List<Double> getGreenMatrix();

  /**
   * Returns the matrix of values corresponding to the blue part of color transformation.
   *
   * @return Returns the blue matrix of values
   */
  List<Double> getBlueMatrix();
}
