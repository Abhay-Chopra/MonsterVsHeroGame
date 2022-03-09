package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;

import java.io.*;

/**
 * Class to assist reading in world file
 * @author Jonathan Hudson, Abhay Chopra
 * @version 1.1
 * TA: Amir (Tutorial 06)
 * Match 8th, 2022
 */
public final class Reader {
    /**
     * Reads the World.java file (provided by user) and creates a corresponding World object
     *
     * @param fileWorld the World.java file that is provided by the user
     * @return World object that is created and corresponds/maps to the given file
     */
    public static World loadWorld(File fileWorld){
        World world = null;
        if(fileWorld.isFile() && fileWorld.canRead() && fileWorld.exists()){
            //Try and Catch Block for reading from file (With-resources)
            try(FileReader fileReader = new FileReader(fileWorld);
                BufferedReader bufferedReader = new BufferedReader(fileReader))
            {
                //Reading from File
                int rowCount = Integer.parseInt(bufferedReader.readLine());
                int columnCount = Integer.parseInt(bufferedReader.readLine());
                //Creating a world object
                world = new World(rowCount, columnCount);
                String line = bufferedReader.readLine();
                //Looping for "following" lines
                for(int currentRow = 0; currentRow < rowCount; currentRow++){
                    for (int currentColumn = 0; currentColumn < columnCount; currentColumn++) {
                        //TODO Ask about checking if line is null
                        //Conditional checks that there is content within the line
                        if(line != null){
                            //Reading and splitting a line
                            String[] lineInfo = line.split(",");
                            //Initializing vars
                            int row = 0;
                            int column = 0;
                            try{
                                //Initializing info common between Entities
                                row = Integer.parseInt(lineInfo[0]);
                                column = Integer.parseInt(lineInfo[1]);
                                if(row != currentRow || column != currentColumn)
                                {
                                    throw new Exception("Skipped row or column");
                                }
                            }catch (ArrayIndexOutOfBoundsException e){
                                System.err.println("Invalid entries in world file!");
                                e.printStackTrace();
                                System.exit(1);
                            } catch (Exception e) {
                                //TODO Ask about exception types
                                System.err.println("Skipped a row or column within world file!");
                                e.printStackTrace();
                                System.exit(1);
                            }
                            //Conditional for handling non-floor entities in row, column
                            if(lineInfo.length > 2)
                            {
                                //getting health and symbol from the passed line
                                int entityHealth = Integer.parseInt(lineInfo[4]);
                                char entitySymbol = lineInfo[3].charAt(0);
                                //Creating entity dependent on type
                                if(lineInfo[2].equals("MONSTER"))
                                {
                                    //Creating variables as current Entity would be of type Monster
                                    char weaponSymbol = lineInfo[5].charAt(0);
                                    Monster monster = new Monster(entityHealth, entitySymbol, WeaponType.getWeaponType(weaponSymbol));
                                    //Adding entity onto the current world
                                    world.addEntity(row, column, monster);
                                }
                                else if(lineInfo[2].equals("HERO"))
                                {
                                    //Creating variables as current Entity would be of type Hero
                                    int weaponStrength = Integer.parseInt(lineInfo[5]);
                                    int armorStrength = Integer.parseInt(lineInfo[6]);
                                    Hero hero = new Hero(entityHealth, entitySymbol, weaponStrength, armorStrength);
                                    //Adding entity onto the world
                                    world.addEntity(row, column, hero);
                                }
                            }
                            line = bufferedReader.readLine();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.err.println("Could not find file: " + fileWorld.getAbsolutePath());
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Could not close file: " + fileWorld.getAbsolutePath());
                System.exit(1);
            }
        }
        return world;
    }
}
