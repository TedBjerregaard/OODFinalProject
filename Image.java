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
  Pixel[][] pixelArray;

  public Image(int height, int width, int maxColorVal, Pixel[][] pixelArray) {
    this.height = height;
    this.width = width;
    this.maxColorVal = maxColorVal;
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

    List<Pixel> pixels = new ArrayList<> ();

    for (int i=0;i<height;i++) {
      for (int j=0;j<width;j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel toAdd = new Pixel (i,j,new PixelColor(r,g,b));
        pixels.add (toAdd);
      }
    }
    this.height = height;
    this.width = width;
    this.maxColorVal = maxValue;

    int current = 0;
    this.pixelArray = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.pixelArray[i][j] = pixels.get(current);
        current++;
      }
    }
  }

  public Image filter(Kernel kernel) {
    Pixel[][] newPixList = new Pixel[height][width];

    for (int i = 0;  i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        Pixel currentPixel  = this.pixelArray[i][j];

        PixelColor newColor = getNewColor(currentPixel, kernel);
        Pixel newPixel = new Pixel(currentPixel.x, currentPixel.y, newColor);
        newPixList[i][j] = newPixel;
      }
    }

    return new Image(this.height,this.width, this.maxColorVal, newPixList);
  }

  public PixelColor getNewColor(Pixel current, Kernel kernel) {
    double newRed = 0;
    double newGreen = 0;
    double newBlue = 0;
    for ( int i = 0; i < kernel.size; i ++) {
      for (int j = 0; j < kernel.size; j ++) {
        int xValue = current.x + kernel.kArray[i][j][0];
        int yValue = current.y + kernel.kArray[i][j][1];
        if (pixelArray[i][j] != null) {
          newRed += pixelArray[i][j].color.applyKernelRed(kernel.valuesArray[i][j]);
          newGreen += pixelArray[i][j].color.applyKernelGreen(kernel.valuesArray[i][j]);
          newBlue += pixelArray[i][j].color.applyKernelBlue(kernel.valuesArray[i][j]);
        }
      }
    }
    if(newRed > maxColorVal) {
      newRed = maxColorVal;
    }
    if(newGreen > maxColorVal) {
      newGreen = maxColorVal;
    }
    if(newBlue > maxColorVal) {
      newBlue = maxColorVal;
    }
    return new PixelColor (newRed,newGreen,newBlue);
  }

}
