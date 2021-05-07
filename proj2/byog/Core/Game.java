package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private boolean setupMode = true;     // flag to check whether setup has been done
    private boolean newGameMode = false; // flag to check whether a new game is gonna be generated
    private boolean quitMode = false;
    private String seedString = ""; // store input random seed numbers as String
    static Random random;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
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
    private void processInput(String input) {

        if (input == null) {
            System.out.println("No input given.");
            System.exit(0);
        }

        String first = Character.toString(input.charAt(0));
        first = first.toLowerCase(); // normalize an input to lower case
        processInputString(first);

        if (input.length() > 1) {
            String rest = input.substring(1);
            processInput(rest); // recursive call until input ends
        }

    }

    private void switchSetupMode() {
        setupMode = !setupMode;
    }

    private void switchNewGameMode() {
        newGameMode = !newGameMode;
    }

    private void switchQuitMode() {
        quitMode = !quitMode;
    }


    private void processInputString(String first) {

        if (setupMode) {      // when the setup hasn't been done
            switch (first) {
                case "n":       // new game gonna be generated
                    switchNewGameMode();
                    break;
                case "s":       // setup a new game
                    setupNewGame();
                    break;
                case "q":
                    System.exit(0);
                    break;
                default:        // append next seed integer to seedString
                    try {
                        Long.parseLong(first);
                        seedString += first;
                    } catch (NumberFormatException e) { // exit program if input is invalid
                        System.out.println("Invalid input given: " + first);
                        System.exit(0);
                    }
                    break;
            }
        }
    }

    private void setupNewGame() {
        random = new Random(Long.parseLong(seedString));
    }

    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        processInput(input);
        WorldGenerator wg = new WorldGenerator();
        TETile[][] world = wg.initialworld();
        TETile[][] finalWorldFrame = wg.playthegame(world);
        return finalWorldFrame;
    }
}
