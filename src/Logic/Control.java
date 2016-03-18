package Logic;

import ProgramIO.ExecutableControl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Directory: Main.NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/18/2015.
 */
public class Control implements Runnable {

    private UserIO.Window window;

    private CharReader charReader;

    private ExecutableControl exeControl;

    public boolean running;

    public Control() {
        init();
    }

    public void init() {

        exeControl = new ExecutableControl("SuperMario.nes");

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

            //Check if executable ever closes
            exeControl.checkExeAvailable();

            //Get an array of bytes from the NES game window
            int[] array = exeControl.getArray(exeControl.dimension);

            //Give the charReader the color array for reference
            charReader.setGameArray(array);

            //TESTING

            //Create a new bufferedImage from the color array
            BufferedImage result = new BufferedImage(exeControl.w, exeControl.h, BufferedImage.TYPE_INT_RGB);
            result.setRGB(0, 0, exeControl.w, exeControl.h, array, 0, exeControl.w);

            //Display a new window that shows a copy of the NES game window
            Graphics2D g = (Graphics2D) window.bufferStrategy.getDrawGraphics();
            g.drawImage(result, 0, 0, null);

            window.bufferStrategy.show();

            char[] chars = new char[10];
            for (int i = 0; i < ExecutableControl.w; i++) {
                System.out.print(charReader.getChar(i,16));
            }
            System.out.println();

        }

    }

}
