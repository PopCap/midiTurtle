package musicapp;

//Java libraries
import java.io.*;

import javax.swing.*;

//Multimedia libraries
import app.*;

// Application libraries
import gui.*;


/**
 * An application that displays weather observations or forecasts on a static map.
 * 
 * @author Prod. David Bernstein, James Madison University
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
    
    String grayWatermark, useWatermark;
    if (args.length < 1) useWatermark = null;
    else useWatermark = args[0];
    
    if (args.length < 2) grayWatermark = null;
    else grayWatermark = args[1];
    
    musicScreen = new DynamicMusicShapes(useWatermark, grayWatermark, WIDTH, HEIGHT-60);
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
