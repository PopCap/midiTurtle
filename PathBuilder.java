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
  }
  
  public Shape buildShape()
  {
    float x, y, deltaX, deltaY;
    int tickIndex, maxTrackSize, indexJump, uniqueTicks, eventIndex, sampleNumber;
    
    ArrayList<Long> tickTimes = new ArrayList<Long>();
    boolean[] activeNotes = new boolean[12];
    Path2D.Float visual = new Path2D.Float();
    Track[] midiTracks = sequence.getTracks();

    // center of window
    x = 500.0f;
    y = 500.0f;
    
    tickIndex = 0; // index for tickTimes ArrayList
    maxTrackSize = 0; // most events for a single track in MIDI
    eventIndex = 0; // index for current place in all tracks
    sampleNumber = 0; // keep track of current number of times sampled from song
    
    visual.moveTo(x, y);
//    for (int i = 1; i < 51; i++)
//    {
//      visual.lineTo(x + (i * 10), i % 2 == 0 ? y + i : y - i );
//    }
    
    
    
    /////////////////////////////////////////////////////////////////
//  int trackNumber = 0;
//  for (Track track :  sequence.getTracks()) {
//      trackNumber++;
//      System.out.println("Track " + trackNumber + ": size = " + track.size());
//      System.out.println();
//        for(int i = 0; i < track.size(); i++)
//        {
//          MidiEvent event = track.get(i);
//          System.out.print("@" + event.getTick() + " ");
//          MidiMessage message = event.getMessage();
//          if (message instanceof ShortMessage) {
//              ShortMessage sm = (ShortMessage) message;
//              System.out.print("Channel: " + sm.getChannel() + " ");
//              if (sm.getCommand() == NOTE_ON) {
//                  int key = sm.getData1();
//                  int octave = (key / 12)-1;
//                  int note = key % 12;
//                  String noteName = NOTE_NAMES[note];
//                  int velocity = sm.getData2();
//                  System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
//              } else if (sm.getCommand() == NOTE_OFF) {
//                  int key = sm.getData1();
//                  int octave = (key / 12)-1;
//                  int note = key % 12;
//                  String noteName = NOTE_NAMES[note];
//                  int velocity = sm.getData2();
//                  System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
//              } else {
//                  System.out.println("Command:" + sm.getCommand());
//              }
//          } else {
//              System.out.println("Other message: " + message.getClass());
//          }
//        }
//      }
//      
////      I think the best way to get this to work is to keep a boolean table of which notes are on at the time of sampling
////      and then figure out when to sample: I am a genius and don't tell me otherwise
//      //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//
//      System.out.println();
  //////////////////////////////////////////////////////////////////
    
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
    } 
    else
    {
      indexJump = 1;
      deltaX = 500.0f / uniqueTicks;
    }
    
    
    // loop through again taking note of which notes are activated at specific tick times to calculate
    // visual change in y later
    //ArrayList<boolean[]> notesActive = new ArrayList<boolean[]>();
    
    // simulating going through each track in parallel
    while (eventIndex < maxTrackSize)
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
            if (event.getTick() == tickTimes.get(tickIndex)) // TAKE A LOOK AT THIS AGAIN
            {
              if (sm.getCommand() == NOTE_ON)
              {
                activeNotes[sm.getData1() % 12] = true;
              }
              else if (sm.getCommand() == NOTE_OFF)
              {
                activeNotes[sm.getData1() % 12] = false;
              }
              // check whether at a designated sample time
              if (tickIndex == sampleNumber * indexJump)
              {
                deltaY = 0.0f;
                // collect current note data to move line
                for (boolean note: activeNotes)
                {
                  
                }
//                visual.lineTo(x + deltaX, i % 2 == 0 ? y + i : y - i );
              }
              tickIndex++;
              
            }
          }
        }
      }
      eventIndex++;
    }
    
      
//      for (int i=0; i < track.size(); i++)
//      {
//        
//        {
//          
//            // if a tick is chosen for sampling draw a line to new point
////            if (tickIndex == indexJump * )
//          }
//        }
//      }
//    }
      
//      for (Long tick: tickTimes)
//      {
//        System.out.println(tick);
//      }
        return visual;
  }
  
  /**
   * 
   * @param key
   * @param octave
   * @return
   */
  private double octaveFrequency(final int key, final int octave)
  {
    return (NOTE_FREQ[key] * Math.pow(octave, 2));
  }
}
