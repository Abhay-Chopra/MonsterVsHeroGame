package mvh.test;

import mvh.Main;
import mvh.enums.Direction;
import mvh.util.Reader;
import mvh.world.Entity;
import mvh.world.Hero;
import mvh.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for multiple functions from mvh package
 * @author Abhay Chopra
 * @version 1.0
 * TA: T06(W/Amir)
 * Date: March 9th, 2022
 */
class MvHTest {

    //Initializing Strings needed in tests
    String worldMap;
    String worldString;

    /**
     * Basic structure setup to visualize the different worlds (created via being given different files)
     */
    @BeforeEach
    void setUp() {
        worldMap =
                """
                ######
                #A.B.#
                #..C.#
                #....#
                #....#
                #.DEF#
                ######
                """;
        worldString =
                """
                ######
                #A.B.#
                #..C.#
                #....#
                #....#
                #.DEF#
                ######
                NAME   \tS\tH\tSTATE\tINFO
                Mons(1)\tA\t10\tALIVE\tAXE
                Mons(2)\tB\t9\tALIVE\tSWORD
                Mons(3)\tC\t8\tALIVE\tCLUB
                Hero(4)\tD\t8\tALIVE\t5\t2
                Hero(5)\tE\t7\tALIVE\t4\t1
                Hero(6)\tF\t6\tALIVE\t3\t1
                """;
    }
    private World getWorld(){
        File file = new File("world.txt");
        return Reader.loadWorld(file);
    }
    private World getWorldBig(){
        File file = new File("worldBig.txt");
        return Reader.loadWorld(file);
    }

    /**
     * Reset entity counter as each test is likely creating some new world where entities are created
     */
    @AfterEach
    void tearDown(){
        Entity.resetIDCounter();
    }

    /**
     * Testing whether we can produce an identical world (relative to expected) given a basic world file.
     */
    @Test
    void loadWorldBasicWorld() {
        File file = new File("world.txt");
        World world = Reader.loadWorld(file);
        //Confirming that the world is the expected size
        int expectedRowsColumns = 3;
        assertEquals(expectedRowsColumns, world.getRows());
        assertEquals(expectedRowsColumns, world.getColumns());
        //Checking if the world has the correct entities (at the required locations)
        assertTrue(world.isHero(2, 2));
        assertTrue(world.isMonster(0,0));
    }

    /**
     * Testing whether we can produce an identical world given a larger world file (as compared to last test case) with
     * multiple entities (and entities of the same type)
     */
    @Test
    void loadWorldBigWorld(){
        File file = new File("worldBig.txt");
        World world = Reader.loadWorld(file);
        //Checking that the world is expected size
        int expectedRows = 5;
        int expectedColumns = 4;
        assertEquals(expectedRows, world.getRows());
        assertEquals(expectedColumns, world.getColumns());
        //Checking if the world has the correct hero's (at the required locations)
        assertTrue(world.isHero(4, 1));
        assertTrue(world.isHero(4, 2));
        assertTrue(world.isHero(4, 3));
        //Checking monsters at correct locations within created world
        assertTrue(world.isMonster(0,0));
        assertTrue(world.isMonster(0,2));
        assertTrue(world.isMonster(1,2));
    }

    /**
     * Testing load world but via comparing string representation (different method but same test case essentially)
     */
    @Test
    void loadWorld(){
        /*Justification to using toString: gameString is already a function that is being tested thus it is easy to use it
          within testing */
        File file = new File("worldBig.txt");
        World world = Reader.loadWorld(file);
        assertEquals(worldString, world.toString());
    }

    /**
     * Test case to compare game string produced for worldBig file
     */
    @Test
    void gameString(){
        File file = new File("worldBig.txt");
        World world = Reader.loadWorld(file);
        assertEquals(worldString, world.gameString());
    }

    /**
     * Test case to compare world string produced for worldBig file
     */
    @Test
    void worldString(){
        File file = new File("worldBig.txt");
        World world = Reader.loadWorld(file);
        assertEquals(worldMap, world.worldString());
    }

    /**
     * Creating a 3x3 localized world (for the basic world)
     */
    @Test
    void getLocal3x3(){
        World world = getWorld();
        World local3x3View = world.getLocal(3, 0,0);
        //Checking to confirm that our local view has been correctly loaded
        assertTrue(local3x3View.isMonster(1,1));
        //Confirming we have the same entity in the local view
        assertSame(world.getEntity(0,0), local3x3View.getEntity(1,1));
        //Checking to make sure there are walls around the monster
        assertFalse(local3x3View.canMoveOnTopOf(0,0));
        assertFalse(local3x3View.canMoveOnTopOf(0,1));
        assertFalse(local3x3View.canMoveOnTopOf(1, 0));
    }

    /**
     * Creating a 5x5 localized world (from big world file) and confirming we get an accurate subview of the overall world
     */
    @Test
    void getLocal5x5(){
        World world = getWorldBig();
        //Getting world view of 5x5 centred on an entity (file is worldBig.txt)
        World local5x5View = world.getLocal(5, 1,2);
        //Checking to confirm that our local view has been correctly loaded
        assertTrue(local5x5View.isMonster(2, 2));
        //Confirming we have the same entity in the local view
        assertSame(world.getEntity(1,2), local5x5View.getEntity(2,2));
        //Checking for entities around the centred entity (and if they match with actual world)
        assertTrue(local5x5View.isMonster(1,2));
        assertTrue(local5x5View.isMonster(1,0));
        assertFalse(local5x5View.canMoveOnTopOf(0,0));
    }

