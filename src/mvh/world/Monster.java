package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;

/**
 * A Monster is an Entity with a set ARMOR STRENGTH and a user provided WEAPON TYPE
 * @author Jonathan Hudson, Abhay Chopra
 * @version 1.1
 * TA: Amir (Tutorial 06)
 * Date: March 8th, 2022
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
     * Choosing where the Monster should attack given a localized view of the hero's surroundings (3x3)
     *
     * @param local The local view of the entity (immediate neighbors 3x3)
     * @return Direction where and when a valid attack is possible, otherwise returns null
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
     * Choosing where the Hero should move given a localized view of the world (5x5)
     *
     * @param local The local view of the entity
     * @return Direction to move to when a valid position is open and not a blocked path, otherwise, in the worst case
     * scenario => returns the Staying position,ie, staying at the same location
     */
    @Override
    public Direction chooseMove(World local) {
        //Getting monster location (local world)
        int monsterRowLocation = 2;
        int monsterColumnLocation = 2;
        //Looping through local world to see if any alive monsters
        for (int row = 4; row >= 0; row--) {
            for (int column = 4; column >= 0; column--) {
                //Checking if location is an attack-able hero, ie, an alive hero
                if(local.isHero(row, column) && local.canBeAttacked(row, column)){
                    Direction[] directions = Direction.getDirections(row - monsterRowLocation,column - monsterColumnLocation);
                    for (Direction direction:directions) {
                        //Confirming location can be moved on top of
                        if(local.canMoveOnTopOf(monsterRowLocation, monsterColumnLocation, direction)){
                            return direction;
                        }
                    }
                }
            }
        }
        //Handling other cases (when no monster alive)
        Direction randomDirection = Direction.getRandomDirection();
        //Checking if hero can move South-East
        if(local.canMoveOnTopOf(monsterRowLocation, monsterColumnLocation, Direction.SOUTHEAST)){
            return Direction.SOUTHEAST;
        }
        //Checking if hero can move in some random direction
        else if(local.canMoveOnTopOf(monsterRowLocation, monsterColumnLocation, randomDirection)){
            return randomDirection;
        }
        //When no other direction to move to, hero stays at current location
        else{
            return Direction.STAY;
        }
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
