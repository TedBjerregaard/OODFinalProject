import java.util.List;

public class PixelColor {

  int maxVal;
  int minVal;
  double red;
  double green;
  double blue;

  public PixelColor(double red, double green, double blue, int maxVal, int minVal) {
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
    if(this.red < minVal) {
      this.red = minVal;
    }
    if(this.green < minVal) {
      this.green = minVal;
    }
    if(this.blue < minVal) {
      this.blue = minVal;
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
    return new PixelColor(this.red * kValue, this.green * kValue,
        this.blue *kValue, this.maxVal, this.minVal);
  }

  public double getTransformed(List<Double> red) {

    return  (red.get(0) * this.red + red.get(1) * this.green + red.get(2) *this.blue);
  }
}
