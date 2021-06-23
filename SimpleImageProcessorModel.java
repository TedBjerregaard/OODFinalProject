package model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * A simple model for an model.Image Processor. This model is able to perform several operations to
 * an image with a corresponding file name. Operations included currently include: Producing
 * filtered images (blurred and sharpened). Producing images with after applying a given color
 * transformation (Sepia and Greyscale). Importing images (PPM format). Exporting images (PPM
 * format).
 */
public class SimpleImageProcessorModel implements IPModel {

  public Image image;

/*
  */
/**
   * Creates a model for processing images given a string representing the name of an image to be
   * imported.
   *
   * @param fileName String representing an model.Image to be imported to the model.
   *//*

  public SimpleImageProcessorModel(String fileName) {
    importImage(fileName);
  }
*/


  //Added a new constructor taking in a Image
  public SimpleImageProcessorModel(Image image) {
    this.image = image;
  }


  /**
   * Empty constructor for an image processor model that allows the user to skip the step of
   * importing an image, and allows them to instead create an image to be processed.
   */
  public SimpleImageProcessorModel() {
    //nothing

  }

  @Override
  public Image blur() {
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel(3, kValues);
    return this.image.filter(k);
  }

  @Override
  public Image sharpen() {
    List<Double> kValues = Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125,
        -0.125, .25, .25, .25, -0.125,
        -0.125, 0.25, 1.0, 0.25, -0.125,
        -0.125, 0.25, 0.25, 0.25, -0.125,
        -0.125, -0.125, -0.125, -0.125, -0.125);
    Kernel k = new Kernel(5, kValues);
    return this.image.filter(k);
  }

  //TODO: combine into oneApplyColorTransformation method
  @Override
  public Image applyGreyscale() {
    List<Double> redList = Arrays.asList(.2126, .7152, .0722);
    List<Double> greenList = Arrays.asList(.2126, .7152, .0722);
    List<Double> blueList = Arrays.asList(.2126, .7152, .0722);
    CTMatrix matrix = new CTMatrix(redList, greenList, blueList);
    Image finalImage = this.image.transformColor(matrix);
    return finalImage;
  }

  @Override
  public Image applySepia() {
    List<Double> redList = Arrays.asList(.393, .769, .189);
    List<Double> greenList = Arrays.asList(.349, .686, .168);
    List<Double> blueList = Arrays.asList(.272, .534, .131);
    CTMatrix matrix = new CTMatrix(redList, greenList, blueList);
    Image finalImage = this.image.transformColor(matrix);
    return finalImage;
  }


  @Override
  public Image createImage(int tileSize, int numTiles, PixelColor color1, PixelColor color2,
      int maxColorVal) {
    int side = tileSize * numTiles;
    Pixel[][] pixelArray = new Pixel[side][side];

    int pixCountX = 0;
    int row = 0;
    while (row < side) {

      int pixCountY = 0;
      int col = 0;
      while (col < side) {

        if (pixCountX >= 2 * tileSize) {
          pixCountX = 0;
        }
        if (pixCountY >= 2 * tileSize) {
          pixCountY = 0;
        }

        if (pixCountY < tileSize &&
            pixCountX < tileSize) {
          Pixel newPix = new Pixel(col, row, color1);
          pixelArray[row][col] = newPix;
          pixCountY++;
          col++;
        } else if (pixCountY >= tileSize &&
            pixCountX >= tileSize) {
          Pixel newPix = new Pixel(col, row, color1);
          pixelArray[row][col] = newPix;
          pixCountY++;
          col++;
        } else {
          Pixel newPix = new Pixel(col, row, color2);
          pixelArray[row][col] = newPix;
          pixCountY++;
          col++;
        }
      }
      pixCountX++;
      row++;
    }
    return new Image(side, side, maxColorVal, pixelArray);
  }


  public Image getCurrentImage() {
    return this.image;
  }

}

