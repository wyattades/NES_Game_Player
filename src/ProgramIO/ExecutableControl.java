package ProgramIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

/**
 * Created by wyatt on 1/26/2016.
 */
public class ExecutableControl {

    public final int w = 256, h = 224;
    private final int NES_color_1 = new Color(63, 63, 0).getRGB();
    private final int NES_color_2 = new Color(127, 127, 0).getRGB();

    private int x, y;
    private Rectangle dimension;
    private Robot robot;
    private BufferedImage screenShot;

    public ExecutableControl() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        openExecutable();
    }

    private void openExecutable() {
        File file = new File(getClass().getResource("/NES/nes.exe").getPath());
        if (! file.exists()) {
            throw new IllegalArgumentException("The file " + file.getName() + " does not exist");
        }
        try {
            Process p = Runtime.getRuntime().exec(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void destroyExecutable() {

    }

    private void openMarioGame() {

    }

    public int[] getArray(Rectangle dimension) {

        //Use robot to create a bufferedImage from a screenShot
        screenShot = robot.createScreenCapture(dimension);

        //Convert the bufferedImage to int[]
        return ((DataBufferInt) screenShot.getRaster().getDataBuffer() ).getData();

    }

}


