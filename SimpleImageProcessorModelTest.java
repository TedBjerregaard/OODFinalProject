import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SimpleImageProcessorModelTest {

  SimpleImageProcessorModel model;

  @Before
  public void setup() {
    this.model = new SimpleImageProcessorModel ();
  }

  @Test
  public void testCreateImage(){

    Image newImage = model.createImage(2,2, 255);
    System.out.println(newImage.pixelArray[3][3].color.red);

    PixelColor firstSquareType = new PixelColor(255,0,0,255,0);

    assertEquals (4, newImage.height);
    assertEquals (255,newImage.maxColorVal );
    assertEquals (4,newImage.width);
    assertEquals (4, newImage.pixelArray.length);
    assertEquals (firstSquareType.red,
        newImage.pixelArray[0][0].color.red);
    assertEquals (firstSquareType.green,
        newImage.pixelArray[0][0].color.green);
    assertEquals (firstSquareType.blue,
        newImage.pixelArray[0][0].color.blue);
  }

  @Test
  public void testExport() {

    SimpleImageProcessorModel model = new SimpleImageProcessorModel("Koala.ppm");
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

    model.exportImage(blurred,"KoalaAfter", ".ppm");
    model.exportImage(blurred5,"KoalaBlurred", ".ppm");


    SimpleImageProcessorModel modelafter = new SimpleImageProcessorModel("KoalaAfter.ppm");

    Image afterImage = modelafter.image;
    assertEquals (768, afterImage.height);
    assertEquals (255,afterImage.maxColorVal);
    assertEquals (1024,afterImage.width);
    assertEquals (768, afterImage.pixelArray.length);
  }

  @Test
  public void testImportOfExport(){


    SimpleImageProcessorModel modelafter = new SimpleImageProcessorModel("KoalaAfter.ppm");

    Image afterImage = modelafter.image;
    assertEquals (500, afterImage.height);
    assertEquals (255,afterImage.maxColorVal);
    assertEquals (500,afterImage.height);
    assertEquals (500, afterImage.pixelArray.length);
  }
}
