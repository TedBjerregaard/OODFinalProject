package model;

/**
 * A representation of a pixel in an image. A pixel has an x and y value to describe its location in
 * an image. The model.PixelColor corresponds to the red, green, and blue values for a given pixel.
 */
public class Pixel {

  private final int x;
  private final int y;
  private final PixelColor color;

  /**
   * Constructor for the model.Pixel class.
   *
   * @param x     X position of this model.Pixel.
   * @param y     Y position of this model.Pixel.
   * @param color Color of this pixel (r,g,b).
   */
  public Pixel(int x, int y, PixelColor color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  /**
   * Applies a given color transformation matrix to a pixel to obtain it's new red, green, and blue
   * values for its model.PixelColor.
   *
   * @param matrix Color Transformation matrix used to find new red, green, and blue values for this
   *               model.Pixel.
   * @return The new color of this model.Pixel
   */
  public PixelColor getTransformedColor(CTMatrix matrix) {
    int newRed = this.color.getTransformed(matrix.getRedMatrix());
    int newGreen = this.color.getTransformed(matrix.getGreenMatrix());
    int newBlue = this.color.getTransformed(matrix.getBlueMatrix());
    return new PixelColor(newRed, newGreen, newBlue, this.color.getMaxVal(), 0);
  }

  /**
   * Gets the x-value of this model.Pixel.
   *
   * @return Int representing the X-value.
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the y-value of this model.Pixel.
   *
   * @return Int representing the Y-value.
   */
  public int getY() {
    return this.y;
  }

  /**
   * Getter for the color of this model.Pixel.
   *
   * @return model.PixelColor with the red, green, and blue values for the color of this model.Pixel.
   */
  public PixelColor getColor() {
    return this.color;
  }
}
