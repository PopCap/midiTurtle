package musicapp;

//Java libraries
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.io.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.*;

//Multimedia libraries
import app.*;
import io.*;
import pathwork.PathBuilder;

/**
 * An application that displays shapes drawn from music properties.
 * 
 * 
 * @author Hunter Cantrell, Matt Foley
 * @version 1.0
 */
public abstract class MusicDrawerApplication extends JApplication implements ActionListener
{
  public static final int WIDTH = 1000;
  public static final int HEIGHT = 1000;

  protected static final String LOAD = "Load";
  protected static final String PLAY = "Play";
  protected static final String PAUSE = "Pause";
  protected static final String EXPORT = "Export";
  protected static final String RESTART = "Restart";
  
  protected Sequence sequence = null;
  protected Sequencer sequencer = null;
  
  protected JButton playButton, pauseButton, loadButton, exportButton, restartButton;
  protected JTextField fileField;
  
  /**
   * Explicit value constructor.
   * 
   * @param args The command line arguments
   */
  public MusicDrawerApplication(final String[] args)
  {
    super(args, WIDTH, HEIGHT);
  }

  /**
   * Handle actionPerformed messages (required by ActionListener). In particular,
   * get the input, perform the requested conversion, and display the result.
   * 
   * @param evt The ActionEvent that generated the actionPerformed message
   */
  @Override
  public void actionPerformed(final ActionEvent evt)
  {
    String ac = evt.getActionCommand();

    if (ac.equalsIgnoreCase(PLAY))
      handlePlay();
    else if (ac.equalsIgnoreCase(PAUSE))
      handlePause();
    else if (ac.equalsIgnoreCase(EXPORT))
      handleExport();
    else if (ac.equalsIgnoreCase(LOAD))
      handleLoad();
    else if (ac.equalsIgnoreCase(RESTART))
      handleRestart();
  }

  /**
   * Handle the LOAD button.
   */
  protected void handleLoad()
  {
    if(sequencer != null && sequencer.isRunning())
    {
      sequencer.close();
    }
      String fileName = fileField.getText();
      File file;

      try
      {
        file = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        sequence = MidiSystem.getSequence(fileInputStream);

        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.setSequence(sequence);
        
        
//        pathBuilder = new PathBuilder(seq);
//        pathBuilder.buildPath();
      } catch (IOException ioe)
      {
        JOptionPane.showMessageDialog(getGUIComponent(), "There was a problem reading " + fileName,
            "Error", JOptionPane.ERROR_MESSAGE);
      } catch (InvalidMidiDataException e)
      {
        JOptionPane.showMessageDialog(getGUIComponent(), "There was a problem reading " + fileName,
            "Error", JOptionPane.ERROR_MESSAGE);
      } catch (MidiUnavailableException e)
      {
        JOptionPane.showMessageDialog(getGUIComponent(), "There was a problem reading " + fileName,
            "Error", JOptionPane.ERROR_MESSAGE);
      }
  }

  /**
   * Handle the PLAY button.
   */
  protected void handlePlay()
  {
    sequencer.start();
  }

  /**
   * Handle the PAUSE button.
   */
  protected void handlePause()
  {
    sequencer.stop();
  }

  /**
   * Handle the EXPORT button.
   */
  protected void handleExport()
  {
    try
    {
      Component contentPane = getGUIComponent();
      BufferedImage image = new BufferedImage(contentPane.getWidth(), contentPane.getHeight(),
          BufferedImage.TYPE_INT_RGB);

      BufferedImage img = image.getSubimage(0, 0, contentPane.getWidth(), contentPane.getHeight());

      Graphics2D g2d = img.createGraphics();
      g2d = img.createGraphics();
      contentPane.printAll(g2d);
      g2d.dispose();
      try
      {
        Random random = new Random();
        ImageIO.write(img, "png",
            new File(
                getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()
                    + "\\shapeDrawerApplicationImage_" + random.nextInt(100) + ".png"));
      } catch (URISyntaxException e)
      {
        e.printStackTrace();
      }
    } catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }

  /**
   * Handle the RESTART button.
   */
  protected void handleRestart()
  {
    sequencer.stop();
    handleLoad();
  }

  /**
   * Get the GUI components to use to display the weather information.
   * 
   * @return The WeatherObserverPanel
   */
  protected abstract JComponent getGUIComponent();

  /**
   * Initialize this JApplication (required by JApplication). Specifically,
   * construct and layout the JFrame.
   */
  @Override
  public void init()
  {
    // Setup the content pane
    JPanel contentPane = (JPanel) getContentPane();
    contentPane.setLayout(null);

    JLabel label = new JLabel("File: ");
    label.setBounds(30, 30, 40, 30);
    contentPane.add(label);

    fileField = new JTextField();
    fileField.setBounds(80, 30, 200, 30);
    contentPane.add(fileField);

    loadButton = new JButton(LOAD);
    loadButton.setBounds(320, 30, 60, 30);
    loadButton.addActionListener(this);
    contentPane.add(loadButton);

    playButton = new JButton(PLAY);
    playButton.setBounds(400, 30, 60, 30);
    playButton.addActionListener(this);
    contentPane.add(playButton);

    pauseButton = new JButton(PAUSE);
    pauseButton.setBounds(480, 30, 60, 30);
    pauseButton.addActionListener(this);
    contentPane.add(pauseButton);

    restartButton = new JButton(RESTART);
    restartButton.setBounds(560, 30, 80, 30);
    restartButton.addActionListener(this);
    contentPane.add(restartButton);

    exportButton = new JButton(EXPORT);
    exportButton.setBounds(660, 30, 80, 30);
    exportButton.addActionListener(this);
    contentPane.add(exportButton);

    JComponent component = getGUIComponent();
    component.setBounds(0, 60, WIDTH, HEIGHT - 60);
    contentPane.add(component);
  }

}
