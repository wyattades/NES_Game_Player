package Logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Directory: Main.NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/19/2015.
 */
public class CharReader {

    private final int
            charSize = 8,
            charSheetWidth = 16,
            charSheetHeight = 6,
            WHITE = -1;
    private final static char[] chars = {
            ' ', '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';' , '<', '=', '>', '?',
            '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K' , 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[' , '\\', ']', '^', '_',
            '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k' , 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{' , '|', '}', '~', ' '
    };

    private int[][] gameArray;
    private int[][] charImageArray;
    private int textColor;

    //TEMP
    public BufferedImage snapShotTest, charImageTest;

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
        //Set a -1/0 array based on the char reference image
        charImageArray = new int[charSheetWidth*charSize][charSheetHeight*charSize];
        for (int i = 0; i < charSheetWidth*charSize; i++) {
            for (int j = 0; j < charSheetHeight*charSize; j++) {
                if (image.getRGB(i,j) == WHITE)
                    charImageArray[i][j] = WHITE;
            }
        }

        printArray(charImageArray);

    }

    //TEMP
    public static void printArray(int[][] a) {
        System.out.println();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(((a[i][j]==0)?" ":"#") + " ");
            }
            System.out.println();
        }
    }

    public void setGameArray(int[][] gameArray) {
        this.gameArray = gameArray;
    }

    public char getChar(int x, int y) {

        char c = 0;

        int[][] snapShotArray = subArray(gameArray,x,y,charSize,charSize);
        int[][] charImageSubArray = subArray(charImageArray, 3 * charSize, 4 * charSize, charSize, charSize);

//        printArray(gameArray);
//        System.exit(0);

        for (int i = 1, row = 0; i < chars.length-1; i++) {
            int col = i % charSheetWidth;
            if (Arrays.equals(
                    snapShotArray,
                    subArray(charImageArray, col * charSize, row * charSize, charSize, charSize))
                    ) {
                c = chars[i];
            }

        }

        snapShotTest = new BufferedImage(charSize,charSize,BufferedImage.TYPE_INT_RGB);
        charImageTest = new BufferedImage(charSize,charSize,BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < charSize; i++) {
            for (int j = 0; j < charSize; j++) {
                if (snapShotArray[i][j] == WHITE) snapShotTest.setRGB(i,j,-1);
                //if (charImageSubArray[i][j] == WHITE) charImageTest.setRGB(i,j,-1);
            }
        }


//        if (c == ' ') System.out.println("space");
//        if (c == 0) System.out.println("Failed to locate matching char");

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
               // if (result[j][k] != textColor) result[j][k] = 0;
            }
        }

        return result;
    }

}
