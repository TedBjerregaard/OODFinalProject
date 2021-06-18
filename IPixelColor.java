package model;

import java.util.List;

public interface IPixelColor {

  double applyKernelRed(double kValue);

  double applyKernelGreen(double kValue);

  double applyKernelBlue(double kValue);

  PixelColor applyKernel(float kValue);

  int getTransformed(List<Double> red);

  int getMaxVal();

  int getMinVal();

  int getRed();

  int getGreen();

  int getBlue();

}
