package pathwork;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.io.IOException;
import java.util.ArrayList;

import visual.statik.described.Content;

/**
 * A class for building visual content from a PathBuilder.
 * 
 * @author Matthew Foley, Hunter Cantrell
 * @version 1.0
 *
 */
public class VisualBuilder
{
  private PathBuilder builder;
  
  /**
   * Explicit value constructor.
   * 
   * @param builder PathBuilder core.
   */
  public VisualBuilder(final PathBuilder builder)
  {
    this.builder = builder;
  }
  
  /**
   * Create the described statik background visual in a particular color from the PathBuilder's path.
   * 
   * @param boundaryColor Color of line segment.
   * @param interiorColor should be transparent.
   * @return visual Content.
   * @throws IOException
   */
  public Content buildContent(final Color boundaryColor, final Color interiorColor)
    throws IOException
    {
      Content visual;
      Shape path;
      path = builder.buildShape();
      
      visual = new Content(path, boundaryColor, interiorColor, new BasicStroke());
      visual.setLocation(0, 0);
      
      return visual;
    }
  
  /**
   * Fill and return ArrayList of described statik content which composes one singular audio "line".
   * 
   * @param boundaryColor Color of the line segment.
   * @param interiorColor should be transparent.
   * @return visualFrames the ArrayList.
   */
  public ArrayList<Content> buildContentFrames(final Color boundaryColor, final Color interiorColor)
  {
    ArrayList<Content> visualFrames = new ArrayList<Content>();
    for(Shape frame: builder.getShapeFrames())
    {
      Content visual;
      
      visual = new Content(frame, boundaryColor, interiorColor, new BasicStroke());
      visual.setLocation(0, 0);
      visualFrames.add(visual);
    }
    return visualFrames;
  }
}
