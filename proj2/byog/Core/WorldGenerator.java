package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class WorldGenerator {
    private static final long SEED = 123;
    private static final Random RANDOM = new Random(SEED);
    private static final int WIDTH = 70;
    private static final int HEIGHT = 35;

    private static class Position {

        private int x;
        private int y;
        private int room_width;
        private int room_height;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void getWidthHeight(int x, int y) {
            room_width = x;
            room_height = y;
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

    public static Position generateRoom(TETile[][] world, Position p, TETile t) {
        int width = getRandomNumberUsingNextInt(1, 8);
        int height = getRandomNumberUsingNextInt(1, 8);
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
        temp = new Position(dif_x + width - 1, dif_y + height - 1);
        temp.getWidthHeight(width, height);
        return temp;
    }

    public static Position addRoom(TETile[][] world, TETile t) {
        Position p = generatePosition();
        return generateRoom(world, p, t);
    }

    public static boolean isGrass(TETile t) {
        return t == Tileset.GRASS;
    }

    public static boolean isNothing(TETile t) {
        return t == Tileset.NOTHING;
    }


    public static boolean checkSurroding(TETile[][] world, int x, int y) {
            if (x == 0 & y == 0) {
                for (int i = x; i < x + 2; i++) {
                    for (int j = y; j < y + 2; j++) {
                        if (isGrass(world[i][j])) {
                            return true;
                        }
                    }
                }
            } else if (x == 0 & y != 0 & y != HEIGHT - 1) {
                for (int i = x; i < x + 2; i++) {
                    for (int j = y - 1; j < y + 2; j++) {
                        if (isGrass(world[i][j])) {
                            return true;
                        }
                    }
                }
            } else if (y == 0 & x != 0 & x != WIDTH - 1) {
                for (int i = x - 1; i < x + 2; i++) {
                    for (int j = y; j < y + 2; j++) {
                        if (isGrass(world[i][j])) {
                            return true;
                        }
                    }
                }
            } else if (x == WIDTH - 1 & y == HEIGHT - 1) {
                for (int i = x - 1; i < x + 1; i++) {
                    for (int j = y - 1; j < y + 1; j++) {
                        if (isGrass(world[i][j])) {
                            return true;
                        }
                    }
                }
            } else if (x == WIDTH - 1 & y != HEIGHT - 1 & y != 0) {
                for (int i = x - 1; i < x + 1; i++) {
                    for (int j = y - 1; j < y + 2; j++) {
                        if (isGrass(world[i][j])) {
                            return true;
                        }
                    }
                }
            } else if (y == HEIGHT - 1 & x != WIDTH - 1 & x != 0) {
                for (int i = x - 1; i < x + 2; i++) {
                    for (int j = y - 1; j < HEIGHT; j++) {
                        if (isGrass(world[i][j])) {
                            return true;
                        }
                    }
                }
            } else if (x == 0 & y == HEIGHT - 1) {
                for (int i = x; i < x + 2; i++) {
                    for (int j = y - 1; j < HEIGHT; j++) {
                        if (isGrass(world[i][j])) {
                            return true;
                        }
                    }
                }
            } else if (x == WIDTH - 1 & y == 0) {
                for (int i = x - 1; i < WIDTH; i++) {
                    for (int j = y; j < y + 2; j++) {
                        if (isGrass(world[i][j])) {
                            return true;
                        }
                    }
                }
            }
        if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
                return false;
            }
            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (isGrass(world[i][j])) {
                        return true;
                    }
                }
            }
            return false;
    }

    public static void addFloor(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (isNothing(world[i][j]) & checkSurroding(world, i, j)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    public static void detechRowsBack(TETile[][] world, Position p) {
        for (int i = p.x - 1 - p.room_width; i > 0; i--) {
            if (isGrass(world[i][p.y])) {
                for (int j = p.x; j > i ; j--) {
                    world[j][p.y] = Tileset.GRASS;
                }
                break;
            }
        }
    }

    public static void detectRowsNextOne(TETile[][] world, Position p) {
        for (int i = p.x + 1; i < WIDTH; i++) {
            if (isGrass(world[i][p.y])) {
                for (int j = p.x; j < i; j++) {
                    world[j][p.y] = Tileset.GRASS;
                }
                break;
            } else {
                detechRowsBack(world, p);
            }
        }
    }

    public static void detechColumnBack(TETile[][] world, Position p) {
        for (int i = p.y - 1 - p.room_height; i > 0; i--) {
            if (isGrass(world[p.x][i])) {
                for (int j = p.y; j > i; j--) {
                    world[p.x][j] = Tileset.GRASS;
                }
                break;
            }
        }
    }

    public static void detectColumnsNextOne(TETile[][] world, Position p) {
        for (int i = p.y + 1; i < HEIGHT; i++) {
            if (isGrass(world[p.x][i])) {
                for (int j = p.y; j < i; j++) {
                    world[p.x][j] = Tileset.GRASS;
                }
                break;
            } else {
                detechColumnBack(world, p);
            }
        }
    }

    public static void generateMultipleRooms(TETile[][] world, TETile t) {
        int number = getRandomNumberUsingNextInt(30, 40);
        Position temp;
        for (int i = 0; i < 15; i++) {
            temp = addRoom(world, t);
            detectRowsNextOne(world, temp);
            detectColumnsNextOne(world, temp);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initialworld(world, WIDTH, HEIGHT);
        generateMultipleRooms(world, Tileset.GRASS);
        //addFloor(world);
        ter.renderFrame(world);
    }
}
