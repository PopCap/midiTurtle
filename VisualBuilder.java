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
   * @param builder Used to create the paths
   */
  public VisualBuilder(final PathBuilder builder)
  {
    this.builder = builder;
  }
  
  /**
   * Build the content.
   *
   * @param boundaryColor set the color.
   * @param interiorColor set the color of the inside of the shape.
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
   * Build the content to be able to iterate over them.
   *
   * @param boundaryColor set the color.
   * @param interiorColor set the color of the inside of the shape.
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
