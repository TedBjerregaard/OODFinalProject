import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
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

    Image newImage = model.createImage(500,500,255);


    assertEquals (500, newImage.height);
    assertEquals (255,newImage.maxColorVal);
    assertEquals (500,newImage.height);
    assertEquals (500, newImage.pixelArray.length);
  }

  @Test
  public void testExport(){

    SimpleImageProcessorModel model = new SimpleImageProcessorModel("Koala.ppm");

    model.exportImage("KoalaAfter", ".ppm");


    SimpleImageProcessorModel modelafter = new SimpleImageProcessorModel("KoalaAfter.ppm");

    Image afterImage = modelafter.image;
    assertEquals (500, afterImage.height);
    assertEquals (255,afterImage.maxColorVal);
    assertEquals (500,afterImage.height);
    assertEquals (500, afterImage.pixelArray.length);


  }
}
