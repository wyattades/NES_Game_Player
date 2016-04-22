package ProgramIO;

import Main.NES_Game_Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wyatt on 1/26/2016.
 */
public class ExecutableControl {

    public final static int w = 262, h = 253;
    private final int NES_color_1 = new Color(63, 63, 0).getRGB();
    private final int NES_color_2 = new Color(127, 127, 0).getRGB();
    private final String gameName;
    private final String exeName = "/NES/nes.exe";

    public Rectangle dimension;
    private Robot robot;
    private Process process;
    public Output output;

    public ExecutableControl(String fileName) {

        gameName = fileName;

        dimension = new Rectangle(0, 0, w, h);

        //Initiate robot
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        output = new Output(robot);

        openExecutable();
        //Wait a bit for exe to fully open
        NES_Game_Player.sleep(500);
        openMarioGame();
        NES_Game_Player.sleep(500);
        locateWindow();
        startGame();

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void checkExeAvailable() {
        if (!process.isAlive()) {
            System.out.println("The executable has closed or is no longer available.");
            System.exit(0);
        }

        BufferedImage screenShot = robot.createScreenCapture(
                new Rectangle((int) dimension.getX() + 7, (int) dimension.getY() - 40, 2, 1)
        );
        if (screenShot.getRGB(0, 0) != NES_color_1 || screenShot.getRGB(1, 0) != NES_color_2) {
            locateWindow();
        }
    }

    private void startGame() {
        output.addActions(new ArrayList<int[]>(){{add(new int[]{Output.CA_START,1});}});
    }

    private void openExecutable() {

        System.out.println("Opening executable: " + exeName);

        File file = new File(getClass().getResource(exeName).getPath());
        if (!file.exists()) {
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

        NES_Game_Player.sleep(500);

        String[] chars = gameName.split("(?!^)");
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i].charAt(0);
            if (Character.isUpperCase(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(c);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.keyRelease(c);
            } else {
                c = Character.toUpperCase(c);
                robot.keyPress(c);
                robot.keyRelease(c);
            }
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public void locateWindow() {
        //Take a screenshot of the entire screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BufferedImage screenShot = robot.createScreenCapture(
                new Rectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight())
        );

        boolean foundWindow = false;

        //Set x and y to the top left corner of the NES game
        outerLoop:
        for (int i = 0; i < screenShot.getWidth(); i++) {
            for (int j = 0; j < screenShot.getHeight(); j++) {
                if (screenShot.getRGB(i, j) == NES_color_1 && screenShot.getRGB(i + 1, j) == NES_color_2) {
                    dimension.setLocation(i - 7, j + 40);
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

        return ((DataBufferInt) robot.createScreenCapture(dimension).getRaster().getDataBuffer()).getData();

    }

}


