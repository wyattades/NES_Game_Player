package Logic;

import ProgramIO.Input;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Directory: Main.NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/18/2015.
 */
public class Interpreter implements Runnable {

    private final int w = 256, h = 224;
    private final int NES_color_1 = (new Color(63, 63, 0).getRGB());
    private final int NES_color_2 = (new Color(127, 127, 0).getRGB());

    private int x, y;
    private Rectangle dimension;

    private UserIO.Window window;

    private Input input;

    private Robot robot;

    private CharReader charReader;

    public boolean running;

    public Interpreter() {
        init();
    }

    public void init() {


        //Create a display window
        window = new UserIO.Window(w,h);

        charReader = new CharReader(w,h);

        robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        input = new Input(robot);

        running = true;

        //Clicks the NES game icon in order to bring it to the front
        Point2D previousPos = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove(1060, 1060);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseMove((int) previousPos.getX(), (int) previousPos.getY());
        try {
            Thread.sleep(500);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        //Take a screenshot of the entire screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BufferedImage screenShot = robot.createScreenCapture(
                new Rectangle(0,0,(int)screenSize.getWidth(),(int)screenSize.getHeight())
        );

        boolean foundWindow = false;

        //Set x and y to the top left corner of the NES game
        outerLoop:
        for (int i = 0; i < screenShot.getWidth(); i++) {
            for (int j = 0; j < screenShot.getHeight(); j++) {
                if (screenShot.getRGB(i, j) == NES_color_1 && screenShot.getRGB(i+1, j) == NES_color_2) {
                    x = i - 4;
                    y = j + 42;
                    foundWindow = true;
                    break outerLoop;
                }
            }
        }

        //If unable to locate window, then exit
        if (!foundWindow) {
            System.out.println("Unable to locate the NES game window");
            System.exit(0);
        }

        dimension = new Rectangle(x,y,w,h);

    }

    public void run() {

        //Thread loop
        while(running) {

            //Get an array of bytes from the NES game window
            int[] array = input.getArray(dimension);

            //Give the charReader the color array for reference
            charReader.setGameArray(convertOneDimensionalToTwoDimensional(h,w,array));

            //TESTING
            //Print out the char at the given location
            System.out.println(charReader.getChar(24, 8, Color.white.getRGB()));

            //System.exit(0);

            //TESTING
            //Create a new bufferedImage from the color array
            BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            result.setRGB(0, 0, w, h, array, 0, w);

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
