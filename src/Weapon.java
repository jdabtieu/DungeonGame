/**
 * <p>A class storing details about a weapon
 * 
 * <p>A weapon deals 0 damage if it has no durability left, and weapons cannot
 * be switched out during battle.
 */
public class Weapon {
    /**
     * The name of the weapon
     */
    private final String name;
    
    /**
     * The amount of damage the weapon does
     */
    private final int damage;
    
    /**
     * The amount of durability the weapon has left
     */
    private int durability;
    
    public Weapon(final String name, final int damage, final int durability) {
        this.damage = damage;
        this.durability = durability;
        this.name = name;
    }
    
    /**
     * <p>Calculates the score of the current weapon.
     * 
     * <p>Mathematically, it is half the product of the damage and durability
     * @return  this weapon's score
     */
    public int score() {
        return damage * durability / 2;
    }
    
    /**
     * Performs an attack
     * @return  how much damage should be dealt
     */
    public int attack() {
        if (durability == 0) {
            return (int) (Math.random() + 0.2);
        }
        durability--;
        if (Math.random() > 0.7) {
            System.out.println("Critical hit!");
            // 220-240% of weapon's damage
            return (int) (damage * (Math.random() / 5 + 2.2));
        } else {
            // 100-120% of weapon's damage
            return (int) (damage * (Math.random() / 5 + 1));
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s\tDamage: %d\tDurability: %d", name, damage, durability);
    }
}