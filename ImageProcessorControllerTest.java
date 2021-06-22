import static org.junit.Assert.assertEquals;

import controller.IPController;
import controller.ImageProcessorController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import model.ComplexImageProcessorModel;
import model.Image;
import model.SimpleImageProcessorModel;
import org.junit.Test;

/**
 * Testing class for the ImageProcessorController class and its operations.
 */
public class ImageProcessorControllerTest {


  @Test
  public void TestImport() throws FileNotFoundException {
    InputStream stream = new FileInputStream("TextCommands.txt");
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel complexModel = new ComplexImageProcessorModel(simpleModel);
    IPController controller = new ImageProcessorController(complexModel, stream, System.out);
    controller.runIP();
    Image newImage = complexModel.getCurrentImage();

    assertEquals(519, newImage.getHeight());


  }

  @Test
  public void TestPNG() throws FileNotFoundException {
    InputStream stream = new FileInputStream("PNGTest.txt");
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel complexModel = new ComplexImageProcessorModel(simpleModel);
    IPController controller = new ImageProcessorController(complexModel, stream, System.out);
    controller.runIP();
    Image newImage = complexModel.getCurrentImage();

    assertEquals(48, newImage.getHeight());


  }

  @Test
  public void TestJPEG() throws FileNotFoundException {
    InputStream stream = new FileInputStream("JpegTest.txt");
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel complexModel = new ComplexImageProcessorModel(simpleModel);
    IPController controller = new ImageProcessorController(complexModel, stream, System.out);
    controller.runIP();
    Image newImage = complexModel.getCurrentImage();

    assertEquals(519, newImage.getHeight());


  }

  @Test
  public void TestPPM() throws FileNotFoundException {
    InputStream stream = new FileInputStream("PPMTest.txt");
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel complexModel = new ComplexImageProcessorModel(simpleModel);
    IPController controller = new ImageProcessorController(complexModel, stream, System.out);
    controller.runIP();
    Image newImage = complexModel.getCurrentImage();

    assertEquals(30, newImage.getHeight());


  }

  @Test
  public void TestTC1() throws FileNotFoundException {
    InputStream stream = new FileInputStream("TC1.txt");
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel complexModel = new ComplexImageProcessorModel(simpleModel);
    IPController controller = new ImageProcessorController(complexModel, stream, System.out);
    controller.runIP();
    Image newImage = complexModel.getCurrentImage();

    assertEquals(519, newImage.getHeight());


  }

  @Test
  public void TestTC2() throws FileNotFoundException {
    InputStream stream = new FileInputStream("res/TC2.txt");
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel complexModel = new ComplexImageProcessorModel(simpleModel);
    IPController controller = new ImageProcessorController(complexModel, stream, System.out);
    controller.runIP();
    Image newImage = complexModel.getCurrentImage();

    assertEquals(300, newImage.getHeight());
    assertEquals(300, newImage.getWidth());


  }
}