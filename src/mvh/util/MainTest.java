package mvh.util;

import org.junit.jupiter.api.*;

class MainTest {

    @BeforeEach
    void setUp() {

    }

    /**
     *
     */
    @Test
    void loadWorldFailTest() {

    }

    /**
     *
     */
    @Test
    void loadWorldCorrectEntries(){

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