package models;

import controllers.JukeBox;
import main.Settings;

/**
 * This class load the music from the resources folder and prepare to execute
 *
 * Refactor by
 * @author Chang Zi Jac
 */
public class Music {
    // initialize the variable
    private static float vol = Settings.Volume();

    /**
     * This method initializes the audio loader and load the music from the resources folder
     */
    public static void init() {
        JukeBox.init();

        //load background music
        JukeBox.load("/resources/Music/homemenu.mp3", "homemenu");
        JukeBox.load("/resources/Music/ingame.mp3", "ingame");
        JukeBox.load("/resources/Music/settings.mp3", "pausemenu");

        //set background music volume
        JukeBox.setVolume("homemenu", vol);
        JukeBox.setVolume("ingame", vol);
        JukeBox.setVolume("pausemenu", vol);

        //load SFX music
        JukeBox.load("/resources/SFX/impact.wav", "impact");
        JukeBox.load("/resources/SFX/crack.wav", "crack");
        JukeBox.load("/resources/SFX/destroy.wav", "destroy");

        //set SFX music volume
        JukeBox.setVolume("impact", 0);
    }

    /**
     * This method loop the home menu background music
     */
    public static void HomeStart() {
        JukeBox.loop("homemenu", 1000, 1000, JukeBox.getFrames("homemenu") - 1000);
    }

    /**
     * This method end the home menu background music
     */
    public static void HomeEnd() {JukeBox.stop("homemenu");}

    /**
     * This method loop the in-game background music
     */
    public static void GameStart() {
        JukeBox.loop("ingame", 1000, 1000, JukeBox.getFrames("ingame") - 1000);
    }

    /**
     * This method end the in-game background music
     */
    public static void GameEnd() {JukeBox.stop("ingame");}

    /**
     * This method loop the pause menu background music and pause the in-game music
     */
    public static void PauseMenu() {
        JukeBox.stop("ingame");
        JukeBox.loop("pausemenu", 1000, 1000, JukeBox.getFrames("pausemenu") - 1000);
    }

    /**
     * This method resume loop the in-game background music and pause the pause menu music
     */
    public static void PauseEnd() {
        JukeBox.stop("pausemenu");
        JukeBox.resumeLoop("ingame");

    }

    /**
     * This method play the impact sfx
     */
    public static void Impact() {JukeBox.play("impact");}

    /**
     * This method play the brick crack sfx
     */
    public static void BrickCrack() {JukeBox.play("crack");}

    /**
     * This method play the brick destroy sfx
     */
    public static void BrickDestroy() {JukeBox.play("destroy");}
}
