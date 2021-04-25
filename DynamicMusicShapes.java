package gui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Sequence;

import io.ResourceFinder;
import pathwork.PathBuilder;
import pathwork.VisualBuilder;
import visual.VisualizationView;
import visual.dynamic.described.DescribedSprite;
import visual.dynamic.described.Stage;
import visual.statik.described.AggregateContent;
import visual.statik.described.Content;
import visual.statik.described.TransformableContent;



/**
 * 
 * @author Hunter Cantrell
 *
 */
public class DynamicMusicShapes extends Stage
{
  private Color currentColor;
  private List<TransformableContent> rays;
  private ArrayList<visual.statik.described.Content> frameList;
  private ArrayList<AggregateContent> aggregateList;
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
    
    final Color BACKGROUND_COLOR = new Color(20, 20, 20);
    
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
      TransformableContent visual = visualBuilder.buildContent(Color.BLACK, new Color(0, 0, 0, 0));
      visual.setRotation(i, 500.0, 500.0);
      rays.add(visual);
    }
    
    for (TransformableContent ray: rays) add(ray);
    
    frameList = visualBuilder.buildContentFrames(Color.WHITE, new Color(0, 0, 0, 0));
    
//    for (Content frame: frameList) System.out.println(frame.getBounds2D().getWidth());
  }
  
  public void updateFrames() //visual.statik.described.Content
  {
//    aggregateList = new ArrayList<AggregateContent>();
//    
//    for (int i = 0; i < frameList.size(); i++)
//    {
//      AggregateContent aggregate = new AggregateContent();
//      for (int j = 0; j < i; j++)
//      {
//        aggregate.add(frameList.get(i));
//      }
//      
//      
//      aggregateList.add(aggregate);
//    }
    
    DescribedSprite sprite = new DescribedSprite();
    Point2D location = new Point(0, 0);
    AggregateContent aggregate = new AggregateContent();
//    AggregateContent aggregate2 = new AggregateContent();
    
    for(int i = 0; i < frameList.size()/2; i++)  
    { 
      aggregate.add(frameList.get(i));
      for (int j = 0; j < 89; j++)
      {
        sprite.addKeyTime(i * 1000, location, i*1.0, 1.0, aggregate);
      }
    }
    
//    for(int i = 0; i < frameList.size(); i++)  
//    { 
//      aggregate2.add(frameList.get(i));  
//    }
//    
//    sprite.addKeyTime(4000,  location, 0.0, 1.0, aggregate2);

    
//    for (int i = 0; i < 89; i++)
//    {
//      aggregate.setRotation(i, 500.0, 500.0);
//      sprite.addKeyTime(1500,  location, 0.0, 1.0, aggregate);
//    }
    add(sprite);
  }

}