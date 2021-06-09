public class PixelColor {

  double red;
  double green;
  double blue;

  public PixelColor(double red, double green, double blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
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
    return new PixelColor(this.red * kValue, this.green * kValue, this.blue *kValue);
  }
}
