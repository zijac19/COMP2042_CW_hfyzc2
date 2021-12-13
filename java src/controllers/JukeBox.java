package controllers;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * This class loads and play the audio clips
 *
 * Refactor by
 * @author Chang Zi Jac
 */
public class JukeBox {
    // initialize the variables
    private static HashMap<String, Clip> clips;
    private static int gap;

    /**
     * This method creates new clips HashMap
     */
    public static void init() {
        clips = new HashMap<String, Clip>();
        gap = 0;
    }

    /**
     * This method loads up the audio clip located at the path "s" and store it in the HashMap with key "n"
     * @param s
     * @param n
     */
    public static void load(String s, String n) {
        if(clips.get(n) != null) return;
        Clip clip;
        try {
            InputStream in = JukeBox.class.getResourceAsStream(s);
            InputStream bin = new BufferedInputStream(in);
            AudioInputStream ais =
                    AudioSystem.getAudioInputStream(bin);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
            clips.put(n, clip);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method plays the audio clip
     *
     * @param s
     */
    public static void play(String s) {
        play(s, gap);
    }

    /**
     * This method plays the audio clip
     *
     * @param s
     * @param i
     */
    public static void play(String s, int i) {
        Clip c = clips.get(s);
        if(c == null) return;
        if(c.isRunning()) c.stop();
        c.setFramePosition(i);
        while(!c.isRunning()) c.start();
    }

    /**
     * This method stops the audio clip
     *
     * @param s
     */
    public static void stop(String s) {
        if(clips.get(s) == null) return;
        if(clips.get(s).isRunning()) clips.get(s).stop();
    }

    /**
     * This method resumes the audio clip from the latest location
     *
     * @param s
     */
    public static void resume(String s) {
        if(clips.get(s).isRunning()) return;
        clips.get(s).start();
    }

    /**
     * This method resume the audio loop
     *
     * @param s
     */
    public static void resumeLoop(String s) {
        Clip c = clips.get(s);
        if(c == null) return;
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * This method loops the audio clip
     *
     * @param s
     */
    public static void loop(String s) {
        loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
    }

    /**
     * This method loop the audio clip
     *
     * @param s
     * @param frame
     */
    public static void loop(String s, int frame) {
        loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
    }

    /**
     * This method loop the audio clip
     *
     * @param s
     * @param start
     * @param end
     */
    public static void loop(String s, int start, int end) {
        loop(s, gap, start, end);
    }

    /**
     * This method loop the audio clip
     *
     * @param s
     * @param frame
     * @param start
     * @param end
     */
    public static void loop(String s, int frame, int start, int end) {
        Clip c = clips.get(s);
        if(c == null) return;
        if(c.isRunning()) c.stop();
        c.setLoopPoints(start, end);
        c.setFramePosition(frame);
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * This method sets the position of the audio clip
     *
     * @param s
     * @param frame
     */
    public static void setPosition(String s, int frame) {
        clips.get(s).setFramePosition(frame);
    }

    /**
     * This method gets the audio clip from the directory
     *
     * @param s
     * @return clips.get(s).getFrameLenght()
     */
    public static int getFrames(String s) { return clips.get(s).getFrameLength(); }

    /**
     * This method get the position of the audio clip
     *
     * @param s
     * @return clips.get(s).getFramePosition()
     */
    public static int getPosition(String s) { return clips.get(s).getFramePosition(); }

    /**
     * This method close the audio clip
     *
     * @param s
     */
    public static void close(String s) {
        stop(s);
        clips.get(s).close();
    }

    /**
     * This method sets the volume of the audio clip
     *
     * @param s
     * @param f
     */
    public static void setVolume(String s, float f) {
        Clip c = clips.get(s);
        if(c == null) return;
        FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
        vol.setValue(f);
    }

    /**
     * This method checks the audio clip's running status
     *
     * @param s
     * @return c.isRunning()
     */
    public static boolean isPlaying(String s) {
        Clip c = clips.get(s);
        if(c == null) return false;
        return c.isRunning();
    }

}