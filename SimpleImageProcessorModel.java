import java.util.Arrays;
import java.util.List;

public class SimpleImageProcessorModel implements ImageProcessorModel {

  Image image;

  public SimpleImageProcessorModel(String filename) {
    importImage(filename);
  }


  @Override
  public Image blur() {
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel (3,kValues);
    return this.image.filter(k);
  }

  @Override
  public Image sharpen() {
    List<Double> kValues = Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125,
        -0.125, .25, .25, .25, -0.125,
        -0.125, 0.25, 1.0, 0.25, -0.125,
        -0.125, 0.25, 0.25, 0.25, -0.125,
        -0.125, -0.125, -0.125, -0.125, -0.125);
    Kernel k = new Kernel (5,kValues);
    return this.image.filter(k);
  }

  @Override
  public Image applyGreyscale() {
    List<Double> redList = Arrays.asList(.2126,.7152,.0722);
    List<Double> greenList = Arrays.asList(.2126,.7152,.0722);
    List<Double> blueList = Arrays.asList(.2126,.7152,.0722);
    CTMatrix matrix = new CTMatrix(redList,greenList,blueList);
    Image finalImage = this.image.transformColor(matrix);
    return finalImage;
  }

  @Override
  public Image applySepia() {
    List<Double> redList = Arrays.asList(.393,.769,.189);
    List<Double> greenList = Arrays.asList(.349,.686,.168);
    List<Double> blueList = Arrays.asList(.272,.534,.131);
    CTMatrix matrix = new CTMatrix(redList,greenList,blueList);
    Image finalImage = this.image.transformColor(matrix);
    return finalImage;
  }


  @Override
  public Image createImage() {
    return null;
  }


  @Override
  public void importImage(String filename) {
    this.image = new Image(filename);
  }

}
