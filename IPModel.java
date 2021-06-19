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
  public Image blur();

  /**
   * Applies a filter (model.Kernel) to an image and creates a sharpened version of the same image.
   *
   * @return A sharpened version of an image.
   */
  public Image sharpen();

  /**
   * Applies a matrix of equations that will be used to calculate the new red, green, and blue
   * values for each pixel in an image. Creates a new image with a greyscale color transformation
   * applied to it.
   *
   * @return A version of an image with a greyscale color transformation applied to it.
   */
  public Image applyGreyscale();

  /**
   * Applies a matrix of equations that will be used to calculate the new red, green, and blue
   * values for each pixel in an image. Creates a new image with a sepia color transformation
   * applied to it.
   *
   * @return A version of an image with a sepia color transformation applied to it.
   */
  public Image applySepia();

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
  public Image createImage(int tileSize, int numTiles, PixelColor color1, PixelColor color2,
      int maxColorVal);

  /**
   * Imports a valid image given the file name. Currently, only imports .ppm files.
   *
   * @param fileName String representing the name of a file corresponding to an image.
   */
  public void importImage(String fileName);

  /**
   * Exports an image with a specified file name and type. Currently, only able to export ".ppm"
   * files.
   *
   * @param image    model.Image to be exported from the method to the client.
   * @param fileName Name of the final exported image file.
   * @param fileType Type of file that this image will be exported as.
   */
  public void exportImage(Image image, String fileName, String fileType);

  //added
  Image getCurrentImage();


}
