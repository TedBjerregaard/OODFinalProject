import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * A simple model for an Image Processor. This model is able to perform several operations to an
 * image with a corresponding file name.
 * Operations included currently include:
 * Producing filtered images (blurred and sharpened).
 * Producing images with after applying a given color transformation (Sepia and Greyscale).
 * Importing images (PPM format).
 * Exporting images (PPM format).
 */
public class SimpleImageProcessorModel implements ImageProcessorModel {

  Image image;

  /**
   * Creates a model for processing images given a string representing the name of an image to be
   * imported.
   *
   * @param fileName String representing an Image to be imported to the model.
   */
  public SimpleImageProcessorModel(String fileName) {
    importImage(fileName);
  }

  /**
   * Empty constructor for an image processor model that allows the user to skip the step of
   * importing an image, and allows them to instead create an image to be processed. //TODO check this.
   */
  public SimpleImageProcessorModel() {

  }

  @Override
  public Image blur() {
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel (3,kValues);
    return this.image.filter(k);
  }

  @Override
  public Image sharpen() {
    List<Double> kValues = Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125,
        -0.125, .25, .25, .25, -0.125,
        -0.125, 0.25, 1.0, 0.25, -0.125,
        -0.125, 0.25, 0.25, 0.25, -0.125,
        -0.125, -0.125, -0.125, -0.125, -0.125);
    Kernel k = new Kernel (5,kValues);
    return this.image.filter(k);
  }

  //TODO: combine into oneApplyColorTransformation method
  @Override
  public Image applyGreyscale() {
    List<Double> redList = Arrays.asList(.2126,.7152,.0722);
    List<Double> greenList = Arrays.asList(.2126,.7152,.0722);
    List<Double> blueList = Arrays.asList(.2126,.7152,.0722);
    CTMatrix matrix = new CTMatrix(redList,greenList,blueList);
    Image finalImage = this.image.transformColor(matrix);
    return finalImage;
  }

  @Override
  public Image applySepia() {
    List<Double> redList = Arrays.asList(.393,.769,.189);
    List<Double> greenList = Arrays.asList(.349,.686,.168);
    List<Double> blueList = Arrays.asList(.272,.534,.131);
    CTMatrix matrix = new CTMatrix(redList,greenList,blueList);
    Image finalImage = this.image.transformColor(matrix);
    return finalImage;
  }

  //TODO: make checkerboard a method in image
  @Override
  public Image createImage(int sizeOfSquare, int sizeBoard, int maxColor) {
    int side = sizeBoard * sizeOfSquare;
    int side2 = side * 1;
    Pixel[][] pixelArray = new Pixel[side][side2];

    int pixCountX = 0;
    for (int row = 0; row < side; row++) {

      int pixCountY = 0;
      for (int col = 0; col < side; col++) {


        while (pixCountY < sizeOfSquare &&
            pixCountX < sizeOfSquare) {
          int red = 255;
          int green = 0;
          int blue = 0;
          PixelColor color = new PixelColor(red, green, blue, maxColor,0);
          Pixel newPix = new Pixel (col,row,color);
          pixelArray[row][col] = newPix;
          pixCountY ++;
          col++;
        }
        while (pixCountY < 2 * sizeOfSquare &&
            pixCountX < 2 * sizeOfSquare) {
          int red = 0;
          int green = 0;
          int blue = 255;
          PixelColor color = new PixelColor(red, green, blue, maxColor,0);
          Pixel newPix = new Pixel (col,row,color);
          pixelArray[row][col] = newPix;
          pixCountY ++;
          col++;
        }
      }

      if (pixCountX >= 2 * sizeOfSquare) {
        pixCountX = 0;
      }
      else {
        pixCountX++;
      }
    }
    return new Image(side,side,maxColor,pixelArray);
  }



  @Override
  public void importImage(String filename) throws IllegalArgumentException {

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream (filename));
    }
    catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File "+filename+ " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    int minColorVal = 0;

    List<Pixel> pixels = new ArrayList<> ();

    for (int row=0;row<height;row++) {
      for (int col=0;col<width;col++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel toAdd = new Pixel (row,col,new PixelColor(r,g,b, maxValue, minColorVal));
        pixels.add (toAdd);
      }
    }

    int current = 0;
    Pixel[][]pixelArray = new Pixel[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        pixelArray[row][col] = pixels.get(current);
        current++;
      }
    }

    this.image = new Image(height, width, maxValue, pixelArray);
  }

  @Override
  public void exportImage(Image image, String fileName, String fileType) {

    File file;
    String finalFileName = fileName + fileType;
    FileOutputStream FStream = null;
    String imageValues = image.getImageValues(finalFileName);

    try {
      file = new File(fileName + ".ppm");
      FStream = new FileOutputStream(file);

      if (!file.exists()) {
        file.createNewFile();
      }
      byte[] bArray = imageValues.getBytes();

      FStream.write(bArray);
      FStream.flush ();
    }

    catch (IOException e) {
      //
    }

    try {
      if (FStream != null) {
        FStream.close();
      }
    }
    catch (IOException e) {
      //
    }
  }

}
