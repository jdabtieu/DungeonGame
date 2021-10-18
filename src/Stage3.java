import java.util.Arrays;

/**
 * Code describing all levels in stage 3
 */
public class Stage3 {
    /**
     * Whether one-time events have occured
     * level2Ambush     Whether the player has beat the ambush in level 2
     * level4Hp         Whether the player has gotten the health pot in room 4
     * level5Quiz       Whether the player has attempted the quiz in level 5
     * interviewComplete Whether the player has completed the interview in stage
     *                   2 (this triggers an alternative ending)
     */
    private static boolean level2Ambush = false;
    private static boolean level4Hp = false;
    private static boolean level5Quiz = false;
    private static boolean interviewComplete = false;
    
    /**
     * Resets the one-time events
     */
    public static void reset() {
        level2Ambush = false;
        level4Hp = false;
        level5Quiz = false;
        interviewComplete = false;
    }
    
    /**
     * Sets the interview complete flag for the alternate ending
     */
    public static void interviewComplete() {
        interviewComplete = true;
    }
    
    /**
     * <p>Code for level 3.1
     * 
     * <p>This level links to 3.2.
     */
    public static void level1() {
        System.out.println("|========================    2    ========|\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|        Tip: Weapons cannot be           |\n"
                + "|        switched out mid-fight, so       |\n"
                + "|        make sure the weapon you         |\n"
                + "|        choose has enough durability.    |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|=========================================|");
        Main.p.weapons();
        Util.switchRoom(Arrays.asList(new Integer[] {2}));
    }
    
    /**
     * <p>Code for level 3.2
     * 
     * <p>This level contains an ambush that cannot be bypassed.
     * 
     * <p>This level links to 3.1 and 3.3.
     */
    public static void level2() {
        if (!level2Ambush) {
            l2ambush();
            level2Ambush = true;
            return; // Go back to the beginning of this level
        }
        System.out.println("|==========================    3    ======|\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|=====    1    ===========================|");
        Main.p.weapons();
        Util.switchRoom(Arrays.asList(new Integer[] {1, 3}));
    }
    
    /**
     * Code for the level 3.2 ambush
     */
    private static void l2ambush() {
        System.out.println("|==========================    3    ======|\n"
                + "|                                         |\n"
                + "|          AMBUSH!!!                <     |\n"
                + "|                                         |\n"
                + "|         >                       <       |\n"
                + "|            <          >                 |\n"
                + "|        >                                |\n"
                + "|                      >            <     |\n"
                + "|                                         |\n"
                + "|=====    1    ===========================|");
        System.out.println("The unknown enemies are back for revenge!");
        Util.safeSleep(1000);
        System.out.println("Their stats are buffed, but still unknown.");
        Util.safeSleep(1000);
        System.out.println("Which weapon do you want to use?");
        Main.p.weapons();
        // 50% chance to deal between 1-5 damage per turn
        if (!Util.fight(Main.p.weaponSelect(), 98, () ->
            (int) (Math.random() + 0.5) * (int) (Math.random() * 5 + 1))) {
            return;
        }
        System.out.println("You defeated the enemies again!");
        System.out.println("Press Enter to continue");
        Main.in.readLine();
    }
    
