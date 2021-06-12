import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * A class that represents one method of representing a sequence of pixels, an Image,
 * and its corresponding data. Each pixel has a corresponding color that is representing using 3
 * channels: red, green, and blue. Each of these channels are stored using 8-bits, and thus have
 * 256 distinct possible values.
 *
 * Height: Refers to the size of the picture vertically in pixels.
 * Width: Refers to the size of the Image horizontally in pixels.
 * maxColorVal: The maximum integer value for each channel of every pixel in the Image.
 * minColorVal: The minimum integer value for each channel of every pixel in the Image.
 * pixelArray: A 2D array of pixels representing each pixel in the Image and its location in the
 *             image.
 */
public class Image {

  int height;
  int width;
  int maxColorVal;
  int minColorVal;
  Pixel[][] pixelArray;

  /**
   * Constructor for the Image Class. Minimum color value for a channel in a pixel is automatically
   * set to 0.
   *
   * @param height      Size of the image vertically in pixels.
   * @param width       Size of the image vertically in pixels.
   * @param maxColorVal The maximum integer value for each channel of every pixel in the Image.
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
   * @return An Image with the transformed color values for each pixel.
   */
  public Image transformColor(CTMatrix matrix) {
    Pixel[][] newPixList = new Pixel[height][width];
    for (int row = 0;  row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Pixel currentPixel  = this.pixelArray[row][col];

        PixelColor newColor = currentPixel.getTransformedColor(matrix);
        Pixel newPixel = new Pixel(currentPixel.x, currentPixel.y, newColor);
        newPixList[row][col] = newPixel;
      }
    }
    return new Image(this.height,this.width, this.maxColorVal, newPixList);
  }

  /**
   * Applies an odd-sized Kernel to each pixel in an image to produce a final filtered image.
   * The Kernel is applied to each pixel and is used to compute the final color values for that
   * given pixel by multiplying the valid, that is existent, pixel color values with the
   * corresponding Kernel values given a particular pixel.
   *
   * @param kernel An odd-sized matrix of values that will be applied to each pixel to filter an
   *               image.
   * @return  Returns a new, filtered Image.
   */
  public Image filter(Kernel kernel) {
    Pixel[][] newPixList = new Pixel[height][width];

    for (int row = 0;  row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Pixel currentPixel  = this.pixelArray[row][col];

        PixelColor newColor = getNewColor(currentPixel, kernel);
        Pixel newPixel = new Pixel(currentPixel.x, currentPixel.y, newColor);
        newPixList[row][col] = newPixel;
      }
    }

    return new Image(this.height,this.width, this.maxColorVal, newPixList);
  }

  //TODO: check this
  public PixelColor getNewColor(Pixel current, Kernel kernel) {
    int newRed = 0;
    int newGreen = 0;
    int newBlue = 0;
    for ( int row = 0; row < kernel.size; row ++) {
      for (int col = 0; col < kernel.size; col ++) {
        int xValue = current.x + kernel.kArray[row][col][0];
        int yValue = current.y + kernel.kArray[row][col][1];

        if (xValue >= 0 && yValue >= 0 && xValue < height  && yValue < width) {
          newRed += pixelArray[xValue][yValue].color.applyKernelRed(kernel.valuesArray[row][col]);
          newGreen += pixelArray[xValue][yValue].color.applyKernelGreen(kernel.valuesArray[row][col]);
          newBlue += pixelArray[xValue][yValue].color.applyKernelBlue(kernel.valuesArray[row][col]);
        }
      }
    }
    return new PixelColor (Math.round(newRed), Math.round(newGreen), Math.round(newBlue),
        this.maxColorVal, this.minColorVal);
  }


  public String getImageValues(String finalFileName) {
    //add switch case for difference files, with private methods for each
    StringBuilder builder = new StringBuilder();
    builder.append("P3 \n# " + finalFileName + "\n" + this.width + " " + this.height + "\n"
        + this.maxColorVal);
    builder.append("\n");

    int [][] red = new int[this.height][this.width];
    int [][] green = new int[this.height][this.width];
    int [][] blue = new int[this.height][this.width];

    for ( int row = 0; row < this.height; row ++) {
      for (int col = 0; col < this.width; col ++) {
        PixelColor currentColor = this.pixelArray[row][col].color;
        red[row][col] = currentColor.red;
        green[row][col] = currentColor.green;
        blue[row][col] = currentColor.blue;

        builder.append(red[row][col] + "\n");
        builder.append(green[row][col] + "\n");
        builder.append(blue[row][col] + "\n");
      }
    }
    return builder.toString();
  }
}