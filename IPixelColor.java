package model;

import java.util.List;

/**
 * Interface for a PixelColor, or the color of a pixel and the operation that can be performed on
 * it. Used to represent the color values of pixels in an image in this implementation.
 */
public interface IPixelColor {

  /**
   * Applies the given value in a Kernel to the red value of this PixelColor to find the filtered
   * color.
   *
   * @param kValue Value in a Kernel to be multiplied with this red value.
   * @return New red value for this PixelColor.
   */
  double applyKernelRed(double kValue);

  /**
   * Applies the given value in a Kernel to the green value of this PixelColor to find the filtered
   * color.
   *
   * @param kValue Value in a Kernel to be multiplied with this green value.
   * @return New green value for this PixelColor.
   */
  double applyKernelGreen(double kValue);

  /**
   * Applies the given value in a Kernel to the blue value of this PixelColor to find the filtered
   * color.
   *
   * @param kValue Value in a Kernel to be multiplied with this blue value.
   * @return New blue value for this PixelColor.
   */
  double applyKernelBlue(double kValue);

  /**
   * Get the new color after applying the color transformation matrix equation.
   *
   * @param red Red values of color transformation matrix
   * @return New color value after color transformation
   */
  int getTransformed(List<Double> red);

  /**
   * Gets the maximum value of this PixelColor.
   *
   * @return Maximum value.
   */
  int getMaxVal();

  /**
   * Gets the minimum value of this PixelColor.
   *
   * @return Minimum value.
   */
  int getMinVal();

  /**
   * Gets the red value for this PixelColor.
   *
   * @return Red value.
   */
  int getRed();

  /**
   * Gets the green value for this PixelColor.
   *
   * @return Green value.
   */
  int getGreen();

  /**
   * Gets the blue value for this PixelColor.
   *
   * @return Blue value.
   */
  int getBlue();
}
