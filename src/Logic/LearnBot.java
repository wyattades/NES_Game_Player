package Logic;

import ProgramIO.Output;

import java.util.ArrayList;

/**
 * Created by wyatt on 4/20/2016.
 */
public class LearnBot {

    private int[] screenArray;
    private int score, coins, world, level, time;
    private ArrayList<int[]> actions;

    public LearnBot() {
        actions = new ArrayList<>();
    }

    void update() {
        actions.add(new int[]{Output.CA_RIGHT, 100});
    }

    public ArrayList<int[]> getCurrentActions() {
        ArrayList<int[]> copy = new ArrayList<int[]>(actions);
        actions.clear();
        return copy;
    }

    public void giveNewInput(int[] scores, int[] screenArray) {
        score = scores[0];
        coins = scores[1];
        world = scores[2];
        level = scores[3];
        time = scores[4];
        this.screenArray = screenArray;
        update();
    }

    public void printData() {
//        System.out.println("Hello World!");
    }

}
