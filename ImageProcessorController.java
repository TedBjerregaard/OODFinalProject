package controller;

import static java.awt.image.BufferedImage.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import model.ComplexImageProcessorModel;
import model.IImageLayer;
import model.Image;
import model.Pixel;
import model.PixelColor;
import model.SimpleImageProcessorModel;
import view.IPView;
import view.IViewListener;
import view.ImageProcessorView;


/**
 * A controller used in the implementation of an image processor. Able to process images with manual
 * text commands, or with batch commands through text files.
 */
public class ImageProcessorController implements IPController, IViewListener {

  private ComplexImageProcessorModel model;
  private IPView view;
  private Scanner in;
  private Appendable ap;


  /**
   * Constructor for the ImageProcessorController class.
   *
   * @param model Model used in this implementation.
   * @param in    InputStream from the user, either manual text or text file.
   * @param out   Output user sees through the view.
   */
  public ImageProcessorController(ComplexImageProcessorModel model, InputStream in,
      Appendable out, IPView view) {
    this.model = model;
    this.in = new Scanner(in);
    this.ap = out;
    this.view = Objects.requireNonNull(view);
    this.view.registerViewEventListener(this);



  }


  @Override
  public void runIP() {

    ImageProcessorView view = new ImageProcessorView(this.ap);
    boolean quit = false;

    while (!quit && in.hasNext()) {
      String command = in.next();
      switch (command) {
        case "quit":
          quit = true;
          renderMessageHelp(this.view, "IP closed");
          break;

        case "txtCommand":
          String filename = in.next();
          File inFile = new File(filename);
          try {
            SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
            ComplexImageProcessorModel complexModel = new ComplexImageProcessorModel(simpleModel);
            InputStream targetStream = new FileInputStream(inFile);
            IPController controller = new ImageProcessorController(complexModel, targetStream,
                System.out,this.view);
            controller.runIP();
          } catch (IOException io) {
            throw new IllegalArgumentException("run failed");
          }
          break;

        case "create":
          int index = this.model.getNumLayers();
          this.model.createLayer(index);
          this.model.setCurrentLayer(index);
          renderMessageHelp(this.view, "layer " + this.model.getCurrentLayerIndex() + " created\n");
          renderMessageHelp(this.view, "Current Layer: " + this.model.getCurrentLayerIndex()
              + "\n Layers in IP: " + this.model.getNumLayers() + "\n");
          break;

        case "current":
          int currentIndex = in.nextInt();
          if (currentIndex >= this.model.getNumLayers() || currentIndex < 0) {
            renderMessageHelp(this.view, "invalid layer index\n");
            break;
          }
          this.model.setCurrentLayer(currentIndex);

          renderMessageHelp(this.view, "Current Layer: " + this.model.getCurrentLayerIndex() + "\n");
          break;

        case "load":

          String fileName = in.next();

          try {
            this.importImage(fileName);
            renderMessageHelp(this.view, "Top visible Layer: " +
                this.model.getTopVisibleLayer().getName() + "\n");
            BufferedImage SBuff = getBuff(this.model.getTopVisibleLayer().getImage());
            this.view.updateTopVisibleLayer(SBuff);
          } catch (IllegalArgumentException e) {
            renderMessageHelp(this.view, "Image Wrong Size\n");
            break;
          }

          renderMessageHelp(this.view, fileName + " loaded to layer: "
              + this.model.getCurrentLayerIndex() + "\n");

          break;

        case "export":
          String fileNameExport = in.next();
          this.exportMultiLayeredImage(fileNameExport);
          renderMessageHelp(this.view, fileNameExport + " Exported" + "\n");
          break;

        case "import":
          String fileNameImport = in.next();
          this.model = new ComplexImageProcessorModel(new SimpleImageProcessorModel());
          this.importMultiLayeredImage(fileNameImport);
          renderMessageHelp(this.view, fileNameImport + " Imported" + "\n");
          renderMessageHelp(this.view, "Multilayered image information: \n");
          renderMessageHelp(this.view, "Layers: " + this.model.getNumLayers() + "\n");

          this.updateTopmostVisible();

          break;

        case "save":
          String fileNameSave = in.next();
          String fileType = in.next();
          String fileTypeSave = fileType.toLowerCase(Locale.ROOT);

          IImageLayer topVisible = this.model.getTopVisibleLayer();
          this.exportImage(topVisible.getImage(), fileNameSave, fileTypeSave);
          renderMessageHelp(this.view, fileNameSave + " Saved" + "\n");

          break;

        case "copy":
          int layerIndex = in.nextInt();

          try {
            IImageLayer copyTo = this.model.getLayerAt(layerIndex);
            if(!copyTo.isEmptyLayer()) {
              renderMessageHelp(this.view, "Layer attempting to copy to is not empty");
              break;
            }
          }
          catch (IndexOutOfBoundsException e) {
            renderMessageHelp(this.view, "Invalid index to copy to");
            break;
          }



          this.model.copyCurrentLayer(layerIndex);
          renderMessageHelp(this.view, "Layer Copied to: " + layerIndex + "\n");
          if (layerIndex == this.model.getNumLayers() - 1) {
            BufferedImage CBuff = getBuff(this.model.getCurrentImage()); //TODO: check this loop
            this.view.updateTopVisibleLayer(CBuff);
          }
          break;

        case "remove":
          int layerIndexRemove = in.nextInt();
          if (layerIndexRemove >= this.model.getNumLayers() || layerIndexRemove < 0 ||
              this.model.getNumLayers() == 0) {
            renderMessageHelp(this.view, "Invalid layer index to remove\n");
            break;
          }
          this.model.removeLayer(layerIndexRemove);
          renderMessageHelp(this.view, "Layer: " + layerIndexRemove + " Removed" + "\n");
          BufferedImage rBuff = getTopMostVisibleImage();
          this.view.updateTopVisibleLayer(rBuff);

          break;

        case "visible":
          this.model.makeVisible();
          renderMessageHelp(this.view, "layer: " + this.model.getCurrentLayerIndex() +
              " is now Visible" + "\n");
          BufferedImage VBuff = getTopMostVisibleImage();
          this.view.updateTopVisibleLayer(VBuff);

          break;

        case "invisible":
          this.model.makeInvisible();
          renderMessageHelp(this.view, "Layer: " + this.model.getCurrentLayerIndex()
              + " is now Invisible" + "\n");
          BufferedImage IBuff = getTopMostVisibleImage();
          this.view.updateTopVisibleLayer(IBuff);

          break;

        case "sepia":
          this.model.applySepia();
          BufferedImage buff = getTopMostVisibleImage();
          this.view.updateTopVisibleLayer(buff);
          renderMessageHelp(this.view, "Layer: " + this.model.getCurrentLayerIndex() +
              " Sepia applied" + "\n");
          //this.updateTopmostVisible();

          break;

        case "greyscale":
          this.model.applyGreyscale();
          BufferedImage greyBuff = getTopMostVisibleImage();
          this.view.updateTopVisibleLayer(greyBuff);
          renderMessageHelp(this.view, "Layer: " + this.model.getCurrentLayerIndex() +
              " Greyscale applied" + "\n");
          //this.updateTopmostVisible();

          break;

        case "blur":

          this.model.blur();
          BufferedImage blurBuff = getTopMostVisibleImage();
          this.view.updateTopVisibleLayer(blurBuff);
          //this.updateTopmostVisible();
          renderMessageHelp(this.view, "Layer: " + this.model.getCurrentLayerIndex() + " Blur applied"
              + "\n");


          break;

        case "sharpen":
          this.model.sharpen();
          BufferedImage sharpBuff = getTopMostVisibleImage();
          this.view.updateTopVisibleLayer(sharpBuff);
          renderMessageHelp(this.view, "Layer: " + this.model.getCurrentLayerIndex() +
              " Sharpen applied" + "\n");
          //this.updateTopmostVisible();

          break;

        case "createImage":
          int tileSize = in.nextInt();
          int numTiles = in.nextInt();
          int color1Red = in.nextInt();
          int color1Green = in.nextInt();
          int color1Blue = in.nextInt();
          int color1MaxVal = in.nextInt();
          int color1MinVal = in.nextInt();
          int color2Red = in.nextInt();
          int color2Green = in.nextInt();
          int color2Blue = in.nextInt();
          int color2MaxVal = in.nextInt();
          int color2MinVal = in.nextInt();
          PixelColor color1 = new PixelColor(color1Red, color1Green, color1Blue, color1MaxVal,
              color1MinVal);
          PixelColor color2 = new PixelColor(color2Red, color2Green, color2Blue, color2MaxVal,
              color2MinVal);
          int maxColorVal = in.nextInt();
          this.model.createImage(tileSize, numTiles, color1, color2, maxColorVal);
          renderMessageHelp(this.view, "Layer: " + this.model.getCurrentLayerIndex() + " " + numTiles +
              " x " + numTiles + "tile Checkerboard image created" + "\n");
          break;

        default:
          renderMessageHelp(this.view, "Not a supported command. Try again." + "\n");
      }
    }
  }

