import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * A collection of utility functions for the Dungeon game.
 */
public class Util {
    /**
     * These objects control the background music used in initAudio() and
     * stopAudio()
     */
    private static Clip clip;
    private static final LineListener audioListener = e -> {
        if (e.getType() == LineEvent.Type.STOP) {
            clip.setFramePosition(0);
            clip.start();
        }
    };
    
    
    /**
     * Sleeps for at least mills milliseconds when not in DEBUG mode. If interrupted, the
     * function will immediately return instead of throwing an InterruptedException.
     * @param mills the length of time to sleep in milliseconds
     * @throws IllegalArgumentException if the value of mills is negative
     */
    public static void safeSleep(int mills) {
        if (Main.DEBUG) { // do not sleep in DEBUG mode
            return;
        }
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {} // interruption not important
    }
    
    /**
     * Clears the screen
     */
    public static void cls() {
        char[] buf;
        if (Main.DEBUG) { // decrease the number of lines printed in DEBUG mode
            buf = new char[10];
        } else {
            buf = new char[100];
        }
        Arrays.fill(buf, '\n');
        System.out.println(buf);
    }
    
    /**
     * Prints the game banner
     */
    public static void banner() {
        cls();
        System.out.println("//==============================================================================\\\\\n"
            + "||  _____                                     ______                            ||\n"
            + "|| |  __ \\                                   |  ____|                           ||\n"
            + "|| | |  | |_   _ _ __   __ _  ___  ___  _ __ | |__   ___  ___ __ _ _ __   ___   ||\n"
            + "|| | |  | | | | | '_ \\ / _` |/ _ \\/ _ \\| '_ \\|  __| / __|/ __/ _` | '_ \\ / _ \\  ||\n"
            + "|| | |__| | |_| | | | | (_| |  __/ (_) | | | | |____\\__ \\ (_| (_| | |_) |  __/  ||\n"
            + "|| |_____/ \\__,_|_| |_|\\__, |\\___|\\___/|_| |_|______|___/\\___\\__,_| .__/ \\___|  ||\n"
            + "||                     __/  |                                     | |           ||\n"
            + "||                     |___/                                      |_|           ||\n"
            + "||                                  A game by Jonathan Wu       ICS4U, 2021     ||\n"
            + "\\\\==============================================================================//");
    }
    
    /**
     * Allows the user to switch to one of the rooms specified by rooms
     * @param rooms a List of valid rooms for the user to switch to
     * @throws NullPointerException if rooms is null
     * @throws IllegalArgumentException if rooms is empty
     */
    public static void switchRoom(final List<Integer> rooms) {
        if (rooms.size() < 1) throw new IllegalArgumentException("rooms cannot be empty!");
        System.out.println("Which room would you like to enter?");
        Main.level = Main.in.readInt(e -> rooms.contains(e));
    }
    
    /**
     * Prompts the user with a yes/no question, and waits for a y/n response
     * @param pmt   The prompt
     * @return      true if the answer is y, and false if the answer is n
     */
    public static boolean prompt(final String pmt) {
        System.out.println(pmt + " [y/n]");
        // ensure input is 1 letter and a capital/lowercase 'y' or 'n'
        return Main.in.readLine(e -> e.length() == 1 && "yYnN".indexOf(e) != -1)
                      .equalsIgnoreCase("y");
    }
    
    /**
     * Triggers a win, which will play the ending credits
     */
    public static void triggerWin() {
        Main.stage = Integer.MAX_VALUE;
        Main.level = 1;
    }
    
    /**
     * Triggers a loss, if the player runs out of health
     */
    public static void triggerLoss() {
        Main.stage = Integer.MAX_VALUE;
        Main.level = -1;
    }
    
    /**
     * Performs a fight with an enemy. The player loses if they fall to 0 health
     * and the player wins if the enemy falls to 0 health.
     * @param wp            the weapon to be used
     * @param enemyHealth   the starting health of the enemy
     * @param e             a function describing the enemy's attack pattern
     * @return              whether the player won
     */
    public static boolean fight(final Weapon wp, int enemyHealth, final EnemyAttackPattern e) {
        while (enemyHealth > 0 && Main.p.hp > 0) { // simulate attacks
            System.out.println("Enemy Health: " + enemyHealth);
            System.out.println("Your health: " + Main.p.hp);
            System.out.println();
            enemyHealth -= wp.attack();
            Main.p.hp -= e.attack();
            Util.safeSleep(800);
        }
        
        // player lost
        if (Main.p.hp <= 0) {
            System.out.println("Enemy Health: " + enemyHealth);
            System.out.println("Your Health: 0");
            triggerLoss();
            return false;
        }
        
        // player won
        System.out.println("Enemy Health: 0");
        System.out.println("Your health: " + Main.p.hp);
        return true;
    }
    
    /**
     * Starts the audio track
     * @param relPath   a relative path to the file to be played
     * @param repeat    whether the music should loop forever
     */
    public static void initAudio(final String relPath, final boolean repeat) {
        if (Main.DEBUG) { // do not play audio in DEBUG mode
            return;
        }
        URL is;
        AudioInputStream ais;
        try {
            // Loading the audio file from the JAR
            is = Main.class.getResource("/" + relPath);
            if (is == null) { // Loading the audio file from the filesystem
                is = new URL("file:" + relPath);
            }
            ais = AudioSystem.getAudioInputStream(is);
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, ais.getFormat()));
            if (repeat) {
                clip.addLineListener(audioListener);
            }
            clip.open(ais);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("[WARNING] Audio failed to start.");
        }
    }
    
    /**
     * Stops the audio track
     */
    public static void stopAudio() {
        if (clip == null) { // Audio failed to start; that's fine
            return;
        }
        clip.removeLineListener(audioListener);
        clip.stop();
        clip.close();
        clip = null; // Prevent access to the clip after closing
    }
}
