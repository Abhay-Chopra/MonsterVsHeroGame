package mvh.util;

import mvh.world.Entity;
import mvh.world.World;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for multiple functions from mvh package
 * @author Abhay Chopra
 * @version 1.0
 * TA: T06(W/Amir)
 * Date: March 9th, 2022
 */
class MainTest {

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
    //TODO Ask if this is allowed
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
        //Checking to make sure there are walls around the monster
        assertFalse(local3x3View.canMoveOnTopOf(0,0));
        assertFalse(local3x3View.canMoveOnTopOf(0,1));
        assertFalse(local3x3View.canMoveOnTopOf(1, 0));
    }

    /**
     * Creating a 5x5 localized world (from big world file)
     */
    @Test
    void getLocal5x5(){
        World world = getWorldBig();
        //Getting world view of 5x5 centred on an entity (file is worldBig.txt)
        World local5x5View = world.getLocal(5, 1,2);
        //Checking to confirm that our local view has been correctly loaded
        assertTrue(local5x5View.isMonster(2, 2));
        //Checking for entities around the centred entity (and if they match with actual world)
        assertTrue(local5x5View.isMonster(1,2));
        assertTrue(local5x5View.isMonster(1,0));
        assertFalse(local5x5View.canMoveOnTopOf(0,0));
    }

    /**
     * Creating a 5x5 localized world focused on different entity (thus the "new entity" within test name)
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
     * Test where given a world where hero has no available attacking positions
     */
    @Test
    void heroAttackNone(){

    }

    /**
     * Test where hero should be able to attack a monster that is in its direct vicinity
     */
    @Test
    void heroAttackMonster(){

    }

    /**
     * Test where given a world where monster has no available attacking positions
     */
    @Test
    void monsterAttackNone(){

    }

    /**
     * Test where monster should be able to attack a hero that is in its direct vicinity
     */
    @Test
    void monsterAttackHero(){

    }

    /**
     * Test for chooseMove() where the hero has a valid position to move to
     */
    @Test
    void heroMoveDirection(){

    }

    /**
     * Test for chooseMove() where the hero has no valid positions to move to, ie, should stay at the same location
     */
    @Test
    void heroStay(){

    }

    /**
     * Test for chooseMove() where the monster has a valid position to move to
     */
    @Test
    void monsterMoveDirection(){

    }

    /**
     * Test for chooseMove() where the monster has no valid positions to move to, ie, should stay at the same location
     */
    @Test
    void monsterStay(){

    }
}