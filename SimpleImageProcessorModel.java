import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class SimpleImageProcessorModel implements ImageProcessorModel {

  Image image;

  public SimpleImageProcessorModel(String filename) {
    importImage(filename);
  }

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

  @Override
  public Image createImage(int height, int width, int maxColor){
    Pixel[][] pixelArray = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {

        int pixCount = 0;
        while (pixCount <= 10) {
          double red = 255;
          double green = 0;
          double blue = 0;
          PixelColor color = new PixelColor(red, green, blue, maxColor,0);
          Pixel newPix = new Pixel (col,row,color);
          pixelArray[row][col] = newPix;
          pixCount ++;
        }
        while (pixCount <= 20) {
          double red = 0;
          double green = 0;
          double blue = 255;
          PixelColor color = new PixelColor(red, green, blue, maxColor,0);
          Pixel newPix = new Pixel (col,row,color);
          pixelArray[row][col] = newPix;
          pixCount ++;
        }





      }
    }



    return new Image(height,width,maxColor,pixelArray);
  }



  @Override
  public void importImage(String filename) {
    this.image = new Image(filename);
  }

  @Override
  public void exportImage(String fileName, String fileType){

    File file;
    String finalFileName = fileName + fileType;
    FileOutputStream FStream = null;
    String imageValues = this.image.getImageValues(finalFileName);

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
