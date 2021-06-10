import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PixelColorTest {


  PixelColor color;

  @Test
  public void testImage2() {

    this.color = new PixelColor(10, 10, 10, 255, 0);

    assertEquals(10, color.blue, .1);
    assertEquals(10, color.red, .1);
    assertEquals(10, color.green, .1);
  }
}
