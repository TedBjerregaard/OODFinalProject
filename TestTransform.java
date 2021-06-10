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

    double before = this.image.pixelArray[200][200].color.red;
    Image newImage = this.image.transformColor(matrix);
    double after = newImage.pixelArray[200][200].color.red;

    assertEquals(after,before, .1);

  }
}
