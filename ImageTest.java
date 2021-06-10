import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ImageTest {


  Image image;


  @Before
  public void setup(){
    this.image = new Image("Koala.ppm");
  }

  @Test
  public void testCreateImage() {

    Image newImage = new Image("Koala.ppm");
    assertEquals(768,newImage.height);
    assertEquals(1024,newImage.width);
    assertEquals(255,newImage.maxColorVal);

  }

  @Test
  public void testImage() {

    Pixel pix = image.pixelArray[10][20];
    assertEquals(768,image.height);
    assertEquals(1024,image.width);
    assertEquals(255,image.maxColorVal);
    assertEquals(10,pix.x);
    assertEquals(20,pix.y);
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
    Pixel pix3 = image.pixelArray[300][0];


    assertEquals (300, pix.x);
    assertEquals (300, pix.y);
    assertEquals (0, pix2.x);
    assertEquals (0, pix2.y);
    assertEquals (300, pix3.x);
    assertEquals (0, pix3.y);
  }

  @Test
  public void testGetPixelinfo() {

    Pixel pix = image.pixelArray[300][300];
    Pixel pix2 = image.pixelArray[0][0];

    assertEquals (119, pix.color.green, .1);
    assertEquals (0, pix2.x);
    assertEquals (0, pix2.y);

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
  public void testtransform(){

    List<Double> kValues = Arrays.asList (.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel (3, kValues);

  }

    @Test
  public void testfilter() {

    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel (3,kValues);




    //top row left
    double redBefore1 = image.pixelArray[49][49].color.red;
    double greenBefore1 = image.pixelArray[49][49].color.green;
    double blueBefore1 = image.pixelArray[49][49].color.blue;
    assertEquals (117, redBefore1,.1);
    assertEquals (78, blueBefore1,.1);
    assertEquals (101, greenBefore1,.1);
    //top row middle
    double redBefore2 = image.pixelArray[49][50].color.red;
    double greenBefore2 = image.pixelArray[49][50].color.green;
    double blueBefore2 = image.pixelArray[49][50].color.blue;
    assertEquals (115, redBefore2,.1);
    assertEquals (74, blueBefore2,.1);
    assertEquals (99, greenBefore2,.1);
    //top row right
    double redBefore3 = image.pixelArray[49][51].color.red;
    double greenBefore3 = image.pixelArray[49][51].color.green;
    double blueBefore3 = image.pixelArray[49][51].color.blue;
    assertEquals (114, redBefore3,.1);
    assertEquals (71, blueBefore3,.1);
    assertEquals (100, greenBefore3,.1);

    //middle row left
    double redBefore11 = image.pixelArray[50][49].color.red;
    double greenBefore11 = image.pixelArray[50][49].color.green;
    double blueBefore11 = image.pixelArray[50][49].color.blue;
    assertEquals (117, redBefore11,.1);
    assertEquals (80, blueBefore11,.1);
    assertEquals (100, greenBefore11,.1);
    //middle row middle
    double redBefore12 = image.pixelArray[50][50].color.red;
    double greenBefore12 = image.pixelArray[50][50].color.green;
    double blueBefore12 = image.pixelArray[50][50].color.blue;
    assertEquals (117, redBefore12,.1);
    assertEquals (78, blueBefore12,.1);
    assertEquals (101, greenBefore12,.1);
    //middle row right
    double redBefore13 = image.pixelArray[50][51].color.red;
    double greenBefore13 = image.pixelArray[50][51].color.green;
    double blueBefore13 = image.pixelArray[50][51].color.blue;
    assertEquals (118, redBefore13,.1);
    assertEquals (77, blueBefore13,.1);
    assertEquals (104, greenBefore13,.1);

    //bottom row left
    double redBefore21 = image.pixelArray[51][49].color.red;
    double greenBefore21 = image.pixelArray[51][49].color.green;
    double blueBefore21 = image.pixelArray[51][49].color.blue;
    assertEquals (110, redBefore21,.1);
    assertEquals (74, blueBefore21,.1);
    assertEquals (98, greenBefore21,.1);
    //bottom row middle
    double redBefore22 = image.pixelArray[51][50].color.red;
    double greenBefore22 = image.pixelArray[51][50].color.green;
    double blueBefore22 = image.pixelArray[51][50].color.blue;
    assertEquals (115, redBefore22,.1);
    assertEquals (77, blueBefore22,.1);
    assertEquals (100, greenBefore22,.1);
    //bottom row right
    double redBefore23 = image.pixelArray[51][51].color.red;
    double greenBefore23 = image.pixelArray[51][51].color.green;
    double blueBefore23 = image.pixelArray[51][51].color.blue;
    assertEquals (121, redBefore23,.1);
    assertEquals (82, blueBefore23,.1);
    assertEquals (105, greenBefore23,.1);


    Image newImage = this.image.filter(k);

    double redAfter1 = newImage.pixelArray[50][50].color.red;
    double greenAfter1 = newImage.pixelArray[50][50].color.green;
    double blueAfter1 = newImage.pixelArray[50][50].color.blue;
    assertEquals (116.25, redAfter1,.1);
    assertEquals (100.875, greenAfter1,.1);
    assertEquals (77.0625, blueAfter1,.1);
    //top row left
    /*
    //top row middle
    double redAfter2 = newImage.pixelArray[49][50].color.red;
    double greenAfter2 = newImage.pixelArray[49][50].color.green;
    double blueAfter2 = newImage.pixelArray[49][50].color.blue;
    //top row right
    double redAfter3 = newImage.pixelArray[49][51].color.red;
    double greenAfter3 = newImage.pixelArray[49][51].color.green;
    double blueAfter3 = newImage.pixelArray[49][51].color.blue;

    //middle row left
    double redAfter11 = newImage.pixelArray[50][49].color.red;
    double greenAfter11 = newImage.pixelArray[50][49].color.green;
    double blueAfter11 = newImage.pixelArray[50][49].color.blue;
    //middle row middle
    double redAfter12 = newImage.pixelArray[50][50].color.red;
    double greenAfter12 = newImage.pixelArray[50][50].color.green;
    double blueAfter12 = newImage.pixelArray[50][50].color.blue;
    //middle row right
    double redAfter13 = newImage.pixelArray[50][51].color.red;
    double greenAfter13 = newImage.pixelArray[50][51].color.green;
    double blueAfter13 = newImage.pixelArray[50][51].color.blue;

    //bottom row left
    double redAfter21 = newImage.pixelArray[51][49].color.red;
    double greenAfter21 = newImage.pixelArray[51][49].color.green;
    double blueAfter21 = newImage.pixelArray[51][49].color.blue;
    //bottom row middle
    double redAfter22 = newImage.pixelArray[51][50].color.red;
    double greenAfter22 = newImage.pixelArray[51][50].color.green;
    double blueAfter22 = newImage.pixelArray[51][50].color.blue;
    //bottom row right
    double redAfter23 = newImage.pixelArray[51][51].color.red;
    double greenAfter23 = newImage.pixelArray[51][51].color.green;
    double blueAfter23 = newImage.pixelArray[51][51].color.blue;
*/



  }


}
