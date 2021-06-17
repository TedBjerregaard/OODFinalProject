import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.Before;
import org.junit.Test;

/**
 * test for Model.
 */
public class SimpleImageProcessorModelTest {

  SimpleImageProcessorModel model;


  @Before
  public void setup() {
    this.model = new SimpleImageProcessorModel();
  }

  @Test
  public void testCreateImage() {

    PixelColor red = new PixelColor(255, 0, 0, 255, 0);
    PixelColor blue = new PixelColor(0, 0, 255, 255, 0);

    Image newImage = model.createImage(3, 10, red, blue, 255);
    System.out.println(newImage.pixelArray[3][3].getColor().getRed());

    PixelColor firstSquareType = new PixelColor(255, 0, 0, 255, 0);

    assertEquals(4, newImage.getHeight());
    assertEquals(255, newImage.getMaxColorVal());
    assertEquals(4, newImage.getWidth());
    assertEquals(4, newImage.pixelArray.length);
    assertEquals(firstSquareType.getRed(),
        newImage.pixelArray[0][0].getColor().getRed());
    assertEquals(firstSquareType.getGreen(),
        newImage.pixelArray[0][0].getColor().getGreen());
    assertEquals(firstSquareType.getBlue(),
        newImage.pixelArray[0][0].getColor().getBlue());
  }

  @Test
  public void testExport() {

    SimpleImageProcessorModel model = new SimpleImageProcessorModel("Coffee.ppm");
    Image blurred = model.blur();
    model.image = blurred;
    Image blurred2 = model.blur();
    model.image = blurred2;
    Image blurred3 = model.blur();
    model.image = blurred3;
    Image blurred4 = model.blur();
    model.image = blurred4;
    Image blurred5 = model.blur();
    model.image = blurred5;

    model.exportImage(blurred, "CoffeeBlurred", ".ppm");

    SimpleImageProcessorModel modelAfter = new SimpleImageProcessorModel("CoffeeAfter.ppm");

    Image afterImage = modelAfter.image;
    assertEquals(519, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(488, afterImage.getWidth());
    assertEquals(519, afterImage.pixelArray.length);
  }

  @Test
  public void testImportOfExport() {

    SimpleImageProcessorModel modelAfter = new SimpleImageProcessorModel("CoffeeAfter.ppm");

    Image afterImage = modelAfter.image;
    assertEquals(519, afterImage.getHeight());
    assertEquals(255, afterImage.getMaxColorVal());
    assertEquals(519, afterImage.getHeight());
    assertEquals(519, afterImage.pixelArray.length);


  }

  @Test
  public void testMethodsOnCoffee() {
    SimpleImageProcessorModel model = new SimpleImageProcessorModel("Coffee.ppm");
    Image blurredCoffee = model.blur();
    Image sepiaCoffee = model.applySepia();
    Image greyscaleCoffee = model.applyGreyscale();
    Image sharpCoffee = model.sharpen();
    assertEquals(519, sharpCoffee.getHeight());

    model.exportImage(blurredCoffee, "blurredCoffee", ".ppm");
    model.exportImage(sharpCoffee, "sharpCoffee", ".ppm");
    model.exportImage(sepiaCoffee, "sepiaCoffee", ".ppm");
    model.exportImage(greyscaleCoffee, "greyscaleCoffee", ".ppm");
  }

  @Test
  public void testMethodsOnTed() {
    SimpleImageProcessorModel model = new SimpleImageProcessorModel("Ted.ppm");
    Image blurredTed = model.blur();
    Image sepiaTed = model.applySepia();
    Image greyscaleTed = model.applyGreyscale();
    Image sharpTed = model.sharpen();
    assertEquals(576, sharpTed.getHeight());

    model.exportImage(blurredTed, "blurredTed", ".ppm");
    model.exportImage(sharpTed, "sharpTed", ".ppm");
    model.exportImage(sepiaTed, "sepiaTed", ".ppm");
    model.exportImage(greyscaleTed, "greyscaleTed", ".ppm");
  }
}
