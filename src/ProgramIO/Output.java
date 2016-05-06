package ProgramIO;

import Main.NES_Game_Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Directory: Main.NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/18/2015.
 */
public class Output {

    public ArrayList<CustomAction> actions;
    private Robot robot;

    public static int
            CA_A = 0,
            CA_B = 1,
            CA_UP = 2,
            CA_DOWN = 3,
            CA_LEFT = 4,
            CA_RIGHT = 5,
            CA_SELECT = 6,
            CA_START = 7;

    public Output(Robot robot) {

        this.robot = robot;

        actions = new ArrayList<>();

        actions.add(new CustomAction("a", KeyEvent.VK_X));
        actions.add(new CustomAction("b", KeyEvent.VK_Z));
        actions.add(new CustomAction("up", KeyEvent.VK_UP));
        actions.add(new CustomAction("down", KeyEvent.VK_DOWN));
        actions.add(new CustomAction("left", KeyEvent.VK_LEFT));
        actions.add(new CustomAction("right", KeyEvent.VK_RIGHT));
        actions.add(new CustomAction("select", KeyEvent.VK_A));
        actions.add(new CustomAction("start", KeyEvent.VK_S));

    }

    public void performActions() {

        for (CustomAction c : actions) {
            if (c.currentDuration > 0) {
                robot.keyPress(c.keyEvent);
                if (c.pressed) {

                    c.pressed = false;
                    System.out.println(c.name + " , " + c.currentDuration);
                }
                c.currentDuration--;
            } else if (c.currentDuration == 0) {
                robot.keyRelease(c.keyEvent);
            }
        }

    }

    public void addActions(ArrayList<int[]> currentActions) {
        for (int[] a : currentActions) {
            if (a[0] >= actions.size()) {
                System.out.println("Invalid action index: " + a[0]);
                System.exit(1);
            }

            actions.get(a[0]).setDuration(a[1]);
        }
    }

    private class CustomAction {

        private boolean pressed;

        public int currentDuration;

        private int time, keyEvent;

        public String name;

        public CustomAction(String name, int keyEvent) {

            this.name = name;

            this.keyEvent = keyEvent;

            pressed = true;

        }

        public void setDuration(int d) {
            currentDuration = d;
//            pressed = true;
//            time = NES_Game_Player.currentTimeMillis();
        }

//        public int getKeyEvent() {
//            return keyEvent;
//        }

    }

}
