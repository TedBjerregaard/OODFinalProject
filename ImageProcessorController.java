package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import model.ComplexImageProcessorModel;
import model.SimpleImageProcessorModel;
import view.ImageProcessorView;

public class ImageProcessorController implements IPController {
  ComplexImageProcessorModel model;
  private Scanner in;
  Appendable ap;


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
            throw new IllegalArgumentException ("stupid idiot");
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
          this.model.importImage(fileName);
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

          this.model.exportTopVisibleLayer(in.next(), in.next());
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
  private void renderMessageHelp(ImageProcessorView view, String string) {
    try {
      view.renderMessage(string);
    }
    catch (IOException io) {
      throw new IllegalStateException("error rendering message");
    }
  }
}
