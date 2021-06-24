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

/**
 * An implementation of the model for an image processor that allows support for creating,
 * manipulating and exporting multilayered images. Multilayered images are essentially a list of
 * image layers that have an image with its file type, name, and visibility. Operations can be
 * performed on the current layer, which can be set. The layer can be seen when visible, and can't
 * be when invisible.
 */
public class ComplexImageProcessorModel implements IPModel, MultiLayerIPModel {

  private IPModel delegate;
  private int currentLayer;
  private int height;
  private int width;
  private List<IImageLayer> layers;

  /**
   * Constructor for the ComplexImageProcessorModel. Utilizes a SimpleImageProcessorModel as a
   * delegate to retain and expand on functionality made for processing operations on a single
   * image.
   *
   * @param delegate SimpleImageProcessorModel used to perform basic commands.
   */
  public ComplexImageProcessorModel(IPModel delegate) {
    Objects.requireNonNull(delegate);
    this.delegate = delegate;
    this.layers = new ArrayList<>();
  }

  /**
   * Applies a filter (Kernel) to an image and creates a blurred version of the same image. Replaces
   * the image in the current image layer with the blurred one.
   *
   * @return A blurred version of an image.
   */
  @Override
  public Image blur() {
    IImageLayer current = this.layers.get(this.currentLayer);
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel(3, kValues);
    Image modImage = current.getImage().filter(k);
    current.replaceImage(modImage);
    return modImage;
  }


  /**
   * Applies a filter (Kernel) to an image and creates a sharpened version of the same image.
   * Replaces the image in the current image layer with the sharpened one.
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
    Image modImage = current.getImage().filter(k);
    current.replaceImage(modImage);
    return modImage;
  }

  /**
   * Applies a matrix of equations that will be used to calculate the new red, green, and blue
   * values for each pixel in an image. Creates a new image with a greyscale color transformation
   * applied to it. Replaces the image in the current image layer with one that has a greyscale
   * color transformation.
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
    current.replaceImage(modImage);
    return modImage;
  }

  /**
   * Applies a matrix of equations that will be used to calculate the new red, green, and blue
   * values for each pixel in an image. Creates a new image with a sepia color transformation
   * applied to it. Replaces the image in the current image layer with one that has a sepia color
   * transformation.
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
    current.replaceImage(modImage);
    return modImage;

  }

  /**
   * Creates a new image programmatically. Currently, the method creates a checkerboard with a
   * specified square tile size, number of tiles in the checkerboard, and colors to use.
   * Replaces the image in the current image layer with one that has a greyscale color
   * transformation.
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

    IImageLayer current = this.layers.get(this.currentLayer);
    Image checkerBoard = this.delegate.createImage(tileSize, numTiles, color1, color2, maxColorVal);
    current.replaceImage(checkerBoard);
    return checkerBoard;
  }


  @Override
  public void saveImageLayer(Image image) {
    IImageLayer current = this.layers.get(this.currentLayer);
    current.replaceImage(image);
  }

  @Override
  public void setCurrentLayer(int index) throws IllegalArgumentException {

    this.currentLayer = index;
  }

  @Override
  public Image getCurrentImage() {
    return this.layers.get(this.currentLayer).getImage();
  }


  @Override
  public IImageLayer getTopVisibleLayer() throws IllegalArgumentException {

    for (int i = this.layers.size() - 1; i >= 0; i--) {
      if (this.layers.get(i).isVisible()) {
        return layers.get(i);
      }
    }
    throw new IllegalArgumentException("No visible layers in image!");
  }

  @Override
  public void createLayer(int index) {

    this.layers.add(index, new ImageLayer(null, null, null));
  }

  @Override
  public void copyCurrentLayer(int index) {
    IImageLayer current = this.layers.get(this.currentLayer);
    IImageLayer copy = current.makeCopy();
    this.layers.get(index).updateLayer(copy);
  }

  @Override
  public void removeLayer(int index) {
    if(index < this.getNumLayers() && index >= 0) {
      this.layers.remove(index);
      if (index == this.currentLayer) {
        this.currentLayer = this.currentLayer - 1;
      }
    }
  }

  @Override
  public void makeInvisible() {
    IImageLayer current = this.layers.get(this.currentLayer);
    current.makeInvisible();
  }

  @Override
  public void makeVisible() {
    IImageLayer current = this.layers.get(this.currentLayer);
    current.makeInvisible();
  }


  @Override
  public int getCurrentLayerIndex() {
    return this.currentLayer;
  }

  @Override
  public IImageLayer getCurrentLayer() {
    return this.layers.get(currentLayer);
  }

  @Override
  public void setWidth(int w) {
    this.width = w;
  }

  @Override
  public void setHeight(int h) {
    this.height = h;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public IImageLayer getLayerAt(int i) {
    return this.layers.get(i);
  }

  @Override
  public int getNumLayers() {
    return this.layers.size();
  }



}
