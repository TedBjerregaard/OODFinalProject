/*
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;
import model.ComplexImageProcessorModel;
import model.IImageLayer;
import model.PixelColor;
import model.SimpleImageProcessorModel;
import view.IPView;
import view.IViewListener;

public class IPGUIController implements IViewListener {
  private ComplexImageProcessorModel model;
  private IPView view;

  public IPGUIController (ComplexImageProcessorModel model, IPView view) {
    this.model = model;
    this.view = Objects.requireNonNull(view);
    this.view.registerViewEventListener(this);
  }



  @Override
  public void handleActionEvent() {

  }

  @Override
  public void handleLoadEvent(String absolutePath) {

  }


  */
/**
   * Runs the image processor to perform available commands on images.
   *//*

  public void runIP(String arg) {

    boolean quit = false;

    Scanner scan = new Scanner(arg);

    while (!quit) {
      String command = scan.next();
      switch (command) {
        case "quit":
          quit = true;
          break;

        case "txtCommand":
          String filename = scan.next();
          File inFile = new File(filename);
          try {
            SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
            ComplexImageProcessorModel complexModel = new ComplexImageProcessorModel(simpleModel);
            InputStream targetStream = new FileInputStream(inFile);
            IPController controller = new ImageProcessorController(complexModel, targetStream,
                System.out);
            controller.runIP();
          } catch (IOException io) {
            throw new IllegalArgumentException("run failed");
          }
          break;

        case "create":
          int index = scan.nextInt();
          if (index > this.model.getNumLayers() || index < 0) {

            break;
          }
          this.model.createLayer(index);
          this.model.setCurrentLayer(index);

          break;

        case "current":
          int currentIndex = scan.nextInt();
          if (currentIndex > this.model.getNumLayers() || currentIndex < 0) {
            break;
          }
          this.model.setCurrentLayer(currentIndex);

          break;

        case "load":

          String fileName = scan.next();

          try {
            this.importImage(fileName);
          } catch (IllegalArgumentException e) {
            break;
          }

          break;

        case "export":
          String fileNameExport = scan.next();

          break;

        case "import":
          String fileNameImport = scan.next();

          break;

        case "save":
          String fileNameSave = scan.next();
          String fileTypeSave = scan.next();

          IImageLayer topVisible = this.model.getTopVisibleLayer();
          this.exportImage(topVisible.getImage(), fileNameSave, fileTypeSave);

          break;

        case "copy":
          int layerIndex = scan.nextInt();
          this.model.copyCurrentLayer(layerIndex);

          break;

        case "remove":
          int layerIndexRemove = scan.nextInt();
          this.model.removeLayer(layerIndexRemove);
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
       */
/* case "createImage":
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

          break;
*//*

        default:
      }
    }
  }
  }

*/
