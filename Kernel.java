
import java.util.List;

/**
 * A class representing a matrix of values that will be applied to each given pixel in an image to
 * achieve some kind of filtering effect. This is done by applying the center of the Kernel matrix
 * of values to a pixel and multiplying the value of the kernel with the color values of the pixels
 * that correspond to it (i.e. overlap kernel and pixel of interest in image to do math, and thus
 * apply a filter). The size of the Kernel, in this case, must be odd to ensure there is a center
 * point for the Kernel. When applying the Kernel, if there are no corresponding pixels in the image
 * (i.e. on the edges of the image) then those Kernel values are ignored.
 */
public class Kernel {

  private final int size;
  private final double[][] valuesArray;
  private final int[][][] kArray;

  /**
   * Constructor for the Kernel class.
   *
   * @param size   Size of the Kernel.
   * @param values List of values used to populate the Kernel.
   */
  public Kernel(int size, List values) {

    if (size % 2 == 0 || size * size != values.size()) {
      throw new IllegalArgumentException("invalid Kernel Size");
    } else {
      this.size = size;
      this.valuesArray = new double[size][size];//values;
      this.kArray = new int[size][size][2];

      int currentVal = 0;
      while (currentVal < values.size()) {
        for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {

            valuesArray[i][j] = (double) values.get(currentVal);
            currentVal += 1;
          }
        }
      }

      int buffer = size / 2;

      for (int row = -buffer; row <= buffer; row++) {
        for (int col = -buffer; col <= buffer; col++) {
          int i = row + buffer;
          int j = col + buffer;
          kArray[i][j] = new int[]{row, col};
        }
      }
    }
  }

  /**
   * Getter for the size of the Kernel.
   *
   * @return Size of this Kernel.
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Returns the value associated with a given row and column in the Kernel.
   *
   * @param row "Y" position of this value.
   * @param col "X" position of this value.
   * @return The value at the given row and column position in this Kernel.
   */
  public double getValue(int row, int col) {
    return valuesArray[row][col];
  }

  /**
   * Gets the value that should be multiplied to each channel of a pixel.
   *
   * @param row         Row where the k value is located.
   * @param col         Column where the k value is located.
   * @param kernelValue Value the color should be multiplied by.
   * @return K value.
   */
  public int getK(int row, int col, int kernelValue) {
    return kArray[row][col][kernelValue];
  }

}
