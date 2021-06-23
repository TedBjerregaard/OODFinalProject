import controller.IPController;
import controller.ImageProcessorController;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.ComplexImageProcessorModel;
import model.SimpleImageProcessorModel;
import view.IPView;
import view.SwingFrame;

/**
 * A main class for this image processor used to run the controller with custom inputs.
 */
public class MainClass {

  public static void main(String []args) {
    SwingFrame.setDefaultLookAndFeelDecorated(false);




    try {
      // Set cross-platform Java L&F (also called "Metal")
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

    } catch (UnsupportedLookAndFeelException e) {
      // handle exception
    } catch (ClassNotFoundException e) {
      // handle exception
    } catch (InstantiationException e) {
      // handle exception
    } catch (IllegalAccessException e) {
      // handle exception
    } catch (Exception e) {
    }

    SimpleImageProcessorModel simpleModel = new SimpleImageProcessorModel();
    ComplexImageProcessorModel ComplexModel = new ComplexImageProcessorModel(simpleModel);
    SwingFrame view = new SwingFrame();
    view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    view.setVisible(true);
    IPController controller = new ImageProcessorController(ComplexModel, System.in,
        System.out, view);
    controller.runIP();
  }
}
