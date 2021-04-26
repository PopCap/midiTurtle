package musicapp;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
//Java libraries
import java.io.*;
import java.net.URISyntaxException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

//Multimedia libraries
import app.*;

// Application libraries
import gui.*;


/**
 * An application that music visualizations from a Midi file.
 * 
 * @author Hunter Cantrell & Matthew Foley
 * @version 1.0
 */
public class MusicShapeApplication extends MusicDrawerApplication
{
  private DynamicMusicShapes musicScreen;
  
  
  /**
   * Explicit value constructor.
   * 
   * @param args   The command line arguments
   */
  public MusicShapeApplication(final String[] args) throws IOException
  {
    super(args);
    
    musicScreen = new DynamicMusicShapes(WIDTH, HEIGHT-60, sequence);
  }
    
  /**
   * Get the GUI component that will be used to display the weather information.
   * 
   * @return The WeatherObserverPanel
   */
  @Override
  protected JComponent getGUIComponent()
  {
    return musicScreen.getView();
  }
  
  /**
   * Handle the EXPORT button.
   */
  @Override
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
   * Handle the LOAD button. Clear previous midi file information from musicScreen.
   */
  @Override
  protected void handleLoad()
  {
    if(sequencer != null && sequencer.isRunning())
    {
      sequencer.close();
    }
    
    musicScreen.stop();
    musicScreen.clear();
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
      musicScreen.setStageTime(sequencer.getMicrosecondLength());
      musicScreen.updateShapes(sequence);
      musicScreen.updateFrames();
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
  @Override
  protected void handlePlay()
  {
    sequencer.start();
    musicScreen.start();
  }
  
  /**
   * Handle the PAUSE button.
   */
  @Override
  protected void handlePause()
  {
    sequencer.stop();
    musicScreen.stop();
  }
  
  /**
   * Handle the RESTART button.
   */
  @Override
  protected void handleRestart()
  {
    sequencer.stop();
    musicScreen.stop();
    musicScreen.clear();
    handleLoad();
  }
  
  /**
   * Construct and invoke  (in the event dispatch thread) 
   * an instance of this JApplication.
   * 
   * @param args The command line arguments
   */
  public static void main(final String[] args) throws IOException
  {
    JApplication app = new MusicShapeApplication(args);
    invokeInEventDispatchThread(app);
  }
}