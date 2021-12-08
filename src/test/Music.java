package test;

import test.JukeBox;

public class Music {

    public static void init() {
        JukeBox.init();

        //load background music
        JukeBox.load("/resources/Music/homemenu.mp3", "homemenu");
        JukeBox.load("/resources/Music/ingame.mp3", "ingame");

        //set background music
        JukeBox.setVolume("homemenu", -10);
        JukeBox.setVolume("ingame", -10);
    }

    public static void HomeStart() {
        JukeBox.loop("homemenu", 1000, 1000, JukeBox.getFrames("homemenu") - 1000);
    }

    public static void HomeEnd() {JukeBox.stop("homemenu");}

    public static void GameStart() {
        JukeBox.loop("ingame", 1000, 1000, JukeBox.getFrames("ingame") - 1000);
    }

    public static void GameEnd() {JukeBox.stop("ingame");}
}
