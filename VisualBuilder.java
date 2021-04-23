package pathwork;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.io.IOException;

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
  
  public VisualBuilder(final PathBuilder builder)
  {
    this.builder = builder;
  }
  
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
}
