
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

  /**
   * Registers listeneres for action events in this view.
   *
   * @param listener An object that reacts to certain events.
   */
  void registerViewEventListener(IViewListener listener);

  /**
   * Updates the top visible layer, thus hte image being shown in a graphical user interface with
   * the given BufferedImage.
   *
   * @param buff BufferedImage of the topmost visible image in a multilayered image.
   */
  void updateTopVisibleLayer(BufferedImage buff);
}