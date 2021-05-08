package byog.lab5;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class addHexagon {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    private static class Position {

        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static Position addHexagon(TETile[][] world, Position p, int s, TETile t) {
        drawLastHalf(world, p, s, t);
        Position new_p = new Position(p.x, p.y + s);
        drawFirstHalf(world, new_p, s, t);
        Position pos = new Position(p.x, p.y);
        return pos;
    }

    public static Position up_add(TETile[][] world, Position p, int s, TETile t) {
        Position temp = new Position(p.x, p.y + 2 * s);
        addHexagon(world, temp, s, t);
        return temp;
    }
    public static Position left_add(TETile[][] world, Position p, int s, TETile t) {
        Position temp = new Position(p.x - s * 2 + 1, p.y + s);
        addHexagon(world, temp, s, t);
        return temp;
    }

    public static Position right_add(TETile[][] world, Position p, int s, TETile t) {
        Position temp = new Position(p.x + s * 2 - 1, p.y + s);
        addHexagon(world, temp, s, t);
        return temp;
    }

    public static void drawLastHalf(TETile[][] world, Position p, int s, TETile t) {
        int temp = 0;
        for (int col = 0; col < s; col++) {
            for (int row = 0; row < s; row++) {
                world[p.x - row][p.y + col] = t;
                world[p.x - row + temp][p.y + col] = t;
                world[p.x - row - temp][p.y + col] = t;
            }
            temp += 1;
        }
    }

    public static void drawFirstHalf(TETile[][] world, Position p, int s, TETile t) {
        int temp = s - 1;
        for (int col = 0; col < s; col++) {
            for (int row = 0; row < s; row++) {
                world[p.x - row][p.y + col] = t;
                world[p.x - row + temp][p.y + col] = t;
                world[p.x - row - temp][p.y + col] = t;
            }
            temp -= 1;
        }
    }

    public static void initialworld (TETile[][] world,int width, int height) {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initialworld(world, WIDTH, HEIGHT);
        Position bottom_pos = new Position(WIDTH / 2, 0);
        Position initial_pos;
        Position left_position;
        Position right_position;
        Position up_position;
        initial_pos = addHexagon(world, bottom_pos, 3, Tileset.MOUNTAIN);
        right_position = right_add(world, initial_pos, 3, Tileset.SAND);
        right_position = right_add(world, right_position, 3, Tileset.WALL);
        up_position = up_add(world, initial_pos, 3, Tileset.UNLOCKED_DOOR);
        left_add(world, up_position, 3, Tileset.GRASS);
        right_add(world, up_position, 3, Tileset.FLOWER);
        left_position = left_add(world, initial_pos, 3, Tileset.WATER);
        left_position = left_add(world, left_position, 3, Tileset.LOCKED_DOOR);
        left_position = up_add(world, left_position, 3, Tileset.MOUNTAIN);
        right_position = up_add(world, right_position, 3, Tileset.MOUNTAIN);
        left_position = right_add(world, left_position, 3, Tileset.UNLOCKED_DOOR);
        right_position = left_add(world, right_position, 3, Tileset.WATER);
        up_position = up_add(world, up_position, 3, Tileset.MOUNTAIN);
        left_position = left_add(world, left_position, 3, Tileset.SAND);
        up_position = up_add(world, up_position, 3, Tileset.FLOOR);
        right_position = right_add(world, right_position, 3, Tileset.GRASS);
        up_position = up_add(world, up_position, 3, Tileset.MOUNTAIN);
        left_position = right_add(world, left_position, 3, Tileset.FLOWER);
        right_position = left_add(world, right_position, 3, Tileset.WALL);
        ter.renderFrame(world);
    }
}