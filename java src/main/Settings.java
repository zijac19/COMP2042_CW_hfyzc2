package main;

/**
 * This class set up the volume of the music that imported
 *
 * Refactor by
 * @author Chang Zi Jac
 */
public class Settings {
    // initialize the sound volume
    private static float SoundVolume = -10;

    /**
     * This is an empty method
     */
    public Settings() {
    }

    /**
     * This method set up the sound volume
     */
    public static float Volume() {
        return SoundVolume;
    }
}
