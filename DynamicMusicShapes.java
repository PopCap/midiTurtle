package gui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
 * @author Hunter Cantrell and Matthew Foley
 *
 */
public class DynamicMusicShapes extends Stage
{
  private Color currentColor;
  private List<TransformableContent> rays;
  private ArrayList<visual.statik.described.Content> frameList;
  private DescribedSprite[] spriteList;
  PathBuilder pathBuilder;
  VisualBuilder visualBuilder;
  VisualizationView view;

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
    
    view = getView();
    view.setBounds(0, 0, width, height);
    view.setSize(width, height);
    view.setBackground(BACKGROUND_COLOR);
    
  }
  
  /**
   * 
   * Draws the initial shape with black lines.
   * @param width         Midi file to be sequenced.
   * @throws IOException 
   */
  public void updateShapes(final Sequence sequence) throws IOException
  {
    rays.clear();
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
  
  /**
   * Creates new contents from the initial shape to begin tracing.
   */
  public void updateFrames() 
  {
    spriteList = new DescribedSprite[90];
//    DescribedSprite sprite = new DescribedSprite();
    for (int i = 0; i < spriteList.length; i++)
    {
      spriteList[i] = new DescribedSprite();
    }
    
    Point2D location = new Point(0, 0);
    
    AggregateContent[] ac = new AggregateContent[frameList.size()];
    for (int i = 0; i < frameList.size(); i++)
    {
      ac[i] = new AggregateContent();
      frameList.get(i).setRotation(90, 500.0, 500.0);
      ac[i].add(frameList.get(i));
//      ac[i].setRotation(90, 500.0, 500.0);
    }
    int time = 300;
    
//    DescribedSprite sprite = new DescribedSprite();

//    sprite.addKeyTime(500, location, 0.0, 1.0, ac[1]);
//    sprite.addKeyTime(1000, location, 0.0, 1.0, ac[2]);
    
//    for (int i = 0; i < spriteList.length; i++)
    DescribedSprite sprite = new DescribedSprite();
    {
      for (AggregateContent content: ac)
      {
        sprite.addKeyTime(time, location, 0.0, 1.0, content);
//        spriteList[i].setRotation(i, 500.0, 500.0);
        time += 300;
      }
    }
    sprite.setRotation(180);
    add(sprite);
//    for (AggregateContent content: ac)
//    {
//      spriteList[0].addKeyTime(time, location, 0.0, 1.0, content);
//      spriteList[1].addKeyTime(time, location, 0.0, 1.0, content);
//      time += 300;
//    }
//    add(spriteList[0]);
//    add(spriteList[1]);
  }

}
