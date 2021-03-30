package graphics.gui;

import graphics.color.Color;

public class Button {

    private final float x;
    private final float y;

    private final float lenX;
    private final float lenY;

    private final String text;

    private final int textSize;
    private final String font;

    private final Command command;
    private final Color color;

    public Button(final float x, final float y,
                  final float lenX, final float lenY,
                  final String text, final Color color,
                  final int textSize, final String font,
                  final Command command) {
        this.x = x;
        this.y = y;
        this.lenX = lenX;
        this.lenY = lenY;
        this.text = text;
        this.color = color;
        this.command = command;
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

    public Color getColor() {
        return color;
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

    public void click(final float mouseX, final float mouseY) {
        if(mouseX >= x && mouseX <= x + lenX && mouseY >= y && mouseY <= y + lenY) {
            this.command.execute();
        }
    }
}
