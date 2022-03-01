package mvh.util;

import mvh.world.World;

import java.io.*;
import java.util.Arrays;

/**
 * Class to assist reading in world file
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Reader {
    public static World loadWorld(File fileWorld){
        //TODO Don't need to check with conditionals, already handled
        if(fileWorld.isFile() && fileWorld.canRead() && fileWorld.exists()){
            //Try and Catch Block for reading from file (With-resources)
            try(FileReader fileReader = new FileReader(fileWorld);
                BufferedReader bufferedReader = new BufferedReader(fileReader))
            {
                //Reading from File
                int rowCount = Integer.parseInt(bufferedReader.readLine());
                int columnCount = Integer.parseInt(bufferedReader.readLine());
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
                        else if(lineInfo.length > 1)
                        {
                            System.out.println();
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
        return null;
    }
}
