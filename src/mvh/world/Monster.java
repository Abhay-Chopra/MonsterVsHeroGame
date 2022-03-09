package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;

/**
 * A Monster is an Entity with a set ARMOR STRENGTH and a user provided WEAPON TYPE
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Monster extends Entity {

    /**
     * The set armor strength of a Monster
     */
    private static final int MONSTER_ARMOR_STRENGTH = 2;

    /**
     * The user provided weapon type
     */
    private final WeaponType weaponType;

    /**
     * A Monster has regular health and symbol as well as a weapon type
     *
     * @param health     Health of Monster
     * @param symbol     Symbol for map to show Monster
     * @param weaponType The weapon type of the Monster
     */
    public Monster(int health, char symbol, WeaponType weaponType) {
        super(symbol, health);
        this.weaponType = weaponType;
    }

    /**
     * The weapon strength of monster is from their weapon type
     * @return The weapon strength of monster is from their weapon type
     */
    @Override
    public int weaponStrength() {
        return weaponType.getWeaponStrength();
    }

    /**
     * The armor strength of monster is from the stored constant
     * @return The armor strength of monster is from the stored constant
     */
    @Override
    public int armorStrength() {
        return MONSTER_ARMOR_STRENGTH;
    }

    /**
     *
     * @param local The local view of the entity (immediate neighbors 3x3)
     * @return
     */
    @Override
    public Direction attackWhere(World local) {
        int monsterRowLocation = 1;
        int monsterColumnLocation = 1;
        //Looping through rows and columns and scanning if there is a location that can be attacked
        for (int row = 2; row >= 0; row--) {
            for (int column = 2; column >= 0; column--) {
                //conditional to confirm current searched location has a hero, and it is alive
                if(local.canBeAttacked(row, column) && local.isHero(row, column))
                {
                    return Direction.getDirection(row - monsterRowLocation, column - monsterColumnLocation);
                }
            }
        }
        //default return null if no attack able locations
        return null;
    }

    /**
     *
     * @param local The local view of the entity
     * @return
     */
    @Override
    public Direction chooseMove(World local) {
        return null;
    }

    /**
     * Can only be moved on top of if dad
     * @return isDead()
     */
    @Override
    public boolean canMoveOnTopOf() {
        return isDead();
    }

    /**
     * Can only be attacked if alive
     * @return isAlive()
     */
    @Override
    public boolean canBeAttacked() {
        return isAlive();
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + weaponType;
    }
}
