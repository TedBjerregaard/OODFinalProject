import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import model.CTMatrix;
import model.Pixel;
import model.PixelColor;
import org.junit.Test;

/**
 * Test for model.Pixel.
 */
public class PixelTest {

  Pixel pixel;
  PixelColor color;

  @Test
  public void testPixel() {
    this.color = new PixelColor(10, 10, 10, 255, 0);
    this.pixel = new Pixel(200, 200, color);

    assertEquals(10, pixel.getColor().getBlue(), .1);
    assertEquals(10, pixel.getColor().getRed(), .1);
    assertEquals(10, pixel.getColor().getGreen(), .1);
  }

  @Test
  public void testTransformedColor() {
    this.color = new PixelColor(10, 10, 10, 255, 0);
    this.pixel = new Pixel(200, 200, color);
    List<Double> redlist = Arrays.asList(.2126, .7152, .0722);
    List<Double> greenlist = Arrays.asList(.2126, .7152, .0722);
    List<Double> bluelist = Arrays.asList(.2126, .7152, .0722);
    CTMatrix matrix = new CTMatrix(redlist, greenlist, bluelist);
    PixelColor newPix = this.pixel.getTransformedColor(matrix);

    assertEquals(9, newPix.getBlue(), .1);
    assertEquals(9, newPix.getRed(), .1);
    assertEquals(9, newPix.getGreen(), .1);
  }

}