  /**
   * Imports a multilayered image in a specific text file format. We gave a tag "IP3" to the file
   * to signal that this is a text file with this format that can be imported as a multilayered
   * image.
   *
   * @param fileName Name of text file used to import a multilayered image.
   */
  private void importMultiLayeredImage(String fileName) {

    try {
      File toImport = new File(fileName);
      Scanner reader = new Scanner(toImport);
      int current = 0;
      String token;

      while (reader.hasNext()) {
        token = reader.next();

        if (token.equals("IP3")) {
          int numLayers = reader.nextInt();
          for (int i = 0; i < numLayers; i++) {
            this.model.createLayer(current);
            this.model.setCurrentLayer(current);
            IImageLayer currentLayer = this.model.getLayerAt(current);
            String visible = reader.next();
            if (visible.equals("true")) {
              currentLayer.makeVisible();
            }
            if (visible.equals("false")) {
              currentLayer.makeInvisible();
            }
            this.importImage(reader.next());
            current++;
          }

        } else {
          throw new IllegalArgumentException("Bad file format");
        }
      }
    } catch (IOException io) {
      //
    }
  }

  /**
   * Imports an image and retrieves the pixel data needed to represent an image in the way we have
   * chosen for this implementation. Behaves differently depending on the file type of the image.
   *
   * @param fileName Name of the image file to be imported.
   */
  private void importImage(String fileName) {

    FileInputStream fileIn;

    String fileTag = fileName.substring(fileName.length() - 3);
    if (fileTag.equals("ppm")) {
      Image newImage = this.importPPM(fileName);
      IImageLayer current = this.model.getCurrentLayer();


      this.checkWidthAndHeight(newImage);
      current.replaceImage(newImage);
      current.setFiletype(fileTag);
      current.setName(fileName);

    } else {
      try {

        BufferedImage buffImage = ImageIO.read(new File(fileName));


        fileIn = new FileInputStream(fileName);

        ImageInputStream imStream = ImageIO.createImageInputStream(fileIn);
        Iterator<ImageReader> imageReader = ImageIO.getImageReaders(imStream);

        Image imported = importHelper(buffImage);

        if (!imageReader.hasNext()) {
          throw new RuntimeException("reader error");
        }

        IImageLayer current = this.model.getCurrentLayer();

        this.checkWidthAndHeight(imported);

        ImageReader reader = imageReader.next();
        current.setName(fileName);

        //maybe move this to importHelper
        current.setFiletype(reader.getFormatName()); //TODO: maybe get rid of field for file type


        current.replaceImage(imported);


      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File " + fileName + " not found!");
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
  }

  /**
   * Helper method for the import image method that deals specifically with importing JPEG and PNG
   * type files in this implementation.
   *
   * @param buffImage      BufferedImage to be imported.
   * @return               Imported Image.
   * @throws IOException   Thrown if importing fails.
   */
  private Image importHelper(BufferedImage buffImage) throws IOException {


    int w = buffImage.getWidth();
    int h = buffImage.getHeight();




    Pixel[][] pixArray = new Pixel[h][w];
    for (int row = 0; row < h; row++) {
      for (int col = 0; col < w; col++) {
        Color thisColor = new Color(buffImage.getRGB(col, row));

        PixelColor pc = new PixelColor(thisColor.getRed(), thisColor.getGreen(),
            thisColor.getBlue(), 255, 0);
        pixArray[row][col] = new Pixel(col, row, pc);
      }
    }

    return new Image(h, w, 255, pixArray);
  }


  /**
   * Helper method for the importImage method that deals specifically with importing PPM type
   * Images.
   *
   * @param filename                  Name of Image file to be imported.
   * @return                          Imported PPM image.
   * @throws IllegalArgumentException Thrown if the given file name is invalid.
   */
  private Image importPPM(String filename) throws IllegalArgumentException {

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
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

    List<Pixel> pixels = new ArrayList<>();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel toAdd = new Pixel(row, col, new PixelColor(r, g, b, maxValue, minColorVal));
        pixels.add(toAdd);
      }
    }

    int current = 0;
    Pixel[][] pixelArray = new Pixel[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        pixelArray[row][col] = pixels.get(current);
        current++;
      }
    }

