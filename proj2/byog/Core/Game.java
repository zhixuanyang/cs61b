package byog.Core;
import byog.TileEngine.Tileset;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import static java.lang.System.exit;

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
    String inputfile = "";
    boolean gameOver;
    private int midHeight = HEIGHT / 2;
    static Random random;
    private boolean gameStart;
    String inputStringfile = "";

    public Game() {
        gameStart = false;
    }
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     * @return
     */

    public String displayWorldclass(double x, double y, TETile[][] tempworld) {
        int tempx = (int) x;
        int tempy = (int) y;
        return tempworld[tempx][tempy].description();
    }

    public void drawGUI() {
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
    public void setupGUI() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.black);
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
                inputfile += String.valueOf(key);
                return input;
            } else if (!Character.isDigit(key)) {
                throw new IllegalArgumentException("Seed Should Be Number!");
            }
            input += String.valueOf(key);
            inputfile += String.valueOf(key);
            drawSeed(input);
        }
        return input;
    }

    public void drawSeed(String s) {
        StdDraw.clear(Color.black);
        drawGUI();
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

    public void displayInfo(String info) {
        Font smallFont = new Font("Arial", Font.PLAIN, 16);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(3, 32, info);
        StdDraw.show();
    }

    public void save(String input) {
        File f = new File("./save_game.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(input);
        } catch (IOException e) {
            System.out.println(e);
            exit(0);
        }
    }

    public String open() {
        File f = new File("./save_game.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (String) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println(e);
                exit(0);
            } catch (IOException e) {
                System.out.println(e);
                exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("e");
                exit(0);
            }
        }
        return "";
    }


    public void movePlayer(TETile[][] tempworld, WorldGenerator tempwg, String input) {
        if (input.equals("W") || input.equals("w")) {
            if (!tempwg.isWall(tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y + 1])) {
                tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y] = Tileset.GRASS;
                if (tempwg.isLockeddoor(tempworld[tempwg.playerPosition.x]
                        [tempwg.playerPosition.y + 1])) {
                    gameOver = true;
                }
                tempworld[tempwg.playerPosition.x]
                        [tempwg.playerPosition.y + 1] = Tileset.PLAYER;
                tempwg.playerPosition.y = tempwg.playerPosition.y + 1;
            }
            inputfile += input;
            ter.renderFrame(tempworld);
        } else if (input.equals("S") || input.equals("s")) {
            if (!tempwg.isWall(tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y - 1])) {
                tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y] = Tileset.GRASS;
                if (tempwg.isLockeddoor(tempworld[tempwg.playerPosition.x]
                        [tempwg.playerPosition.y - 1])) {
                    gameOver = true;
                }
                tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y - 1] = Tileset.PLAYER;
                tempwg.playerPosition.y = tempwg.playerPosition.y - 1;
            }
            inputfile += input;
            ter.renderFrame(tempworld);
        } else if (input.equals("A") || input.equals("a")) {
            if (!tempwg.isWall(tempworld[tempwg.playerPosition.x - 1][tempwg.playerPosition.y])) {
                tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y] = Tileset.GRASS;
                if (tempwg.isLockeddoor(tempworld[tempwg.playerPosition.x - 1]
                        [tempwg.playerPosition.y])) {
                    gameOver = true;
                }
                tempworld[tempwg.playerPosition.x - 1][tempwg.playerPosition.y] = Tileset.PLAYER;
                tempwg.playerPosition.x = tempwg.playerPosition.x - 1;
            }
            inputfile += input;
            ter.renderFrame(tempworld);
        } else if (input.equals("D") || input.equals("d")) {
            if (!tempwg.isWall(tempworld[tempwg.playerPosition.x + 1][tempwg.playerPosition.y])) {
                tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y] = Tileset.GRASS;
                if (tempwg.isLockeddoor(tempworld[tempwg.playerPosition.x + 1]
                        [tempwg.playerPosition.y])) {
                    gameOver = true;
                }
                tempworld[tempwg.playerPosition.x + 1][tempwg.playerPosition.y] = Tileset.PLAYER;
                tempwg.playerPosition.x = tempwg.playerPosition.x + 1;
            }
            inputfile += input;
            ter.renderFrame(tempworld);
        } else if (input.equals(":")) {
            boolean temp = assessmentQ();
            if (temp) {
                save(inputfile);
                exit(0);
            }
        }
    }

    public boolean assessmentQ() {
        String input = solicitNCharsInput(1);
        if (input.equals("Q") || input.equals("q")) {
            return true;
        }
        return false;
    }

    public void startGame() {
        ter.initialize(WIDTH, HEIGHT + 4);
        wg = new WorldGenerator();
        world = wg.initialworld();
        gameStart = true;
        finalWorldFrame = wg.playthegame(world);
        while (!gameOver) {
            ter.renderFrame(finalWorldFrame);
            displayInfo(displayWorldclass(StdDraw.mouseX(), StdDraw.mouseY(), finalWorldFrame));
            if (StdDraw.hasNextKeyTyped()) {
                movePlayer(finalWorldFrame, wg, solicitNCharsInput(1));
            }
        }
        StdDraw.clear(Color.BLACK);
        Font bigFont = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight + 3, "You Won!");
        StdDraw.show();
    }

    public void exitGame() {
        exit(0);
    }

    public void playWithKeyboard() {
        setupGUI();
        drawGUI();
        while (!gameStart) {
            String input = solicitNCharsInput(1);
            if (input.equals("N") || input.equals("n")) {
                inputfile += input;
                String seed = solicitNCharsSeed();
                rand = Long.parseLong(seed);
                random = new Random(rand);
                startGame();
            } else if (input.equals("q") || input.equals("Q")) {
                exitGame();
            } else if (input.equals("L") || input.equals("l")) {
                ter.initialize(WIDTH, HEIGHT + 4);
                gameOver = false;
                gameStart = true;
                finalWorldFrame = playWithInputString(open());
                wg = new WorldGenerator();
                wg.getPlayerposition(finalWorldFrame);
                inputfile += open();
                while (!gameOver) {
                    ter.renderFrame(finalWorldFrame);
                    displayInfo(displayWorldclass(StdDraw.mouseX(),
                            StdDraw.mouseY(), finalWorldFrame));
                    if (StdDraw.hasNextKeyTyped()) {
                        movePlayer(finalWorldFrame, wg, solicitNCharsInput(1));
                    }
                }
                StdDraw.clear(Color.BLACK);
                Font bigFont = new Font("Arial", Font.BOLD, 40);
                StdDraw.setFont(bigFont);
                StdDraw.setPenColor(Color.white);
                StdDraw.text(WIDTH / 2, HEIGHT / 2 + 3, "You Won!");
                StdDraw.show();
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


    public void movePlayerString(TETile[][] tempworld, WorldGenerator tempwg, String input,
                                 char[] inputlist, int i) {
        if (input.equals("W") || input.equals("w")) {
            if (!tempwg.isWall(tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y + 1])) {
                tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y] = Tileset.GRASS;
                if (tempwg.isLockeddoor(tempworld[tempwg.playerPosition.x]
                        [tempwg.playerPosition.y + 1])) {
                    gameOver = true;
                }
                tempworld[tempwg.playerPosition.x]
                        [tempwg.playerPosition.y + 1] = Tileset.PLAYER;
                tempwg.playerPosition.y = tempwg.playerPosition.y + 1;
            }
            inputStringfile += input;
        } else if (input.equals("S") || input.equals("s")) {
            if (!tempwg.isWall(tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y - 1])) {
                tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y] = Tileset.GRASS;
                if (tempwg.isLockeddoor(tempworld[tempwg.playerPosition.x]
                        [tempwg.playerPosition.y - 1])) {
                    gameOver = true;
                }
                tempworld[tempwg.playerPosition.x]
                        [tempwg.playerPosition.y - 1] = Tileset.PLAYER;
                tempwg.playerPosition.y = tempwg.playerPosition.y - 1;
            }
            inputStringfile += input;
        } else if (input.equals("A") || input.equals("a")) {
            if (!tempwg.isWall(tempworld[tempwg.playerPosition.x - 1][tempwg.playerPosition.y])) {
                tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y] = Tileset.GRASS;
                if (tempwg.isLockeddoor(tempworld[tempwg.playerPosition.x - 1]
                        [tempwg.playerPosition.y])) {
                    gameOver = true;
                }
                tempworld[tempwg.playerPosition.x - 1][tempwg.playerPosition.y] = Tileset.PLAYER;
                tempwg.playerPosition.x = tempwg.playerPosition.x - 1;
            }
            inputStringfile += input;
        } else if (input.equals("D") || input.equals("d")) {
            if (!tempwg.isWall(tempworld[tempwg.playerPosition.x + 1][tempwg.playerPosition.y])) {
                tempworld[tempwg.playerPosition.x][tempwg.playerPosition.y] = Tileset.GRASS;
                if (tempwg.isLockeddoor(tempworld[tempwg.playerPosition.x + 1]
                        [tempwg.playerPosition.y])) {
                    gameOver = true;
                }
                tempworld[tempwg.playerPosition.x + 1][tempwg.playerPosition.y] = Tileset.PLAYER;
                tempwg.playerPosition.x = tempwg.playerPosition.x + 1;
            }
            inputStringfile += input;
        } else if (input.equals(":")) {
            boolean temp = assessmentQString(String.valueOf(inputlist[i + 1]));
            if (temp) {
                save(inputStringfile);
            }
        }
    }

    public boolean assessmentQString(String input) {
        if (input.equals("Q") || input.equals("q")) {
            return true;
        }
        return false;
    }

    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        if (input.length() == 0) {
            exit(0);
        }
        char[] chars = input.toCharArray();
        wg = new WorldGenerator();
        world = new TETile[0][];
        finalWorldFrame = new TETile[0][];
        String temp = "";
        int loc = 0;
        if (chars[loc] == 'N' || chars[loc] == 'n') {
            world = wg.initialworld();
            inputStringfile += String.valueOf(chars[0]);
            loc += 1;
        } else if (chars[loc] == 'L' || chars[loc] == 'l') {
            finalWorldFrame = playWithInputString(open());
            wg = new WorldGenerator();
            wg.getPlayerposition(finalWorldFrame);
            inputStringfile += open();
            loc += 1;
            for (int i = loc; i < chars.length; i++) {
                movePlayerString(finalWorldFrame, wg, String.valueOf(chars[i]), chars, i);
            }
            return finalWorldFrame;
        }

        while (!Character.isAlphabetic(chars[loc])) {
            temp += String.valueOf(chars[loc]);
            inputStringfile += String.valueOf(chars[loc]);
            loc += 1;
        }
        if (chars[0] != 'L' || chars[0] != 'l') {
            rand = Long.parseLong(temp);
            random = new Random(rand);
        }
        if (chars[loc] == 'S' || chars[loc] == 's') {
            finalWorldFrame = wg.playthegame(world);
            inputStringfile += String.valueOf(chars[loc]);
            loc += 1;
        }
        while (loc < chars.length) {
            movePlayerString(finalWorldFrame, wg, String.valueOf(chars[loc]), chars, loc);
            loc += 1;
        }
        return finalWorldFrame;
    }
}
