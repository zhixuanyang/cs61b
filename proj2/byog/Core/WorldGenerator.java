package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class WorldGenerator {
    private static final long SEED = 111;
    private static final Random RANDOM = new Random(SEED);
    private static final int WIDTH = 70;
    private static final int HEIGHT = 35;

    private static class Position {

        private int x;
        private int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void initialworld(TETile[][] world, int width, int height) {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static Position generatePosition() {
        Position temp = new Position(getRandomNumberUsingNextInt(1, WIDTH - 1),
                getRandomNumberUsingNextInt(1, HEIGHT - 1));
        return temp;
    }

    public static void generateRoom(TETile[][] world, Position p, TETile t) {
        int width = getRandomNumberUsingNextInt(2, 8);
        int height = getRandomNumberUsingNextInt(2, 8);
        Position temp;
        int dif_x = p.x;
        int dif_y = p.y;
        if (p.x + width > WIDTH - 1) {
             dif_x = p.x - ((p.x + width) - WIDTH) - 1;
        }
        if (p.y + height > HEIGHT - 1) {
            dif_y = p.y - ((p.y + height) - HEIGHT) - 1;
        }
        temp = new Position(dif_x, dif_y);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[temp.x + i][temp.y + j] = t;
            }
        }
    }

    public static void addRoom(TETile[][] world, TETile t) {
        Position p = generatePosition();
        generateRoom(world, p, t);
    }

    public static void generateMultipleRooms(TETile[][] world, TETile t) {
        int number = getRandomNumberUsingNextInt(20, 30);
        for (int i = 0; i < number; i++) {
            addRoom(world, t);
        }
    }

    public static void main(String[] args) {
        System.out.print(WIDTH);
        System.out.print(HEIGHT);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initialworld(world, WIDTH, HEIGHT);
        generateMultipleRooms(world, Tileset.GRASS);
        ter.renderFrame(world);
    }
}
