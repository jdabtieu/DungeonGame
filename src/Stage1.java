import java.util.Arrays;

/**
 * Code describing all levels in stage 1
 */
public class Stage1 {
    /**
     * Whether one-time events have occured
     * key2Found    Whether the player has received the key in room 2
     * coins3Found  Whether the player has solved the combination lock in room 3
     * weapon4Found Whether the player has found the wooden axe in room 4
     */
    private static boolean key2Found = false;
    private static boolean coins3Found = false;
    private static boolean weapon4Found = false;
    
    /**
     * Resets the one-time events
     */
    public static void reset() {
        key2Found = false;
        coins3Found = false;
        weapon4Found = false;
    }
    
    /**
     * <p>Code for Level 1.1
     * 
     * <p>This level introduces the user to room numbers.
     * 
     * <p>This level links to 1.2.
     */
    public static void level1() {
        System.out.println("|==============================    2    ==|\n"
                + "|                                  /\\     |\n"
                + "|                                  ||     |\n"
                + "|     Tip: Look around the room    ||     |\n"
                + "|     for room numbers and         ||     |\n"
                + "|     clues.                     a room   |\n"
                + "|                                number   |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|=========================================|");
        Main.p.weapons();
        Util.switchRoom(Arrays.asList(new Integer[] {2}));
    }
    
    /**
     * <p>Code for level 1.2
     * 
     * <p>This level gives the player a key (to be used in level 3.5), and 
     * also contains part of the answer for the combination lock in level 1.3.
     * 
     * <p>This level links to 1.1, 1.3, and 1.4.
     */
    public static void level2() {
        System.out.println("|=========================================|\n"
                + "|                                         |\n"
                + "                                          |\n"
                + "3     Tip: You can return to previous     |\n"
                + "      rooms at any time.                   \n"
                + "|                                         4\n"
                + "|                                          \n"
                + "|                                         |\n"
                + "|1..34.                                   |\n"
                + "|======================    1    ==========|");
        if (!key2Found) {
            System.out.println("You found a key!");
            Main.p.keys++;
            key2Found = true;
        }
        Main.p.weapons();
        Util.switchRoom(Arrays.asList(new Integer[] {1, 3, 4}));
    }
    
    /**
     * <p>Code for level 1.3
     * 
     * <p>This level has the combination lock, and the second part of the answer
     * for the combination lock. Upon successful completion, the player gets
     * 400 coins.
     * 
     * <p>This level links to 1.2.
     */
    public static void level3() {
        System.out.println("|=========================================|\n"
                + "|               |?|?|?|?|?|?|             |\n"
                + "|                                         |\n"
                + "|                                          \n"
                + "|                                         2\n"
                + "|                                          \n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                   .42..2|\n"
                + "|=========================================|");
        Main.p.weapons();
        // ask the user if they want to try the lock if they haven't already
        // gotten the prize
        while (!coins3Found && Util.prompt("Do you want to try the combination lock?")) {
            System.out.println("Guess: ");
            if (Main.in.readLine(e -> e.length() > 0).equals("142342")) {
                System.out.println("You found 400 coins!");
                Main.p.coins += 400;
                coins3Found = true;
            } else {
                System.out.println("Incorrect combination!");
            }
        }
        Util.switchRoom(Arrays.asList(new Integer[] {2}));
    }
    
    /**
     * <p>Code for level 1.4
     * 
     * <p>This level gives the user a wooden axe to use in the boss fight in
     * level 1.5.
     * 
     * <p>This level links to 1.3 and 1.5.
     * 
     */
    public static void level4() {
        System.out.println("|=========================================|\n"
                + "|                                         |\n"
                + "|           Tip: You can hold up          |\n"
                + "            to 3 weapons. Any more         \n"
                + "2           must be discarded.            5\n"
                + "                                           \n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|=========================================|");
        if (!weapon4Found) {
            Main.p.addWeapon(new Weapon("Wooden Axe", 3, 30));
            weapon4Found = true;
        }
        Main.p.weapons();
        Util.switchRoom(Arrays.asList(new Integer[] {2, 5}));
    }
    
    /**
     * <p>Code for level 1.5
     * 
     * <p>This level is a boss fight, and rewards the user with 3000 coins and
     * Cubic Scales upon completion.
     * 
     * <p>This level links to 2.1.
     */
    public static void level5() {
        System.out.println("|=========================================|\n"
                + "|                                         |\n"
                + "|              BOSS FIGHT!                |\n"
                + "|               _______                   |\n"
                + "|               |  __  |    hp = 30       |\n"
                + "|               | |__| |                  |\n"
                + "|               |______|                  |\n"
                + "|                                         |\n"
                + "|                                         |\n"
                + "|=========================================|");
        System.out.println("Which weapon do you want to use?");
        Main.p.weapons();
        // 30% chance to deal between 1-5 damage per turn
        if (!Util.fight(Main.p.weaponSelect(), 30, () ->
                (int) (Math.random() + 0.3) * (int) (Math.random() * 5 + 1))) {
            return;
        }
        // defeated the boss message
        System.out.println("You defeated the boss!");
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        Util.cls();
        
        // prize message
        Main.p.addWeapon(new Weapon("Cubic Scales", 5, 30));
        Util.safeSleep(1000);
        System.out.println("3000 coins acquired!");
        Main.p.coins += 3000;
        Util.safeSleep(2000);
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        Util.cls();
        
        // move onto next level
        System.out.println(">>> That was the longest flight of stairs ever.");
        Util.safeSleep(2000);
        System.out.println(">>> It's brighter now...does that mean I'm closer to the surface?");
        Util.safeSleep(2000);
        System.out.println("Press Enter to continue");
        Main.in.readLine();
        Main.stage = 2;
        Main.level = 1;
    }
}
