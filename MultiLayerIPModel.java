package model;

/**
 * An interface for a multilayered image processor model and its operations.
 * Multilayered images are essentially a list of image layers that have an image with its
 * file type, name, and visibility. Operations can be performed on the current layer,
 * which can be set. The layer can be seen when visible, and can't be when invisible.
 */
public interface MultiLayerIPModel {

  /**
   * Replaces the image in the current layer in the multilayered image with the given image.
   *
   * @param image Image to be set to the current layer.
   */
  void saveImageLayer(Image image);

  /**
   * Takes in an index representing a layer number that will be set as the current layer that
   * all operations can then be performed on. Throws an IllegalArgumentException if the index is
   * invalid. That is, it is below 0, or above the index for the topmost layer in the multilayered
   * image.
   *
   * @param index Layer number to be set to the curren, working layer.
   * @throws IllegalArgumentException Thrown when the given index is below 0, or above that of the
   *                                  index for the topmost layer in the multilayered image.
   */
  void setCurrentLayer(int index) throws IllegalArgumentException;

  /**
   * Retrieves the image in the current layer of the multilayer image.
   *
   * @return Image in the current layer of the multilayer image.
   */
  Image getCurrentImage();

  /** //TODO: fix all this!!!!!
   * Exports the topmost visible layer of a multilayered image with the provided filename and type.
   * Supported types are those mentioned above: PPM, JPEG, and PNG.
   *
   */
  IImageLayer getTopVisibleLayer() throws IllegalArgumentException;

  /**
   * Creates a new blank layer in the multilayer image as the topmost visible layer.
   *
   * @param index Index to create a new blank layer
   */
  void createLayer(int index);

  /**
   * Copies a current layer and sets the layer at the given index to the layer that was copied.
   *
   * @param index Layer to be set to the copy of the current layer.
   */
  void copyCurrentLayer(int index);

  /**
   * Removes the layer at the given index from the multilayered image.
   *
   * @param index Index of layer to be removed.
   */
  void removeLayer(int index);

  /**
   * Makes the current layer of the multilayered image invisible.
   */
  void makeInvisible();

  /**
   * Makes the current layer of the multilayered image visible.
   */
  void makeVisible();

  /**
   * Imports the given multilayered image from a text file and sets the layers of the model equal to
   * those of the multilayered image.
   *
   * @param fileName Text file storing information needed to import this multilayered image.
   *                 Specifically, information about the different layers and their location.
   */
  void importMultiLayeredImage(String fileName);

  /**
   * Exports a multilayered image to a text file and exports the images at the different layers of
   * the multilayered image with their given name and file type.
   *
   * @param fileName Name of the text file exported storing information
   *                 about the multilayered image.
   */
  void exportMultiLayeredImage(String fileName);

  /**
   * Returns the current layer of this ComplexImageProcessorModel.
   *
   * @return Current layer of model.
   */
  int getCurrentLayerIndex();

  /**
   * Returns the number of layers in this ComplexImageProcessorModel.
   *
   * @return Number of layers in the multilayered image.
   */
  int getNumLayers();

  IImageLayer getCurrentLayer();

  void setWidth(int w);

  void setHeight(int h);

  int getHeight();

  int getWidth();

  IImageLayer getLayerAt(int i);

}
