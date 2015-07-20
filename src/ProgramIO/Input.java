package ProgramIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Directory: Main.NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/18/2015.
 */
public class Input {

    private BufferedImage screenShot;

    private Robot robot;

    public Input(Robot robot) {

        this.robot = robot;

    }

    public int[] getArray(Rectangle dimension) {

        //Use robot to create a bufferedImage from a screenShot
        screenShot = robot.createScreenCapture(dimension);

        //Convert the bufferedImage to int[]
        return ((DataBufferInt) screenShot.getRaster().getDataBuffer() ).getData();

    }

}
