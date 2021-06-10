import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
}
