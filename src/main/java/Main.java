import engine.DisplayManager;
import engine.Loader;
import engine.Renderer;
import entity.Camera;
import entity.Entity;
import model.ModelTexture;
import model.RawModel;
import model.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import shader.StaticShader;
import support.OBJLoader;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        DisplayManager.createDisplay();
        Loader loader = new Loader();

        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        RawModel model = null;
        try {
            model = OBJLoader.loadObjModel("forest", loader);
        } catch (IOException e) {
            System.err.println("Error while load object model: [" + e.toString() + "]");
            shader.cleanUp();
            loader.cleanUp();
            DisplayManager.closeDisplay();
            return;
        }

        TexturedModel tmodel = new TexturedModel(model, new ModelTexture(loader.loadTexture("grass")));

        Entity entity = new Entity(tmodel, new Vector3f(0, 0, -50), 0, 0, 0, 1);

        Camera camera = new Camera();

        while(!Display.isCloseRequested()) {
            entity.increaseRotation(0, 0.1f, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }
}