    /**
     * <p>Code for level 3.3
     * 
     * <p>This level contains a maze, in which stepping on spikes deals 2
     * damage. Upon making it to the end, the player will be dropped into level
     * 3.4 with no way back.
     * 
     * <p>This level links to 3.4.
     */
    public static void level3() {
        final char[][] level = {
            "|================================ 4 ======|".toCharArray(),
            "|#       ##############      ##### #######|".toCharArray(),
            "|# ##### ############## #### #####   #####|".toCharArray(),
            "|# ###                  #### ####### #####|".toCharArray(),
            "|# ######## #### ### #######         #####|".toCharArray(),
            "|# ##       #### ### ############ ########|".toCharArray(),
            "|# ## ##### #### ### ############     ####|".toCharArray(),
            "|# ## #####      ### ################ ####|".toCharArray(),
            "|# ## ##############    #######       ####|".toCharArray(),
            "|=== X ===================================|".toCharArray()
        };
        // starting and ending coordinates
        int r = 9, c = 5;
        final int endR = 1, endC = 34;
        for (char[] e : level) {
            System.out.println(e);
        }
        Main.p.weapons();
        System.out.println("Oh no, a maze is in the way!");
        Util.safeSleep(1000);
        if (!Util.prompt("Try the maze?")) {
            Main.level = 2;
            return; // go back to the previous level
        }
        
        // instructions
        System.out.println("Enter a sequence of moves to get to the end");
        Util.safeSleep(1000);
        System.out.println("Type U, L, D, R for up, left, down, or right");
        Util.safeSleep(1000);
        System.out.println("If you step on the spikes, you will lose health");
        Util.safeSleep(1000);
        
        // prompt for moves
        System.out.print(">>> ");
        String moves = Main.in.readLine(e -> e.matches("[uldrULDR]+"),
                                        "That's an invalid path").toUpperCase();
        
        // simulate user moves
        for (int i = 0; i < moves.length(); i++) {
            if (moves.charAt(i) == 'U') {
                r = Math.max(1, r - 1);
            } else if (moves.charAt(i) == 'D') {
                r = Math.min(8, r + 1);
            } else if (moves.charAt(i) == 'R') {
                c = Math.min(41, c + 1);
            } else {
                c = Math.max(1, c - 1);
            }
            
            // print current position
            Util.cls();
            for (int j = 0; j < level.length; j++) {
                for (int k = 0; k < level[0].length; k++) {
                    if (j == r && k == c) {
                        System.out.print('X');
                    } else {
                        System.out.print(level[j][k]);
                    }
                }
                System.out.println();
            }
            
            // check if player stepped on a spike
            if (level[r][c] == '#') {
                System.out.println("Ouch! You stepped on a spike!");
                Main.p.hp -= 2;
                System.out.println("Health left: " + Math.max(Main.p.hp, 0));
                Util.safeSleep(300);
                // end the game if player dead
                if (Main.p.hp <= 0) {
                    Util.triggerLoss();
                    return;
                }
            }
            Util.safeSleep(200);
        }
        // send player back to beginning if they didn't get to the end
        if (r != endR || c != endC) {
            System.out.println("Uh oh, you didn't make it to the end and got lost.");
            Util.safeSleep(1000);
            System.out.println("A mysterious force has brought you back to the beginning.");
            Util.safeSleep(1000);
            System.out.println("Press Enter to continue");
            Main.in.readLine();
            return; // go back to beginning of this level
        }
        System.out.println("You completed the maze!");
        Util.safeSleep(1000);
        System.out.println("Unfortunately, the exit door was fake, and you fell into a dark room.");
        Util.safeSleep(1000);
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        Main.level = 4;
    }
    
    /**
     * <p>Code for level 3.3
     * 
     * <p>This level helps the player to 80 hp (or has no effect if the player
     * has more than 80 hp).
     * 
     * <p>This level links to 3.5.
     */
    public static void level4() {
        System.out.println("|========================    5    ========|\n"
                + "|                  __   __                |\n"
                + "|                 /  \\ /  \\               |\n"
                + "|                 |  _|_  |               |\n"
                + "|                   / | \\                 |\n"
                + "|                  /  |  \\                |\n"
                + "|                  |\\___/|                |\n"
                + "|                   \\___/                 |\n"
                + "|                                         |\n"
                + "|=========================================|");
        if (!level4Hp) {
            System.out.println("The fountain of youth has restored you to 80 hp");
            Util.safeSleep(1000);
            System.out.println("Press Enter to continue");
            Main.in.readLine();
            level4Hp = true;
            Main.p.hp = Math.max(Main.p.hp, 80);
        }
        Main.p.weapons();
        Util.switchRoom(Arrays.asList(new Integer[] {5}));
    }
    
