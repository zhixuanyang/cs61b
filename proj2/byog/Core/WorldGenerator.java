package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class WorldGenerator {
    private static Random RANDOM;
    private static int WIDTH;
    private static int HEIGHT;
    private static Position[] isolation;
    private static int index = 0;
    public static TETile[][] world;

    public static void generaterandom(long seed) {
        RANDOM = new Random(seed);
    }
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

    public static boolean detechRowsBack(TETile[][] world, Position p) {
        for (int i = p.x - 1 - p.room_width; i > 0; i--) {
            if (isGrass(world[i][p.y])) {
                for (int j = p.x; j > i ; j--) {
                    world[j][p.y] = Tileset.GRASS;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean detectRowsNextOne(TETile[][] world, Position p) {
        boolean temp = false;
        for (int i = p.x + 1; i < WIDTH; i++) {
            if (isGrass(world[i][p.y])) {
                for (int j = p.x; j < i; j++) {
                    world[j][p.y] = Tileset.GRASS;
                }
                return true;
            } else {
                temp = detechRowsBack(world, p);
            }
        }
        return temp;
    }

    public static boolean detechColumnBack(TETile[][] world, Position p) {
        for (int i = p.y - 1 - p.room_height; i > 0; i--) {
            if (isGrass(world[p.x][i])) {
                for (int j = p.y; j > i; j--) {
                    world[p.x][j] = Tileset.GRASS;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean detectColumnsNextOne(TETile[][] world, Position p) {
        boolean temp = false;
        for (int i = p.y + 1; i < HEIGHT; i++) {
            if (isGrass(world[p.x][i])) {
                for (int j = p.y; j < i; j++) {
                    world[p.x][j] = Tileset.GRASS;
                }
                return true;
            } else {
                temp = detechColumnBack(world, p);
            }
        }
        return temp;
    }

    public static void generateMultipleRooms(TETile[][] world, TETile t) {
        int number = getRandomNumberUsingNextInt(30, 40);
        isolation = new Position[number];
        Position temp;
        boolean rows;
        boolean columns;
        for (int i = 0; i < number; i++) {
            temp = addRoom(world, t);
            rows = detectRowsNextOne(world, temp);
            columns = detectColumnsNextOne(world, temp);
            if (!rows & !columns) {
                isolation[index] = temp;
                index += 1;
            }
        }
    }

    public static void doubleCheckHighway(TETile[][] world, Position[] iso) {
        for (int i = 0; i < index; i++) {
            if (!detectColumnsNextOne(world, iso[i])) {
                detectRowsNextOne(world,iso[i]);
            }
        }
    }
    public static TETile[][] initializetheworld(int x, int y) {
        TETile[][] world = new TETile[x][y];
        return world;
    }

    public static void getWH(int x, int y) {
        WIDTH = x;
        HEIGHT = y;
    }

    public static void getworld(TETile[][] temp) {
        world = temp;
    }

    public static TETile[][] playthegame(int x, int y, String input) {
        char[] chars = input.toCharArray();
        getWH(x, y);
        String temp = "";
        long rand;
        int loc = 1;
        if (chars[0] == 'N' || chars[0] == 'n') {
            getworld(initializetheworld(x, y));
        }
        while (!Character.isAlphabetic(chars[loc])) {
            temp += String.valueOf(chars[loc]);
            loc += 1;
        }
        rand = Long.parseLong(temp);
        generaterandom(rand);
        System.out.print(rand);
        if (chars[loc] == 'S' || chars[loc] == 's') {
            initialworld(world, WIDTH, HEIGHT);
            generateMultipleRooms(world, Tileset.GRASS);
            doubleCheckHighway(world, isolation);
            addFloor(world);
        }
        return world;
    }
}
