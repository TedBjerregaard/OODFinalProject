package model;

import java.util.List;

/**
 * A representation of the color for a Pixel. A PixelColor is made up of red, green, and blue
 * values. A Pixel has a maximum and minimum integer value that its red, green, and blue values can
 * be.
 */
public class PixelColor implements IPixelColor {

  private final int maxVal;
  private final int minVal;
  private int red;
  private int green;
  private int blue;

  /**
   * Constructor for the PixelColor class. Will automatically clamp a PixelColor to either the
   * maximum or minimum allowed value if it is not between those two numbers.
   *
   * @param red    Red value for this color.
   * @param green  Green value for this color.
   * @param blue   Blue value for this color.
   * @param maxVal Maximum value that red, green, or blue can be.
   * @param minVal Minimum value that red, green, or blue can be.
   */
  public PixelColor(int red, int green, int blue, int maxVal, int minVal) {
    this.maxVal = maxVal;
    this.minVal = minVal;
    this.red = red;
    this.green = green;
    this.blue = blue;

    if (this.red > maxVal) {
      this.red = maxVal;
    }
    if (this.green > maxVal) {
      this.green = maxVal;
    }
    if (this.blue > maxVal) {
      this.blue = maxVal;
    }
    if (this.red < 0) {
      this.red = 0;
    }
    if (this.green < 0) {
      this.green = 0;
    }
    if (this.blue < 0) {
      this.blue = 0;
    }
  }

  public double applyKernelRed(double kValue) {
    return this.red * kValue;
  }


  public double applyKernelGreen(double kValue) {
    return this.green * kValue;
  }


  public double applyKernelBlue(double kValue) {
    return this.blue * kValue;
  }


  public int getTransformed(List<Double> red) {

    return (int) (red.get(0) * this.red + red.get(1) * this.green + red.get(2) * this.blue);
  }


  public int getMaxVal() {
    return this.maxVal;
  }


  public int getMinVal() {
    return this.minVal;
  }


  public int getRed() {
    return this.red;
  }


  public int getGreen() {
    return this.green;
  }


  public int getBlue() {
    return this.blue;
  }

}
