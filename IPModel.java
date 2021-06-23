package model;

/**
 * Interface for an image processor model. Contains the current, core operations that any image
 * processor must have.
 */
public interface IPModel {

  /**
   * Applies a filter (model.Kernel) to an image and creates a blurred version of the same image.
   *
   * @return A blurred version of an image.
   */
  Image blur();

  /**
   * Applies a filter (model.Kernel) to an image and creates a sharpened version of the same image.
   *
   * @return A sharpened version of an image.
   */
  Image sharpen();

  /**
   * Applies a matrix of equations that will be used to calculate the new red, green, and blue
   * values for each pixel in an image. Creates a new image with a greyscale color transformation
   * applied to it.
   *
   * @return A version of an image with a greyscale color transformation applied to it.
   */
  Image applyGreyscale();

  /**
   * Applies a matrix of equations that will be used to calculate the new red, green, and blue
   * values for each pixel in an image. Creates a new image with a sepia color transformation
   * applied to it.
   *
   * @return A version of an image with a sepia color transformation applied to it.
   */
  Image applySepia();

  /**
   * Creates a new image programmatically. Currently, the method creates a checkerboard with a
   * specified square tile size, number of tiles in the checkerboard, and colors to use.
   *
   * @param tileSize    Size of each square tile in the image (size of one side of each tile).
   * @param numTiles    Number of tiles to be created in this image.
   * @param color1      First color to be used in this checkerboard image
   * @param color2      Second color to be used in this checkerboard image
   * @param maxColorVal The maximum color value for any channel (red, green, blue) for each pixel in
   *                    this image.
   * @return An image that has been created programmatically.
   */
  Image createImage(int tileSize, int numTiles, PixelColor color1, PixelColor color2,
      int maxColorVal);


  /**
   * Returns the image associated with a model in a given state. Specifically, in the model for
   * multilayered images, it returns the image at the current layer.
   *
   * @return The image in this model, or at the current layer for a multilayred image
   */
  Image getCurrentImage();


}
