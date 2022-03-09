package mvh.util;

import mvh.world.Entity;
import org.junit.jupiter.api.*;

import java.io.File;

class MainTest {

    //Initializing Strings needed in tests
    String basicWorld;
    String complexWorld;

    /**
     * Basic structure setup to visualize the different worlds (created via being given different files)
     */
    @BeforeEach
    void setUp() {
        basicWorld =
                """
                #####
                #M..#
                #...#
                #..H#
                #####
                NAME   \tS\tH\tSTATE\tINFO
                Mons(1)\tM\t10\tALIVE\tSWORD
                Hero(2)\tH\t10\tALIVE\t3\t1
                """;
        complexWorld =
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
        Assertions.assertEquals(basicWorld, Reader.loadWorld(file).toString());
    }

    /**
     * Testing whether we can produce an identical world given a larger world (as compared to last test case) with
     * multiple entities (and entities of the same type)
     */
    @Test
    void loadWorldBigWorld(){
        File file = new File("worldBig.txt");
        Assertions.assertEquals(complexWorld, Reader.loadWorld(file).toString());
    }

    /**
     *
     */
    @Test
    void gameString(){

    }

    /**
     *
     */
    @Test
    void worldString(){

    }

    /**
     * Creating a 3x3 localized world
     */
    @Test
    void getLocal3x3(){

    }

    /**
     * Creating a 5x5 localized world
     */
    @Test
    void getLocal5x5(){

    }

    //TODO Ask about catching errors within tests
    /**
     * Attempting to create a world of even size, ie, should throw an error
     */
    @Test
    void getLocalFailTest(){

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
    void heroMoveInDirection(){

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
    void monsterMoveInDirection(){

    }

    /**
     * Test for chooseMove() where the monster has no valid positions to move to, ie, should stay at the same location
     */
    @Test
    void monsterStay(){

    }
}