    return new Image(height, width, maxValue, pixelArray);
  }

  /**
   * Determines if this image can be imported as part of a multilayered image as all images in a
   * multilayered image have to have the same size (height and width).
   *
   * @param image  Image that is being checked for correct sizing.
   */
  private void checkWidthAndHeight(Image image) {
    if (this.model.getCurrentLayerIndex() == 0) {
      int w = image.getWidth();
      int h = image.getHeight();
      this.model.setWidth(w);
      this.model.setHeight(h);
    } else if (this.model.getHeight() != image.getHeight()
        || this.model.getWidth() != image.getWidth()) {
      throw new IllegalArgumentException("Image must match size ");
    }
  }


  /**
   * Helper method for rendering a message to the user through the text box in the graphical user
   * interface.
   *
   * @param view   The view used to relay the message to the graphical user interface
   * @param string Message to be outputed.
   */
  private void renderMessageHelp(IPView view, String string) {
    try {
      view.renderMessage(string);
    } catch (IOException io) {
      throw new IllegalStateException("error rendering message");
    }
  }

  /**
   * Exports a multilayered image as a text file with specific format and a given name that can
   * then be imported later on by this program to work on later.
   *
   * @param fileName name of the text file storing information about a multilayered image.
   */
  private void exportMultiLayeredImage(String fileName) {

    StringBuilder builder = new StringBuilder();
    builder.append("IP3\n" + this.model.getNumLayers() + "\n");
    for (int i = 0; i < this.model.getNumLayers(); i++) {
      IImageLayer current = this.model.getLayerAt(i);
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
      } catch (IOException io) {
        throw new IllegalArgumentException("cannot create text file");
      }
    }
  }


  /**
   * Exports an image from this multilayered image as a file with the given file type.
   *
   * @param image    Image to be exported.
   * @param fileName Name of the file for this exported image.
   * @param fileType file extension, type of file, for exported image.
   */
  public void exportImage(Image image, String fileName, String fileType) {

    if (fileType.equals("ppm")) {
      this.exportPPM(image, fileName);
    } else {
      String finalFileName = fileName + "." + fileType;

      File file = new File(finalFileName);
      BufferedImage buff = new BufferedImage(image.getWidth(),image.getHeight(),
          TYPE_INT_RGB);
      for (int row = 0; row < buff.getHeight(); row++) {
        for (int col = 0; col < buff.getWidth(); col++) {
          Pixel currentPix = image.getPixel(row, col);
          PixelColor pixColor = currentPix.getColor();
          Color currentColor = new Color(pixColor.getRed(), pixColor.getGreen(),
              pixColor.getBlue());
          int colorInt = currentColor.getRGB();
          buff.setRGB(col, row, colorInt);
        }
      }
      try {
        ImageIO.write(buff, fileType, file);
      } catch (IOException e) {
        throw new IllegalArgumentException("check filetype");
      }
    }
  }


  /**
   * Exports an image as a PPM file.
   *
   * @param image     Image to be exported.
   * @param fileName  Final file name of this exported image.
   */
  private void exportPPM(Image image, String fileName) {
    File file;
    String finalFileName = fileName + ".ppm";
    FileOutputStream fStream = null;
    String imageValues = image.getImageValues(finalFileName);

    try {
      file = new File(finalFileName);
      fStream = new FileOutputStream(file);

      if (!file.exists()) {
        file.createNewFile();
      }
      byte[] bArray = imageValues.getBytes();

      fStream.write(bArray);
      fStream.flush();
    } catch (IOException e) {
      //
    }

    try {
      if (fStream != null) {
        fStream.close();
      }
    } catch (IOException e) {
      //
    }
  }

  @Override
  public void handleActionEvent(String cmd) {
    this.in = new Scanner(cmd);
    this.runIP();
  }

  /**
   * Given an Image, as defined in this implementation, returns a buffered image with the same
   * characteristics.
   *
   * @param img  Image to be represented as a BufferedImage.
   * @return     BufferedImage of the given image.
   */
  public BufferedImage getBuff(Image img) {
    BufferedImage buff = new BufferedImage(img.getWidth(),img.getHeight(), TYPE_INT_RGB);
    for (int x = 0; x < buff.getWidth(); x++) {
      for (int y = 0; y < buff.getHeight(); y++) {
        Pixel currentPix = img.getPixel(y, x);
        PixelColor pixColor = currentPix.getColor();
        Color currentColor = new Color(pixColor.getRed(), pixColor.getGreen(),
            pixColor.getBlue());
        int colorInt = currentColor.getRGB();
        buff.setRGB(x, y, colorInt);

      }
    }
    return buff;
  }

  /**
   * Updates the topmost visible layer after a command has been processed and affected the model.
   */
  public void updateTopmostVisible() {
    if (!this.model.hasVisibleLayer()) {
      this.view.updateTopVisibleLayer(new BufferedImage(0,0, TYPE_INT_RGB));
      return;
    }

    String layerPath = this.model.getTopVisibleLayer().getName();
    //ppm case
    if (this.model.getTopVisibleLayer().getFileType().equals("ppm")) {
      Image imgFrom = this.model.getTopVisibleLayer().getImage();
      BufferedImage buff = new BufferedImage(imgFrom.getWidth(),imgFrom.getHeight(),
          TYPE_INT_RGB);
      for (int row = 0; row < buff.getHeight(); row++) {
        for (int col = 0; col < buff.getWidth(); col++) {
          Pixel currentPix = imgFrom.getPixel(row, col);
          PixelColor pixColor = currentPix.getColor();
          Color currentColor = new Color(pixColor.getRed(), pixColor.getGreen(),
              pixColor.getBlue());
          int colorInt = currentColor.getRGB();
          buff.setRGB(col, row, colorInt);

        }
      }
      this.view.updateTopVisibleLayer(buff);
      renderMessageHelp(view, "file pushed to frame" + "\n");
    }

    else {
      try {
        BufferedImage buffImage = ImageIO.read(new File(layerPath));
        this.view.updateTopVisibleLayer(buffImage);
        renderMessageHelp(view, "file pushed to frame" + "\n");

      }
      catch (IOException io) {
        new IllegalArgumentException(io.getMessage());
      }
    }
  }

  /**
   * Returns the topmost visible image in this multilayered image.
   *
   * @return Topmost visible image.
   */
  private BufferedImage getTopMostVisibleImage() {
    if (!this.model.hasVisibleLayer()) {
      return new BufferedImage(1,1, TYPE_INT_RGB);
    }
    else {
      Image bi = this.model.getTopVisibleLayer().getImage();
      return getBuff(bi);
    }
  }

}
