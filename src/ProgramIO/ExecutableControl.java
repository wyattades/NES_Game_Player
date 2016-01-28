package ProgramIO;

import Main.NES_Game_Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

/**
 * Created by wyatt on 1/26/2016.
 */
public class ExecutableControl {

    public final int w = 260, h = 250;
    private final int NES_color_1 = new Color(63, 63, 0).getRGB();
    private final int NES_color_2 = new Color(127, 127, 0).getRGB();
    private final String gameName = "supermario.nes";
    private final String exeName = "/NES/nes.exe";

    public int x, y;
    public Rectangle dimension;
    private Robot robot;
    private BufferedImage screenShot;
    private Process process;

    public ExecutableControl() {

        //Initiate robot
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        openExecutable();
        //Wait a bit for exe to fully open
        NES_Game_Player.sleep(500);
        openMarioGame();
        locateWindow();

        dimension = new Rectangle(x,y,w,h);

    }

    public void checkExeAvailable() {
        if (!process.isAlive()) {
            System.out.println("The executable has closed or is no longer available.");
            System.exit(0);
        }
    }

    private void openExecutable() {

        System.out.println("Opening executable: " + exeName);

        File file = new File(getClass().getResource(exeName).getPath());
        if (! file.exists()) {
            throw new IllegalArgumentException("The file " + exeName + " does not exist at that location.");
        }
        try {
            process = Runtime.getRuntime().exec(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroyExecutable() {

        System.out.println("Shutting down executable: " + exeName);

        process.destroy();

    }

    private void openMarioGame() {
        System.out.println("Opening NES game: " + gameName);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_O);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_O);

        NES_Game_Player.sleep(300);

        String[] chars = gameName.split("(?!^)");
        for (String s: chars) {
            char c = Character.toUpperCase(s.charAt(0));
            robot.keyPress(c);
            robot.keyRelease(c);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private void locateWindow() {
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
            System.out.println("Unable to locate the NES game window.");
            System.exit(0);
        }
    }

    public int[] getArray(Rectangle dimension) {

        //Use robot to create a bufferedImage from a screenShot
        screenShot = robot.createScreenCapture(dimension);

        //Convert the bufferedImage to int[]
        return ((DataBufferInt) screenShot.getRaster().getDataBuffer() ).getData();

    }

}


