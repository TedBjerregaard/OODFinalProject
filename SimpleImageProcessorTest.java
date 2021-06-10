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

    Image newImage = model.createImage(500,500);


    assertEquals (500, newImage.height);
    assertEquals (255,newImage.maxColorVal );
    assertEquals (500,newImage.height);
    assertEquals (117, newImage.pixelArray.length);
  }
}
