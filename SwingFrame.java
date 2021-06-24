package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SwingFrame extends JFrame implements IPView, ActionListener, ItemListener {
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JLabel imageLabel;
  private JPanel imagePanel;
  private BufferedImage topVisibleLayer;
  private Boolean imgImported = false;
  private int numLayers;
  private int currentLayer;
  private final List<IViewListener> iViewListeners;
  private JTextField currentTextField;
  private JTextField removeTextField;
  private JTextField copyTextField;

  public SwingFrame() {
    super();
    this.iViewListeners = new ArrayList<>();

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

    this.numLayers = 1;
    this.currentLayer = 0;



    //set current layer
    JPanel currentLayerPanel = new JPanel();
    createLayerPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(currentLayerPanel);
    JLabel currentLabel = new JLabel("Set current to layer #: ");
    currentTextField = new JTextField(5);
    currentLabel.setLabelFor(currentTextField);
    currentLayerPanel.add(currentLabel);
    currentLayerPanel.add(currentTextField);

    JButton setCurrent = new JButton("set");
    setCurrent.setActionCommand("current");
    setCurrent.addActionListener(this);
    currentLayerPanel.add(setCurrent);


    //copy panel
    JPanel copyPanel = new JPanel();
    copyPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(copyPanel);

    JLabel copyLabel = new JLabel("Copy current layer to layer #: ");
    copyTextField = new JTextField(5);
    copyLabel.setLabelFor(copyTextField);
    copyPanel.add(copyLabel);
    copyPanel.add(copyTextField);

    JButton copy = new JButton("copy");
    copy.setActionCommand("copy");
    copy.addActionListener(this);
    copyPanel.add(copy);


    //remove panel
    JPanel removeLayerPanel = new JPanel();
    currentLayerPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(removeLayerPanel);
    JLabel removeLabel = new JLabel("remove layer #: ");
    removeTextField = new JTextField(5);
    removeLabel.setLabelFor(removeTextField);
    removeLayerPanel.add(removeLabel);
    removeLayerPanel.add(removeTextField);

    JButton remove = new JButton("remove");
    remove.setActionCommand("remove");
    remove.addActionListener(this);
    removeLayerPanel.add(remove);


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
    this.imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new FlowLayout());
    imagePanel.setVisible(true);
    mainPanel.add(imagePanel);




  }
  public void showImageHelp() {
    if (this.numLayers > 0 && this.imgImported) {
      this.imageLabel = new JLabel();
      ImageIcon topmostVisibleImg = new ImageIcon(this.topVisibleLayer);
      this.imageLabel.setIcon(topmostVisibleImg);
      this.imagePanel.add(this.imageLabel);
    }
  }

  public void registerViewEventListener(IViewListener listener){
    this.iViewListeners.add( Objects.requireNonNull(listener));
  }

  @Override
  public void updateTopVisibleLayer(BufferedImage buff) {
    this.topVisibleLayer = buff;
  }

  public void emitActionEvent(String cmd) {
    for ( IViewListener listener : this.iViewListeners ){
      listener.handleActionEvent(cmd);
    }
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
            "JPG PNG PPM Images", "jpg", "pmm", "png","jpeg");
        openChooser.setFileFilter(filter);
        int retvalue = openChooser.showOpenDialog(SwingFrame.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = openChooser.getSelectedFile();
          f.getName();
          String path = f.getAbsolutePath();
          this.emitActionEvent("load " + path);
          this.imgImported = true;
          this.showImageHelp();
          //emitLoadEvent(f.getAbsolutePath());

        }
        break;
      case "create layer":
        this.emitActionEvent("create ");
        this.numLayers ++;
        try {
          this.renderMessage("layer created");
        } catch (IOException exception) {
          exception.printStackTrace();
        }
        break;

      case "current":
        try {
          String text = currentTextField.getText();
          if (text.length() > 0) {
            this.currentLayer = Integer.parseInt(text);
            this.emitActionEvent("current " + this.currentLayer);
            this.currentTextField.setText("");
          }

        } catch (NumberFormatException ex) {
          ex.printStackTrace();
        }

        break;
      case "remove":
        try {
          String text = removeTextField.getText();
          if (text.length() > 0) {
            int toRemove = Integer.parseInt(text);
            this.emitActionEvent("remove " + toRemove);
            this.removeTextField.setText("");
          }
        } catch (NumberFormatException ex) {
          ex.printStackTrace();
        }
        break;
      case "copy":
        try {
          String text = copyTextField.getText();
          if (text.length() > 0) {
            int toCopy = Integer.parseInt(text);
            this.emitActionEvent("copy " + toCopy);
            this.copyTextField.setText("");
          }
        } catch (NumberFormatException ex) {
          ex.printStackTrace();
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
        this.emitActionEvent("sharpen");
        break;
      case "blur":
        this.emitActionEvent("blur");
        break;
      case "sepia":
        this.emitActionEvent("sepia");
        break;
      case "greyscale":
        this.emitActionEvent("greyscale");
        break;

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
   * Displays the given message to the user.
   *
   * @param message Message to be displayed.
   * @throws IOException Thrown if output fails.
   */
  @Override
  public void renderMessage(String message) throws IOException {

  }
}
