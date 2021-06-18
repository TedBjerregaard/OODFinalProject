import static org.junit.Assert.assertEquals;

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
    modelAfter.setCurrentLayer (1);
    modelAfter.importImage("CheckerboardBlurred.jpeg");

    Image afterImage = modelAfter.getCurrentImage ();
    assertEquals(30, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(30, afterImage.getWidth());
    assertEquals(30, afterImage.pixelArray.length);

    modelAfter.setCurrentLayer (0);
    Image afterImage2 = modelAfter.getCurrentImage ();
    assertEquals(525, afterImage2.getHeight());
    assertEquals(255, afterImage2.getMaxColorVal());
    assertEquals(1000, afterImage2.getWidth());
    assertEquals(525, afterImage2.pixelArray.length);



  }

  @Test
  public void testImportPNG() {

    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel modelAfter = new ComplexImageProcessorModel(simpleModel);

    modelAfter.importImage("smoke.png");

    Image afterImage = modelAfter.getCurrentImage ();
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

    Image afterImage = modelAfter.getCurrentImage ();
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





    model.exportImage(model.getCurrentImage (), "CoffeeBlurred", "ppm");

    SimpleImageProcessorModel simpleModel2 = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model2 = new ComplexImageProcessorModel(simpleModel2);
    model2.importImage("CoffeeBlurred.ppm");
    Image afterImage = model2.getCurrentImage ();
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

    model.exportImage(model.getCurrentImage (),"Checkerboard", "ppm" );
  }

  @Test
  public void testExportAsPNG() {
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model = new ComplexImageProcessorModel(simpleModel);
    model.importImage("Checkerboard.ppm");





    model.exportImage(model.getCurrentImage (), "CheckerboardBlurred", "png");

    SimpleImageProcessorModel simpleModel2 = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model2 = new ComplexImageProcessorModel(simpleModel2);
    model2.importImage("CheckerboardBlurred.png");
    Image afterImage = model2.getCurrentImage ();
    assertEquals(30, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(30, afterImage.getWidth());
    assertEquals(30, afterImage.pixelArray.length);
  }

  @Test
  public void testExportAsJPEG() {
    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model = new ComplexImageProcessorModel(simpleModel);
    model.setCurrentLayer (0);
    model.importImage("Checkerboard.ppm");

    model.blur();
    model.blur();
    model.blur();
    model.blur();
    model.blur();
    model.blur();




    model.exportImage(model.getCurrentImage (), "CheckerboardBlurred", "jpeg");

    SimpleImageProcessorModel simpleModel2 = new SimpleImageProcessorModel();
    ComplexImageProcessorModel model2 = new ComplexImageProcessorModel(simpleModel2);
    model2.importImage("CheckerboardBlurred.jpeg");
    Image afterImage = model2.getCurrentImage ();
    assertEquals(30, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(30, afterImage.getWidth());
    assertEquals(30, afterImage.pixelArray.length);
  }


}
