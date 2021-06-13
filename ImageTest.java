import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Image.
 */
public class ImageTest {

  SimpleImageProcessorModel model;
  Image image;

  @Before
  public void setup() {

    this.model = new SimpleImageProcessorModel();


  }

  @Test
  public void testCreateImage() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    assertEquals(576,image.height);
    assertEquals(436,image.width);
    assertEquals(255,image.maxColorVal);

  }

  @Test
  public void testImage() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    Pixel pix = image.pixelArray[10][20];
    assertEquals(576,image.height);
    assertEquals(436,image.width);
    assertEquals(255,image.maxColorVal);
    assertEquals(0,image.minColorVal);
    assertEquals(10,pix.x);
    assertEquals(20,pix.y);
  }



  // TODO: remove
  @Test
  public void testGetPixel() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    Pixel pix = image.pixelArray[300][300];
    Pixel pix2 = image.pixelArray[0][0];
    Pixel pix3 = image.pixelArray[300][0];


    assertEquals(300, pix.x);
    assertEquals(300, pix.y);
    assertEquals(0, pix2.x);
    assertEquals(0, pix2.y);
    assertEquals(300, pix3.x);
    assertEquals(0, pix3.y);
  }

  @Test
  public void testGetPixelinfo() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    Pixel pix = image.pixelArray[300][300];
    Pixel pix2 = image.pixelArray[0][0];

    assertEquals(140, pix.color.green, .1);
    assertEquals(127, pix.color.blue , .1);
    assertEquals(216, pix.color.red , .1);

    assertEquals(0, pix2.x);
    assertEquals(0, pix2.y);

  }

  @Test
  public void testNewColor() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel(3,kValues);

    double redBefore = image.pixelArray[50][50].color.red;
    double greenBefore = image.pixelArray[50][50].color.green;
    double blueBefore = image.pixelArray[50][50].color.blue;

    PixelColor c = image.getNewColor(image.pixelArray[50][50], k);

    double redAfter = c.red;
    double greenAfter = c.green;
    double BlueAfter = c.blue;
    assertEquals(241, redAfter,.1);
    assertEquals(246, redBefore,.1);
    assertEquals(234, greenBefore,.1);
    assertEquals(230, greenAfter,.1);
    assertEquals(206, blueBefore,.1);
    assertEquals(199, BlueAfter,.1);


  }

  @Test
  public void testtransform() {

    this.model.importImage("Ted.ppm");
    this.image = model.image;


    List<Double> redlist = Arrays.asList(.2126,.7152,.0722);
    List<Double> greenlist = Arrays.asList(.2126,.7152,.0722);
    List<Double> bluelist = Arrays.asList(.2126,.7152,.0722);
    CTMatrix matrix = new CTMatrix(redlist,greenlist,bluelist);

    double redBefore = this.image.pixelArray[200][200].color.red;
    double greenBefore = this.image.pixelArray[200][200].color.green;
    double blueBefore = this.image.pixelArray[200][200].color.blue;
    assertEquals(110,redBefore, .1);
    assertEquals(69,greenBefore, .1);
    assertEquals(60,blueBefore, .1);

    Image newImage = this.image.transformColor(matrix);

    double redAfter = newImage.pixelArray[200][200].color.red;
    double greenAfter = newImage.pixelArray[200][200].color.green;
    double blueAfter = newImage.pixelArray[200][200].color.blue;
    assertEquals(77,redAfter, .1);
    assertEquals(77,greenAfter, .1);
    assertEquals(77,blueAfter, .1);


  }

    @Test
  public void testfilter() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel (3,kValues);




    //top row left
    double redBefore1 = image.pixelArray[49][49].color.red;
    double greenBefore1 = image.pixelArray[49][49].color.green;
    double blueBefore1 = image.pixelArray[49][49].color.blue;
    assertEquals(246, redBefore1,.1);
    assertEquals(206, blueBefore1,.1);
    assertEquals(234, greenBefore1,.1);
    //top row middle
    double redBefore2 = image.pixelArray[49][50].color.red;
    double greenBefore2 = image.pixelArray[49][50].color.green;
    double blueBefore2 = image.pixelArray[49][50].color.blue;
    assertEquals(246, redBefore2,.1);
    assertEquals(206, blueBefore2,.1);
    assertEquals(234, greenBefore2,.1);
    //top row right
    double redBefore3 = image.pixelArray[49][51].color.red;
    double greenBefore3 = image.pixelArray[49][51].color.green;
    double blueBefore3 = image.pixelArray[49][51].color.blue;
    assertEquals(246, redBefore3,.1);
    assertEquals(206, blueBefore3,.1);
    assertEquals(234, greenBefore3,.1);

    //middle row left
    double redBefore11 = image.pixelArray[50][49].color.red;
    double greenBefore11 = image.pixelArray[50][49].color.green;
    double blueBefore11 = image.pixelArray[50][49].color.blue;
    assertEquals(246, redBefore11,.1);
    assertEquals(206, blueBefore11,.1);
    assertEquals(234, greenBefore11,.1);
    //middle row middle
    double redBefore12 = image.pixelArray[50][50].color.red;
    double greenBefore12 = image.pixelArray[50][50].color.green;
    double blueBefore12 = image.pixelArray[50][50].color.blue;
    assertEquals(246, redBefore12,.1);
    assertEquals(206, blueBefore12,.1);
    assertEquals(234, greenBefore12,.1);
    //middle row right
    double redBefore13 = image.pixelArray[50][51].color.red;
    double greenBefore13 = image.pixelArray[50][51].color.green;
    double blueBefore13 = image.pixelArray[50][51].color.blue;
    assertEquals(246, redBefore13,.1);
    assertEquals(206, blueBefore13,.1);
    assertEquals(234, greenBefore13,.1);

    //bottom row left
    double redBefore21 = image.pixelArray[51][49].color.red;
    double greenBefore21 = image.pixelArray[51][49].color.green;
    double blueBefore21 = image.pixelArray[51][49].color.blue;
    assertEquals(246, redBefore21,.1);
    assertEquals(206, blueBefore21,.1);
    assertEquals(234, greenBefore21,.1);
    //bottom row middle
    double redBefore22 = image.pixelArray[51][50].color.red;
    double greenBefore22 = image.pixelArray[51][50].color.green;
    double blueBefore22 = image.pixelArray[51][50].color.blue;
    assertEquals(246, redBefore22,.1);
    assertEquals(206, blueBefore22,.1);
    assertEquals(234, greenBefore22,.1);
    //bottom row right
    double redBefore23 = image.pixelArray[51][51].color.red;
    double greenBefore23 = image.pixelArray[51][51].color.green;
    double blueBefore23 = image.pixelArray[51][51].color.blue;
    assertEquals(246, redBefore23,.1);
    assertEquals(206, blueBefore23,.1);
    assertEquals(234, greenBefore23,.1);


    Image newImage = this.image.filter(k);

    double redAfter1 = newImage.pixelArray[50][50].color.red;
    double greenAfter1 = newImage.pixelArray[50][50].color.green;
    double blueAfter1 = newImage.pixelArray[50][50].color.blue;
    assertEquals(241, redAfter1,.1);
    assertEquals(230, greenAfter1,.1);
    assertEquals(199, blueAfter1,.1);




  }


}
