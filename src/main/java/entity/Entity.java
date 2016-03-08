package entity;

import model.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class Entity {

    //private TexturedModel model;
    private List<TexturedModel> models;
    private Vector3f position;
    private float rotX,rotY,rotZ;
    private float scale;
    private int sprite = 0;
    private float ratio = (float) Display.getWidth() / (float) Display.getHeight();
    private float wConst = 2.0F;
    private float vConst = wConst / ratio - .32F;

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }

    public Entity(List<TexturedModel> models, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.models = models;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public void move(){
        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
            this.rotZ-= 0.05f;
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
            this.rotZ += 0.05f;
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
            this.rotX += 0.05f;
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
            this.rotX -= 0.05f;
    }

    public List<TexturedModel> getModels() {
        return models;
    }

    public void setModels(List<TexturedModel> model) {
        this.models = models;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setX(float x) {
        this.position.x = x;
    }
    public void setY(float y) {
        this.position.y = y;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getwConst() {
        return wConst;
    }

    public float getRatio() {
        return ratio;
    }

    public float getvConst() {
        return vConst;
    }
}

