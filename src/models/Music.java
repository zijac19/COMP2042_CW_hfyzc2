package models;

import handlers.JukeBox;
import main.Settings;

public class Music {
    private static float vol = Settings.Volume();

    public static void init() {
        JukeBox.init();

        //load background music
        JukeBox.load("/resources/Music/homemenu.mp3", "homemenu");
        JukeBox.load("/resources/Music/ingame.mp3", "ingame");

        //set background music
        JukeBox.setVolume("homemenu", vol);
        JukeBox.setVolume("ingame", vol);

        //load SFX music
        JukeBox.load("/resources/SFX/impact.wav", "impact");
        JukeBox.load("/resources/SFX/crack.wav", "crack");
        JukeBox.load("/resources/SFX/destroy.wav", "destroy");
    }

    public static void HomeStart() {
        JukeBox.loop("homemenu", 1000, 1000, JukeBox.getFrames("homemenu") - 1000);
    }

    public static void HomeEnd() {JukeBox.stop("homemenu");}

    public static void GameStart() {
        JukeBox.loop("ingame", 1000, 1000, JukeBox.getFrames("ingame") - 1000);
    }

    public static void GameEnd() {JukeBox.stop("ingame");}

    public static void Impact() {JukeBox.play("impact");}

    public static void BrickCrack() {JukeBox.play("crack");}

    public static void BrickDestroy() {JukeBox.play("destroy");}
}
