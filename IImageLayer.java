package model;

public interface IImageLayer {


  void makeVisible();

  void makeInvisible();

  ImageLayer makeNewBlankLayer();

  ImageLayer makeCopy();

  Image getImage();

  void replaceImage(Image image);

  boolean isVisible();

  String getName();

  String getFileType();

  void updateLayer(IImageLayer layer);


  void setFiletype(String filetype);

  void setName(String name);

  int getImageHeight();

  int getImageWidth();



}