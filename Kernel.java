package model;

import java.util.List;

/**
 * A class representing a matrix of values that will be applied to each given pixel in an image to
 * achieve some kind of filtering effect. This is done by applying the center of the model.Kernel
 * matrix of values to a pixel and multiplying the value of the kernel with the color values of the
 * pixels that correspond to it (i.e. overlap kernel and pixel of interest in image to do math, and
 * thus apply a filter). The size of the model.Kernel, in this case, must be odd to ensure there is
 * a center point for the model.Kernel. When applying the model.Kernel, if there are no
 * corresponding pixels in the image (i.e. on the edges of the image) then those model.Kernel values
 * are ignored.
 */
public class Kernel implements IKernel {

  private final int size;
  private final double[][] valuesArray;
  private final int[][][] kArray;

  /**
   * Constructor for the model.Kernel class.
   *
   * @param size   Size of the model.Kernel.
   * @param values List of values used to populate the model.Kernel.
   */
  public Kernel(int size, List values) {

    if (size % 2 == 0 || size * size != values.size()) {
      throw new IllegalArgumentException("invalid model.Kernel Size");
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

  public int getSize() {
    return this.size;
  }

  public double getValue(int row, int col) {
    return valuesArray[row][col];
  }

  public int getK(int row, int col, int kernelValue) {
    return kArray[row][col][kernelValue];
  }
}
