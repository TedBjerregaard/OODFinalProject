import java.nio.channels.FileLock;
import java.util.List;

public class Kernel {

  int size;
  double[][] valuesArray;
  int[][][] kArray;

  public Kernel(int size, List values) {

    if (size % 2 == 0 || size * size != values.size()) {
      throw new IllegalArgumentException ("invalid Kernel Size");
    }
    else {
      this.size = size;
      this.valuesArray = new double[size][size];//values;
      this.kArray = new int[size][size][2];


      int currentVal = 0;
      while (currentVal < values.size()) {
        for (int i = 0; i < size; i ++) {
          for (int j = 0; j < size; j++) {

            valuesArray[i][j] = (double) values.get(currentVal);
            currentVal += 1;
          }
        }
      }

      int buffer = size / 2;

      for (int row = -buffer; row <= buffer; row++) {
        for (int col = -buffer; col <= buffer; col++) {
          int i = row + buffer;
          int j = col + buffer;
          kArray[i][j] = new int[] {row, col};
        }
      }
    }
  }

}
