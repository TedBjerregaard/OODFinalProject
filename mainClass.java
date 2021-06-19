import controller.IPController;
import controller.ImageProcessorController;
import model.ComplexImageProcessorModel;
import model.IPModel;
import model.SimpleImageProcessorModel;

public class mainClass {
  public static void main(String []args) {
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel ComplexModel = new ComplexImageProcessorModel(simpleModel);
    IPController controller = new ImageProcessorController (ComplexModel,System.in);
    controller.go();
  }
}
