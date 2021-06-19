package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ComplexImageProcessorModel implements IPModel, MultiLayerIPModel {

  private IPModel delegate;
  int currentLayer;  //private
  private List<IImageLayer> layers;

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
  public Image blur() {
    IImageLayer current = this.layers.get(this.currentLayer);
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel(3, kValues);
    Image modImage = current.getImage().filter(k);
    //this.exportImage(modImage,current.getName() + "-blur",current.getFileType());
    current.replaceImage(modImage);
    return modImage;
  }



  /**
   * Applies a filter (Kernel) to an image and creates a sharpened version of the same image.
   *
   * @return A sharpened version of an image.
   */
  @Override
  public Image sharpen() {
    IImageLayer current = this.layers.get(this.currentLayer);
    List<Double> kValues = Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125,
        -0.125, .25, .25, .25, -0.125,
        -0.125, 0.25, 1.0, 0.25, -0.125,
        -0.125, 0.25, 0.25, 0.25, -0.125,
        -0.125, -0.125, -0.125, -0.125, -0.125);
    Kernel k = new Kernel(5, kValues);
    Image modImage =current.getImage().filter(k);
    //this.exportImage(modImage,current.getName() + "-Sharpen",current.getFileType());
    current.replaceImage(modImage);
    return modImage;
  }

  /**
   * Applies a matrix of equations that will be used to calculate the new red, green, and blue
   * values for each pixel in an image. Creates a new image with a greyscale color transformation
   * applied to it.
   *
   * @return A version of an image with a greyscale color transformation applied to it.
   */
  @Override
  public Image applyGreyscale() {
    IImageLayer current = this.layers.get(this.currentLayer);

    List<Double> redList = Arrays.asList(.2126, .7152, .0722);
    List<Double> greenList = Arrays.asList(.2126, .7152, .0722);
    List<Double> blueList = Arrays.asList(.2126, .7152, .0722);
    CTMatrix matrix = new CTMatrix(redList, greenList, blueList);
    Image modImage = current.getImage().transformColor(matrix);
    //this.exportImage(modImage,current.getName() + "-Greyscale",current.getFileType());
    current.replaceImage(modImage);
    return modImage;
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
    IImageLayer current = this.layers.get(this.currentLayer);
    List<Double> redList = Arrays.asList(.393, .769, .189);
    List<Double> greenList = Arrays.asList(.349, .686, .168);
    List<Double> blueList = Arrays.asList(.272, .534, .131);
    CTMatrix matrix = new CTMatrix(redList, greenList, blueList);
    Image modImage = current.getImage().transformColor(matrix);
    //this.exportImage(modImage,current.getName() + "-Sepia",current.getFileType());
    current.replaceImage(modImage);
    return modImage;

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
      int maxColorVal) {

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

    String fileTag = fileName.substring(fileName.length() - 3);
    if (fileTag.equals("ppm")) {
      this.delegate.importImage(fileName);
      IImageLayer current = this.layers.get(this.currentLayer);
      if (current.getImage() == null) {
        current.replaceImage(this.delegate.getCurrentImage());
      }
      current.setFiletype(fileTag);
      current.setName(fileName);

    }

    else {
      try {

        fileIn = new File(fileName);
        ImageInputStream imStream = ImageIO.createImageInputStream(fileIn);
        Iterator<ImageReader> imageReader = ImageIO.getImageReaders(imStream);
        if (!imageReader.hasNext()) {
          throw new RuntimeException ("reader error");
        }
        IImageLayer current = this.layers.get(this.currentLayer);

        ImageReader reader = imageReader.next();
        current.setName(fileName);
        current.setFiletype(reader.getFormatName()); //TODO: maybe get rid of field for file type
        this.layers.set(this.currentLayer,importHelper(fileIn));


      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File " + fileName + " not found!");
      } catch (IOException exception) {
        exception.printStackTrace ();
      }
    }
  }

  private IImageLayer importHelper(File fileIn) throws IOException {
    BufferedImage BuffImage;
    BuffImage = ImageIO.read(fileIn);
    int w = BuffImage.getWidth();
    int h = BuffImage.getHeight();

    Pixel[][] pixArray = new Pixel[h][w];
    for (int row = 0; row < h; row++) {
      for (int col = 0; col < w; col++) {
        Color thisColor = new Color(BuffImage.getRGB(col, row));

        PixelColor pc = new PixelColor (thisColor.getRed (), thisColor.getGreen (),
            thisColor.getBlue (), 0,255);
        pixArray[row][col] = new Pixel (col,row,pc);
      }
    }

    Image image = new Image (h,w,255,pixArray);
    IImageLayer current = this.layers.get(this.currentLayer);

    IImageLayer imageLayer = new ImageLayer(image,fileIn.getName(), current.getFileType());
    return imageLayer;
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

  public void saveImageLayer(Image image) {
    IImageLayer current = this.layers.get(this.currentLayer);
    current.replaceImage(image);
  }

  public void setCurrentLayer(int index) {
    this.currentLayer = index;
  }

  public Image getCurrentImage() {
    return this.layers.get(this.currentLayer).getImage();
  }



  public void exportTopVisibleLayer(String filename, String fileType) {
    IImageLayer current = this.layers.get(this.currentLayer);

    for (int i = this.layers.size() - 1; i >= 0; i--) {
      if (this.layers.get(i).isVisible()) {
        this.exportImage(this.layers.get(i).getImage(), filename, fileType);
        return;
      }
    }
  }

  public void createLayer(int index) {
    this.layers.add(index,new ImageLayer(null,null,null));
  }

  public void copyCurrentLayer(int index) {
    IImageLayer current = this.layers.get(this.currentLayer);
    IImageLayer copy = current.makeCopy();
    this.layers.get(index).updateLayer(copy);
  }

  public void removeLayer(int index) {
    this.layers.remove(index);
    if (index == this.currentLayer) {
      this.currentLayer = this.currentLayer - 1;
    }
  }

  public void makeInvisible() {
    IImageLayer current = this.layers.get(this.currentLayer);
    current.makeInvisible();
  }

  public void makeVisible() {
    IImageLayer current = this.layers.get(this.currentLayer);
    current.makeInvisible();
  }

  @Override
  public void importMultiLayeredImage(String fileName) {

    try {
      File toImport = new File(fileName);
      Scanner reader = new Scanner(toImport);
      int current = 0;
      String token;

      while (reader.hasNext()) {
        token = reader.next();

        if (token.equals("IP3")) {
          int numLayers = reader.nextInt();
          for (int i = 0; i < numLayers; i++ ) {
            this.createLayer(current);
            this.setCurrentLayer(current);
            IImageLayer currentLayer = this.layers.get(current);
            String visible = reader.next();
            if (visible.equals("true")) {
              currentLayer.makeInvisible();
            }
            if (visible.equals("false")) {
              currentLayer.makeInvisible();
            }
            this.importImage(reader.next());
            current ++;
          }

        }
        else {
          throw new IllegalArgumentException ("bad file format");
        }
      }



    }
    catch (IOException io) {
      //
    }
  }

  @Override
  public void exportMultiLayeredImage(String fileName) {

    StringBuilder builder = new StringBuilder();
    builder.append("IP3\n" + this.layers.size() +"\n");
    for (int i = 0; i < this.layers.size(); i++) {
      IImageLayer current = layers.get(i);
      builder.append(current.isVisible() + "\n");
      builder.append(current.getName() + "\n");
      int lengthType = current.getFileType().length() + 1;
      int lengthFileName = current.getName().length() - lengthType;
      String fileExportName = current.getName().substring(0, lengthFileName);
      this.exportImage(current.getImage(), fileExportName, current.getFileType());

      File multiLayeredLocations = new File(fileName);
      try {
        FileWriter writer = new FileWriter(multiLayeredLocations);
        writer.write(builder.toString());
        writer.flush();
        writer.close();
      }
      catch (IOException io) {
        throw new IllegalArgumentException("cannot create text file");
      }
    }
  }

  public int getCurrentLayer() {
    return this.currentLayer;
  }

  public int getNumLayers() {
    return this.layers.size();
  }

}
