import java.util.List;

/**
 * A representation of the color for a Pixel. A PixelColor is made up of red, green, and blue
 * values. A Pixel has a maximum and minimum integer value that its red, green, and blue values can
 * be.
 */
public class PixelColor {

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

  /**
   * Applies the given value in a Kernel to the red value of this PixelColor to find the filtered
   * color.
   *
   * @param kValue Value in a Kernel to be multiplied with this red value.
   * @return New red value for this PixelColor.
   */
  public double applyKernelRed(double kValue) {
    return this.red * kValue;
  }

  /**
   * Applies the given value in a Kernel to the green value of this PixelColor to find the filtered
   * color.
   *
   * @param kValue Value in a Kernel to be multiplied with this green value.
   * @return New green value for this PixelColor.
   */
  public double applyKernelGreen(double kValue) {
    return this.green * kValue;
  }

  /**
   * Applies the given value in a Kernel to the blue value of this PixelColor to find the filtered
   * color.
   *
   * @param kValue Value in a Kernel to be multiplied with this blue value.
   * @return New blue value for this PixelColor.
   */
  public double applyKernelBlue(double kValue) {
    return this.blue * kValue;
  }


  public PixelColor applyKernal(float kValue) {
    return new PixelColor(Math.round(this.red * kValue), Math.round(this.green * kValue),
        Math.round(this.blue * kValue), this.maxVal, this.minVal);
  }

  /**
   * Get the new color after applying the color transformation matrix equation.
   *
   * @param red Red values of color transformation matrix
   * @return New color value after color transformation
   */
  public int getTransformed(List<Double> red) {

    return (int) (red.get(0) * this.red + red.get(1) * this.green + red.get(2) * this.blue);
  }

  /**
   * Gets the maximum value of this PixelColor.
   *
   * @return Maximum value.
   */
  public int getMaxVal() {
    return this.maxVal;
  }

  /**
   * Gets the minimum value of this PixelColor.
   *
   * @return Minimum value.
   */
  public int getMinVal() {
    return this.minVal;
  }

  /**
   * Gets the red value for this PixelColor.
   *
   * @return Red value.
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Gets the green value for this PixelColor.
   *
   * @return Green value.
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Gets the blue value for this PixelColor.
   *
   * @return Blue value.
   */
  public int getBlue() {
    return this.blue;
  }

}
