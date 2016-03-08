import engine.DisplayManager;
import engine.Loader;
import engine.Renderer;
import entity.Camera;
import entity.Entity;
import entity.Light;
import model.ModelTexture;
import model.RawModel;
import model.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import shader.StaticShader;
import support.OBJLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        DisplayManager.createDisplay();
        Loader loader = new Loader();

        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        List<RawModel> rawModels = new ArrayList<RawModel>();
        try {
            rawModels = OBJLoader.loadObjModels("forest", loader);
        } catch (IOException e) {
            System.err.println("Error while load object model: [" + e.toString() + "]");
            shader.cleanUp();
            loader.cleanUp();
            DisplayManager.closeDisplay();
            return;
        }

        //TexturedModel tmodel = new TexturedModel(model, new ModelTexture(loader.loadTexture("bark")));
        List<TexturedModel> texturedModels = loadTextures(rawModels, loader);

//        Entity entity = new Entity(tmodel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        Entity entity = new Entity(texturedModels, new Vector3f(0, -10, -50), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(-10, 10, -50), new Vector3f(1, 1, 1));

        Camera camera = new Camera();

        while(!Display.isCloseRequested()) {
            entity.increaseRotation(0, 0.25f, 0);
            entity.move();
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadLigth(light);
            shader.loadViewMatrix(camera);

//            renderer.render(entity, shader);

            for (TexturedModel model : entity.getModels())
                renderer.render(entity, model, shader);

            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

    /*private static List<TexturedModel> loadTextures(List<RawModel> rawModels, Loader loader){

        ModelTexture treeTexture = new ModelTexture(loader.loadTexture("bark1"));
        ModelTexture leafTexture = new ModelTexture(loader.loadTexture("leaf1"));
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grass1"));

        ModelTexture modelTexture = grassTexture;

        List<TexturedModel> texturedModels = new ArrayList<TexturedModel>();
        for (RawModel rawModel : rawModels){
            if (rawModel.getName().startsWith("tree")){
                modelTexture = treeTexture;
            } else if (rawModel.getName().startsWith("leaves")) {
                modelTexture = leafTexture;
            }

            TexturedModel model = new TexturedModel(rawModel, modelTexture);
            texturedModels.add(model);
        }

        return texturedModels;
    }*/

    private static List<TexturedModel> loadTextures(List<RawModel> rawModels, Loader loader){
        List<TexturedModel> texturedModels = new ArrayList<TexturedModel>();
        for (RawModel rawModel : rawModels){
            String texture;
            if (rawModel.getName().startsWith("tree")){
                texture = "bark";
            } else if (rawModel.getName().startsWith("leaves")) {
//                texture = "leaf";
                    texture = "leaf1";
            } else { //plan (and envelop?!).
                texture = "grass";
            }



            ModelTexture mt = new ModelTexture(loader.loadTexture(texture));

            if (!texture.equals("leaf1")) {
                mt.setShineDamper(5);
                mt.setReflectivity(0.1f);
            }

            TexturedModel model = new TexturedModel(rawModel, mt);


            /*ModelTexture mt = model.getTexture();
            mt.setShineDamper(10);
            mt.setReflectivity(1);*/

            texturedModels.add(model);
        }

        return texturedModels;
    }
}
