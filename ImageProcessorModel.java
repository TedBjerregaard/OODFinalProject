public interface ImageProcessorModel {

  public Image blur();
  public Image sharpen();
  public Image applyGreyscale();
  public Image applySepia();
  public Image createImage(int height, int width, int maxColor);
  public void importImage(String filename);
  public void exportImage(String filename, String filetype);



}
