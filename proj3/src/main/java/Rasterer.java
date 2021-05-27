import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private int sl;
    private double d0;
    private double d1;
    private double d2;
    private double d3;
    private double d4;
    private double d5;
    private double d6;
    private double d7;
    private double rasterullon;
    private double rasterullat;
    private double rasterlrlon;
    private double rasterlrlat;
    private String[][] rendergrid;
    private int depth;
    private double totalLon;
    private double totalLat;
    private Map<Integer, Double> unitLon;
    private Map<Integer, Double> unitLat;

    Map<Integer, Double> degreeList;
    public Rasterer() {
        // YOUR CODE HERE
        sl = 288200;
        d0 = 98.94561767578125;
        d1 = 49.472808837890625;
        d2 = 24.736404418945312;
        d3 = 12.368202209472656;
        d4 = 6.184101104736328;
        d5 = 3.092050552368164;
        d6 = 1.546025276184082;
        d7 = 0.773012638092041;
        totalLon = Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON);
        totalLat = Math.abs(MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT);
        unitLon = new HashMap<>();
        unitLat = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            unitLon.put(i, totalLon / Math.pow(2.0, (double) i));
            unitLat.put(i, totalLat / Math.pow(2.0, (double) i));
        }
    }

    public double getrasterullon() {
        return rasterullon;
    }

    public double getrasterullat() {
        return rasterullat;
    }

    public double getrasterlrlon() {
        return rasterlrlon;
    }

    public double getrasterlrlat() {
        return rasterlrlat;
    }

    public int getDepth(double ullon, double lrlon, double w) {
        double xDist = Math.abs(ullon - lrlon);
        double ftperPixel = xDist * sl / w;
        if (ftperPixel < d6 & ftperPixel > d7) {
            return 7;
        } else if (ftperPixel < d5 & ftperPixel > d6) {
            return 6;
        } else if (ftperPixel < d4 & ftperPixel > d5) {
            return 5;
        } else if (ftperPixel < d3 & ftperPixel > d4) {
            return 4;
        } else if (ftperPixel < d2 & ftperPixel > d3) {
            return 3;
        } else if (ftperPixel < d1 & ftperPixel > d2) {
            return 2;
        } else if (ftperPixel < d0 & ftperPixel > d1) {
            return 1;
        } else if (ftperPixel < d7) {
            return 7;
        }
        return 0;
    }

    public String[][] getFiles(double lrlon, double ullon, double ullat, double lrlat) {
        double singleLon = unitLon.get(depth);
        double singleLat = unitLat.get(depth);
        double currLon = MapServer.ROOT_ULLON;
        double currLat = MapServer.ROOT_ULLAT;
        int tempX = 0;
        int tempY = 0;
        int endX = 0;
        int endY = 0;

        while (currLon < ullon) {
            if (currLon + singleLon > ullon) {
                break;
            }
            tempX += 1;
            currLon += singleLon;
        }
        while (currLat > ullat) {
            if (currLat - singleLat < ullat) {
                break;
            }
            tempY += 1;
            currLat -= singleLat;
        }
        double startLon = MapServer.ROOT_ULLON + ((double) tempX) * singleLon;
        double startLat = MapServer.ROOT_ULLAT - ((double) tempY) * singleLat;
        rasterullon = startLon;
        rasterullat = startLat;

        while (startLon < lrlon) {
            endX += 1;
            startLon += singleLon;
        }
        while (startLat > lrlat) {
            endY += 1;
            startLat -= singleLat;
        }
        if (endX > Math.pow(2.0, (double) depth)) {
            endX = (int) Math.pow(2.0, (double) depth);
        }
        if (endY > Math.pow(2.0, (double) depth)) {
            endY = (int) Math.pow(2.0, (double) depth);
        }
        rasterlrlon = rasterullon + ((double) endX) * singleLon;
        rasterlrlat = rasterullat - ((double) endY) * singleLat;
        rendergrid = new String[endY][endX];
        for (int i = 0; i < endY; i++) {
            for (int j = 0; j < endX; j++) {
                rendergrid[i][j] = "d" + depth + "_x" + (tempX + j) + "_y" + (tempY + i) + ".png";
            }
        }
        return rendergrid;
    }

    public boolean getquerysuccess(double ullon, double ullat) {
        if (ullon > MapServer.ROOT_LRLON || ullat < MapServer.ROOT_LRLAT) {
            return false;
        }
        return true;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "rendergrid"   : String[][], the files to display. <br>
     * "rasterullon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "rasterullat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "rasterlrlon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "rasterlrlat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "querysuccess" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        depth = getDepth(params.get("ullon"), params.get("lrlon"), params.get("w"));
        results.put("depth", depth);
        String[][] files = getFiles(params.get("lrlon"), params.get("ullon"),
                params.get("ullat"), params.get("lrlat"));
        results.put("render_grid", files);
        results.put("raster_ul_lon", getrasterullon());
        results.put("raster_ul_lat", getrasterullat());
        results.put("raster_lr_lon", getrasterlrlon());
        results.put("raster_lr_lat", getrasterlrlat());
        results.put("query_success", getquerysuccess(params.get("ullon"), params.get("ullat")));
        return results;
    }
}
