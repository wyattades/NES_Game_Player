package Logic;

import ProgramIO.ExecutableControl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Directory: Main.NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/18/2015.
 */
public class Interpreter implements Runnable {

    private UserIO.Window window;

    private CharReader charReader;

    private ExecutableControl exeControl;

    public boolean running;

    public Interpreter() {
        init();
    }

    public void init() {

        exeControl = new ExecutableControl();

        //Create a display window
        window = new UserIO.Window(200,200,exeControl.w,exeControl.h);

        charReader = new CharReader(Color.white);

        running = true;

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                exeControl.destroyExecutable();
            }
        }));

    }

    public void run() {

        //Thread loop
        while(running) {

            //Get an array of bytes from the NES game window
            int[] array = exeControl.getArray(exeControl.dimension);

//            for (int j = 0; j < array.length; j+=exeControl.w) {
//                int[] t = Arrays.copyOfRange(array, j, j+exeControl.w);
//                for(int p : t) {
//                    System.out.print((p==-1)?'#':' ');
//                }
//                System.out.println();
//            }
//            System.exit(1);

            //Give the charReader the color array for reference
//            charReader.setGameArray(convertOneDimensionalToTwoDimensional(exeControl.w,exeControl.h,array));
            charReader.setGameArray(array);

            //Check if executable ever closes
            exeControl.checkExeAvailable();

            //TESTING

            //Print out the char at the given location
//            for (int i = 0; i < exeControl.h-8; i++){
//                for (int j = 0; j < exeControl.w-8; j ++) {
//                    char c = charReader.getChar(i,j);
//                    if (c != 0) {
//                        System.out.println("CHAR: " + c + " at: "+i+","+j);
//                    }
//                }
//            }
//            System.exit(1);

            //Create a new bufferedImage from the color array
            BufferedImage result = new BufferedImage(exeControl.w, exeControl.h, BufferedImage.TYPE_INT_RGB);
            result.setRGB(0, 0, exeControl.w, exeControl.h, array, 0, exeControl.w);

            //Display a new window that shows a copy of the NES game window
            Graphics2D g = (Graphics2D) window.bufferStrategy.getDrawGraphics();
            g.drawImage(result, 0, 0, null);

            //TEMP
            //g.drawImage(charReader.snapShotTest,0,0,null);
            //g.drawImage(charReader.charImageTest,8,0,null);

            window.bufferStrategy.show();

        }

    }


    private static int[][] convertOneDimensionalToTwoDimensional(int rowSize, int numberOfRows, int[] srcMatrix) {
        int srcMatrixLength = srcMatrix.length;
        int srcPosition = 0;

        int[][] returnMatrix = new int[numberOfRows][];
        for (int i = 0; i < numberOfRows; i++) {
            int[] row = new int[rowSize];
            int nextSrcPosition = srcPosition + rowSize;
            if (srcMatrixLength >= nextSrcPosition) {
                // Copy the data from the file if it has been written before. Otherwise we just keep row empty.
                System.arraycopy(srcMatrix, srcPosition, row, 0, rowSize);
            }
            returnMatrix[i] = row;
            srcPosition = nextSrcPosition;
        }
        return returnMatrix;
    }

}
