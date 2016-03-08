package model;

public class RawModel {
    private final String name;
    private int vaoID;
    private int vertexCount;

    public RawModel(int vaoID, int vertexCount, String name) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.name = name;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public String getName() {
        return name;
    }
}
