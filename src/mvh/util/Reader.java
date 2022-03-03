package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;

import java.io.*;

/**
 * Class to assist reading in world file
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Reader {
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
                for(int linesRead = 0; linesRead < rowCount * columnCount;linesRead++){
                    //Just adding minor conditional check
                    if(line != null){
                        //Reading and splitting a line
                        String[] lineInfo = line.split(",");
                        System.out.println(Arrays.toString(lineInfo));
                        //Conditional for handling floors
                        if(lineInfo.length == 1)
                        {
                            System.out.println();
                        }
                        //Conditional for handling non-floor entities in row, column
                        if(lineInfo.length > 2)
                        {
                            //Initializing info common between Entities
                            int row = Integer.parseInt(lineInfo[0]);
                            int column = Integer.parseInt(lineInfo[1]);
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
