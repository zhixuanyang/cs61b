import java.util.ArrayList;
import java.util.List;
public class Node {

    private long id;
    private double lon;
    private double lat;
    private String name;
    private List<Long> adj;


    void setName(String name) {
        this.name = name;
    }

    void connectTo(long vertexId) {
        this.adj.add(vertexId);
    }

    public Node(long id, double lon, double lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.adj = new ArrayList<>();
    }

    public long id() {
        return id;
    }

    public double lon() {
        return lon;
    }

    public double lat() {
        return lat;
    }

    public String name() {
        return name;
    }

    public List<Long> adj() {
        return adj;
    }

    @Override
    public int hashCode() {
        return (int) this.id();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Node other = (Node) o;
        return this.id == other.id;
    }
    @Override
    public String toString() {
        return String.format("Node id: %d, lat: %.10f, lon: %.10f", id, lat, lon);
    }
}

