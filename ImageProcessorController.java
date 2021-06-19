package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import model.ComplexImageProcessorModel;
import model.IImageLayer;
import model.Image;
import model.ImageLayer;
import model.Pixel;
import model.PixelColor;
import model.SimpleImageProcessorModel;
import view.ImageProcessorView;

public class ImageProcessorController implements IPController {
  private ComplexImageProcessorModel model;
  private Scanner in;
  private Appendable ap;


  public ImageProcessorController(ComplexImageProcessorModel model, InputStream in,
      Appendable out) {
    this.model = model;
    this.in = new Scanner(in);
    this.ap = out;

  }


  @Override
  public void go() {

    ImageProcessorView view = new ImageProcessorView(this.ap);
    boolean quit = false;

    while (!quit) {
      String command = in.next();
      switch (command) {
        case "quit":
          quit = true;
          renderMessageHelp(view, "IP closed");
          break;

        case "txtCommand":
          String filename = in.next();
          File inFile = new File(filename);
          try {
            SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
            ComplexImageProcessorModel ComplexModel = new ComplexImageProcessorModel(simpleModel);
            InputStream targetStream = new FileInputStream (inFile);
            IPController controller = new ImageProcessorController(ComplexModel, targetStream,
                System.out);
            controller.go();
          }
          catch (IOException io) {
            throw new IllegalArgumentException ("Invalid Text");
          }
          break;


        case "create":
          int index = in.nextInt();
          if (index > this.model.getNumLayers() || index < 0) {
            renderMessageHelp(view,"invalid layer index\n");
            break;
          }
          this.model.createLayer(index);
          this.model.setCurrentLayer(index);
          renderMessageHelp(view,"layer " + this.model.getCurrentLayer() + " created\n");
          renderMessageHelp(view, "Current Layer: " + this.model.getCurrentLayer()
              + "\n Layers in IP: " + this.model.getNumLayers() + "\n");
          break;

        case "current":
          int currentIndex = in.nextInt();
          if (currentIndex > this.model.getNumLayers() || currentIndex < 0) {
            renderMessageHelp(view,"invalid layer index\n");
            break;
          }
          this.model.setCurrentLayer(currentIndex);
          renderMessageHelp(view,"Current Layer: " + this.model.getCurrentLayer() + "\n");

          break;

        case "load":
          // add functionality for multilayered image
          String fileName = in.next();
          //importHelp(fileName);
          try {
            this.model.importImage(fileName);
          }
          catch (IllegalArgumentException e) {
            renderMessageHelp (view, "Image Wrong Size\n");
            break;
          }
          renderMessageHelp(view,fileName + " loaded to layer: "
              + this.model.getCurrentLayer() + "\n");
          break;

        case "export":
          String fileNameExport = in.next();
          this.model.exportMultiLayeredImage(fileNameExport);
          renderMessageHelp(view,fileNameExport + " Exported" + "\n");
          //exports multilayered image as a text file

        case "import":
          String fileNameImport = in.next();
          this.model.importMultiLayeredImage(fileNameImport);
          renderMessageHelp(view,fileNameImport + " Imported" + "\n");


        case "save":
          String fileNameSave = in.next();

          this.model.exportTopVisibleLayer(fileNameSave, in.next());
          renderMessageHelp(view,fileNameSave + " Saved" + "\n");

          break;

        case "copy":
          int layerIndex = in.nextInt();
          this.model.copyCurrentLayer(layerIndex);
          renderMessageHelp(view,"Layer Copied to: " + layerIndex + "\n");

          break;

        case "remove":
          int layerIndexRemove = in.nextInt();
          this.model.removeLayer(layerIndexRemove);
          renderMessageHelp(view, "layer: " + layerIndexRemove + " Removed" + "\n");
          break;

        case "visible":
          this.model.makeInvisible();
          renderMessageHelp(view, "layer: " + this.model.getCurrentLayer() + " Visible"
              + "\n");
          break;

        case "invisible":
          this.model.makeVisible();
          renderMessageHelp(view, "layer: " + this.model.getCurrentLayer() + " Invisible"
              + "\n");

          break;

        case "sepia":
          this.model.applySepia();
          renderMessageHelp(view, "layer: " + this.model.getCurrentLayer() + " Sepia"
              + "\n");

          break;

        case "greyscale":
          this.model.applyGreyscale();
          renderMessageHelp(view, "layer: " + this.model.getCurrentLayer() + " Greyscale"
              + "\n");

          break;

        case "blur":
          this.model.blur();
          renderMessageHelp(view, "layer: " + this.model.getCurrentLayer() + " Blurred"
              + "\n");

          break;

        case "sharpen":
          this.model.sharpen();
          renderMessageHelp(view, "layer: " + this.model.getCurrentLayer() + " Sharpened"
              + "\n");

          break;
      }
    }
  }

  private void importHelp(String fileName) {
//need to think about what to do if there is already an Image in the layer
    File fileIn;

    String fileTag = fileName.substring(fileName.length() - 3);
    if (fileTag.equals("ppm")) {
      this.model.importImage(fileName);
      int currentLayerIndex = this.model.getCurrentLayer();
      IImageLayer current = this.model.getLayerAt(currentLayerIndex);
      if (current.getImage() == null) {
        current.replaceImage(this.model.getCurrentImage());
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
        int currentLayerIndex = this.model.getCurrentLayer();
        IImageLayer current = this.model.getLayerAt(currentLayerIndex);

        ImageReader reader = imageReader.next();
        current.setName(fileName);
        current.setFiletype(reader.getFormatName()); //TODO: maybe get rid of field for file type
        IImageLayer newImage = importHelper2(fileIn);/*
        if (current.getImage() == null) {
          current.replaceImage(this.model.getCurrentImage());
        }*/
        this.model.setLayer(newImage);
        current.replaceImage(newImage.getImage());



      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File " + fileName + " not found!");
      } catch (IOException exception) {
        exception.printStackTrace ();
      }
    }
  }

  private IImageLayer importHelper2(File fileIn) throws IOException {
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
    int currentLayerIndex = this.model.getCurrentLayer();
    IImageLayer current = this.model.getLayerAt(currentLayerIndex);
    IImageLayer imageLayer = new ImageLayer(image,fileIn.getName(), current.getFileType());
    return imageLayer;
  }

  private void renderMessageHelp(ImageProcessorView view, String string) {
    try {
      view.renderMessage(string);
    }
    catch (IOException io) {
      throw new IllegalStateException("error rendering message");
    }
  }
}
