package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WorldGenerator {
    private Position[] isolation;
    private int index = 0;
    Position playerPosition;

    public class Position {

        int x;
        int y;
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

    public TETile[][] initialworld() {
        TETile[][] temp = new TETile[Game.WIDTH][Game.HEIGHT + 4];
        for (int x = 0; x < Game.WIDTH; x += 1) {
            for (int y = 0; y < Game.HEIGHT + 4; y += 1) {
                temp[x][y] = Tileset.NOTHING;
            }
        }
        return temp;
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        return Game.random.nextInt(max - min) + min;
    }

    public Position generatePosition() {
        Position temp = new Position(getRandomNumberUsingNextInt(1, Game.WIDTH - 1),
                getRandomNumberUsingNextInt(1, Game.HEIGHT - 1));
        return temp;
    }

    public Position generateRoom(TETile[][] w, Position p, TETile t) {
        int width = getRandomNumberUsingNextInt(1, 8);
        int height = getRandomNumberUsingNextInt(1, 8);
        Position temp;
        int difx = p.x;
        int dify = p.y;
        if (p.x + width > Game.WIDTH - 1) {
            difx = p.x - ((p.x + width) - Game.WIDTH) - 1;
        }
        if (p.y + height > Game.HEIGHT - 1) {
            dify = p.y - ((p.y + height) - Game.HEIGHT) - 1;
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

    public Position addRoom(TETile[][] w, TETile t) {
        Position p = generatePosition();
        return generateRoom(w, p, t);
    }

    public boolean isGrass(TETile t) {
        return t == Tileset.GRASS;
    }

    public boolean isNothing(TETile t) {
        return t == Tileset.NOTHING;
    }


    public boolean checkSurroding(TETile[][] tempworld, int x, int y) {
        if (x == 0 & y == 0) {
            for (int i = x; i < x + 2; i++) {
                for (int j = y; j < y + 2; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (x == 0 & y != 0 & y != Game.HEIGHT - 1) {
            for (int i = x; i < x + 2; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (y == 0 & x != 0 & x != Game.WIDTH - 1) {
            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y; j < y + 2; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (x == Game.WIDTH - 1 & y == Game.HEIGHT - 1) {
            for (int i = x - 1; i < x + 1; i++) {
                for (int j = y - 1; j < y + 1; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (x == Game.WIDTH - 1 & y != Game.HEIGHT - 1 & y != 0) {
            for (int i = x - 1; i < x + 1; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (y == Game.HEIGHT - 1 & x != Game.WIDTH - 1 & x != 0) {
            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y - 1; j < Game.HEIGHT; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (x == 0 & y == Game.HEIGHT - 1) {
            for (int i = x; i < x + 2; i++) {
                for (int j = y - 1; j < Game.HEIGHT; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        } else if (x == Game.WIDTH - 1 & y == 0) {
            for (int i = x - 1; i < Game.WIDTH; i++) {
                for (int j = y; j < y + 2; j++) {
                    if (isGrass(tempworld[i][j])) {
                        return true;
                    }
                }
            }
        }
        if (x == 0 || x == Game.WIDTH - 1 || y == 0 || y == Game.HEIGHT - 1) {
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

    public void addFloor(TETile[][] tempworld) {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (isNothing(tempworld[i][j]) & checkSurroding(tempworld, i, j)) {
                    tempworld[i][j] = Tileset.WALL;
                }
            }
        }
    }

    public boolean detechRowsBack(TETile[][] tempworld, Position p) {
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

    public boolean detectRowsNextOne(TETile[][] tempworld, Position p) {
        boolean temp = false;
        for (int i = p.x + 1; i < Game.WIDTH; i++) {
            if (isGrass(tempworld[i][p.y])) {
                for (int j = p.x; j < i; j++) {
                    tempworld[j][p.y] = Tileset.GRASS;
                }
                return true;
            }
        }
        temp = detechRowsBack(tempworld, p);
        if (temp) {
            return temp;
        }
        return temp;
    }

    public boolean detechColumnBack(TETile[][] tempworld, Position p) {
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

    public boolean detectColumnsNextOne(TETile[][] tempworld, Position p) {
        boolean temp = false;
        for (int i = p.y + 1; i < Game.HEIGHT; i++) {
            if (isGrass(tempworld[p.x][i])) {
                for (int j = p.y; j < i; j++) {
                    tempworld[p.x][j] = Tileset.GRASS;
                }
                return true;
            }
        }
        temp = detechColumnBack(tempworld, p);
        if (temp) {
            return temp;
        }
        return temp;
    }

    public void generateMultipleRooms(TETile[][] tempworld, TETile t) {
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

    public void doubleCheckHighway(TETile[][] tempworld, Position[] iso) {
        for (int i = 0; i < index; i++) {
            if (!detectColumnsNextOne(tempworld, iso[i])) {
                detectRowsNextOne(tempworld, iso[i]);
            }
        }
    }

    public boolean detechRowsWallBack(TETile[][] tempworld, Position p) {
        for (int i = p.x; i > 0; i--) {
            if (isWall(tempworld[i][p.y])) {
                tempworld[i][p.y] = Tileset.LOCKED_DOOR;
                return true;
            }
        }
        return false;
    }

    public boolean detectRowsWall(TETile[][] tempworld, Position p) {
        boolean temp = false;
        for (int i = p.x; i < Game.WIDTH; i++) {
            if (isWall(tempworld[i][p.y])) {
                tempworld[i][p.y] = Tileset.LOCKED_DOOR;
                return true;
            }
        }
        temp = detechRowsWallBack(tempworld, p);
        if (temp) {
            return temp;
        }
        return temp;
    }

    public boolean detechColumnWallBack(TETile[][] tempworld, Position p) {
        for (int i = p.y; i > 0; i--) {
            if (isWall(tempworld[p.x][i])) {
                tempworld[p.x][i] = Tileset.LOCKED_DOOR;
                return true;
            }
        }
        return false;
    }

    public boolean detectColumnsWall(TETile[][] tempworld, Position p) {
        boolean temp = false;
        for (int i = p.y; i < Game.HEIGHT; i++) {
            if (isWall(tempworld[p.x][i])) {
                tempworld[p.x][i] = Tileset.LOCKED_DOOR;
                return true;
            }
        }
        temp = detechColumnWallBack(tempworld, p);
        if (temp) {
            return temp;
        }
        return temp;
    }

    public boolean isWall(TETile t) {
        return t == Tileset.WALL;
    }

    public void generateRandomDoor(TETile[][] temp) {
        boolean aka;
        Position p = new Position(getRandomNumberUsingNextInt(0, Game.WIDTH),
                getRandomNumberUsingNextInt(0, Game.HEIGHT));
        aka = detectRowsWall(temp, p);
        if (!aka) {
            detectColumnsWall(temp, p);
        }
    }

    public boolean detectColumnsPlayer(TETile[][] tempworld, Position p) {
        boolean temp = false;
        for (int i = p.y + 1; i < Game.HEIGHT; i++) {
            if (isGrass(tempworld[p.x][i])) {
                tempworld[p.x][i] = Tileset.PLAYER;
                playerPosition = new Position(p.x, i);
                return true;
            }
        }
        temp = detechColumnPlayerBack(tempworld, p);
        if (temp) {
            return temp;
        }
        return temp;
    }

    public boolean detechColumnPlayerBack(TETile[][] tempworld, Position p) {
        for (int i = p.y; i > 0; i--) {
            if (isGrass(tempworld[p.x][i])) {
                tempworld[p.x][i] = Tileset.PLAYER;
                playerPosition = new Position(p.x, i);
                return true;
            }
        }
        return false;
    }

    public boolean detechRowsPlayerBack(TETile[][] tempworld, Position p) {
        for (int i = p.x; i > 0; i--) {
            if (isGrass(tempworld[i][p.y])) {
                tempworld[i][p.y] = Tileset.PLAYER;
                playerPosition = new Position(i, p.y);
                return true;
            }
        }
        return false;
    }

    public boolean detectRowsPlayer(TETile[][] tempworld, Position p) {
        boolean temp = false;
        for (int i = p.x; i < Game.WIDTH; i++) {
            if (isGrass(tempworld[i][p.y])) {
                tempworld[i][p.y] = Tileset.PLAYER;
                playerPosition = new Position(i, p.y);
                return true;
            }
        }
        temp = detechRowsPlayerBack(tempworld, p);
        if (temp) {
            return temp;
        }
        return temp;
    }

    public void generatePlayerEntity(TETile[][] temp) {
        Position p = new Position(getRandomNumberUsingNextInt(0, Game.WIDTH),
                getRandomNumberUsingNextInt(0, Game.HEIGHT));
        if (!detectColumnsPlayer(temp, p)) {
            detectRowsPlayer(temp, p);
        }
    }

    public boolean isLockeddoor(TETile t) {
        return t == Tileset.LOCKED_DOOR;
    }

    public TETile[][] playthegame(TETile[][] temp) {
        generateMultipleRooms(temp, Tileset.GRASS);
        if (isolation[0] != null) {
            doubleCheckHighway(temp, isolation);
        }
        addFloor(temp);
        generateRandomDoor(temp);
        generatePlayerEntity(temp);
        return temp;
    }
}
