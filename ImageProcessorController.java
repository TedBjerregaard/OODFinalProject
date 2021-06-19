package controller;

import java.io.InputStream;
import java.util.Scanner;
import model.ComplexImageProcessorModel;

public class ImageProcessorController implements IPController {
  ComplexImageProcessorModel model;
  private Scanner in;
  Readable rd;
  Appendable ap;


  public ImageProcessorController(ComplexImageProcessorModel model, InputStream in) {
    this.model = model;
    this.in = new Scanner(in);

  }


  @Override
  public void go() {

    boolean quit = false;

    while (!quit) {
      String command = in.next();
      switch (command) {
        case "quit":
          quit = true;
          break;
        case "create":
          this.model.createLayer(in.nextInt());
          break;

        case "current":
          this.model.setCurrentLayer(in.nextInt());
          break;

        case "load":
          // add functionality for multilayered image
          this.model.importImage(in.next());
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




}
