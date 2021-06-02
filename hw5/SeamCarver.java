import edu.princeton.cs.algs4.Picture;
import java.util.Stack;
import java.awt.Color;

public class SeamCarver {
    private int width;
    private int height;
    private Picture picture;
    private boolean horizontal = false;

    public SeamCarver(Picture picture) {
        width = picture.width();
        height = picture.height();
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    private double calculateRGB(Color temp1, Color temp2) {
        double temp;
        temp = (temp1.getRed() - temp2.getRed()) * (temp1.getRed() - temp2.getRed());
        temp += (temp1.getBlue() - temp2.getBlue()) * (temp1.getBlue() - temp2.getBlue());
        temp += (temp1.getGreen() - temp2.getGreen()) * (temp1.getGreen() - temp2.getGreen());
        return temp;
    }
    public double energy(int x, int y) {
        if (x >= width() || x < 0 || y >= height() || y < 0) {
            throw new IndexOutOfBoundsException();
        }
        Color temp1;
        Color temp2;
        double column;
        double row;
        if (width() == 1 & height() == 1) {
            temp1 = picture.get(x, y);
            temp2 = picture.get(x, y);
            column = calculateRGB(temp1, temp2);
            row = calculateRGB(temp1, temp2);
        } else if (width() == 1) {
            temp1 = picture.get(x, y);
            temp2 = picture.get(x, y);
            column = calculateRGB(temp1, temp2);
            row = getRow(x, y);
        } else if (height() == 1) {
            temp1 = picture.get(x, y);
            temp2 = picture.get(x, y);
            row = calculateRGB(temp1, temp2);
            if (!horizontal) {
                if (x == 0) {
                    temp1 = picture.get(x + 1, y);
                    temp2 = picture.get(width() - 1, y);
                    column = calculateRGB(temp1, temp2);
                } else if (x == width() - 1) {
                    temp1 = picture.get(0, y);
                    temp2 = picture.get(x - 1, y);
                    column = calculateRGB(temp1, temp2);
                } else {
                    temp1 = picture.get(x + 1, y);
                    temp2 = picture.get(x - 1, y);
                    column = calculateRGB(temp1, temp2);
                }
            } else {
                if (x == 0) {
                    temp1 = picture.get(y, x + 1);
                    temp2 = picture.get(y, width() - 1);
                    column = calculateRGB(temp1, temp2);
                } else if (x == width() - 1) {
                    temp1 = picture.get(y, 0);
                    temp2 = picture.get(y, x - 1);
                    column = calculateRGB(temp1, temp2);
                } else {
                    temp1 = picture.get(y, x + 1);
                    temp2 = picture.get(y, x - 1);
                    column = calculateRGB(temp1, temp2);
                }
            }
        } else {
            column = getColumn(x, y);
            row = getRow(x, y);
        }
        return column + row;
    }

    private double getRow(int x, int y) {
        Color temp1;
        Color temp2;
        double row;
        if (!horizontal) {
            if (y == 0) {
                temp1 = picture.get(x, y + 1);
                temp2 = picture.get(x, height() - 1);
                row = calculateRGB(temp1, temp2);
            } else if (y == height() - 1) {
                temp1 = picture.get(x, 0);
                temp2 = picture.get(x, y - 1);
                row = calculateRGB(temp1, temp2);
            } else {
                temp1 = picture.get(x, y + 1);
                temp2 = picture.get(x, y - 1);
                row = calculateRGB(temp1, temp2);
            }
        } else {
            if (y == 0) {
                temp1 = picture.get(y + 1, x);
                temp2 = picture.get(height() - 1, x);
                row = calculateRGB(temp1, temp2);
            } else if (y == height() - 1) {
                temp1 = picture.get(0, x);
                temp2 = picture.get(y - 1, x);
                row = calculateRGB(temp1, temp2);
            } else {
                temp1 = picture.get(y + 1, x);
                temp2 = picture.get(y - 1, x);
                row = calculateRGB(temp1, temp2);
            }
        }
        return row;
    }

    private double getColumn(int x, int y) {
        Color temp1;
        Color temp2;
        double column;
        if (!horizontal) {
            if (x == 0) {
                temp1 = picture.get(x + 1, y);
                temp2 = picture.get(width() - 1, y);
                column = calculateRGB(temp1, temp2);
            } else if (x == width() - 1) {
                temp1 = picture.get(0, y);
                temp2 = picture.get(x - 1, y);
                column = calculateRGB(temp1, temp2);
            } else {
                temp1 = picture.get(x + 1, y);
                temp2 = picture.get(x - 1, y);
                column = calculateRGB(temp1, temp2);
            }
        } else {
            if (x == 0) {
                temp1 = picture.get(y, x + 1);
                temp2 = picture.get(y, width() - 1);
                column = calculateRGB(temp1, temp2);
            } else if (x == width() - 1) {
                temp1 = picture.get(y, 0);
                temp2 = picture.get(y, x - 1);
                column = calculateRGB(temp1, temp2);
            } else {
                temp1 = picture.get(y, x + 1);
                temp2 = picture.get(y, x - 1);
                column = calculateRGB(temp1, temp2);
            }
        }
        return column;
    }
    public int[] findVerticalSeam() {
        double [][] temp = new double[width()][height()];
        Stack<Integer> store = new Stack<>();
        for (int i = 0; i < width(); i++) {
            temp[i][0] = energy(i, 0);
        }
        if (width() == 1 & height() == 1) {
            temp = temp;
        } else if (width() == 1) {
            for (int j = 1; j < height(); j++) {
                temp[0][j] = energy(0, j) + temp[0][j - 1];
            }
        } else if (height() == 1) {
            temp = temp;
        } else {
            for (int j = 1; j < height(); j++) {
                for (int i = 0; i < width(); i++) {
                    if (i == 0) {
                        temp[i][j] = energy(i, j) + Math.min(temp[i][j - 1], temp[i + 1][j - 1]);
                    } else if (i == width() - 1) {
                        temp[i][j] = energy(i, j) + Math.min(temp[i][j - 1], temp[i - 1][j - 1]);
                    } else {
                        temp[i][j] = energy(i, j) + Math.min(Math.min(temp[i - 1][j - 1],
                                temp[i][j - 1]),
                                temp[i + 1][j - 1]);
                    }
                }
            }
        }
        double min = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < width(); i++) {
            if (temp[i][height() - 1] < min) {
                min = temp[i][height() - 1];
                index = i;
            }
        }
        store.push(index);
        for (int j = height() - 2; j > -1; j--) {
            min = temp[index][j];
            int tempindex = index;
            if (index == 0) {
                if (temp[index + 1][j] < min) {
                    tempindex = index + 1;
                }
            } else if (index == width() - 1) {
                if (temp[index - 1][j] < min) {
                    tempindex = index - 1;
                }
            } else {
                if (temp[index - 1][j] < min) {
                    min = temp[index - 1][j];
                    tempindex = index - 1;
                }
                if (temp[index + 1][j] < min) {
                    tempindex = index + 1;
                }
            }
            index = tempindex;
            store.push(index);
        }
        int[] result = new int[store.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = store.pop();
        }
        return result;
    }


    private void swap() {
        int temp = width;
        width = height;
        height = temp;
    }

    public int[] findHorizontalSeam() {
        swap();
        horizontal = true;
        int[] result = findVerticalSeam();
        swap();
        horizontal = false;
        return result;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width() || !isValidSeam(seam)) {
            throw new IllegalArgumentException();
        }
        SeamRemover.removeHorizontalSeam(this.picture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height() || !isValidSeam(seam)) {
            throw new IllegalArgumentException();
        }
        SeamRemover.removeVerticalSeam(this.picture, seam);
    }

    private boolean isValidSeam(int[] seam) {
        for (int i = 0, j = 1; j < seam.length; i++, j++) {
            if (Math.abs(seam[i] - seam[j]) > 1) {
                return false;
            }
        }
        return true;
    }
}
