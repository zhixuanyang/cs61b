package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private int midWidth = WIDTH / 2;
    TETile[][] world;
    TETile[][] finalWorldFrame;
    WorldGenerator wg;
    long rand;
    private int midHeight = HEIGHT / 2;
    static Random random;
    private boolean gameStart;

    public Game() {
        gameStart = false;
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.black);
    }
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */

    public void DrawGUI() {
        Font bigFont = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight + 5, "CS61B: THE GAME");
        Font smallFont = new Font("Arial", Font.PLAIN, 16);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, "New Game (N)");
        StdDraw.text(midWidth, midHeight - 2, "Load Game (L)");
        StdDraw.text(midWidth, midHeight - 4, "Quit (Q)");
    }


    public String solicitNCharsInput(int n) {
        String input = "";
        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input += String.valueOf(key);
        }
        return input;
    }

    public String solicitNCharsSeed() {
        String input = "";
        drawSeed(input);
        char key = 1;
        while (!Character.isLetter(key)) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            key = StdDraw.nextKeyTyped();
            if (key == 'S' || key == 's') {
                return input;
            } else if (Character.isLetter(key)) {
                throw new IllegalArgumentException("Seed Should Be Numbers!");
            }
            input += String.valueOf(key);
            drawSeed(input);
        }
        return input;
    }

    public void drawSeed(String s) {
        StdDraw.clear(Color.black);
        DrawGUI();
        enterSeed();
        Font smallFont = new Font("Arial", Font.PLAIN, 16);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight - 8, s);
    }

    public void enterSeed() {
        Font smallFont = new Font("Arial", Font.PLAIN, 16);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight - 6, "Enter Seed : ");
    }

    public void StartGame(String seed) {
        rand = Long.parseLong(seed);
        random = new Random(rand);
        ter.initialize(WIDTH, HEIGHT);
        wg = new WorldGenerator();
        WorldGenerator wg = new WorldGenerator();
        world = wg.initialworld();
        gameStart = true;
        finalWorldFrame = wg.playthegame(world);
        ter.renderFrame(finalWorldFrame);
    }
    public void playWithKeyboard() {
        DrawGUI();
        while (!gameStart) {
            String input = solicitNCharsInput(1);
            if (input.equals("N") || input.equals("n")) {
                String seed = solicitNCharsSeed();
                StartGame(seed);
            }
        }
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
        wg = new WorldGenerator();
        world = new TETile[0][];
        finalWorldFrame = new TETile[0][];
        String temp = "";
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
