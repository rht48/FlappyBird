package graphics.gui;


public abstract class Gui {
    protected final float x;
    protected final float y;

    protected final float lenX;
    protected final float lenY;

    protected final String text;

    protected final int textSize;
    protected final String font;

    public Gui(float x, float y, float lenX, float lenY, String text, int textSize, String font) {
        this.x = x;
        this.y = y;
        this.lenX = lenX;
        this.lenY = lenY;
        this.text = text;
        this.textSize = textSize;
        this.font = font;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLenX() {
        return lenX;
    }

    public float getLenY() {
        return lenY;
    }

    public String getText() {
        return text;
    }

    public int getTextSize() {
        return textSize;
    }

    public String getFont() {
        return font;
    }
}
