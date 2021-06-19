package model;

public class ImageLayer implements IImageLayer {

  private Image image;
  private boolean visible;
  private String name;
  private String fileType;

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
  public ImageLayer makeNewBlankLayer() {
    return new ImageLayer(null, null, null);
  }

  @Override
  public ImageLayer makeCopy() {
    return new ImageLayer(this.image, this.name, this.fileType);
  }

  public Image getImage(){
    return image;
  }

  @Override
  public void replaceImage(Image image){
    this.image = image;
  }

  @Override
  public boolean isVisible(){
    return this.visible;
  }

  @Override
  public String getName(){
    return this.name;
  }

  @Override
  public String getFileType(){
    return this.fileType;
  }

  @Override
  public void updateLayer(IImageLayer layer){
    this.image = layer.getImage();
    this.visible = layer.isVisible();
    this.name = layer.getName();
    this.fileType = layer.getFileType();
  }

  @Override
  public void setFiletype(String filetype){
    this.fileType = filetype;
  }

  @Override
  public void setName(String name){
    this.name = name;
  }

  @Override
  public int getImageHeight(){
    return this.image.getHeight();
  }

  @Override
  public int getImageWidth(){
    return this.image.getWidth();
  }


}
