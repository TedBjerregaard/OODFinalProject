public class Pixel {

  int x;
  int y;
  PixelColor color;

  public Pixel(int x, int y, PixelColor color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }


  public PixelColor getTransformedColor(CTMatrix matrix) {
    int newRed = this.color.getTransformed(matrix.red);
    int newGreen = this.color.getTransformed(matrix.green);
    int newBlue = this.color.getTransformed(matrix.blue);
    return new PixelColor(newRed, newGreen, newBlue, this.color.maxVal, 0);
  }
}
