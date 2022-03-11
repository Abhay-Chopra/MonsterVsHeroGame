package mvh.world;

import mvh.enums.Direction;

/**
 * A Monster is an Entity with a user provide WEAPON STRENGTH and ARMOR STRENGTH
 * @author Jonathan Hudson, Abhay Chopra
 * @version 1.1
 * TA: Amir (Tutorial 06)
 * Match 8th, 2022
 */
public final class Hero extends Entity{

    /**
     * The user provided weapon strength
     */
    private final int weaponStrength;

    /**
     * The user provided armor strength
     */
    private final int armorStrength;

    /**
     * A Hero has regular health and symbol as well as a weapon strength and armor strength
     * @param health Health of hero
     * @param symbol Symbol for map to show hero
     * @param weaponStrength The weapon strength of the hero
     * @param armorStrength The armor strength of the hero
     */
    public Hero(int health, char symbol, int weaponStrength, int armorStrength) {
        super(symbol, health);
        this.weaponStrength = weaponStrength;
        this.armorStrength = armorStrength;
    }

    /**
     * The weapon strength of monster is from user value
     * @return The weapon strength of monster is from user value
     */
    @Override
    public int weaponStrength() {
        return weaponStrength;
    }

    /**
     * The armor strength of monster is from user value
     * @return The armor strength of monster is from user value
     */
    @Override
    public int armorStrength() {
        return armorStrength;
    }

    /**
     * Choosing where the Hero should attack given a localized view of the hero's surroundings (3x3)
     *
     * @param local The local view of the entity (immediate neighbors 3x3)
     * @return Direction where and when a valid attack is possible, otherwise returns null
     */
    @Override
    public Direction attackWhere(World local) {
        int heroRowLocation = 1;
        int heroColumnLocation = 1;
        //Looping through rows and columns and scanning if there is a location that can be attacked
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                //conditional to confirm current searched location has a monster, and it is alive (ie, attack-able)
                if(local.canBeAttacked(row, column) && local.isMonster(row, column))
                {
                    return Direction.getDirection(row - heroRowLocation, column - heroColumnLocation);
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
        //Getting hero location (local world)
        int heroRowLocation = 2;
        int heroColumnLocation = 2;
        //Looping through local world to see if any alive monsters
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                if(local.isMonster(row, column) && local.canBeAttacked(row, column)){
                    Direction[] directions = Direction.getDirections(row - heroRowLocation,column - heroColumnLocation);
                    for (Direction direction:directions) {
                        //Confirming we can move onto the location
                        if(local.canMoveOnTopOf(heroRowLocation, heroColumnLocation, direction)){
                            return direction;
                        }
                    }
                }
            }
        }
        //Handling other cases (when no monster alive)
        //Checking if hero can move North-West
        if(local.canMoveOnTopOf(heroRowLocation, heroColumnLocation, Direction.NORTHWEST)){
            return Direction.NORTHWEST;
        }
        Direction randomDirection = Direction.getRandomDirection();
        //Checking if hero can move in some random direction
        if(local.canMoveOnTopOf(heroRowLocation, heroColumnLocation, randomDirection)){
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
    public String toString(){
        return super.toString()+"\t"+weaponStrength+"\t"+armorStrength;
    }
}
