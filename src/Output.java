import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Directory: NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/18/2015.
 */
public class Output {

    public ArrayList<CustomAction> actions;

    private Robot robot;

    public Output(Robot robot) {

        this.robot = robot;

        actions.add(new CustomAction("a", KeyEvent.VK_A));
        actions.add(new CustomAction("b", KeyEvent.VK_B));
        actions.add(new CustomAction("up", KeyEvent.VK_UP));
        actions.add(new CustomAction("down", KeyEvent.VK_DOWN));
        actions.add(new CustomAction("left", KeyEvent.VK_LEFT));
        actions.add(new CustomAction("right", KeyEvent.VK_RIGHT));
        actions.add(new CustomAction("select", KeyEvent.VK_D));
        actions.add(new CustomAction("start", KeyEvent.VK_F));

    }

    public void update() {

        for (CustomAction c : actions) {
            if (c.pressed && NES_Game_Player.currentTimeMillis() - c.time > c.currentDuration) {
                c.pressed = false;
                robot.keyRelease(c.keyEvent);
            }
        }

    }

    public void execute(int actionIndex, int duration) {

        robot.keyRelease(actions.get(actionIndex).getKeyEvent(duration));

    }

    private class CustomAction {

        private boolean pressed;

        public int currentDuration;

        private int time, keyEvent;

        public String name;

        public CustomAction(String name, int keyEvent) {

            this.name = name;

            this.keyEvent = keyEvent;

        }

        public int getKeyEvent(int duration) {

            currentDuration = duration;

            time = NES_Game_Player.currentTimeMillis();

            pressed = true;

            return keyEvent;

        }

    }

}
