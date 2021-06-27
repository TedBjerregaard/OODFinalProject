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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * View class for this representation of an image processor. This view creates a graphical user
 * interface that can be used to performed a set of operations on single and multilayered images.
 */
public class SwingFrame extends JFrame implements IPView, ActionListener {
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private JLabel imageLabel;
  private JPanel imagePanel;
  private ImageIcon topmostVisibleImg;
  private BufferedImage topVisibleLayer;
  private Boolean imgImported = false;
  private int numLayers;
  private int currentLayer;
  private final List<IViewListener> iViewListeners;
  private JTextField currentTextField;
  private JTextField removeTextField;
  private JTextField copyTextField;
  private JTextField saveTextField;
  private JTextArea commandTextArea;

  /**
   * Constructor this class and the graphical user interface specifically.
   */
  public SwingFrame() {
    super();
    this.iViewListeners = new ArrayList<>();

    setTitle("Image Processor");
    setSize(600, 600);

    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Layer/File Operations"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

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


    //Batch Command
    JPanel batchCommandPanel = new JPanel();
    batchCommandPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(batchCommandPanel);
    JButton batchCommandButton = new JButton("Upload Batch Command text file");
    batchCommandButton.setActionCommand("batch command");
    batchCommandButton.addActionListener(this);
    batchCommandPanel.add(batchCommandButton);

    //file open
    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileOpenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("open file");
    fileOpenButton.addActionListener(this);
    fileOpenPanel.add(fileOpenButton);

    //import multilayered image
    JPanel importMultiPanel = new JPanel();
    importMultiPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(importMultiPanel);
    JButton importMultiButton = new JButton("Import Multilayered Image");
    importMultiButton.setActionCommand("import");
    importMultiButton.addActionListener(this);
    importMultiPanel.add(importMultiButton);

    //file save
    JPanel fileSavePanel = new JPanel();
    fileSavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileSavePanel);

    JLabel saveLabel = new JLabel("Save file type (jpeg, png, or ppm): ");
    saveTextField = new JTextField(5);
    saveLabel.setLabelFor(saveTextField);
    fileSavePanel.add(saveLabel);
    fileSavePanel.add(saveTextField);

    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("save file");
    fileSaveButton.addActionListener(this);
    fileSavePanel.add(fileSaveButton);

    //export multilayered image
    JPanel exportMultiPanel = new JPanel();
    exportMultiPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(exportMultiPanel);
    JButton exportMultiButton = new JButton("Export Multilayered Image");
    exportMultiButton.setActionCommand("export");
    exportMultiButton.addActionListener(this);
    exportMultiPanel.add(exportMultiButton);

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
    JLabel removeLabel = new JLabel("Remove layer #: ");
    removeTextField = new JTextField(5);
    removeLabel.setLabelFor(removeTextField);
    removeLayerPanel.add(removeLabel);
    removeLayerPanel.add(removeTextField);

    JButton remove = new JButton("remove");
    remove.setActionCommand("remove");
    remove.addActionListener(this);
    removeLayerPanel.add(remove);


