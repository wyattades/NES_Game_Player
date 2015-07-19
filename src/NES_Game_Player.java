
/**
 * Directory: NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/18/2015.
 */
public class NES_Game_Player {

    public static void main(String[] args) {

        Interpreter interpreter = new Interpreter();

        new Thread(interpreter).start();

    }

    public static int currentTimeMillis() {
        long millisLong = System.currentTimeMillis();
        while (millisLong > Integer.MAX_VALUE) {
            millisLong -= Integer.MAX_VALUE;
        }
        return (int) millisLong;
    }

}
