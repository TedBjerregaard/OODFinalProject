import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Image {

  int height;
  int width;
  int maxColorVal;
  int minColorVal;
  Pixel[][] pixelArray;

  public Image(int height, int width, int maxColorVal, Pixel[][] pixelArray) {
    this.height = height;
    this.width = width;
    this.maxColorVal = maxColorVal;
    this.minColorVal = 0;
    this.pixelArray = pixelArray;
  }

  public Image(String filename) throws IllegalArgumentException {
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

    this.height = height;
    this.width = width;
    this.maxColorVal = maxValue;
    this.minColorVal = 0;

    List<Pixel> pixels = new ArrayList<> ();

    for (int row=0;row<height;row++) {
      for (int col=0;col<width;col++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel toAdd = new Pixel (row,col,new PixelColor(r,g,b, this.maxColorVal, this.minColorVal));
        pixels.add (toAdd);
      }
    }

    int current = 0;
    this.pixelArray = new Pixel[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        this.pixelArray[row][col] = pixels.get(current);
        current++;
      }
    }
  }

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
    double newRed = 0;
    double newGreen = 0;
    double newBlue = 0;
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
    return new PixelColor (newRed,newGreen,newBlue, this.maxColorVal, this.minColorVal);
  }


  public String getImageValues(String finalFileName) {
    //add switch case for difference files, with private methods for each
    StringBuilder builder = new StringBuilder();
    builder.append("P3 \n# " + finalFileName + "\n" + this.height + " " + this.width + "\n"
        + this.maxColorVal);
    builder.append("\n");

    double [][] red = new double[this.height][this.width];
    double [][] green = new double[this.height][this.width];
    double [][] blue = new double[this.height][this.width];

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
