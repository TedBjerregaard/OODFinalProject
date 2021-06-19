import static org.junit.Assert.assertEquals;

import controller.IPController;
import controller.ImageProcessorController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Scanner;
import model.ComplexImageProcessorModel;
import model.Image;
import model.SimpleImageProcessorModel;
import org.junit.Test;

public class ImageProcessorControllerTest {


  @Test
  public void TestImport() throws FileNotFoundException{
    InputStream stream = new FileInputStream("TextCommands.txt");
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel ComplexModel = new ComplexImageProcessorModel(simpleModel);
    IPController controller = new ImageProcessorController (ComplexModel, stream, System.out);
    controller.go();
    Image newImage =  ComplexModel.getCurrentImage();

    assertEquals(576, newImage.getHeight());


  }
}
