package gui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Sequence;

import io.ResourceFinder;
import pathwork.PathBuilder;
import pathwork.VisualBuilder;
import visual.VisualizationView;
import visual.dynamic.described.Stage;
import visual.statik.described.TransformableContent;
import visual.statik.sampled.Content;


/**
 * 
 * @author Hunter Cantrell
 *
 */
public class DynamicMusicShapes extends Stage
{
  private Color currentColor;
  private List<TransformableContent> rays;
  PathBuilder pathBuilder;
  VisualBuilder visualBuilder;

  /**
   * 
   * Explicit value constructor.
   * @param width         The width of this component (in pixels)
   * @param height        The height of this component (in pixels)
   * @throws IOException 
   */
  public DynamicMusicShapes(final int width,
      final int height, final Sequence sequence) throws IOException
  {
    super(100);
    
    rays = new ArrayList<TransformableContent>();
    
    final Color BACKGROUND_COLOR = new Color(0, 0, 0);
    
    VisualizationView view = getView();
    view.setBounds(0, 0, width, height);
    view.setSize(width, height);
    view.setBackground(BACKGROUND_COLOR);
    
  }
  
  public void updateShapes(final Sequence sequence) throws IOException
  {
    pathBuilder = new PathBuilder(sequence);
    visualBuilder = new VisualBuilder(pathBuilder);
    
    // have 90 rays forming the radial visual
    for (int i = 0; i < 89; i++)
    {
      TransformableContent visual = visualBuilder.buildContent(Color.WHITE, new Color(0, 0, 0, 0));
      visual.setRotation(i, 500.0, 500.0);
      rays.add(visual);
    }
    
    for (TransformableContent ray: rays) add(ray);
  }

}