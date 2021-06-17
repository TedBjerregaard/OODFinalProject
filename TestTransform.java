import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import model.CTMatrix;
import model.IPModel;
import model.Image;
import model.SimpleImageProcessorModel;
import org.junit.Before;
import org.junit.Test;

public class TestTransform {
  SimpleImageProcessorModel model = new SimpleImageProcessorModel();



  @Before

  public void setup(){
    model.importImage("Koala.ppm");
  }

  @Test
  public void testTransform() {
    List<Double> redlist = Arrays.asList(.2126,.7152,.0722);
    List<Double> greenlist = Arrays.asList(.2126,.7152,.0722);
    List<Double> bluelist = Arrays.asList(.2126,.7152,.0722);
    CTMatrix matrix = new CTMatrix(redlist,greenlist,bluelist);

    double redBefore = model.image.pixelArray[200][200].getColor().getRed();
    double greenBefore = model.image.pixelArray[200][200].getColor().getGreen();
    double blueBefore = model.image.pixelArray[200][200].getColor().getBlue();
    assertEquals(155,redBefore, .1);
    assertEquals(142,greenBefore, .1);
    assertEquals(136,blueBefore, .1);

    Image newImage = model.image.transformColor(matrix);

    double redAfter = newImage.pixelArray[200][200].getColor().getRed();
    double greenAfter = newImage.pixelArray[200][200].getColor().getGreen();
    double blueAfter = newImage.pixelArray[200][200].getColor().getBlue();
    assertEquals(144.0,redAfter, .1);
    assertEquals(144.0,greenAfter, .1);
    assertEquals(144.0,blueAfter, .1);


  }
}
