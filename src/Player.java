import java.util.Arrays;

/**
 * A class storing details about the player. A player is able to hold up to
 * 3 weapons, and starts with 100 health.
 */
public class Player {
    /**
     * The number of coins the player has
     */
    public int coins;
    
    /**
     * The number of keys the player has
     */
    public int keys;
    
    /**
     * The amount of health the player has
     */
    public int hp;
    
    /**
     * An array of up to 3 weapons the player is holding
     */
    public final Weapon[] weapons;
    
    public Player() {
        coins = 0;
        keys = 0;
        hp = 100;
        weapons = new Weapon[3];
    }
    
    /**
     * <p>Calculates the player's score.
     * 
     * <p>Coins are worth 1 point, keys are worth 100 points, and the score of
     * each weapon is determined by the weapon's score() method.
     * @return  the player's score
     */
    public int score() {
        return coins + 100 * keys + Arrays.stream(weapons)
                                          .filter(e -> e != null)
                                          .mapToInt(e -> e.score()).sum();
    }
    
    /**
     * Prints the player's weapons in a list
     */
    public void weapons() {
        System.out.println("Weapons:");
        for (int i = 1; i <= 3; i++) {
            if (Main.p.weapons[i-1] == null) {
                System.out.printf("%d - \tEmpty slot\n", i);
            } else {
                System.out.printf("%d - \t%s\n", i, Main.p.weapons[i-1]);
            }
        }
    }
    
    /**
     * Adds the specified weapon to the player's inventory, if it is not full.
     * Otherwise, ask the player which weapon they would like to discard.
     * @param wp    the weapon to add
     * @throws NullPointerException     if wp is null
     */
    public void addWeapon(final Weapon wp) {
        System.out.println(wp);
        for (int i = 0; i < weapons.length; i++) {
            // null means empty slot, we can just add it in and return
            if (weapons[i] == null) {
                System.out.println("New weapon acquired!");
                weapons[i] = wp;
                return;
            }
        }
        // discard a weapon if none of the slots are null
        // i.e. there are no empty slots
        int choice;
        System.out.println("Your inventory is full! Choose one weapon to discard");
        weapons();
        System.out.println("Select 1, 2, or 3 for slots 1, 2, or 3, "
                         + "and 0 to discard the current weapon");
        choice = Main.in.readInt(e -> e >= 0 && e <= 3);
        System.out.println("Weapon discarded!");
        // if choice is 0, inventory is unmodified
        // otherwise convert from 1-indexed to 0-indexed
        if (choice != 0) {
            weapons[choice - 1] = wp;
        }
    }
    
    /**
     * Allows the player to select a weapon to use. This method does not print
     * a list of weapons available.
     * @return  the weapon the player selected
     */
    public Weapon weaponSelect() {
        return weapons[Main.in.readInt(e -> e > 0 && e <= 3 && weapons[e - 1] != null) - 1];
    }
}
