import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
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
  public void testCreateImage(){

    PixelColor red = new PixelColor(255, 0, 0, 255, 0);
    PixelColor blue = new PixelColor(0, 0, 255, 255, 0);

    Image newImage = model.createImage(3,10, red, blue);
    System.out.println(newImage.pixelArray[3][3].color.red);

    PixelColor firstSquareType = new PixelColor(255,0,0,255,0);

    assertEquals(4, newImage.height);
    assertEquals(255,newImage.maxColorVal );
    assertEquals(4,newImage.width);
    assertEquals(4, newImage.pixelArray.length);
    assertEquals(firstSquareType.red,
        newImage.pixelArray[0][0].color.red);
    assertEquals(firstSquareType.green,
        newImage.pixelArray[0][0].color.green);
    assertEquals(firstSquareType.blue,
        newImage.pixelArray[0][0].color.blue);
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

    model.exportImage(blurred,"CoffeeAfter", ".ppm");
    model.exportImage(blurred5,"CoffeeBlurred", ".ppm");


    SimpleImageProcessorModel modelafter = new SimpleImageProcessorModel("CoffeeAfter.ppm");

    Image afterImage = modelafter.image;
    assertEquals(519, afterImage.height);
    assertEquals(255,afterImage.maxColorVal);
    assertEquals(488,afterImage.width);
    assertEquals(519, afterImage.pixelArray.length);
  }

  @Test
  public void testImportOfExport(){


    SimpleImageProcessorModel modelafter = new SimpleImageProcessorModel("CoffeeAfter.ppm");

    Image afterImage = modelafter.image;
    assertEquals(519, afterImage.height);
    assertEquals(255,afterImage.maxColorVal);
    assertEquals(519,afterImage.height);
    assertEquals(519, afterImage.pixelArray.length);
  }

  @Test
  public void testMethodsOnCoffee() {
    SimpleImageProcessorModel model = new SimpleImageProcessorModel("Coffee.ppm");
    Image blurredCoffee = model.blur();
    Image sepiaCoffee = model.applySepia();
    Image greyscaleCoffee = model.applyGreyscale();
    Image sharpCoffee = model.sharpen();

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

    model.exportImage(blurredTed, "blurredTed", ".ppm");
    model.exportImage(sharpTed, "sharpTed", ".ppm");
    model.exportImage(sepiaTed, "sepiaTed", ".ppm");
    model.exportImage(greyscaleTed, "greyscaleTed", ".ppm");
  }
}
