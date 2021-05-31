import java.util.List;
public class Edge<Node> {
    private long id;
    private String name;
    private List<Long> vertexs;

    Edge(long id, List<Long> vertexs) {
        this.id = id;
        this.vertexs = vertexs;
    }
    void setName(String name) {
        this.name = name;
    }

    public long id() {
        return id;
    }

    public List<Long> vertexs() {
        return vertexs;
    }

    public String name() {
        return name;
    }
}
