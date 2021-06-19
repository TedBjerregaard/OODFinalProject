package view;

import java.io.IOException;

public class ImageProcessorView implements IPView {
  Appendable ap;

  public ImageProcessorView(Appendable ap) {
    this.ap = ap;
  }


  @Override
  public void renderMessage(String message) throws IOException{
    try {
      this.ap.append(message);
    }

    catch (IllegalArgumentException e) {
      throw new IOException();
    }
  }

}
