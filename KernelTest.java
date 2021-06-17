import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;


/**
 * Testing class for the Kernel class.
 */
public class KernelTest {

  Kernel kernel;

  @Test
  public void testKernalArray() {
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);

    kernel = new Kernel(3, kValues);

    //row 1
    assertEquals(-1, kernel.getK(0, 0, 0));
    assertEquals(-1, kernel.getK(0, 0, 1));
    assertEquals(-1, kernel.getK(0, 1, 0));
    assertEquals(0, kernel.getK(0, 1, 1));
    assertEquals(-1, kernel.getK(0, 2, 0));
    assertEquals(1, kernel.getK(0, 2, 1));

    //row 2
    assertEquals(0, kernel.getK(1, 0, 0));
    assertEquals(-1, kernel.getK(1, 0, 1));
    assertEquals(0, kernel.getK(1, 1, 0));
    assertEquals(0, kernel.getK(1, 1, 1));
    assertEquals(0, kernel.getK(1, 2, 0));
    assertEquals(1, kernel.getK(1, 2, 1));

    //row 3
    assertEquals(1, kernel.getK(2, 0, 0));
    assertEquals(-1, kernel.getK(2, 0, 1));
    assertEquals(1, kernel.getK(2, 1, 0));
    assertEquals(0, kernel.getK(2, 1, 1));
    assertEquals(1, kernel.getK(2, 2, 0));
    assertEquals(1, kernel.getK(2, 2, 1));

    assertEquals(.0625, kernel.getValue(0, 0), .2);
    assertEquals(.125, kernel.getValue(0, 1), .2);
    assertEquals(.125, kernel.getValue(2, 2), .2);
    assertEquals(.25, kernel.getValue(2, 1), .2);


  }


  @Test
      (expected = IllegalArgumentException.class)
  public void testInvalidSize() {
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);

    kernel = new Kernel(6, kValues);
  }

  @Test
      (expected = IllegalArgumentException.class)
  public void testInvalidListSize() {
    List<Double> kValues = Arrays.asList(566.33,.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);

    kernel = new Kernel(3, kValues);

  }
  //add test for exception
  //invalid filter list size
  //invalid kernel size
}