    //current layer visibility
    JPanel visibilityPanel = new JPanel();
    visibilityPanel.setBorder(BorderFactory.createTitledBorder("Current Layer Visibility"));
    visibilityPanel.setLayout(new BoxLayout(visibilityPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(visibilityPanel);

    JPanel changeVisibilityPanel = new JPanel();
    changeVisibilityPanel.setLayout(new FlowLayout());
    visibilityPanel.add(changeVisibilityPanel);
    JLabel visibility = new JLabel("Set visibility of current layer: ");
    changeVisibilityPanel.add(visibility);

    //visible button
    JButton visible = new JButton("visible");
    visible.setActionCommand("visible");
    visible.addActionListener(this);
    changeVisibilityPanel.add(visible);

    //invisible button
    JButton invisible = new JButton("invisible");
    invisible.setActionCommand("invisible");
    invisible.addActionListener(this);
    changeVisibilityPanel.add(invisible);

    //Filter Transformations
    JPanel filterPanel = new JPanel();
    filterPanel.setBorder(BorderFactory.createTitledBorder("Image Operation"));
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
    imagePanel.setBorder(BorderFactory.createTitledBorder("Topmost Visible Image"));
    imagePanel.setLayout(new FlowLayout());
    imagePanel.setVisible(true);
    JScrollPane imageTextPane = new JScrollPane(this.imagePanel);
    mainPanel.add(imageTextPane);
    this.imageLabel = new JLabel();


    //text area with a scrollbar
    commandTextArea = new JTextArea(15, 50);
    JScrollPane commandTextPane = new JScrollPane(commandTextArea);
    commandTextArea.setLineWrap(true);
    commandTextPane.setBorder(BorderFactory.createTitledBorder("IP Status"));
    mainPanel.add(commandTextPane);


  }

  /**
   * Helper method that aids in showing the topmost visible image of a multilayered image in a panel
   * on the graphical user interface.
   */
  public void showImageHelp() {
    if (this.numLayers > 0 && this.imgImported) {
      this.imagePanel.remove(this.imageLabel);
      this.topmostVisibleImg = new ImageIcon(this.topVisibleLayer);
      this.imageLabel.setIcon(this.topmostVisibleImg);
      this.imagePanel.add(this.imageLabel);
      this.repaint();

    }
  }

  /**
   * Registers listeneres for action events in this view.
   *
   * @param listener An object that reacts to certain events.
   */
  public void registerViewEventListener(IViewListener listener){
    this.iViewListeners.add( Objects.requireNonNull(listener));
  }

  @Override
  public void updateTopVisibleLayer(BufferedImage buff) {
    this.topVisibleLayer = buff;
    this.showImageHelp();
  }

  /**
   * Relays an action event as a string command to the controller to perform changes to the model.
   * @param cmd  String command.
   */
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

      case "batch command":
        final JFileChooser cmdChooser = new JFileChooser(".");
        FileNameExtensionFilter filterCMD = new FileNameExtensionFilter(
            "TXT Command File", "txt");
        cmdChooser.setFileFilter(filterCMD);
        int retValueCMD = cmdChooser.showOpenDialog(SwingFrame.this);
        if (retValueCMD == JFileChooser.APPROVE_OPTION) {
          File f = cmdChooser.getSelectedFile();
          String path = f.getAbsolutePath();
          this.emitActionEvent("txtCommand " + path);
          this.imgImported = true;
          this.showImageHelp();
          this.imagePanel.updateUI();
        }
        break;
      case "open file":
        final JFileChooser openChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG PNG PPM Images", "jpg", "ppm", "png","jpeg");
        openChooser.setFileFilter(filter);
        int retValue = openChooser.showOpenDialog(SwingFrame.this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          File f = openChooser.getSelectedFile();
          String path = f.getAbsolutePath();
          this.emitActionEvent("load " + path);
          this.imgImported = true;
          this.showImageHelp();
          this.imagePanel.updateUI();
        }
        break;

      case "import":
        final JFileChooser importChooser = new JFileChooser(".");
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
            "text file", "txt");
        importChooser.setFileFilter(txtFilter);
        int importValue = importChooser.showOpenDialog(SwingFrame.this);
        if (importValue == JFileChooser.APPROVE_OPTION) {
          File f = importChooser.getSelectedFile();
          String path = f.getAbsolutePath();
          this.emitActionEvent("import " + path);
          this.imgImported = true;
          this.showImageHelp();
          this.imagePanel.updateUI();
        }
        break;

      case "export":
        final JFileChooser exportChooser = new JFileChooser(".");
        int exportRetValue = exportChooser.showSaveDialog(SwingFrame.this);
        if (exportRetValue == JFileChooser.APPROVE_OPTION) {
          File f = exportChooser.getSelectedFile();
          this.emitActionEvent("export " + f.getName());
        }
        break;

      case "create layer":
        this.emitActionEvent("create ");
        this.numLayers ++;
        this.imagePanel.updateUI();
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
        this.imagePanel.updateUI();
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

        this.imagePanel.updateUI();
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

        this.imagePanel.updateUI();
        break;

      case "visible":
        this.emitActionEvent("visible");
        this.imagePanel.updateUI();
        break;

      case "invisible":
        this.emitActionEvent("invisible");
        this.imagePanel.updateUI();
        break;

      case "save file":
        String fileType = saveTextField.getText();
        final JFileChooser saveChooser = new JFileChooser(".");
        int chooserRetValue = saveChooser.showSaveDialog(SwingFrame.this);
        if (chooserRetValue == JFileChooser.APPROVE_OPTION) {
          File f = saveChooser.getSelectedFile();
          this.emitActionEvent("save " + f.getAbsolutePath() + " " + fileType);
          this.saveTextField.setText("");
        }

        this.imagePanel.updateUI();
        break;

      case "sharpen":
        this.emitActionEvent("sharpen");
        this.imagePanel.updateUI();
        break;

      case "blur":
        this.emitActionEvent("blur");
        this.imagePanel.updateUI();
        break;

      case "sepia":
        this.emitActionEvent("sepia");
        this.imagePanel.updateUI();
        break;

      case "greyscale":
        this.emitActionEvent("greyscale");
        this.imagePanel.updateUI();
        break;

    }
  }



  /**
   * Displays the given message to the user.
   *
   * @param message Message to be displayed.
   * @throws IOException Thrown if output fails.
   */
  @Override
  public void renderMessage(String message) throws IOException {
    commandTextArea.append(message);
  }
}