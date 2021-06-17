import static org.junit.Assert.assertEquals;

import model.Image;
import model.PixelColor;
import model.SimpleImageProcessorModel;
import org.junit.Before;
import org.junit.Test;

public class SimpleImageProcessorTest {

  SimpleImageProcessorModel model;

  @Before
  public void setup() {
    this.model = new SimpleImageProcessorModel ();
  }

  @Test
  public void testCreateImage(){

    Image newImage = model.createImage(500,500,
        new PixelColor(256, 0, 0, 256, 0),
        new PixelColor(0, 0, 256, 256, 0), 255);


    assertEquals (500, newImage.getHeight());
    assertEquals (255,newImage.getMaxColorVal());
    assertEquals (500,newImage.getHeight());
    assertEquals (500, newImage.pixelArray.length);
  }

  @Test
  public void testExport(){

    SimpleImageProcessorModel model = new SimpleImageProcessorModel("Koala.ppm");

    model.exportImage(model.image, "KoalaAfter", ".ppm");


    SimpleImageProcessorModel modelafter = new SimpleImageProcessorModel("KoalaAfter.ppm");

    Image afterImage = modelafter.image;
    assertEquals (500, afterImage.getHeight());
    assertEquals (255,afterImage.getMaxColorVal());
    assertEquals (500,afterImage.getHeight());
    assertEquals (500, afterImage.pixelArray.length);


  }
}
