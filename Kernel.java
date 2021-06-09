import java.nio.channels.FileLock;
import java.util.List;

public class Kernel {

  int size;
  List<Double> values;
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

      int currentY = buffer;
      while (currentY >= -buffer) {
        for (int i = 0; i < size; i ++) { //i represents the rows
          int currentX = -buffer;
          while (currentX <= buffer) {
            for (int j = 0; j < size; j++) { //j represents the columns

              kArray[i][j] = new int[] {currentX, currentY};
              currentX += 1;
            }
          }
          currentY += -1;


        }
      }
    }




  }



}
