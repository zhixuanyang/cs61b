package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private int midWidth = WIDTH / 2;
    private int midHeight = HEIGHT / 2;
    static Random random;

    public Game () {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.black);
        Font BigFont = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(BigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight + 10, "CS61B: THE GAME");
        Font SmallFont = new Font("Arial", Font.PLAIN, 20);
        StdDraw.setFont(SmallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, "New Game (N)");
        StdDraw.text(midWidth, midHeight - 1, "Load Game (L)");
        StdDraw.text(midWidth, midHeight - 2, "Quit (Q)");
    }
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] playWithInputString(String input) {
        char[] chars = input.toCharArray();
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        ter.initialize(WIDTH, HEIGHT);
        WorldGenerator wg = new WorldGenerator();
        TETile[][] world = new TETile[0][];
        TETile[][] finalWorldFrame = new TETile[0][];
        String temp = "";
        long rand;
        int loc = 1;
        if (chars[0] == 'N' || chars[0] == 'n') {
            world = wg.initialworld();
        }
        while (!Character.isAlphabetic(chars[loc])) {
            temp += String.valueOf(chars[loc]);
            loc += 1;
        }
        rand = Long.parseLong(temp);
        random = new Random(rand);
        if (chars[loc] == 'S' || chars[loc] == 's') {
            finalWorldFrame = wg.playthegame(world);
        }
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }
}
