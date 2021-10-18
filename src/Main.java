import java.lang.reflect.InvocationTargetException;

import com.jdabtieu.input.Input;

/**
 * DungeonEscape is a text-based adventure game that follows the main character with amnesia as
 * they traverse a vast dungeon with three floors (stages). Solve puzzles, defeat enemies
 * and bosses, gather coins and custom weapons, and find the way out!
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2021-10-21
 */
public class Main {
    /**
     * Debug mode disables music and time delays. Must be off in production.
     */
    public static final boolean DEBUG = false;
    
    /**
     * Wrapper for scanner to read input. All methods and classes must use this
     * Input object for reading user input.
     * See com.jdabtieu.input.Input.java
     */
    public static final Input in = new Input();
    
    /**
     * An object to store data about the player
     * See Player.java
     */
    public static Player p;
    
    /**
     * <p>These control the room shown. Players can travel between most levels,
     * but must beat a boss to advance stages.
     * 
     * <p>If the stage is above 3, that means the game is over. In that case,
     * a nonnegative level number indicates victory, and a loss otherwise.
     */
    public static int stage;
    public static int level;

    public static void main(String[] args) {
        while (true) {
            reset();
            
            // Main menu
            Util.banner();
            System.out.println("\n\t\t1 - Start Game\n\t\t2 - Quit");
            if (in.readInt(e -> e >= 1 && e <= 2) == 2) {
                // quit
                in.close();
                return;
            }
            
            // Introduction - leading to stage 1.1
            Util.cls();
            intro();
            Util.initAudio("assets/Mellohi.wav", true);
            
            while (stage < 4) {
                try {
                    // Print level header (stage #, level #, # of coins)
                    Util.cls();
                    System.out.printf("Stage %d Level %d\t\t%d coins\t\t%d hp\n",
                                      stage, level, p.coins, p.hp);
                    
                    // Call the method corresponding to the current level
                    Class.forName("Stage" + stage).getDeclaredMethod("level" + level).invoke(null);
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                         IllegalArgumentException | InvocationTargetException e) {
                    // These errors should never occur as the stage and level
                    // variables should only contain values corresponding to
                    // actual levels (and thus methods)
                    System.err.println("A catastrophic error has occured.");
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
            // Stop the audio in the main menu
            if (!DEBUG) {
                Util.stopAudio();
            }
            
            // Check if the player has won or lost and call the appropriate
            // method
            if (level < 0) {
                lose();
            } else {
                win();
            }
        }
    }
    
    /**
     * <p>Resets the game
     * 
     * <p>This resets the player object (including coins, keys, and weapons) and
     * all the levels
     */
    public static void reset() {
        p = new Player();
        stage = 1;
        level = 1;
        Stage1.reset();
        Stage2.reset();
        Stage3.reset();
    }
    
    /**
     * Prints banner message for a loss
     */
    public static void lose() {
        System.out.println("Yikes...you lost!");
        Util.safeSleep(1000);
        System.out.println("Press Enter to continue");
        in.readLine();
    }
    
    /**
     * Prints banner message and credits for a win
     */
    public static void win() {
        final String[] creds = {
            "Congratulations! You beat the game!",
            "",
            String.format("Final Score: %d", p.score()),
            "",
            "===================\n"
            + "|                 |\n"
            + "|     CREDITS     |\n"
            + "|                 |\n"
            + "===================",
            "Created By:",
            "\tJonathan Wu",
            "",
            "Music and Sound:",
            "\tMellohi - C418",
            "\tQumu - Doodle Champion Island Games: Ending",
            "",
            "Beta Testers:",
            "\tAkshaya Varakunan",
            "\tCullen Ye",
            "\tRay Hang",
            "",
            "More projects:",
            "\thttps://github.com/jdabtieu",
            "",
            "Thanks for playing!",
            "",
            "Press Enter to continue"
        };
        final int lineTime = 38000 / creds.length / 2;
        Util.initAudio("assets/Win.wav", false);
        Util.cls();
        Util.banner();
        Util.safeSleep(2000);
        System.out.println();
        for (final String line : creds) {
            Util.safeSleep(lineTime);
            System.out.println();
            Util.safeSleep(lineTime);
            System.out.println(line);
        }
        in.readLine();
        Util.stopAudio();
    }
    
    /**
     * Prints the pre-game intro
     */
    public static void intro() {
        System.out.println(">>> Where am I?");
        Util.safeSleep(1500);
        System.out.println(">>> ...");
        Util.safeSleep(1500);
        System.out.println(">>> Maybe I should open my eyes.");
        Util.safeSleep(2000);
        System.out.println(">>> There's a torch. This looks like my basement.");
        Util.safeSleep(2500);
        System.out.println(">>> Where am I?");
        Util.safeSleep(2500);
    }
    
    /**
     * Debugging function to jump to a specific stage and level. This function
     * should never be called in production code.
     * 
     * @param stage the stage to jump to
     * @param level the level to jump to
     */
    @SuppressWarnings("unused")
    private static void jump(int stage, int level) {
        Main.stage = stage;
        Main.level = level;
    }
}
