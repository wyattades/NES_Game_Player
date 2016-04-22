package Main;

import Logic.LearnBot;
import ProgramIO.CharReader;
import ProgramIO.ExecutableControl;

import java.awt.*;
import java.awt.image.BufferedImage;

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
        window = new UserIO.Window(200, 200, exeControl.w, exeControl.h);

        charReader = new CharReader(Color.white);

        running = true;

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                exeControl.destroyExecutable();
            }
        }));

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private LearnBot learnBot;
    private int gameState;
    final int PLAY = 0, TRANSITION = 1;

    public void run() {

        learnBot = new LearnBot();
        gameState = PLAY;

        //Thread loop
        while (running) {

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

            int[] scores = charReader.getScoreArray(12, exeControl.w);
            if (scores != null) {
                learnBot.giveNewInput(scores, array);
                learnBot.printData();
                exeControl.output.addActions(learnBot.getCurrentActions());
                exeControl.output.performActions();
            } else {
                System.out.println("scores = NULL");
            }

        }

    }

}