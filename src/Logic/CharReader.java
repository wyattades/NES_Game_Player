package Logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;

/**
 * Directory: Main.NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/19/2015.
 */
public class CharReader {

    private int[][] charImageArray;

    private int WHITE = -1;

    private int[][] gameArray;

    private final static int charSize = 8;

    private final static int charSheetWidth = 16;
    private final static int charSheetHeight = 6;

    private final static char[] chars = {
            ' ', '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';' , '<', '=', '>', '?',
            '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K' , 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[' , '\\', ']', '^', '_',
            '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k' , 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{' , '|', '}', '~', ' '
    };


    //private int width, height;

    public CharReader(int w, int h) {

        //Import the char reference image
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("out/production/NES_Game_Player/Data/neschars.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Set a 0/1 array base on the char reference image
        charImageArray = new int[charSheetWidth*charSize][charSheetHeight*charSize];
        for (int i = 0; i < charSheetWidth*charSize; i ++) {
            for (int j = 0; j < charSheetHeight*charSize; j ++) {
                if (image.getRGB(i, j) == WHITE)
                    charImageArray[i][j] = WHITE;
            }
        }

    }

    BufferedImage test1,test2;

    public void setGameArray(int[][] gameArray) {
        this.gameArray = gameArray;
    }

    public char getChar(int x, int y, int textColor) {

        char c = '?';

        int[][] snapShotArray = subArray(gameArray,x,y,charSize,charSize);
        int[][] charImageSubArray = subArray(charImageArray, 3 * charSize, 4 * charSize, charSize, charSize);

        for (int i = 1, row = 0; i < chars.length-1; i++) {
            int col = i % charSheetWidth;
            if (Arrays.equals(
                    snapShotArray,
                    subArray(charImageArray, col * charSize, row * charSize, charSize, charSize))
                    ) {
                c = chars[i];
            }

        }

        test1 = new BufferedImage(8,8,BufferedImage.TYPE_INT_RGB);
        test2 = new BufferedImage(8,8,BufferedImage.TYPE_INT_RGB);



        for (int i = 0; i < charSize; i ++) {
            for (int j = 0; j < charSize; j ++) {
                if (snapShotArray[i][j] == -1) test1.setRGB(i,j,-1);
                if (charImageSubArray[i][j] == -1) test2.setRGB(i,j,-1);
            }
        }




        if (c == ' ') System.out.println("space");
        if (c == '?') System.out.println("Failed to locate matching char");

        return c;
    }

    //Creates a two dimensional subArray in the given bounds,
    //also sets all values in subArray to 0 or 1
    private int[][] subArray(int[][] fullArray, int x, int y, int w, int h) {

        int[][] result = new int[w][];

        for (int i = x, j = 0; i < x + w; i++, j++) {
            result[j] = Arrays.copyOfRange(fullArray[i], y, y + h);

            //Set the array to only hold 0 and 1
            for (int k = 0; k < w; k++) {
                if (result[j][k] != -1) result[j][k] = 0;
            }
        }

        return result;
    }

}
