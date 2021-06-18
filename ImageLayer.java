package model;

public class ImageLayer implements IImageLayer {

  private Image image;
  boolean visible;
  String name;
  String fileType;

  public ImageLayer(Image image, String name, String fileType) {
    this.image = image;
    this.name = name;
    this.fileType = fileType;
    this.visible = true;
  }

  @Override
  public void makeVisible() {
    this.visible = true;
  }

  @Override
  public void makeInvisible() {
    this.visible = false;
  }

  @Override
  public ImageLayer makeNewBlankLayer(String name, String fileType) {
    return new ImageLayer(null, name, fileType);
  }

  @Override
  public ImageLayer makeCopy(ImageLayer toCopy) {
    return new ImageLayer(toCopy.image, toCopy.name, toCopy.fileType);
  }
  
}
