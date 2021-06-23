package model;

/**
 *  Interface for an image layer and the operations that can be performed on it. An image layer
 *  is currently one of the layers in a multilayered image and its relevant information.
 */
public interface IImageLayer {

  /**
   * Makes this layer visible to the user.
   */
  void makeVisible();

  /**
   * Makes this layer invisible to the user.
   */
  void makeInvisible();

  /**
   * Creates a new, blank image layer with no information. This information can be populated when
   * this layer is used.
   *
   * @return A new, blank image layer.
   */
  ImageLayer makeNewBlankLayer();

  /**
   * Makes a copy of an image layer with all the same information, name, image, visibility, and
   * file type.
   *
   * @return A copy of the image layer this method is called on.
   */
  ImageLayer makeCopy();

  /**
   * Gets the image associated with this image layer.
   *
   * @return Image in this image layer.
   */
  Image getImage();

  /**
   * Replaces the image in this image layer with the given image.
   *
   * @param image New image in this image layer.
   */
  void replaceImage(Image image);

  /**
   * Determines whether this image layer is visible or not.
   *
   * @return Whether or not this image layer is visible.
   */
  boolean isVisible();

  /**
   * Returns the name of this image layer.
   *
   * @return Name of this image layer.
   */
  String getName();

  /**
   * Returns the file type of the image in this image layer.
   *
   * @return file type of the image in this image layer.
   */
  String getFileType();

  /**
   * Updates the information stored in this image layer with that of the given image layer.
   *
   * @param layer Image layer that is used to update this image layers information.
   */
  void updateLayer(IImageLayer layer);


  /**
   * Changes the file type of the image in this image layer to the given file type.
   *
   * @param filetype New file type of the image in this image layer.
   */
  void setFiletype(String filetype);

  /**
   * Changes the name of this image layer to the given string.
   *
   * @param name New name of this image layer.
   */
  void setName(String name);

  /**
   * Returns the height of the image in an image layer.
   *
   * @return Height of the image.
   */
  int getImageHeight();

  /**
   * Returns the height of the image in an image layer.
   *
   * @return Width of the image.
   */
  int getImageWidth();
}