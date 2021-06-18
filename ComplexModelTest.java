import static org.junit.Assert.assertEquals;

import java.io.IOException;
import model.ComplexImageProcessorModel;
import model.Image;
import model.PixelColor;
import model.SimpleImageProcessorModel;
import org.junit.Test;

public class ComplexModelTest {
  @Test
  public void testImportJPEG() {

    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel modelAfter = new ComplexImageProcessorModel(simpleModel);

    modelAfter.importImage("Finn.jpeg");

    Image afterImage = modelAfter.getImage();
    assertEquals(525, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(1000, afterImage.getWidth());
    assertEquals(525, afterImage.pixelArray.length);


  }

  @Test
  public void testImportPNG() {

    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel modelAfter = new ComplexImageProcessorModel(simpleModel);

    modelAfter.importImage("smoke.png");

    Image afterImage = modelAfter.getImage();
    assertEquals(600, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(459, afterImage.getWidth());
    assertEquals(600, afterImage.pixelArray.length);


  }

  @Test
  public void testImportPPM() {

    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel modelAfter = new ComplexImageProcessorModel(simpleModel);

    modelAfter.importImage("Ted.ppm");

    Image afterImage = modelAfter.getImage();
    assertEquals(576, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(436, afterImage.getWidth());
    assertEquals(576, afterImage.pixelArray.length);


  }

  @Test
  public void testExportPPMToPPM() {
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model = new ComplexImageProcessorModel(simpleModel);
    model.importImage("Coffee.ppm");

    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());



    model.exportImage(model.getImage(), "CoffeeBlurred", "ppm");

    SimpleImageProcessorModel simpleModel2 = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model2 = new ComplexImageProcessorModel(simpleModel2);
    model2.importImage("CoffeeBlurred.ppm");
    Image afterImage = model2.getImage();
    assertEquals(519, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(488, afterImage.getWidth());
    assertEquals(519, afterImage.pixelArray.length);
  }

  @Test
  public void makeImage() {
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model = new ComplexImageProcessorModel(simpleModel);
    PixelColor red = new PixelColor(255, 0, 0, 255, 0);
    PixelColor blue = new PixelColor(0, 0, 255, 255, 0);

    model.createImage(3, 10, red, blue, 255);

    model.exportImage(model.getImage(),"Checkerboard", "ppm" );
  }

  @Test
  public void testExportAsPNG() {
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model = new ComplexImageProcessorModel(simpleModel);
    model.importImage("Checkerboard.ppm");

    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());



    model.exportImage(model.getImage(), "CheckerboardBlurred", "png");

    SimpleImageProcessorModel simpleModel2 = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model2 = new ComplexImageProcessorModel(simpleModel2);
    model2.importImage("CheckerboardBlurred.png");
    Image afterImage = model2.getImage();
    assertEquals(30, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(30, afterImage.getWidth());
    assertEquals(30, afterImage.pixelArray.length);
  }

  @Test
  public void testExportAsJPEG() {
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model = new ComplexImageProcessorModel(simpleModel);
    model.importImage("Checkerboard.ppm");

    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());
    model.setBaseImage(model.blur());



    model.exportImage(model.getImage(), "CheckerboardBlurred", "jpeg");

    SimpleImageProcessorModel simpleModel2 = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model2 = new ComplexImageProcessorModel(simpleModel2);
    model2.importImage("CheckerboardBlurred.jpeg");
    Image afterImage = model2.getImage();
    assertEquals(30, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(30, afterImage.getWidth());
    assertEquals(30, afterImage.pixelArray.length);
  }

  @Test
  public void testAddLayer() throws IOException{

    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel modelAfter = new ComplexImageProcessorModel(simpleModel);
    modelAfter.importImage("Coffee.ppm");
    modelAfter.importLayer("CoffeeBlurred.ppm",0);
    modelAfter.importLayer("CheckerboardBlurred.ppm",0);

    Image top = modelAfter.accessLayer(1);
    Image Second = modelAfter.accessLayer(0);

    assertEquals(576, top.getHeight());
    assertEquals(255, Second.getHeight());


  }
}
