package model.entity;

import graphics.TexturedModel;
import processing.core.PImage;
import processing.core.PVector;

public class Entity {

    private TexturedModel model;
    protected PVector position;
    protected float rotX;
    protected float rotY;
    protected float rotZ;
    protected float scaleX;
    protected float scaleY;
    protected float scaleZ;
    protected final float height;
    protected final float width;

    public Entity(final TexturedModel model, final PVector position,
                  final float rotX, final float rotY, final float rotZ,
                  final float scaleX, final float scaleY, final float scaleZ) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;

        final PImage image = model.getImage();
        final int imageWidth = image.width;
        final int imageHeight = image.height;
        height = (float) imageHeight * (float) scaleX;
        width = (float) imageWidth * (float) scaleY;

    }

    public void increasePosition(final float dx, final float dy, final float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(final float dx, final float dy, final float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(final TexturedModel model) {
        this.model = model;
    }

    public PVector getPosition() {
        return position;
    }

    public void setPosition(final PVector position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(final float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(final float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(final float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(final float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(final float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public void setScaleZ(final float scaleZ) {
        this.scaleZ = scaleZ;
    }
}
