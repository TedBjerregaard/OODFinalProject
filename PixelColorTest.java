import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Test for PixelColor.
 */
public class PixelColorTest {


  PixelColor color;

  @Test
  public void testColor() {

    this.color = new PixelColor(10, 10, 10, 255, 0);

    assertEquals(10, color.getBlue(), .1);
    assertEquals(10, color.getRed(), .1);
    assertEquals(10, color.getGreen(), .1);
    assertEquals(255, color.getMaxVal());
    assertEquals(0, color.getMinVal());

  }

  @Test
  public void testColorAboveMax() {

    this.color = new PixelColor(300, 300, 300, 255, 0);

    assertEquals(255, color.getBlue(), .1);
    assertEquals(255, color.getRed(), .1);
    assertEquals(255, color.getGreen(), .1);
  }

  @Test
  public void testColorBelowMin() {

    this.color = new PixelColor(-300, -300, -300, 255, 0);

    assertEquals(0, color.getBlue(), .1);
    assertEquals(0, color.getRed(), .1);
    assertEquals(0, color.getGreen(), .1);
  }

  @Test
  public void testApplyKernelToRGB() {

    this.color = new PixelColor(10, 10, 10, 255, 0);

    double resultBlue = this.color.applyKernelBlue(10);
    double resultGreen = this.color.applyKernelGreen(10);
    double resultRed = this.color.applyKernelRed(10);
    assertEquals(100, resultBlue, .1);
    assertEquals(100, resultGreen, .1);
    assertEquals(100, resultRed, .1);
  }

  @Test
  public void testGetTransformed() {
    List<Double> redlist = Arrays.asList(.2126, .7152, .0722);

    this.color = new PixelColor(10, 10, 10, 255, 0);

    int result = this.color.getTransformed(redlist);

    assertEquals(9, result, .1);

  }
}
