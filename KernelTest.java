import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class KernelTest {

  Kernel kernel;

  @Test
  public void testKernalArray() {
    List<Double> kValues = Arrays.asList(.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625);

    kernel = new Kernel(3,kValues);

    //row 1
    assertEquals(-1,kernel.kArray[0][0][0]);
    assertEquals(-1,kernel.kArray[0][0][1]);
    assertEquals(-1,kernel.kArray[0][1][0]);
    assertEquals(0,kernel.kArray[0][1][1]);
    assertEquals(-1,kernel.kArray[0][2][0]);
    assertEquals(1,kernel.kArray[0][2][1]);

    //row 2
    assertEquals(0,kernel.kArray[1][0][0]);
    assertEquals(-1,kernel.kArray[1][0][1]);
    assertEquals(0,kernel.kArray[1][1][0]);
    assertEquals(0,kernel.kArray[1][1][1]);
    assertEquals(0,kernel.kArray[1][2][0]);
    assertEquals(1,kernel.kArray[1][2][1]);

    //row 3
    assertEquals(1,kernel.kArray[2][0][0]);
    assertEquals(-1,kernel.kArray[2][0][1]);
    assertEquals(1,kernel.kArray[2][1][0]);
    assertEquals(0,kernel.kArray[2][1][1]);
    assertEquals(1,kernel.kArray[2][2][0]);
    assertEquals(1,kernel.kArray[2][2][1]);



    assertEquals(.0625,kernel.valuesArray[0][0],.2);
    assertEquals(.125,kernel.valuesArray[0][1],.2);
    assertEquals(.125,kernel.valuesArray[2][2],.2);
    assertEquals(.25,kernel.valuesArray[2][1],.2);


  }

  //add test for exception
  //invalid filter list size
  //invalid kernel size
}
