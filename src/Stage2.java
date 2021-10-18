import java.util.Arrays;

/**
 * Code describing all levels in stage 2
 */
public class Stage2 {
    /**
     * Whether one-time events have occured
     * level2Interview  Whether the player has attempted the level 2 interview
     * level3Ambust     Whether the player has beat the ambush in room 3
     * level4Vending    Whether the player has used the room 4 vending machine
     */
    private static boolean level2Interview = false;
    private static boolean level3Ambush = false;
    private static boolean level4Vending = false;
    
    /**
     * Resets the one-time events
     */
    public static void reset() {
        level2Interview = false;
        level3Ambush = false;
        level4Vending = false;
    }
    
    /**
     * <p>Code for level 2.1
     * 
     * <p>This level links to 2.2.
     */
    public static void level1() {
        System.out.println("|========================    2    ========|\n"
                + "|                                        V|\n"
                + "|        Tip: Coins can be used at        |\n"
                + "|        vending machines, but the        |\n"
                + "|        more coins you have at the       |\n"
                + "|        end of the game, the higher      |\n"
                + "|        your score.                      |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|=========================================|");
        Main.p.weapons();
        Util.switchRoom(Arrays.asList(new Integer[] {2}));
    }
    
    /**
     * <p>Code for level 2.2
     * 
     * <p>This level offers an interview for 1000 coins. There is only one
     * attempt.
     * 
     * <p>This level links to 2.1 and 2.3.
     */
    public static void level2() {
        System.out.println("|=====    3    ===========================|\n"
                + "|                                         |\n"
                + "|          INTERVIEW  ROOM                |\n"
                + "|             __________                 C|\n"
                + "|        |_    \\|    |/   _|              |\n"
                + "|        | |    |    |   | |              |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|==========================    1    ======|");
        Main.p.weapons();
        if (!level2Interview) {
            System.out.println("The Dungeon Master would like to invite you to create new levels.");
            if (Util.prompt("Accept the offer?")) {
                level2Interview = true;
                l2interview();
                return; // go back to the beginning of this level
            }
        }
        Util.switchRoom(Arrays.asList(new Integer[] {1, 3}));
    }
    
    /**
     * <p>Code for the level 2.2 interview
     * 
     * <p>The player is asked 5 MC questions, of which 4 must be correct.
     * 
     * <p>1000 points are rewarded upon successful completion.
     */
    private static void l2interview() {
        int score = 0;
        
        // intro
        Util.cls();
        System.out.println("So, you want to help us add more levels, eh?");
        Util.safeSleep(2000);
        System.out.println("You're going to have to pass the test first.");
        Util.safeSleep(2000);
        
        // q1: c
        System.out.println("Question 1: Which of these is illegal?\n"
                + "a) public static final double y;\n"
                + "b) private static final ArrayList<Object> y;\n"
                + "c) public package int y;");
        if (Main.in.readLine(e -> e.length() > 0).equalsIgnoreCase("c")) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong!");
        }
        
