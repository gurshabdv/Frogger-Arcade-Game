import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;


public class SoundEffect{// used tp play sounds
    private Clip c;
    SoundEffect(String filename){
        setClip(filename);
    }
    public void setClip(String filename){
        try{
            File f = new File(filename);// get the sound file to be played
            c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(f));
        } catch(Exception e){ System.out.println("error"); }
    }
    public void play(){// play the sound
        c.setFramePosition(0);
        c.start();
    }
    public void stop(){// stop playing the sound
        c.stop();
    }
}