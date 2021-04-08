package gui;


import java.awt.Color;


import java.io.IOException;

import io.ResourceFinder;
import visual.dynamic.described.Stage;
import visual.statik.sampled.Content;


/**
 * 
 * @author Hunter Cantrell
 *
 */
public class DynamicMusicShapes extends Stage
{
  private Content watermark;
  private ResourceFinder jarFinder;
  private Color currentColor;

  /**
   * 
   * Explicit value constructor.
   * 
   * @param useWatermark  Non-null to indicate that a watermark should be used
   * @param grayWatermark Non-null to indicate that the watermark (if used) should
   *                      be gray
   * @param width         The width of this component (in pixels)
   * @param height        The height of this component (in pixels)
   * @throws IOException 
   */
  public DynamicMusicShapes(final String useWatermark, final String grayWatermark, final int width,
      final int height) throws IOException
  {
    super(100);

    final Color BACKGROUND_COLOR = new Color(204, 204, 255);
    
    
  }

}
