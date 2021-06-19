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
              + this.model.getCurrentLayer());
          break;

        case "export":
          this.model.exportMultiLayeredImage(in.next());
          //exports multilayered image as a text file

        case "import":
          this.model.importMultiLayeredImage(in.next());

        case "save":
          this.model.exportTopVisibleLayer(in.next(), in.next());
          break;

        case "copy":
          this.model.copyCurrentLayer(in.nextInt());
          break;

        case "remove":
          this.model.removeLayer(in.nextInt());
          break;

        case "visible":
          this.model.makeInvisible();
          break;

        case "invisible":
          this.model.makeVisible();
          break;

        case "sepia":
          this.model.applySepia();
          break;

        case "greyscale":
          this.model.applyGreyscale();
          break;

        case "blur":
          this.model.blur();
          break;

        case "sharpen":
          this.model.sharpen();
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
