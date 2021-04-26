package gui;


import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Sequence;

import visual.VisualizationView;
import visual.dynamic.described.DescribedSprite;
import visual.dynamic.described.Stage;
import visual.statik.described.AggregateContent;
import visual.statik.described.TransformableContent;

import pathwork.PathBuilder;
import pathwork.VisualBuilder;



/**
 * 
 * @author Hunter Cantrell
 *
 */
public class DynamicMusicShapes extends Stage
{
  private List<TransformableContent> rays;
  private ArrayList<visual.statik.described.Content> frameList;
  private DescribedSprite[] spriteList; // unused because multiple sprites not working
  PathBuilder pathBuilder;
  VisualBuilder visualBuilder;
  VisualizationView view;
  int stageTime;

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
   * Draws the initial radial shape shape pattern.
   * 
   * @param sequence Midi file to be sequenced.
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
  }
  
  /**
   * Creates new contents from the initial shape to begin tracing over length of Midi file.
   */
  public void updateFrames() 
  {
    // MULTIPLE SPRITES NOT DISPLAYING
    //    spriteList = new DescribedSprite[89];
    //    for (int i = 0; i < spriteList.length; i++)
    //    {
    //      spriteList[i] = new DescribedSprite();
    //    }
    DescribedSprite sprite = new DescribedSprite();
    Point2D location = new Point(0, 0);
    
    AggregateContent[] ac = new AggregateContent[frameList.size()];
    for (int i = 0; i < frameList.size(); i++)
    {
      ac[i] = new AggregateContent();
      ac[i].add(frameList.get(i));
    }

    
    int deltaTime = stageTime/frameList.size();
    int time = deltaTime;


    for (AggregateContent content: ac)
    {
      sprite.addKeyTime(time, location, 0.0, 1.0, content);
      time += deltaTime;
    }
    sprite.addKeyTime(time, location,  0.0,  1.0,  null);
    time += deltaTime;
    add(sprite);
  }

  public void setStageTime(long microSecondLength)
  {
    stageTime = (int) microSecondLength/1000;
  }
}