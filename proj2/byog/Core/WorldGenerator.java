package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class WorldGenerator {
    static Random RANDOM;
    private static int WIDTH;
    private static int HEIGHT;
    private static Position[] isolation;
    private static int index = 0;
    public static void generaterandom(long seed) {
        RANDOM = new Random(seed);
    }

    private static class Position {

        private int x;
        private int y;
        private int roomwidth;
        private int roomheight;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void getWidthHeight(int a, int b) {
            roomwidth = a;
            roomheight = b;
        }
    }

    public static TETile[][] initialworld() {
        TETile[][] temp = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                temp[x][y] = Tileset.NOTHING;
            }
        }
        return temp;
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static Position generatePosition() {
        Position temp = new Position(getRandomNumberUsingNextInt(1, WIDTH - 1),
                getRandomNumberUsingNextInt(1, HEIGHT - 1));
        return temp;
    }

    public static Position generateRoom(TETile[][] w, Position p, TETile t) {
        int width = getRandomNumberUsingNextInt(1, 8);
        int height = getRandomNumberUsingNextInt(1, 8);
        Position temp;
        int difx = p.x;
        int dify = p.y;
        if (p.x + width > WIDTH - 1) {
            difx = p.x - ((p.x + width) - WIDTH) - 1;
        }
        if (p.y + height > HEIGHT - 1) {
            dify = p.y - ((p.y + height) - HEIGHT) - 1;
        }
        temp = new Position(difx, dify);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                w[temp.x + i][temp.y + j] = t;
            }
        }
        temp = new Position(difx + width - 1, dify + height - 1);
        temp.getWidthHeight(width, height);
        return temp;
    }

    public static Position addRoom(TETile[][] w, TETile t) {
        Position p = generatePosition();
        return generateRoom(w, p, t);
    }

    public static boolean isGrass(TETile t) {
        return t == Tileset.GRASS;
    }

    public static boolean isNothing(TETile t) {
        return t == Tileset.NOTHING;
    }


    public static boolean checkSurroding(TETile[][] tempworld, int x, int y) {
        if (x == 0 & y == 0) {
            for (int i = x; i < x + 2; i++) {
                for (int j = y; j < y + 2; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (x == 0 & y != 0 & y != HEIGHT - 1) {
            for (int i = x; i < x + 2; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (y == 0 & x != 0 & x != WIDTH - 1) {
            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y; j < y + 2; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (x == WIDTH - 1 & y == HEIGHT - 1) {
            for (int i = x - 1; i < x + 1; i++) {
                for (int j = y - 1; j < y + 1; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (x == WIDTH - 1 & y != HEIGHT - 1 & y != 0) {
            for (int i = x - 1; i < x + 1; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (y == HEIGHT - 1 & x != WIDTH - 1 & x != 0) {
            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y - 1; j < HEIGHT; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (x == 0 & y == HEIGHT - 1) {
            for (int i = x; i < x + 2; i++) {
                for (int j = y - 1; j < HEIGHT; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (x == WIDTH - 1 & y == 0) {
            for (int i = x - 1; i < WIDTH; i++) {
                for (int j = y; j < y + 2; j++) {
                    if (isGrass(tempworld[i][j])) {
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
                if (isGrass(tempworld[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void addFloor(TETile[][] tempworld) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (isNothing(tempworld[i][j]) & checkSurroding(tempworld, i, j)) {
                    tempworld[i][j] = Tileset.WALL;
                }
            }
        }
    }

    public static boolean detechRowsBack(TETile[][] tempworld, Position p) {
        for (int i = p.x - 1 - p.roomwidth; i > 0; i--) {
            if (isGrass(tempworld[i][p.y])) {
                for (int j = p.x; j > i; j--) {
                    tempworld[j][p.y] = Tileset.GRASS;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean detectRowsNextOne(TETile[][] tempworld, Position p) {
        boolean temp = false;
        for (int i = p.x + 1; i < WIDTH; i++) {
            if (isGrass(tempworld[i][p.y])) {
                for (int j = p.x; j < i; j++) {
                    tempworld[j][p.y] = Tileset.GRASS;
                }
                return true;
            } else {
                temp = detechRowsBack(tempworld, p);
                if (temp) {
                    return temp;
                }
            }
        }
        return temp;
    }

    public static boolean detechColumnBack(TETile[][] tempworld, Position p) {
        for (int i = p.y - 1 - p.roomheight; i > 0; i--) {
            if (isGrass(tempworld[p.x][i])) {
                for (int j = p.y; j > i; j--) {
                    tempworld[p.x][j] = Tileset.GRASS;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean detectColumnsNextOne(TETile[][] tempworld, Position p) {
        boolean temp = false;
        for (int i = p.y + 1; i < HEIGHT; i++) {
            if (isGrass(tempworld[p.x][i])) {
                for (int j = p.y; j < i; j++) {
                    tempworld[p.x][j] = Tileset.GRASS;
                }
                return true;
            } else {
                temp = detechColumnBack(tempworld, p);
                if (temp) {
                    return temp;
                }
            }
        }
        return temp;
    }

    public static void generateMultipleRooms(TETile[][] tempworld, TETile t) {
        int number = getRandomNumberUsingNextInt(30, 40);
        isolation = new Position[number];
        Position temp;
        boolean rows;
        boolean columns;
        for (int i = 0; i < number; i++) {
            temp = addRoom(tempworld, t);
            rows = detectRowsNextOne(tempworld, temp);
            columns = detectColumnsNextOne(tempworld, temp);
            if (!rows & !columns) {
                isolation[index] = temp;
                index += 1;
            }
        }
    }

    public static void doubleCheckHighway(TETile[][] tempworld, Position[] iso) {
        for (int i = 0; i < index; i++) {
            if (!detectColumnsNextOne(tempworld, iso[i])) {
                detectRowsNextOne(tempworld, iso[i]);
            }
        }
    }

    public static void getWH(int x, int y) {
        WIDTH = x;
        HEIGHT = y;
    }

    public static boolean detechRowsWallBack(TETile[][] tempworld, Position p) {
        for (int i = p.x; i > 0; i--) {
            if (isWall(tempworld[i][p.y])) {
                tempworld[i][p.y] = Tileset.LOCKED_DOOR;
                return true;
            }
        }
        return false;
    }

    public static boolean detectRowsWall(TETile[][] tempworld, Position p) {
        boolean temp = false;
        for (int i = p.x; i < WIDTH; i++) {
            if (isWall(tempworld[i][p.y])) {
                tempworld[i][p.y] = Tileset.LOCKED_DOOR;
                return true;
            } else {
                temp = detechRowsWallBack(tempworld, p);
                if (temp) {
                    return temp;
                }
            }
        }
        return temp;
    }

    public static boolean detechColumnWallBack(TETile[][] tempworld, Position p) {
        for (int i = p.y; i > 0; i--) {
            if (isWall(tempworld[p.x][i])) {
                tempworld[p.x][i] = Tileset.LOCKED_DOOR;
                return true;
            }
        }
        return false;
    }

    public static boolean detectColumnsWall(TETile[][] tempworld, Position p) {
        boolean temp = false;
        for (int i = p.y; i < HEIGHT; i++) {
            if (isWall(tempworld[p.x][i])) {
                tempworld[p.x][i] = Tileset.LOCKED_DOOR;
                return true;
            } else {
                temp = detechColumnWallBack(tempworld, p);
                if (temp) {
                    return temp;
                }
            }
        }
        return temp;
    }


    public static boolean isWall(TETile t) {
        return t == Tileset.WALL;
    }

    public static void generateRandomDoor(TETile[][] temp) {
        boolean aka;
        Position p = new Position(getRandomNumberUsingNextInt(0, WIDTH),
                getRandomNumberUsingNextInt(0, HEIGHT));
        aka = detectRowsWall(temp, p);
        if (!aka) {
            detectColumnsWall(temp, p);
        }
    }

    public static TETile[][] playthegame(TETile[][] temp) {
        generateMultipleRooms(temp, Tileset.GRASS);
        if (index != 0) {
            doubleCheckHighway(temp, isolation);
        }
        addFloor(temp);
        generateRandomDoor(temp);
        return temp;
    }
}
