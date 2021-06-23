package model;

/**
 * Interface for a Kernel, a matrix of numbers applied to each pixel in images to achieve a
 * filtering effect.
 */
public interface IKernel {

  /**
   * Getter for the size of the model.Kernel.
   *
   * @return Size of this model.Kernel.
   */
  int getSize();

  /**
   * Returns the value associated with a given row and column in the model.Kernel.
   *
   * @param row "Y" position of this value.
   * @param col "X" position of this value.
   * @return The value at the given row and column position in this model.Kernel.
   */
  double getValue(int row, int col);

  /**
   * Gets the value that should be multiplied to each channel of a pixel.
   *
   * @param row         Row where the k value is located.
   * @param col         Column where the k value is located.
   * @param kernelValue Value the color should be multiplied by.
   * @return K value.
   */
  int getK(int row, int col, int kernelValue);

}
