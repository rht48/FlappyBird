package graphics;

import processing.core.PImage;

public class TexturedModel {

    private PImage image;

    public TexturedModel(final PImage image) {
        this.image = image;
    }

    public PImage getImage() {
        return this.image;
    }

    public void setImage(final PImage image) {
        this.image = image;
    }

}
