package Logic;

import ProgramIO.ExecutableControl;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

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
        window = new UserIO.Window(exeControl.w,exeControl.h);

        charReader = new CharReader(exeControl.w,exeControl.h);

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

            //Give the charReader the color array for reference
            charReader.setGameArray(convertOneDimensionalToTwoDimensional(exeControl.h,exeControl.w,array));

            //TESTING
            //Print out the char at the given location
            //System.out.println("CHAR: " + charReader.getChar(24, 8, Color.white.getRGB()));

            //System.exit(0);

            //TESTING
            //Create a new bufferedImage from the color array
            BufferedImage result = new BufferedImage(exeControl.w, exeControl.h, BufferedImage.TYPE_INT_RGB);
            result.setRGB(0, 0, exeControl.w, exeControl.h, array, 0, exeControl.w);

            //Display a new window that shows a copy of the NES game window
            Graphics2D g = (Graphics2D) window.bufferStrategy.getDrawGraphics();
            g.drawImage(result, 0, 0, null);

            //g.drawImage(robot.createScreenCapture(new Rectangle(x + 24, y + 8, 8, 8)), 2, 2, null);

            g.drawImage(charReader.test1,0,0,null);
            g.drawImage(charReader.test2,12,0,null);


            window.bufferStrategy.show();

        }

    }

    private int[][] getTwoDimensionalArray() {
        int[][] result = new int[3][];

        return result;
    }

    private int[][] convertOneDimensionalToTwoDimensional(int numberOfRows, int rowSize, int[] srcMatrix) {
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
