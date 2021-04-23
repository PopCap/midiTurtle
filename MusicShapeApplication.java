package musicapp;

//Java libraries
import java.io.*;

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
  
  @Override
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
        
        
        musicScreen.updateShapes(sequence);
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