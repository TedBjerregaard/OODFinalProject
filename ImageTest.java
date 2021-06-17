import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import model.CTMatrix;
import model.Image;
import model.Kernel;
import model.Pixel;
import model.PixelColor;
import model.SimpleImageProcessorModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for model.Image.
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
    assertEquals(576, image.getHeight());
    assertEquals(436, image.getWidth());
    assertEquals(255, image.getMaxColorVal());

  }

  @Test
  public void testImage() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    Pixel pix = image.pixelArray[10][20];
    assertEquals(576, image.getHeight());
    assertEquals(436, image.getWidth());
    assertEquals(255, image.getMaxColorVal());
    assertEquals(0, image.getMinColorVal());
    assertEquals(10, pix.getX());
    assertEquals(20, pix.getY());
  }


  // TODO: remove
  @Test
  public void testGetPixel() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    Pixel pix = image.pixelArray[300][300];
    Pixel pix2 = image.pixelArray[0][0];
    Pixel pix3 = image.pixelArray[300][0];

    assertEquals(300, pix.getX());
    assertEquals(300, pix.getY());
    assertEquals(0, pix2.getX());
    assertEquals(0, pix2.getY());
    assertEquals(300, pix3.getX());
    assertEquals(0, pix3.getY());
  }

  @Test
  public void testGetPixelinfo() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    Pixel pix = image.pixelArray[300][300];
    Pixel pix2 = image.pixelArray[0][0];

    assertEquals(140, pix.getColor().getGreen(), .1);
    assertEquals(127, pix.getColor().getBlue(), .1);
    assertEquals(216, pix.getColor().getRed(), .1);

    assertEquals(0, pix2.getX());
    assertEquals(0, pix2.getY());

  }

  @Test
  public void testNewColor() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel(3, kValues);

    double redBefore = image.pixelArray[50][50].getColor().getRed();
    double greenBefore = image.pixelArray[50][50].getColor().getGreen();
    double blueBefore = image.pixelArray[50][50].getColor().getBlue();

    PixelColor c = image.getNewColor(image.pixelArray[50][50], k);

    double redAfter = c.getRed();
    double greenAfter = c.getGreen();
    double blueAfter = c.getBlue();
    assertEquals(241, redAfter, .1);
    assertEquals(246, redBefore, .1);
    assertEquals(234, greenBefore, .1);
    assertEquals(230, greenAfter, .1);
    assertEquals(206, blueBefore, .1);
    assertEquals(199, blueAfter, .1);


  }

  @Test
  public void testtransform() {

    this.model.importImage("Ted.ppm");
    this.image = model.image;

    List<Double> redlist = Arrays.asList(.2126, .7152, .0722);
    List<Double> greenlist = Arrays.asList(.2126, .7152, .0722);
    List<Double> bluelist = Arrays.asList(.2126, .7152, .0722);
    CTMatrix matrix = new CTMatrix(redlist, greenlist, bluelist);

    double redBefore = this.image.pixelArray[200][200].getColor().getRed();
    double greenBefore = this.image.pixelArray[200][200].getColor().getGreen();
    double blueBefore = this.image.pixelArray[200][200].getColor().getBlue();
    assertEquals(110, redBefore, .1);
    assertEquals(69, greenBefore, .1);
    assertEquals(60, blueBefore, .1);

    Image newImage = this.image.transformColor(matrix);

    double redAfter = newImage.pixelArray[200][200].getColor().getRed();
    double greenAfter = newImage.pixelArray[200][200].getColor().getGreen();
    double blueAfter = newImage.pixelArray[200][200].getColor().getBlue();
    assertEquals(77, redAfter, .1);
    assertEquals(77, greenAfter, .1);
    assertEquals(77, blueAfter, .1);


  }

  @Test
  public void testfilter() {
    this.model.importImage("Ted.ppm");
    this.image = model.image;
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);
    Kernel k = new Kernel(3, kValues);

    //top row left
    double redBefore1 = image.pixelArray[49][49].getColor().getRed();
    double greenBefore1 = image.pixelArray[49][49].getColor().getGreen();
    double blueBefore1 = image.pixelArray[49][49].getColor().getBlue();
    assertEquals(246, redBefore1, .1);
    assertEquals(206, blueBefore1, .1);
    assertEquals(234, greenBefore1, .1);
    //top row middle
    double redBefore2 = image.pixelArray[49][50].getColor().getRed();
    double greenBefore2 = image.pixelArray[49][50].getColor().getGreen();
    double blueBefore2 = image.pixelArray[49][50].getColor().getBlue();
    assertEquals(246, redBefore2, .1);
    assertEquals(206, blueBefore2, .1);
    assertEquals(234, greenBefore2, .1);
    //top row right
    double redBefore3 = image.pixelArray[49][51].getColor().getRed();
    double greenBefore3 = image.pixelArray[49][51].getColor().getGreen();
    double blueBefore3 = image.pixelArray[49][51].getColor().getBlue();
    assertEquals(246, redBefore3, .1);
    assertEquals(206, blueBefore3, .1);
    assertEquals(234, greenBefore3, .1);

    //middle row left
    double redBefore11 = image.pixelArray[50][49].getColor().getRed();
    double greenBefore11 = image.pixelArray[50][49].getColor().getGreen();
    double blueBefore11 = image.pixelArray[50][49].getColor().getBlue();
    assertEquals(246, redBefore11, .1);
    assertEquals(206, blueBefore11, .1);
    assertEquals(234, greenBefore11, .1);
    //middle row middle
    double redBefore12 = image.pixelArray[50][50].getColor().getRed();
    double greenBefore12 = image.pixelArray[50][50].getColor().getGreen();
    double blueBefore12 = image.pixelArray[50][50].getColor().getBlue();
    assertEquals(246, redBefore12, .1);
    assertEquals(206, blueBefore12, .1);
    assertEquals(234, greenBefore12, .1);
    //middle row right
    double redBefore13 = image.pixelArray[50][51].getColor().getRed();
    double greenBefore13 = image.pixelArray[50][51].getColor().getGreen();
    double blueBefore13 = image.pixelArray[50][51].getColor().getBlue();
    assertEquals(246, redBefore13, .1);
    assertEquals(206, blueBefore13, .1);
    assertEquals(234, greenBefore13, .1);

    //bottom row left
    double redBefore21 = image.pixelArray[51][49].getColor().getRed();
    double greenBefore21 = image.pixelArray[51][49].getColor().getGreen();
    double blueBefore21 = image.pixelArray[51][49].getColor().getBlue();
    assertEquals(246, redBefore21, .1);
    assertEquals(206, blueBefore21, .1);
    assertEquals(234, greenBefore21, .1);
    //bottom row middle
    double redBefore22 = image.pixelArray[51][50].getColor().getRed();
    double greenBefore22 = image.pixelArray[51][50].getColor().getGreen();
    double blueBefore22 = image.pixelArray[51][50].getColor().getBlue();
    assertEquals(246, redBefore22, .1);
    assertEquals(206, blueBefore22, .1);
    assertEquals(234, greenBefore22, .1);
    //bottom row right
    double redBefore23 = image.pixelArray[51][51].getColor().getRed();
    double greenBefore23 = image.pixelArray[51][51].getColor().getGreen();
    double blueBefore23 = image.pixelArray[51][51].getColor().getBlue();
    assertEquals(246, redBefore23, .1);
    assertEquals(206, blueBefore23, .1);
    assertEquals(234, greenBefore23, .1);

    Image newImage = this.image.filter(k);

    double redAfter1 = newImage.pixelArray[50][50].getColor().getRed();
    double greenAfter1 = newImage.pixelArray[50][50].getColor().getGreen();
    double blueAfter1 = newImage.pixelArray[50][50].getColor().getBlue();
    assertEquals(241, redAfter1, .1);
    assertEquals(230, greenAfter1, .1);
    assertEquals(199, blueAfter1, .1);

  }


}
