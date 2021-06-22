package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SwingFrame extends JFrame implements ActionListener, ItemListener,
    ListSelectionListener {
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JCheckBoxMenuItem layers;

  public SwingFrame() {
    super();
    setTitle("Image Processor");
    setSize(400, 400);

    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Import/Save/Current"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    //file open
    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileOpenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("open file");
    fileOpenButton.addActionListener(this);
    fileOpenPanel.add(fileOpenButton);

    //file save
    JPanel fileSavePanel = new JPanel();
    fileSavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileSavePanel);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("save file");
    fileSaveButton.addActionListener(this);
    fileSavePanel.add(fileSaveButton);

    //create layer
    JPanel createLayerPanel = new JPanel();
    createLayerPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(createLayerPanel);
    JButton createLayerButton = new JButton("Create a Layer");
    createLayerButton.setActionCommand("create layer");
    createLayerButton.addActionListener(this);
    createLayerPanel.add(createLayerButton);

    //set current layer
    JComboBox layers = new JComboBox();

    //Filter Transformations
    JPanel filterPanel = new JPanel();
    filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));
    filterPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filterPanel);

    JButton sepia = new JButton("Sepia");
    sepia.setActionCommand("sepia");
    sepia.addActionListener(this);
    filterPanel.add(sepia);

    JButton greyscale = new JButton("Greyscale");
    greyscale.setActionCommand("greyscale");
    greyscale.addActionListener(this);
    filterPanel.add(greyscale);

    JButton blur = new JButton("Blur");
    blur.setActionCommand("blur");
    blur.addActionListener(this);
    filterPanel.add(blur);

    JButton sharpen = new JButton("Sharpen");
    sharpen.setActionCommand("sharpen");
    sharpen.addActionListener(this);
    filterPanel.add(sharpen);
    mainPanel.add(filterPanel);


    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
/*

    BufferedImage image = ImageIO.read(new File(String.valueOf(this.fileOpenDisplay)));
    JLabel label = new JLabel(new ImageIcon(image));
    imagePanel.add(label);
*/

    //imagePanel.setMaximumSize(null);
    mainPanel.add(imagePanel);

    String image = "Penguins.jpg";
    JLabel imageLabel;
    imageLabel = new JLabel();
    imageLabel.setIcon(new ImageIcon(image));


  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {

    switch (e.getActionCommand()) {

      case "open file":
        final JFileChooser openChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF Images", "jpg", "gif");
        openChooser.setFileFilter(filter);
        int retvalue = openChooser.showOpenDialog(SwingFrame.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = openChooser.getSelectedFile();
          fileOpenDisplay.setText(f.getAbsolutePath());
        }
        break;
      case "save file":
        final JFileChooser saveChooser = new JFileChooser(".");
        int chooserRetvalue = saveChooser.showSaveDialog(SwingFrame.this);
        if (chooserRetvalue == JFileChooser.APPROVE_OPTION) {
          File f = saveChooser.getSelectedFile();
          fileSaveDisplay.setText(f.getAbsolutePath());
        }
        break;
      case "sharpen":
      case "blur":
      case "sepia":
      case "greyscale":

    }
  }

  /**
   * Invoked when an item has been selected or deselected by the user. The code written for this
   * method performs the operations that need to occur when an item is selected (or deselected).
   *
   * @param e the event to be processed
   */
  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  /**
   * Called whenever the value of the selection changes.
   *
   * @param e the event that characterizes the change.
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {

  }
}
