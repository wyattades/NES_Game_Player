package Logic;

import ProgramIO.ExecutableControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Directory: Main.NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/19/2015.
 */
public class CharReader {

    private final int
            charSize = 8,
            charSheetWidth = 16,
            WHITE = -1;
    private final char[] chars = {
            ' ', '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';' , '<', '=', '>', '?',
            '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K' , 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[' , '\\', ']', '^', '_',
            '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k' , 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{' , '|', '}', '~', ' '
    };

    private Map<Character,int[]> charArrays;
    private int[] gameArray;
    private int textColor; //Not needed yet

    public CharReader(Color textColor) {

        this.textColor = textColor.getRGB();

        //Import the char reference image
        BufferedImage image = null;
        try {
            //TODO: Fix this file location String \/
            image = ImageIO.read(new File("out/production/NES_Game_Player/Data/neschars.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert image != null;

        int charImageWidth = image.getWidth(), charImageHeight = image.getHeight();
        int[] charImageArray = image.getRGB(0,0,charImageWidth,charImageHeight,null,0,charImageWidth);
        charArrays = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            int[] charArray = subArray(
                    charImageArray,
                    WHITE,
                    charImageWidth,
                    (i%charSheetWidth)*charSize,
                    (i/charSheetWidth)*charSize,
                    charSize,
                    charSize
            );

            charArrays.put(chars[i], charArray);

        }

    }

    public void setGameArray(int[] gameArray) {
        this.gameArray = gameArray;
    }

    public char getChar(int x, int y) {

        int[] snapShotArray = subArray(gameArray,WHITE,ExecutableControl.w,x,y,charSize,charSize);

        for (char c : charArrays.keySet()) {
            if (Arrays.equals(charArrays.get(c), snapShotArray)) {
                return c;
            }
        }

//        System.out.println("Can't find a char at location: " + x + "," + y);

        return 0;
    }

    private int[] subArray(int[] original, int checkColor, int p_w, int x, int y, int c_w, int c_h) {

        int[] result = new int[c_w*c_h];

        for (int i = 0; i < result.length; i++) {
            int nx = x+(i%c_w);
            int ny = y+(i/c_w);
            if (original[nx + (p_w * ny)] == checkColor) {
                result[i] = 1;
            }
        }

        return result;
    }

}
