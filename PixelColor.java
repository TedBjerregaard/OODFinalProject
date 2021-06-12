import java.util.List;

public class PixelColor {

  int maxVal;
  int minVal;
  int red;
  int green;
  int blue;

  public PixelColor(int red, int green, int blue, int maxVal, int minVal) {
    this.maxVal = maxVal;
    this.minVal = minVal;
    this.red = red;
    this.green = green;
    this.blue = blue;

    if(this.red > maxVal) {
      this.red = maxVal;
    }
    if(this.green > maxVal) {
      this.green = maxVal;
    }
    if(this.blue > maxVal) {
      this.blue = maxVal;
    }
    if(this.red < 0) {
      this.red = 0;
    }
    if(this.green < 0) {
      this.green = 0;
    }
    if(this.blue < 0) {
      this.blue = 0;
    }
  }

  public double applyKernelRed(double kValue) {
    return this.red * kValue;
  }

  public double applyKernelGreen(double kValue) {
    return this.green * kValue;
  }

  public double applyKernelBlue(double kValue) {
    return this.blue * kValue;
  }



  public PixelColor applyKernal(float kValue) {
    return new PixelColor(Math.round(this.red * kValue), Math.round(this.green * kValue),
        Math.round(this.blue * kValue), this.maxVal, this.minVal);
  }

  public int getTransformed(List<Double> red) {

    return (int) (red.get(0) * this.red + red.get(1) * this.green + red.get(2) *this.blue);
  }
}
