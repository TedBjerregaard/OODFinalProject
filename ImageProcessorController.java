package controller;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.IPModel;

public class ImageProcessorController implements IPController {
  IPModel model;
  RenderedImage image;
  int w,h;
  String formatName;
  FileOutputStream outputImage;
  FileInputStream InputImage;
  Dimension HeightAndWidth;
  ImageProcessorController() {

  }


  @Override
  public void ImportImage(String fileName) {

    BufferedImage image  = null;
    File fileIn;




    try {
      Scanner sc = new Scanner (new FileInputStream (fileName));

      while (sc.hasNext()) {
        String str = sc.next();

        if (str.contains (".png")) {
          this.formatName = ".png";

        }
        if (str.contains(".jpeg")) {
          this.formatName = ".jpeg";

        }
        if (str.contains(".gif")) {
          this.formatName = ".gif";

        }
      }
      fileIn = new File(fileName);
      this.image = ImageIO.read(fileIn);
      w = this.image.getWidth();
      h = this.image.getHeight();
      ImageIO.write(image, this.formatName, this.outputImage);


    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + fileName + " not found!");
    } catch (IOException exception) {
      exception.printStackTrace ();
    }


  }

  public Dimension getPreferredSize() {
    return new Dimension(w, h);
  }

}
