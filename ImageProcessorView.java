
package view;

import java.io.IOException;

/**
 *  An implementation for the view for a view for an image processor. This view is simple and just
 *  relays information about operations conducted to the user.
 */
public class ImageProcessorView implements IPView {
  Appendable ap;

  /**
   * Constructor for the ImageProcessorView class.
   *
   * @param ap Appendable used to output information to user.
   */
  public ImageProcessorView(Appendable ap) {
    this.ap = ap;
  }


  @Override
  public void renderMessage(String message) throws IOException {
    try {
      this.ap.append(message);
    }

    catch (IllegalArgumentException e) {
      throw new IOException();
    }
  }

  @Override
  public void registerViewEventListener(IViewListener listener) {

  }

}
