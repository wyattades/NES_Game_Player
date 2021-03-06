package Main;

/**
 * Directory: Main.NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/18/2015.
 */
public class NES_Game_Player {

    public static void main(String[] args) {

        Control control = new Control();

        new Thread(control).start();

    }

    public static int currentTimeMillis() {
        long millisLong = System.currentTimeMillis();
        while (millisLong > Integer.MAX_VALUE) {
            millisLong -= Integer.MAX_VALUE;
        }
        return (int) millisLong;
    }

    public static void sleep(int amount) {
        try {
            Thread.sleep(amount);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
