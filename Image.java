package model;

/**
 * A class that represents one method of representing a sequence of pixels, an model.Image, and its
 * corresponding data. Each pixel has a corresponding color that is representing using 3 channels:
 * red, green, and blue. Each of these channels are stored using 8-bits, and thus have 256 distinct
 * possible values. Height: Refers to the size of the picture vertically in pixels. Width: Refers to
 * the size of the model.Image horizontally in pixels. maxColorVal: The maximum integer value for
 * each channel of every pixel in the model.Image. minColorVal: The minimum integer value for each
 * channel of every pixel in the model.Image. pixelArray: A 2D array of pixels representing each
 * pixel in the model.Image and its location in the image.
 */
public class Image {

  private final int height;
  private final int width;
  private final int maxColorVal;
  private final int minColorVal;
  public Pixel[][] pixelArray;

  /**
   * Constructor for the model.Image Class. Minimum color value for a channel in a pixel is
   * automatically set to 0.
   *
   * @param height      Size of the image vertically in pixels.
   * @param width       Size of the image vertically in pixels.
   * @param maxColorVal The maximum integer value for each channel of every pixel in the
   *                    model.Image.
   * @param pixelArray  2D array of pixels that create the image.
   */
  public Image(int height, int width, int maxColorVal, Pixel[][] pixelArray) {
    this.height = height;
    this.width = width;
    this.maxColorVal = maxColorVal;
    this.minColorVal = 0;
    this.pixelArray = pixelArray;
  }

  /**
   * Applies a given color transformation to each pixel in an image. A color transformation is a
   * matrix of 3 equations that are applied to the old red, green, and blue color values for each
   * pixel in order to find the new red, green, and blue color values for each pixel in the final
   * image.
   *
   * @param matrix A 3x3 matrix of equations corresponding to each
   * @return An model.Image with the transformed color values for each pixel.
   */
  public Image transformColor(CTMatrix matrix) {
    Pixel[][] newPixList = new Pixel[height][width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Pixel currentPixel = this.pixelArray[row][col];

        PixelColor newColor = currentPixel.getTransformedColor(matrix);
        Pixel newPixel = new Pixel(currentPixel.getCol(), currentPixel.getRow(), newColor);
        newPixList[row][col] = newPixel;
      }
    }
    return new Image(this.height, this.width, this.maxColorVal, newPixList);
  }

  /**
   * Applies an odd-sized model.Kernel to each pixel in an image to produce a final filtered image.
   * The model.Kernel is applied to each pixel and is used to compute the final color values for
   * that given pixel by multiplying the valid, that is existent, pixel color values with the
   * corresponding model.Kernel values given a particular pixel.
   *
   * @param kernel An odd-sized matrix of values that will be applied to each pixel to filter an
   *               image.
   * @return Returns a new, filtered model.Image.
   */
  public Image filter(Kernel kernel) {
    Pixel[][] newPixList = new Pixel[height][width];

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Pixel currentPixel = this.pixelArray[row][col];

        PixelColor newColor = getNewColor(currentPixel, kernel);
        Pixel newPixel = new Pixel(currentPixel.getRow(), currentPixel.getCol(),  newColor);
        newPixList[row][col] = newPixel;
      }
    }

    return new Image(this.height, this.width, this.maxColorVal, newPixList);
  }

  /**
   * Applies a given model.Kernel to a pixel and returns the new color after filtering.
   *
   * @param current model.Pixel being filtered.
   * @param kernel  Matrix of values being applied to this given pixel and the ones surrounding it
   *                that match with a model.Kernel value.
   * @return New model.PixelColor after filtering through the model.Kernel.
   */
  public PixelColor getNewColor(Pixel current, Kernel kernel) {
    int newRed = 0;
    int newGreen = 0;
    int newBlue = 0;
    for (int row = 0; row < kernel.getSize(); row++) {
      for (int col = 0; col < kernel.getSize(); col++) {
        int xValue = current.getCol() + kernel.getK(row, col, 0);
        int yValue = current.getRow() + kernel.getK(row, col, 1);

        if (xValue > 0 && yValue > 0 && xValue < this.width && yValue < this.height) {
          newRed += pixelArray[yValue][xValue].getColor().applyKernelRed(kernel.getValue(row, col));
          newGreen += pixelArray[yValue][xValue].getColor()
              .applyKernelGreen(kernel.getValue(row, col));
          newBlue += pixelArray[yValue][xValue].getColor()
              .applyKernelBlue(kernel.getValue(row, col));
        }
      }
    }
    return new PixelColor(Math.round(newRed), Math.round(newGreen), Math.round(newBlue),
        this.maxColorVal, this.minColorVal);
  }


  /**
   * Returns this image as a string in PPM format with the given file name.
   *
   * @param finalFileName Name of the file for the image that is being converted to PPM format.
   * @return String of an image in PPM format.
   */
  public String getImageValues(String finalFileName) {
    //add switch case for difference files, with private methods for each
    StringBuilder builder = new StringBuilder();
    builder.append("P3 \n# " + finalFileName + "\n" + this.width + " " + this.height + "\n"
        + this.maxColorVal);
    builder.append("\n");

    int[][] red = new int[this.height][this.width];
    int[][] green = new int[this.height][this.width];
    int[][] blue = new int[this.height][this.width];

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        PixelColor currentColor = this.pixelArray[row][col].getColor();
        red[row][col] = currentColor.getRed();
        green[row][col] = currentColor.getGreen();
        blue[row][col] = currentColor.getBlue();

        builder.append(red[row][col] + "\n");
        builder.append(green[row][col] + "\n");
        builder.append(blue[row][col] + "\n");
      }
    }
    return builder.toString();
  }

  /**
   * Gets the height of this image.
   *
   * @return Height of this image.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the width of this image.
   *
   * @return Width of this image.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the maximum color value for this image.
   *
   * @return Maximum color value.
   */
  public int getMaxColorVal() {
    return this.maxColorVal;
  }

  /**
   * Gets the minimum color value for this image.
   *
   * @return Minimum color value.
   */
  public int getMinColorVal() {
    return this.minColorVal;
  }

  /**
   * Returns the pixel at a given row and column position.
   *
   * @param row Row position.
   * @param col Column position.
   * @return model.Pixel at the given position.
   */
  public Pixel getPixel(int row, int col) {
    return pixelArray[row][col];
  }

}