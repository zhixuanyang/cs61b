import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private int width;
    private int height;
    Picture picture;

    public SeamCarver(Picture picture) {
        width = picture.width();
        height = picture.height();
        this.picture = picture;
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {

    }

    public int[] findHorizontalSeam() {

    }

    public int[] findVerticalSeam() {

    }

    public void removeHorizontalSeam(int[] seam) {

    }

    public void removeVerticalSeam(int[] seam) {

    }

}
