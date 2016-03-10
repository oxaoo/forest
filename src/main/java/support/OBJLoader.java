package support;

import engine.Loader;
import model.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class OBJLoader {

    private static String nameNextModel = "";
    private static List<Vector3f> vertices = new ArrayList<Vector3f>();
    private static List<Vector2f> textures = new ArrayList<Vector2f>();
    private static List<Vector3f> normals = new ArrayList<Vector3f>();
//    private static List<Integer> indices = new ArrayList<Integer>();

    private static BufferedReader br = null;

    public static List<RawModel> loadObjModels(String filename, Loader loader) throws IOException {
        List<RawModel> rawModels = new ArrayList<RawModel>(20);
        FileReader fileReader = null;
        fileReader = new FileReader(new File("src/main/resources/model/" + filename + ".obj"));
        br = new BufferedReader(fileReader);
        reachToObj(br);
        //while (br.ready()){
        while (br != null){
            rawModels.add(loadObjModel2(loader));
        }

        System.out.println("Count of raw models: " + rawModels.size());
        return rawModels;
    }

    private static void reachToObj(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (line != null && !line.startsWith("o"))
            line = br.readLine();
        if (line != null) nameNextModel = line.split(" ")[1];
    }


    public static RawModel loadObjModel2(Loader loader) throws IOException {

//        List<Vector3f> vertices = new ArrayList<Vector3f>();
//        List<Vector2f> textures = new ArrayList<Vector2f>();
//        List<Vector3f> normals = new ArrayList<Vector3f>();
//        List<Integer> indices = new ArrayList<Integer>();

//        List<Vector3f> vertices = new ArrayList<Vector3f>();
//        List<Vector2f> textures = new ArrayList<Vector2f>();
//        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Integer> indices = new ArrayList<Integer>();

        String nameModel = nameNextModel;

        List<String> faces = new ArrayList<String>();

        float[] verticesArr = null;
        float[] normalsArr = null;
        float[] textureArr = null;
        int[] indicesArr = null;

        String line = br.readLine();
        String[] lineArgs;
        do {
            lineArgs = line.split(" ");
            if (lineArgs[0].equals("v"))
                vertices.add(new Vector3f(
                        Float.parseFloat(lineArgs[1]),
                        Float.parseFloat(lineArgs[2]),
                        Float.parseFloat(lineArgs[3])));
            else if (lineArgs[0].equals("vt"))
                textures.add(new Vector2f(
                        Float.parseFloat(lineArgs[1]),
                        Float.parseFloat(lineArgs[2])));
            else if (lineArgs[0].equals("vn"))
                normals.add(new Vector3f(
                        Float.parseFloat(lineArgs[1]),
                        Float.parseFloat(lineArgs[2]),
                        Float.parseFloat(lineArgs[3])));
            else if (lineArgs[0].equals("f"))
                faces.add(line);
            else if (lineArgs[0].equals("o")) {
                nameNextModel = lineArgs[1];
                break;
            }

            line = br.readLine();
        } while (line != null);

        if (line == null) {
            br.close();
            br = null;
        }

        textureArr = new float[vertices.size() * 2];
        normalsArr = new float[vertices.size() * 3];


        for (String face : faces) {
            lineArgs = face.split(" ");

            String[] vertex1 = lineArgs[1].split("/");
            String[] vertex2 = lineArgs[2].split("/");
            String[] vertex3 = lineArgs[3].split("/");

            processVertex(vertex1, indices, textures, normals, textureArr, normalsArr);
            processVertex(vertex2, indices, textures, normals, textureArr, normalsArr);
            processVertex(vertex3, indices, textures, normals, textureArr, normalsArr);

        }

        //br.close();

        verticesArr = new float[vertices.size() * 3];
        indicesArr = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArr[vertexPointer++] = vertex.x;
            verticesArr[vertexPointer++] = vertex.y;
            verticesArr[vertexPointer++] = vertex.z;
        }

        for (int i = 0; i < indices.size(); i++)
            indicesArr[i] = indices.get(i);

        return loader.loadToVAO(verticesArr, textureArr, normalsArr, indicesArr, nameModel);
    }

    /*public static RawModel loadObjModel(String filename, Loader loader) throws IOException {
        FileReader fileReader = null;
        fileReader = new FileReader(new File("src/main/resources/model/" + filename + ".obj"));
        BufferedReader br = new BufferedReader(fileReader);
        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Vector2f> textures = new ArrayList<Vector2f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Integer> indices = new ArrayList<Integer>();
        List<String> faces = new ArrayList<String>();

        float[] verticesArr = null;
        float[] normalsArr = null;
        float[] textureArr = null;
        int[] indicesArr = null;

        int models = 0;
        String line = br.readLine();
        String[] lineArgs;
        do {
            lineArgs = line.split(" ");
            if (lineArgs[0].equals("v"))
                vertices.add(new Vector3f(
                        Float.parseFloat(lineArgs[1]),
                        Float.parseFloat(lineArgs[2]),
                        Float.parseFloat(lineArgs[3])));
            else if (lineArgs[0].equals("vt"))
                textures.add(new Vector2f(
                        Float.parseFloat(lineArgs[1]),
                        Float.parseFloat(lineArgs[2])));
            else if (lineArgs[0].equals("vn"))
                normals.add(new Vector3f(
                        Float.parseFloat(lineArgs[1]),
                        Float.parseFloat(lineArgs[2]),
                        Float.parseFloat(lineArgs[3])));
            else if (lineArgs[0].equals("f"))
                faces.add(line);
            else if (lineArgs[0].equals("o"))
                models++;

            line = br.readLine();
        } while (line != null && models < 2);

        textureArr = new float[vertices.size() * 2];
        normalsArr = new float[vertices.size() * 3];


        for (String face : faces) {
            lineArgs = face.split(" ");

            String[] vertex1 = lineArgs[1].split("/");
            String[] vertex2 = lineArgs[2].split("/");
            String[] vertex3 = lineArgs[3].split("/");

            processVertex(vertex1, indices, textures, normals, textureArr, normalsArr);
            processVertex(vertex2, indices, textures, normals, textureArr, normalsArr);
            processVertex(vertex3, indices, textures, normals, textureArr, normalsArr);

        }

        br.close();

        verticesArr = new float[vertices.size() * 3];
        indicesArr = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArr[vertexPointer++] = vertex.x;
            verticesArr[vertexPointer++] = vertex.y;
            verticesArr[vertexPointer++] = vertex.z;
        }

        for (int i = 0; i < indices.size(); i++)
            indicesArr[i] = indices.get(i);

        return loader.loadToVAO(verticesArr, textureArr, indicesArr);
    }*/

    private static void processVertex(String[] vertexData,
                                      List<Integer> indices,
                                      List<Vector2f> textures,
                                      List<Vector3f> normals,
                                      float[] texturesArray, float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);

        Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
        texturesArray[currentVertexPointer * 2] = currentTexture.x;
        texturesArray[currentVertexPointer * 2 + 1] = 1 - currentTexture.y;
        Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNormal.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNormal.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNormal.z;
    }
}

