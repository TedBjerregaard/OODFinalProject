package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ComplexImageProcessorModel implements IPModel {

  private IPModel delegate;
  private String fileType;
  int curentLayer;
  private List<Image> layers;

  public ComplexImageProcessorModel(IPModel delegate) {
    Objects.requireNonNull(delegate);
    this.delegate = delegate;
    this.layers = new ArrayList<> ();
  }

  /**
   * Applies a filter (Kernel) to an image and creates a blurred version of the same image.
   *
   * @return A blurred version of an image.
   */
  @Override
  public Image blur(){
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel(3, kValues);
    return this.layers.get(this.curentLayer).filter(k);
  }

  public Image getCurrentImage() {
    return this.layers.get(this.curentLayer);
  }

  /**
   * Applies a filter (Kernel) to an image and creates a sharpened version of the same image.
   *
   * @return A sharpened version of an image.
   */
  @Override
  public Image sharpen() {
    List<Double> kValues = Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125,
        -0.125, .25, .25, .25, -0.125,
        -0.125, 0.25, 1.0, 0.25, -0.125,
        -0.125, 0.25, 0.25, 0.25, -0.125,
        -0.125, -0.125, -0.125, -0.125, -0.125);
    Kernel k = new Kernel(5, kValues);
    Image finalImage =this.layers.get(this.curentLayer).filter(k);
    this.layers.set(this.curentLayer,finalImage);
    return finalImage;
  }

  /**
   * Applies a matrix of equations that will be used to calculate the new red, green, and blue
   * values for each pixel in an image. Creates a new image with a greyscale color transformation
   * applied to it.
   *
   * @return A version of an image with a greyscale color transformation applied to it.
   */
  @Override
  public Image applyGreyscale(){
    List<Double> redList = Arrays.asList(.2126, .7152, .0722);
    List<Double> greenList = Arrays.asList(.2126, .7152, .0722);
    List<Double> blueList = Arrays.asList(.2126, .7152, .0722);
    CTMatrix matrix = new CTMatrix(redList, greenList, blueList);
    Image finalImage = this.layers.get(this.curentLayer).transformColor(matrix);
    this.layers.set(this.curentLayer,finalImage);

    return finalImage;
  }

  /**
   * Applies a matrix of equations that will be used to calculate the new red, green, and blue
   * values for each pixel in an image. Creates a new image with a sepia color transformation
   * applied to it.
   *
   * @return A version of an image with a sepia color transformation applied to it.
   */
  @Override
  public Image applySepia() {
    Image currentLayer = this.layers.get(this.curentLayer);
    List<Double> redList = Arrays.asList(.393, .769, .189);
    List<Double> greenList = Arrays.asList(.349, .686, .168);
    List<Double> blueList = Arrays.asList(.272, .534, .131);
    CTMatrix matrix = new CTMatrix(redList, greenList, blueList);
    Image finalImage = currentLayer.transformColor(matrix);
    this.layers.set(this.curentLayer,finalImage);
    return finalImage;

  }

  /**
   * Creates a new image programmatically. Currently, the method creates a checkerboard with a
   * specified square tile size, number of tiles in the checkerboard, and colors to use. e
   *
   * @param tileSize    Size of each square tile in the image (size of one side of each tile).
   * @param numTiles    Number of tiles to be created in this image.
   * @param color1      First color to be used in this checkerboard image
   * @param color2      Second color to be used in this checkerboard image
   * @param maxColorVal The maximum color value for any channel (red, green, blue) for each pixel in
   *                    this image.
   * @return An image that has been created programmatically.
   */
  @Override
  public Image createImage(int tileSize, int numTiles, PixelColor color1, PixelColor color2,
      int maxColorVal){
    this.layers.set (this.curentLayer,delegate.createImage(tileSize,numTiles,color1,color2,maxColorVal));
    return this.delegate.createImage(tileSize,numTiles,color1,color2,maxColorVal);
  }

  /**
   * Imports a valid image given the file name. Currently, only imports .ppm files.
   *
   * @param fileName String representing the name of a file corresponding to an image.
   */
  @Override
  public void importImage(String fileName) {

    File fileIn;

    String fileTag = fileName.substring (fileName.length() -3);
    if ( fileTag.equals("ppm")) {
      this.delegate.importImage(fileName);
      this.layers.add (this.curentLayer,this.delegate.getCurrentImage ());
      this.fileType = "ppm";


    }

    else {
      try {

        fileIn = new File(fileName);
        ImageInputStream imStream = ImageIO.createImageInputStream(fileIn);
        Iterator<ImageReader> imageReader = ImageIO.getImageReaders(imStream);
        if (!imageReader.hasNext()) {
          throw new RuntimeException ("reader error");
        }

        ImageReader reader = imageReader.next();
        this.fileType = reader.getFormatName (); //TODO: maybe get rid of field for file type
        this.layers.add (this.curentLayer,importHelper(fileIn));




        //ImageIO.write(image, this.formatName, this.outputImage);


      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File " + fileName + " not found!");
      } catch (IOException exception) {
        exception.printStackTrace ();
      }
    }

  }

  private Image importHelper(File fileIn) throws IOException {
    BufferedImage BuffImage;
    BuffImage = ImageIO.read(fileIn);
    int w = BuffImage.getWidth();
    int h = BuffImage.getHeight();

    int[][] result = new int[h][w];

    Pixel[][] pixArray = new Pixel[h][w];
    for (int row = 0; row < h; row++) {
      for (int col = 0; col < w; col++) {
        result[row][col] = BuffImage.getRGB(col, row);
        Color mycolor = new Color(BuffImage.getRGB(col, row));

        PixelColor pc = new PixelColor (mycolor.getRed (), mycolor.getGreen (),
            mycolor.getBlue (), 0,255);
        pixArray[row][col] = new Pixel (col,row,pc);
      }
    }

    Image image = new Image (h,w,255,pixArray);

    return image;
  }

  /**
   * Exports an image with a specified file name and type. Currently, only able to export ".ppm"
   * files.
   *
   * @param image    Image to be exported from the method to the client.
   * @param fileName Name of the final exported image file.
   * @param FileType Type of file that this image will be exported as.
   */
  @Override
  public void exportImage(Image image, String fileName, String FileType) {

    if (FileType.equals("ppm")) {
      this.delegate.exportImage(image,fileName,FileType);
    }
    else {
      String finalFileName = fileName + "." +  FileType;

      File file = new File(finalFileName);
      BufferedImage buff = new BufferedImage (image.getWidth(), image.getHeight(),
          BufferedImage.TYPE_INT_RGB);
      for (int row = 0; row < image.getHeight(); row++) {
        for (int col = 0; col < image.getWidth(); col++) {
          Pixel currentPix = image.getPixel(row,col);
          PixelColor pixColor = currentPix.getColor();
          Color currentColor = new Color(pixColor.getRed(),pixColor.getGreen(),pixColor.getBlue());
          int colorInt = currentColor.getRGB();
          buff.setRGB(row,col,colorInt);

        }
      }
      try {
        ImageIO.write(buff,FileType,file);
      }
      catch (IOException e) {
        throw new IllegalArgumentException ("check filetype");
      }


    }
  }

  public void setCurrentLayer(int index) {
    this.curentLayer = index;
  }





  public void exportTopLayer(String filename) {

    this.exportImage(this.layers.get(this.layers.size()), filename, fileType);
  }
}