        // q2: a
        System.out.println("Question 2: Which of the following would give an "
                         + "experienced programmer an aneurism?\n"
                + "a) if (!someBool == true)\n"
                + "b) if (!someBool)\n"
                + "c) if (someBool)\n"
                + "d) if (someBool && someNumber > 3)");
        if (Main.in.readLine(e -> e.length() > 0).equalsIgnoreCase("a")) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong!");
        }
        
        // q3: b
        System.out.println("Question 3: What is an acceptable way to store a secret token?\n"
                + "a) In the program itself\n"
                + "b) In an environment variable\n"
                + "c) In a text file with permissions 777");
        if (Main.in.readLine(e -> e.length() > 0).equalsIgnoreCase("b")) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong!");
        }
        
        // q4: a
        System.out.println("Question 4: What is the correct permission for an ssh private key?\n"
                + "a) 600\n"
                + "b) 644\n"
                + "c) 777");
        if (Main.in.readLine(e -> e.length() > 0).equalsIgnoreCase("a")) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong!");
        }
        
        // q5: c
        System.out.println("Question 5: What does wget do?\n"
                + "a) we get some file!\n"
                + "b) grabs a file from another drive\n"
                + "c) downloads a file off the internet");
        if (Main.in.readLine(e -> e.length() > 0).equalsIgnoreCase("c")) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong!");
        }
        
        Util.cls();
        // check if user has passed
        if (score >= 4) {
            System.out.println("Congrats! You got the job!");
            Util.safeSleep(2000);
            System.out.println("[NARRATOR] You worked for 1 year, to create extra levels for "
                             + "the sequel of this game.");
            System.out.println("[NARRATOR] You were paid 1000 coins in exchange.");
            Main.p.coins += 1000;
            
            // trigger interviewComplete flag for alternate ending
            Stage3.interviewComplete();
        } else {
            System.out.println("You didn't pass the interview.");
        }
        System.out.println("Press Enter to continue");
        Main.in.readLine();
    }
    
    /**
     * <p>Code for level 2.3
     * 
     * <p>This level contains an ambush that cannot be bypassed.
     * 
     * <p>Links to 3.2 and 3.3.
     */
    public static void level3() {
        if (!level3Ambush) {
            l3ambush();
            level3Ambush = true;
            return; // Go back to the beginning of this level
        }
        System.out.println("|==========================    4    ======|\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                        T|\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|=====    2    ===========================|");
        Main.p.weapons();
        Util.switchRoom(Arrays.asList(new Integer[] {2, 4}));
    }
    
    /**
     * Code for the level 2.3 ambush
     */
    private static void l3ambush() {
        System.out.println("|==========================    4    ======|\n"
                + "|                                         |\n"
                + "|          AMBUSH!!!                <     |\n"
                + "|                                         |\n"
                + "|         >                       <       |\n"
                + "|            <          >                 |\n"
                + "|        >                                |\n"
                + "|                      >            <     |\n"
                + "|                                         |\n"
                + "|=====    2    ===========================|");
        System.out.println("Some weird enemy is in the way!");
        Util.safeSleep(1000);
        System.out.println("We don't know their stats...");
        Util.safeSleep(1000);
        System.out.println("Which weapon do you want to use?");
        Main.p.weapons();
        // 40% chance to deal between 1-5 damage per turn
        if (!Util.fight(Main.p.weaponSelect(), 74, () ->
            (int) (Math.random() + 0.4) * (int) (Math.random() * 5 + 1))) {
            return;
        }
        System.out.println("You defeated the enemies!");
        System.out.println("Press Enter to continue");
        Main.in.readLine();
    }
    
    /**
     * <p>Code for level 2.4
     * 
     * <p>This level features a vending machine that sells a one-hit sword.
     * 
     * <p>Links to 2.3 and 2.5.
     */
    public static void level4() {
        if (level4Vending) {
            System.out.println("|=========================================|\n"
                    + "|        | VENDING |                      |\n"
                    + "|        |=========|                      \n"
                    + "|        |         |                      5\n"
                    + "|        |         |                       \n"
                    + "|        |         |                     O|\n"
                    + "|        |         |                      |\n"
                    + "|        ===========                      |\n"
                    + "|                                         |\n"
                    + "|==========================    3    ======|");
            Main.p.weapons();
        } else {
            System.out.println("|=========================================|\n"
                    + "|        | VENDING |                      |\n"
                    + "|        |=========|                      \n"
                    + "|        |   _//   |                      5\n"
                    + "|        |  / /    |                       \n"
                    + "|        | / /     |                     O|\n"
                    + "|        | |/      |                      |\n"
                    + "|        ===========                      |\n"
                    + "|                                         |\n"
                    + "|==========================    3    ======|");
            Main.p.weapons();
            System.out.println("For 3300 coins, Vending machine offers:");
            System.out.println(new Weapon("One Hit Blade", 2000, 1));
            if (Util.prompt("Would you like to purchase it?")) {
                if (Main.p.coins < 3300) {
                    System.out.println("Not enough coins!");
                } else {
                    Main.p.coins -= 3300;
                    Main.p.addWeapon(new Weapon("One Hit Blade", 2000, 1));
                    level4Vending = true;
                }
            }
        }
        Util.switchRoom(Arrays.asList(new Integer[] {3, 5}));
    }
    
    /**
     * <p>Code for level 2.5
     * 
     * <p>This level is a boss fight, and rewards the user with 5000 coins and
     * Shiny Axe upon completion.
     * 
     * <p>This level links to 3.1.
     */
    public static void level5() {
        System.out.println("|=========================================|\n"
                + "|                                         |\n"
                + "|              BOSS FIGHT!                |\n"
                + "|                          hp = 100       |\n"
                + "|                  /\\                     |\n"
                + "|            ||   /  \\   ||               |\n"
                + "|            \\\\=== .. ===//               |\n"
                + "|               /______\\                  |\n"
                + "|                                         |\n"
                + "|=========================================|");
        System.out.println("Which weapon do you want to use?");
        Main.p.weapons();
        // 40% chance to deal between 1-8 damage per turn
        if (!Util.fight(Main.p.weaponSelect(), 100, () ->
            (int) (Math.random() + 0.4) * (int) (Math.random() * 8 + 1))) {
            return;
        }
        // defeated the boss message
        System.out.println("You defeated the boss!");
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        Util.cls();
        
        // prize message
        Main.p.addWeapon(new Weapon("Shiny Axe", 20, 40));
        Util.safeSleep(1000);
        System.out.println("5000 coins acquired!");
        Main.p.coins += 5000;
        Util.safeSleep(2000);
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        Util.cls();
        
        // move on to next level
        System.out.println(">>> I can see clearly now the light is here");
        Util.safeSleep(2000);
        System.out.println(">>> I can see all obstacles in the way");
        Util.safeSleep(2000);
        System.out.println(">>> And I can see that this stage is the last one");
        Util.safeSleep(2000);
        System.out.println(">>> ...");
        Util.safeSleep(2000);
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        Main.stage = 3;
        Main.level = 1;
    }
}
