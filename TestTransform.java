import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TestTransform {
  Image image;


  @Before
  public void setup(){
    this.image = new Image("Koala.ppm");
  }

  @Test
  public void testTransform() {
    List<Double> redlist = Arrays.asList(.2126,.7152,.0722);
    List<Double> greenlist = Arrays.asList(.2126,.7152,.0722);
    List<Double> bluelist = Arrays.asList(.2126,.7152,.0722);
    CTMatrix matrix = new CTMatrix(redlist,greenlist,bluelist);

    double redBefore = this.image.pixelArray[200][200].color.red;
    double greenBefore = this.image.pixelArray[200][200].color.green;
    double blueBefore = this.image.pixelArray[200][200].color.blue;
    assertEquals(155,redBefore, .1);
    assertEquals(142,greenBefore, .1);
    assertEquals(136,blueBefore, .1);

    Image newImage = this.image.transformColor(matrix);

    double redAfter = newImage.pixelArray[200][200].color.red;
    double greenAfter = newImage.pixelArray[200][200].color.green;
    double blueAfter = newImage.pixelArray[200][200].color.blue;
    assertEquals(144.3306,redAfter, .1);
    assertEquals(144.3306,greenAfter, .1);
    assertEquals(144.3306,blueAfter, .1);


  }
}
