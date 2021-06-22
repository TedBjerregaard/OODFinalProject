package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SwingFrame extends JFrame implements ActionListener, ItemListener,
    ListSelectionListener {
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;

  public SwingFrame() {
    super();
    setTitle("Swing features");
    setSize(400, 400);

    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Import/Save, Apply a filter, "
        + "sharpen/blur"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);


    //Filter Transformations
    JPanel filterPanel = new JPanel();
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

    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(this);
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(this);
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);

    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    //imagePanel.setMaximumSize(null);
    mainPanel.add(imagePanel);

    String[] images = {"Jellyfish.jpg", "Koala.jpg", "Penguins.jpg"};
    JLabel[] imageLabel = new JLabel[images.length];
    JScrollPane[] imageScrollPane = new JScrollPane[images.length];
    for (int i = 0; i < imageLabel.length; i++) {
      imageLabel[i] = new JLabel();
      imageScrollPane[i] = new JScrollPane(imageLabel[i]);
      imageLabel[i].setIcon(new ImageIcon(images[i]));
      imageScrollPane[i].setPreferredSize(new Dimension(100, 600));
      imagePanel.add(imageScrollPane[i]);
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

      case "Open file":
      case "Save file":
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
    String who = ((JCheckBox) e.getItemSelectable()).getActionCommand();

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
