
package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * View for an image processor. Returns output to user to signal what operations were done, or
 * what needs to be done.
 */
public interface IPView {

  /**
   * Displays the given message to the user.
   *
   * @param message      Message to be displayed.
   * @throws IOException Thrown if output fails.
   */
  void renderMessage(String message) throws IOException;

  void registerViewEventListener(IViewListener listener);

  void updateTopVisibleLayer(BufferedImage buff);
}