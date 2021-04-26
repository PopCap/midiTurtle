package pathwork;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * PathBuilder class to take Midi sequences and create Path2D representation of them.
 * 
 * @author Matthew Foley, Hunter Cantrell
 * @version 1.0
 */
public class PathBuilder
{
  
  public static final int NOTE_ON = 0x90;
  public static final int NOTE_OFF = 0x80;
  // NOTE_NAMES and NOTE_FREQ are parallel arrays
  public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
  // NOTE_FREQ based on OCTAVE 0 note frequencies
  public static final double[] NOTE_FREQ = {16.351, 17.324, 18.354, 19.445, 20.601, 21.827, 23.124, 24.499, 25.956, 27.5, 29.135, 30.868};
  private Sequence sequence;
  
  private float x, y, deltaX, deltaY;
  private int tickIndex, maxTrackSize, indexJump, uniqueTicks, eventIndex, sampleIndex;
  
  private ArrayList<Long> tickTimes = new ArrayList<Long>(); // list of all unique ticks where a note is changed
  private ArrayList<Long> sampleTimes = new ArrayList<Long>(); // list of chosen tick times to sample for visual
  private ArrayList<Path2D.Float> frames = new ArrayList<Path2D.Float>();
  private boolean[] activeNotes = new boolean[12]; // array of all active notes concurrently
  private Path2D.Float visual = new Path2D.Float();
  private Track[] midiTracks;
  
  
  /**
   * Default constructor.
   */
  public PathBuilder()
  {
    sequence = null;
  }
  
  /**
   * Explicit value constructor.
   * 
   * @param seq Midi sequence
   */
  public PathBuilder(Sequence seq)
  {
    sequence = seq;
    midiTracks = sequence.getTracks();
    analyzeSequence();
  }
  
  /**
   * analyzeSequene determines the "unique" tick times as well as metrics for where to sample from
   * the midi file.
   */
  private void analyzeSequence()
  {
 // center of window
    x = 500.0f;
    y = 500.0f;
    
    tickIndex = 0; // index for tickTimes ArrayList
    maxTrackSize = 0; // most events for a single track in MIDI
    eventIndex = 0; // index for current place in all tracks
    sampleIndex = 0; // keep track of current number of times sampled from song
    
    visual.moveTo(x, y);
    visual.lineTo(x, y);

    // get ArrayList of all tick times in tracks
    for (Track track : midiTracks)
    {
      if (track.size() > maxTrackSize) maxTrackSize = track.size();
      for (int i=0; i < track.size(); i++)
      {
        MidiEvent event = track.get(i);
        MidiMessage message = event.getMessage();
        if (message instanceof ShortMessage)
        {
          ShortMessage sm = (ShortMessage) message;
          // only want ticks where changes in notes happen
          if (sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF)
          {
            // only add unique tick times to list to determine indexing for samples
            if (!tickTimes.contains(event.getTick())) tickTimes.add(event.getTick());
          }
        }
      }
    }
    
    // determine which ticks to use for each change in x axis if greater than 50 unique tick times
    uniqueTicks = tickTimes.size();
    if (uniqueTicks >= 50)
    {
      indexJump = uniqueTicks / 50;
      deltaX = 10.0f;
      for (int i = 0; i < (50 * indexJump); i += indexJump)
      {
        sampleTimes.add(tickTimes.get(i));
      }
    } 
    else
    {
      indexJump = 1;
      deltaX = 500.0f / uniqueTicks;
      for (int i = 0; i < uniqueTicks; i++)
      {
        sampleTimes.add(tickTimes.get(i));
      }
    }
    deltaY = 0.0f;
  }
  
  /**
   * After being run once, buildShape will just return the constructed path.
   * 
   * @return visual the Path.2D representation of the midi
   */
  public Shape buildShape()
  {
    // simulating going through each track in parallel
    while (eventIndex < maxTrackSize && tickIndex < uniqueTicks)// && sampleIndex < )
    {
      for (Track track : midiTracks)
      {
        if (eventIndex < track.size())
        {
          MidiEvent event = track.get(eventIndex);
          MidiMessage message = event.getMessage();
          if (message instanceof ShortMessage)
          {
            ShortMessage sm = (ShortMessage) message;
            // update which notes are on and off at event time
            if (tickTimes.contains(event.getTick()))
            {
              if (sm.getCommand() == NOTE_ON)
              {
                activeNotes[sm.getData1() % 12] = true;
              }
              else if (sm.getCommand() == NOTE_OFF)
              {
                activeNotes[sm.getData1() % 12] = false;
              }
            }
          }
        }
      }
//    check whether at a designated sample time
      if (sampleIndex < 50 && tickTimes.get(tickIndex) == sampleTimes.get(sampleIndex))
      {
        deltaY = 0.0f;
        // collect current note data to change deltaY
        for (int i = 0; i < 12; i++)
        {
          if (activeNotes[i])
          {
            deltaY += NOTE_FREQ[i];
          }
        }
        
        visual.lineTo(x + deltaX, y + deltaY);
        x += deltaX;
        
        // clone current version of Path to consider it a frame
        frames.add((Path2D.Float)visual.clone());
        
        sampleIndex++;
        tickIndex++;
      }
      else tickIndex++;
    
      eventIndex++;
    }
    
    return visual;
  }
  
  /**
   * Access frames ArrayList.
   * @return frames the ArrayList.
   */
  public ArrayList<Path2D.Float> getShapeFrames()
  {
    return frames;
  }
  
  /**
   * Helper function to determine octave impact on frequency.
   * 
   * @param key base note
   * @param octave number
   * @return appropriate frequency
   */
  private double octaveFrequency(final int key, final int octave)
  {
    return (NOTE_FREQ[key] * Math.pow(2, octave));
  }
}

