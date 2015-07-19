import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Directory: NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/18/2015.
 */
public class Input {

    private BufferedImage screenShot;

    private Robot robot;

    public Input(Robot robot) {

        this.robot = robot;

    }

    public byte[] getArray(Rectangle dimension) {

        //Use robot to create a bufferedImage from a screenShot
        screenShot = robot.createScreenCapture(dimension);

        //Convert the bufferedImage to byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] byteArray = null;
        try {
            ImageIO.write(screenShot, "bmp", baos);
            baos.flush();
            byteArray = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArray;

    }

}
