import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import model.CTMatrix;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for model.CTMatrix.
 */
public class CTMatrixTest {

  CTMatrix matrix;

  @Before
  public void setup() {
    List<Double> redList = Arrays.asList(.393, .769, .189);
    List<Double> greenList = Arrays.asList(.349, .686, .168);
    List<Double> blueList = Arrays.asList(.272, .534, .131);
    this.matrix = new CTMatrix(redList, greenList, blueList);
  }

  @Test
  public void TestFields() {
    double blue1 = matrix.getBlueMatrix().get(0);
    double blue2 = matrix.getBlueMatrix().get(1);
    double blue3 = matrix.getBlueMatrix().get(2);

    double green1 = matrix.getGreenMatrix().get(0);
    double green2 = matrix.getGreenMatrix().get(1);
    double green3 = matrix.getGreenMatrix().get(2);

    double red1 = matrix.getRedMatrix().get(0);
    double red2 = matrix.getRedMatrix().get(1);
    double red3 = matrix.getRedMatrix().get(2);

    assertEquals(0.272, blue1, .1);
    assertEquals(.534, blue2, .1);
    assertEquals(.131, blue3, .1);

    assertEquals(.349, green1, .1);
    assertEquals(.686, green2, .1);
    assertEquals(.168, green3, .1);

    assertEquals(.393, red1, .1);
    assertEquals(.769, red2, .1);
    assertEquals(.189, red3, .1);


  }

  @Test
      (expected = IllegalArgumentException.class)
  public void TooManyItemsInList() {
    List<Double> redList = Arrays.asList(.393, .769, .189, .99);
    List<Double> greenList = Arrays.asList(.349, .686, .168);
    List<Double> blueList = Arrays.asList(.272, .534, .131);
    this.matrix = new CTMatrix(redList, greenList, blueList);
  }

  @Test
      (expected = IllegalArgumentException.class)
  public void TooManyItemsInList2() {
    List<Double> redList = Arrays.asList(.393, .769, .189);
    List<Double> greenList = Arrays.asList(.349, .686, .168, .99);
    List<Double> blueList = Arrays.asList(.272, .534, .131);
    this.matrix = new CTMatrix(redList, greenList, blueList);
  }

  @Test
      (expected = IllegalArgumentException.class)
  public void TooManyItemsInList3() {
    List<Double> redList = Arrays.asList(.393, .769, .189);
    List<Double> greenList = Arrays.asList(.349, .686, .168);
    List<Double> blueList = Arrays.asList(.272, .534, .131, .99);
    this.matrix = new CTMatrix(redList, greenList, blueList);
  }

  @Test
      (expected = IllegalArgumentException.class)
  public void TooFewItemsInList() {
    List<Double> redList = Arrays.asList(.393, .769);
    List<Double> greenList = Arrays.asList(.349, .686, .168);
    List<Double> blueList = Arrays.asList(.272, .534, .131);
    this.matrix = new CTMatrix(redList, greenList, blueList);
  }

  @Test
      (expected = IllegalArgumentException.class)
  public void TooFewItemsInList2() {
    List<Double> redList = Arrays.asList(.393, .769, .189);
    List<Double> greenList = Arrays.asList(.349, .686);
    List<Double> blueList = Arrays.asList(.272, .534, .131);
    this.matrix = new CTMatrix(redList, greenList, blueList);
  }

  @Test
      (expected = IllegalArgumentException.class)
  public void TooFewItemsInList3() {
    List<Double> redList = Arrays.asList(.393, .769, .189);
    List<Double> greenList = Arrays.asList(.349, .686, .168);
    List<Double> blueList = Arrays.asList(.272, .534);
    this.matrix = new CTMatrix(redList, greenList, blueList);
  }

}
