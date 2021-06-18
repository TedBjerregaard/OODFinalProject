package model;

public interface IImageLayer {


  public void makeVisible();

  public void makeInvisible();

  public ImageLayer makeNewBlankLayer(String name, String fileType);

  public ImageLayer makeCopy(ImageLayer toCopy);




}
