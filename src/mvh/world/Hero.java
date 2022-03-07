package mvh.world;

import mvh.enums.Direction;

/**
 * A Monster is an Entity with a user provide WEAPON STRENGTH and ARMOR STRENGTH
 * @author Jonathan Hudson
 * @version 1.0
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

    @Override
    public Direction attackWhere(World local) {
        int heroRowLocation = 1;
        int heroColumnLocation = 1;
        //TODO Ask if we need to throw error if not 3x3 world and whether we can hard code in locations
        //Looping through rows and columns and scanning if there is a location that can be attacked
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                //conditional to confirm current searched location has a monster, and it is alive
                if(local.canBeAttacked(row, column) && local.isMonster(row, column))
                {
                    return Direction.getDirection(row - heroRowLocation, column - heroColumnLocation);
                }
            }
        }
        //default return null if no attack able locations
        return null;
    }

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
    public String toString(){
        return super.toString()+"\t"+weaponStrength+"\t"+armorStrength;
    }
}