    /**
     * Creating a 5x5 localized world focused on different entity (thus the "new entity" within test name) from the world
     * generated through worldBig.txt
     */
    @Test
    void getLocalNewEntity(){
        World world = getWorldBig();
        //Getting world view of 5x5 centred on an entity (file is worldBig.txt)
        World local5x5View = world.getLocal(5, 4,1);
        //Checking to confirm that our local view has been correctly loaded
        assertTrue(local5x5View.isHero(2,2));
        //Checking for entities around the centred entity (and if they match with actual world)
        assertTrue(local5x5View.isHero(2,3));
        assertTrue(local5x5View.isHero(2,4));
        //Checking for walls (to confirm we created an accurate localized world)
        assertFalse(local5x5View.canMoveOnTopOf(2,3));
        assertFalse(local5x5View.canMoveOnTopOf(3,3));
    }

    /**
     * Test case for attackWhere() for Hero.java when given a world where hero has no available attacking positions
     */
    @Test
    void heroAttackNone(){
        World world = getWorld();
        //In world, the hero is not close enough to the monster to attack it, thus expected result should be null
        assertNull(world.getEntity(2,2).attackWhere(world.getLocal(3, 2, 2)));
    }

    /**
     * Test case for attackWhere() for Hero.java where hero should be able to attack a monster that is in its direct vicinity
     */
    @Test
    void heroAttackMonster(){
        World world = getWorld();
        world.moveEntity(0, 0, Direction.SOUTHEAST);
        //Getting local world for the attack perspective
        assertEquals(Direction.NORTHWEST, world.getEntity(2,2).attackWhere(world.getLocal(3, 2, 2)));
    }

    /**
     * Test where given a world in which monster has no available attacking positions
     */
    @Test
    void monsterAttackNone(){
        World world = getWorld();
        //In world, the monster is not close enough to the hero to attack it
        assertNull(world.getEntity(0,0).attackWhere(world.getLocal(3, 0, 0)));
    }

    /**
     * Test where monster should be able to attack a hero that is in its direct vicinity
     */
    @Test
    void monsterAttackHero(){
        World world = getWorld();
        //Moving the hero closer to monster in our world, thus it is close enough for an attack
        world.moveEntity(2, 2, Direction.NORTHWEST);
        assertEquals(Direction.SOUTHEAST, world.getEntity(0,0).attackWhere(world.getLocal(3, 0, 0)));
    }

    /**
     * Test for chooseMove() where the hero has a valid position to move to
     */
    @Test
    void heroMoveDirection(){
        World world = getWorld();
        //In world, the hero is not close enough to the monster to attack it, thus it should move towards the monster
        assertEquals(Direction.NORTHWEST, world.getEntity(2,2).chooseMove(world.getLocal(5, 2,2)));
    }

    /**
     * Test for chooseMove() where the hero has valid positions to move to, but some are blocked directly next to the hero
     */
    @Test
    void heroBlocked(){
        World world = getWorldBig();
        //Moving entities so that entity at the bottom right of the world has some paths obstructed
        world.moveEntity(4,2, Direction.NORTHEAST);
        world.moveEntity(4, 1, Direction.EAST);

        assertEquals(Direction.NORTHWEST, world.getEntity(4,3).chooseMove(world.getLocal(5, 4,3)));
    }

    /**
     * Test for chooseMove() where the monster has a valid position to move to
     */
    @Test
    void monsterMoveDirection(){
        World world = getWorld();
        //In world, the monster is not close enough to the hero to attack it, thus it should move towards the hero
        assertEquals(Direction.SOUTHEAST, world.getEntity(0,0).chooseMove(world.getLocal(5, 0,0)));
    }

    /**
     * Test for chooseMove() where the monster has some positions blocked directly next to it
     */
    @Test
    void monsterBlocked(){
        World world = getWorldBig();
        //Moving entities so that entity at the top left of the world has some paths obstructed
        world.moveEntity(1,2, Direction.WEST);
        world.moveEntity(1,1, Direction.WEST);
        world.moveEntity(0,2, Direction.WEST);

        assertEquals(Direction.SOUTHEAST, world.getEntity(0,0).chooseMove(world.getLocal(5, 0,0)));
    }
    /**
     * Test for chooseMove() where the monster has all location blocked in its direct vicinity, ie, monster should stay
     */
    @Test
    void monsterStay(){
        World world = getWorldBig();
        //Moving entities so that entity at the top left of the world has some paths obstructed
        world.moveEntity(1,2, Direction.WEST);
        world.moveEntity(1,1, Direction.WEST);
        world.moveEntity(0,2, Direction.WEST);
        //Adding entity so that the monster is fully blocked in
        Hero hero = new Hero(10, 'H', 3, 1);
        world.addEntity(1,1, hero);
        //Instantiating our random var in main so our random number generator works
        Main.random = new Random(12345);
        assertEquals(Direction.STAY, world.getEntity(0,0).chooseMove(world.getLocal(5,0,0)));
    }
}