    /**
     * <p>Code for level 3.5
     * 
     * <p>This level contains a quiz show with two questions, which award 1200
     * coins for each correct answer. The player only has one attempt.
     * 
     * <p>This level links to 3.4 and 3.6.
     */
    public static void level5() {
        System.out.println("|========================    6    ========|\n"
                + "|                    |                    |\n"
                + "|     QUIZ SHOW!     |                    |\n"
                + "|   Admission: 1 key |                    |\n"
                + "|                    |                    |\n"
                + "|                    |                    |\n"
                + "|                     \\                   |\n"
                + "|                      \\                  |\n"
                + "|                    |                    |\n"
                + "|========================    4    ========|");
        if (!level5Quiz) {
            System.out.println("A quiz show is running, but requires one key");
            Util.safeSleep(1000);
            if (Util.prompt("Do you want to use your key?")) {
                Main.p.keys--;
                level5Quiz = true;
                
                level5Trivia("Guess the phrase\n"
                           + "_ _ _ _ _   _ _ _ _ _   _ _ _ _   _ _ _   _ _\n"
                           + "80s Song Lyrics", "never gonna give you up");
                Util.safeSleep(2000);
                level5Trivia("Finish the word using previous clues:\n"
                           + "_ E _ _ _ R", "vector");
                Util.safeSleep(1000);
                System.out.println("Quiz Show over!");
                Util.safeSleep(1000);
                System.out.println("Press Enter to continue");
                Main.in.readLine();
                return; // go back to start of level
            }
        }
        Main.p.weapons();
        Util.switchRoom(Arrays.asList(new Integer[] {4, 6}));
    }
    
    /**
     * Helper function for quiz show. The question is printed, and the player's
     * answer is compared to the reference answer (case-insensitive). 1200
     * coins are awarded on a correct answer.
     * @param ques  the question to be asked
     * @param ans   the reference (correct) answer
     */
    private static void level5Trivia(String ques, String ans) {
        System.out.println(ques);
        System.out.print(">>> ");
        if (Main.in.readLine().trim().replace(" ", "").equalsIgnoreCase(ans.replace(" ", ""))) {
            System.out.println("That is correct!!!!!!");
            Util.safeSleep(1000);
            System.out.println("You won: 1200 coins");
            Main.p.coins += 1200;
        } else {
            System.out.println("Unfortunately, that's wrong");
        }
    }
    
    /**
     * <p>Code for level 3.6
     * 
     * <p>This level is a boss fight, and rewards the user with 5000 coins upon completion.
     * 
     * <p>This level links to 3.7.
     */
    public static void level6() {
        System.out.println("|=========================================|\n"
                + "|              BOSS FIGHT!                |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|              Mr. Anthony                |\n"
                + "|                hp = 850                 |\n"
                + "|            damage = too much            |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|=========================================|");
        System.out.println("Which weapon do you want to use?");
        Main.p.weapons();
        // 70% chance to deal 2 damage and 30% chance to deal 1 damage per turn
        if (!Util.fight(Main.p.weaponSelect(), 850, () -> (int) (Math.random() + 1.7))) {
            return;
        }
        
        // defeated the boss message
        System.out.println("You defeated the boss!");
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        Util.cls();
        
        // prize message
        System.out.println("5000 coins acquired!");
        Main.p.coins += 5000;
        Util.safeSleep(2000);
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        Util.cls();
        
        // move on to next level
        System.out.println(">>> ...");
        Util.safeSleep(2000);
        System.out.println(">>> There's a door");
        Util.safeSleep(2000);
        System.out.println(">>> ...");
        Util.safeSleep(2000);
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        Main.level = 7;
    }
    
    /**
     * <p>Code for level 3.7
     * 
     * <p>This level comes right after the final boss fight, and has the door that is alluded to.
     * The player is forced to press Enter, starting the final storyline, and triggering a win.
     * 
     * <p>This level is the last level.
     */
    public static void level7() {
        System.out.println("|=========================================|\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                          \n"
                + "|                            ------------> \n"
                + "|                                          \n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|=========================================|");
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        level7End();
        Util.triggerWin();
    }
    
    /**
     * Storyline for level 3.7.
     */
    private static void level7End() {
        final String[] story = {
            ">>> It's the outside world!",
            ">>> I can see the brilliant blue sky!",
            ">>> And plants too!",
            ">>> I wonder how long I've been stuck here",
            String.format(">>> %d years, I think?", interviewComplete ? 4 : 3),
            interviewComplete ? ">>> Well, time to try out the sequel I made..."
                              : ">>> Well, time to catch up on what I missed..."
        };
        for (String line : story) {
            Util.safeSleep(1200);
            System.out.println();
            Util.safeSleep(1200);
            System.out.println(line);
        }
        Util.safeSleep(1200);
        System.out.println("Press Enter to continue");
        Main.in.readLine();
    }
}
