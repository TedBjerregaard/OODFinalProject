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

    Image newImage = model.createImage(50,5, 255);

    PixelColor firstSquareType = new PixelColor(255,0,0,255,0);

    System.out.println(newImage.pixelArray[0][0].color.red);
    System.out.println(newImage.pixelArray[0][0].color.blue);
    System.out.println(newImage.pixelArray[0][0].color.green);

    System.out.println(newImage.pixelArray[15][15].color.red);
    System.out.println(newImage.pixelArray[15][15].color.blue);
    System.out.println(newImage.pixelArray[15][15].color.green);

    assertEquals (250, newImage.height);
    assertEquals (255,newImage.maxColorVal );
    assertEquals (250,newImage.width);
    assertEquals (250, newImage.pixelArray.length);
    assertEquals (firstSquareType.red,
        newImage.pixelArray[0][0].color.red, 0.1);
    assertEquals (firstSquareType.green,
        newImage.pixelArray[0][0].color.green, 0.1);
    assertEquals (firstSquareType.blue,
        newImage.pixelArray[0][0].color.blue, 0.1);
  }
}
