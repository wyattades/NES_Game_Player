import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * Directory: NES_Game_Player/PACKAGE_NAME/
 * Created by Wyatt on 7/19/2015.
 */
public class CharReader {

    private int[][] charImageArray;

    private int[] gameArray;

    private final static int charSize = 7;

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


    private int width, height;

    public CharReader(int w, int h) {

        this.width = w;
        this.height = h;

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
                if (image.getRGB(i, j) == Color.white.getRGB())
                    charImageArray[i][j] = 1;
            }
        }

    }

    public void setGameArray(int[] gameArray) {
        this.gameArray = gameArray;
    }

    public char getChar(int x, int y, int textColor) {

        char c = '?';

        for (int k = 0; k < charSheetWidth; k++) {
            for (int l = 0; l < charSheetHeight; l++) {
                boolean isChar = true;
                for (int i = 0; i < charSize; i++) {
                    for (int j = 0; j < charSize; j++) {
                        int gA = gameArray[(x + i) * charSize + y + j];
                        int cIA = charImageArray[k * charSize + i][l * charSize + j];
                        System.out.println((gA == Color.white.getRGB()) +" , "+(cIA == 1));
                        if (gA != textColor || cIA != 1) {
                            isChar = false;
                        }
                    }
                }
                if (isChar) {
                    c = chars[l * charSheetWidth + k];
                    break;
                }
            }
        }

        if (c == '?') System.out.println("Failed to locate matching char");

        return c;
    }

}
