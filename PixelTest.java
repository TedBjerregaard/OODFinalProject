import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Test for Pixel.
 */
public class PixelTest {

  Pixel pixel;
  PixelColor color;

  @Test
  public void testPixel() {
    this.color = new PixelColor(10, 10, 10, 255, 0);
    this.pixel = new Pixel(200,200,color);


    assertEquals(10, pixel.color.blue, .1);
    assertEquals(10, pixel.color.red, .1);
    assertEquals(10, pixel.color.green, .1);
  }
  @Test
  public void testTransformedColor() {
    this.color = new PixelColor(10, 10, 10, 255, 0);
    this.pixel = new Pixel(200,200,color);
    List<Double> redlist = Arrays.asList(.2126,.7152,.0722);
    List<Double> greenlist = Arrays.asList(.2126,.7152,.0722);
    List<Double> bluelist = Arrays.asList(.2126,.7152,.0722);
    CTMatrix matrix = new CTMatrix(redlist,greenlist,bluelist);
    PixelColor newPix = this.pixel.getTransformedColor(matrix);


    assertEquals(9, newPix.blue, .1);
    assertEquals(9, newPix.red, .1);
    assertEquals(9, newPix.green, .1);
  }

}
