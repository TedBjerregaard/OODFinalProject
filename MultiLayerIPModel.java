package model;

public interface MultiLayerIPModel {

  void saveImageLayer(Image image);
  void setCurrentLayer(int index);
  void exportTopVisibleLayer(String filename, String fileType);
  void createLayer(int index);
  void copyCurrentLayer(int index);
  void removeLayer(int index);
  void makeInvisible();
  void makeVisible();

  void importMultiLayeredImage(String fileName);

  void exportMultiLayeredImage(String fileName);

}
