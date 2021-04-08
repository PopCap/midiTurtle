package musicapp;

//Java libraries
import java.awt.event.*;

import java.io.*;
import javax.swing.*;

//Multimedia libraries
import app.*;
import io.*;


/**
 * An application that displays shapes drawn from music properties.
 * 
 * 
 * @author Hunter Cantrell, Matt Foley
 * @version 1.0
 */
public abstract class MusicDrawerApplication extends JApplication implements ActionListener
{
  public static final int WIDTH  = 1000;
  public static final int HEIGHT = 800;

  protected static final String LOAD = "Load";
  protected static final String PLAY = "Play";
  protected static final String PAUSE = "Pause";
  protected static final String EXPORT = "Export";
  
  private JButton playButton, pauseButton, loadButton, exportButton;
  private JTextField fileField;
  
  /**
   * Explicit value constructor.
   * 
   * @param args   The command line arguments
   */
  public MusicDrawerApplication(final String[] args)
  {
    super(args, WIDTH, HEIGHT);
  }

  
  /**
   * Handle actionPerformed messages (required by ActionListener).
   * In particular, get the input, perform the requested conversion,
   * and display the result.
   * 
   * @param evt  The ActionEvent that generated the actionPerformed message
   */
  @Override
  public void actionPerformed(final ActionEvent evt)
  {
    String ac = evt.getActionCommand();
    
    if (ac.equalsIgnoreCase(PLAY)) handlePlay();
    else if (ac.equalsIgnoreCase(PAUSE)) handlePause();
    else if (ac.equalsIgnoreCase(EXPORT)) handleExport();
    else if (ac.equalsIgnoreCase(LOAD)) handleLoad();
  }
  
  /**
   * Handle the LOAD button.
   */
  protected void handleLoad()
  {

  }
  
  /**
   * Handle the PLAY button.
   */
  protected void handlePlay()
  {

  }
  
  /**
   * Handle the PAUSE button.
   */
  protected void handlePause()
  {

  }
  
  /**
   * Handle the LOAD button.
   */
  protected void handleExport()
  {

  }
  
  /**
   * Get the GUI components to use to display the weather information.
   * 
   * @return The WeatherObserverPanel
   */
  protected abstract JComponent getGUIComponent();
  
  /**
   * Initialize this JApplication (required by JApplication).
   * Specifically, construct and layout the JFrame.
   */
  @Override
  public void init()
  {
    // Setup the content pane
    JPanel contentPane = (JPanel)getContentPane();
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
    
    exportButton = new JButton(EXPORT);
    exportButton.setBounds(560, 30, 80, 30);
    exportButton.addActionListener(this);
    contentPane.add(exportButton);
    
    JComponent component = getGUIComponent();
    component.setBounds(0, 60, WIDTH, HEIGHT-60);
    contentPane.add(component);
  }
}
