import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ImageTest {


  Image image = new Image("Koala.ppm");

  @Test
  public void testImage() {

    assertEquals(768,image.height);
    assertEquals(1024,image.width);
    assertEquals(255,image.maxColorVal);
    //assertEquals(786432,image.pixelArray.);
  }

  @Test
  public void testImage2() {

    Pixel[][] pixArray = this.image.pixelArray; // not populated currently.
    //int xBefore = this.image.pixels.get(0).x;
    //int yBefore = this.image.pixels.get(0).y;

    assertEquals(pixArray.length, image.height * image.width);

  }

  // TODO: remove
  @Test
  public void testGetPixel() {

    Pixel pix = image.pixelArray[300][300];
    Pixel pix2 = image.pixelArray[0][0];
    Pixel pix3 = image.pixelArray[0][-1];
    assertEquals (300, pix.x);
    assertEquals (300, pix.y);
    assertEquals (0, pix2.x);
    assertEquals (0, pix2.y);
    assertNull(pix3);
  }

  @Test
  public void testNewColor() {
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel (3,kValues);

    double redBefore = image.pixelArray[50][50].color.red;
    double greenBefore = image.pixelArray[50][50].color.green;
    double blueBefore = image.pixelArray[50][50].color.blue;

    PixelColor c = image.getNewColor(image.pixelArray[50][50], k);

    double redAfter = c.red;
    double greenAfter = c.green;
    double BlueAfter = c.blue;
    assertEquals (116.25, redAfter,.1);
    assertEquals (117, redBefore,.1);
    assertEquals (101, greenBefore,.1);
    assertEquals (100.875, greenAfter,.1);
    assertEquals (78, blueBefore,.1);
    assertEquals (77.0625, BlueAfter,.1);


  }

  @Test
  public void testfilter() {

    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel (3,kValues);
    double redBefore = image.pixelArray[50][50].color.red;
    double greenBefore = image.pixelArray[50][50].color.green;
    double blueBefore = image.pixelArray[50][50].color.blue;


    Image newImage = this.image.filter(k);

    double redAfter = newImage.pixelArray[50][50].color.red;
    double greenAfter = newImage.pixelArray[50][50].color.green;
    double BlueAfter = newImage.pixelArray[50][50].color.blue;


    assertEquals (116.25, redAfter,.1);
    assertEquals (117, redBefore,.1);
    assertEquals (101, greenBefore,.1);
    assertEquals (100.875, greenAfter,.1);
    assertEquals (78, blueBefore,.1);
    assertEquals (77.0625, BlueAfter,.1);

  }


}
