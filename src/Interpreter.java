import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * Directory: NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/18/2015.
 */
public class Interpreter implements Runnable {

    private final int w = 256, h = 224;
    private final int NES_color_1 = (new Color(63, 63, 0).getRGB());
    private final int NES_color_2 = (new Color(127, 127, 0).getRGB());

    private int x, y;
    private Rectangle dimension;

    private Window window;

    private Robot robot;

    private CharReader charReader;

    public boolean running;

    public Interpreter() {
        init();
    }

    public void init() {

        //Create a display window
        window = new Window(w,h);

        charReader = new CharReader(w,h);

        robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

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
                    x = i-4;
                    y = j+42;
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
            Input input = new Input(robot);
            byte[] byteArray = input.getArray(dimension);

            IntBuffer intBuf = ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
            int[] array = new int[intBuf.remaining()];
            intBuf.get(array);
            // ^ is this int[] thing even correct?
            charReader.setGameArray(array);
            System.out.println(charReader.getChar(25, 9, Color.white.getRGB()));

            //TESTING
            //Display a new window that shows a copy of the NES game window
            Graphics2D g = (Graphics2D) window.bufferStrategy.getDrawGraphics();
            InputStream in = new ByteArrayInputStream(byteArray);
            BufferedImage result = null;
            try {
                result = ImageIO.read(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(result,0,0,null);
            window.bufferStrategy.show();
        }

    }
}
