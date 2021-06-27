package model;

/**
 * A representation of a pixel in an image. A pixel has an x and y value to describe its location in
 * an image. The PixelColor corresponds to the red, green, and blue values for a given pixel.
 */
public class Pixel {

  private final int col;
  private final int row;
  private final PixelColor color;

  /**
   * Constructor for the Pixel class.
   *
   * @param x     X position of this Pixel.
   * @param y     Y position of this Pixel.
   * @param color Color of this pixel (r,g,b).
   */
  public Pixel(int y, int x, PixelColor color) {
    this.row = y;
    this.col = x;
    this.color = color;
  }

  /**
   * Applies a given color transformation matrix to a pixel to obtain it's new red, green, and blue
   * values for its PixelColor.
   *
   * @param matrix Color Transformation matrix used to find new red, green, and blue values for this
   *               Pixel.
   * @return The new color of this Pixel
   */
  public PixelColor getTransformedColor(CTMatrix matrix) {
    int newRed = this.color.getTransformed(matrix.getRedMatrix());
    int newGreen = this.color.getTransformed(matrix.getGreenMatrix());
    int newBlue = this.color.getTransformed(matrix.getBlueMatrix());
    return new PixelColor(newRed, newGreen, newBlue, this.color.getMaxVal(), 0);
  }

  /**
   * Gets the x-value of this Pixel.
   *
   * @return Int representing the X-value.
   */
  public int getCol() {
    return this.col;
  }

  /**
   * Gets the y-value of this Pixel.
   *
   *
   * @return Int representing the Y-value.
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Getter for the color of this model.Pixel.
   *
   * @return      PixelColor with the red, green, and blue values for the color of this Pixel.
   */
  public PixelColor getColor() {
    return this.color;
  }
}
